package com.example.sl_trip_planner;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sl_trip_planner.data.JSONparser;
import com.example.sl_trip_planner.data.JourneyList;
import com.example.sl_trip_planner.data.JourneyModel;
import com.example.sl_trip_planner.data.Stops;
import com.example.sl_trip_planner.recyclerview.JourneyAdapter;
import com.example.sl_trip_planner.recyclerview.JourneyRecycler;
import com.example.sl_trip_planner.utils.AlertDial;

import java.util.ArrayList;
import java.util.List;

public class ActivityTripList extends AppCompatActivity {
    private static final String LOG_TAG = "ActivityTripList";

    // Volley & data
    private RequestQueue mRequestQueue;
    private List<JourneyModel> journeyList;

    private final Handler timerHandler = new Handler();
    private JSONparser parser;

    /*-------- HOOKS -------*/
    private RecyclerView recyclerView;
    private TextView from_to_TV;

    /*------- DATA ---------*/
    public int originId, destinationId;
    public String time, date;

    // runs in onStart
    private final Runnable timerRunnable = new Runnable() {
        //check network connection
        @Override
        public void run() {
            from_to_TV.setText(R.string.loading_data);
            ConnectivityManager connectivityManager = (ConnectivityManager) getApplication()
                    .getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            // Networking
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            Log.i(LOG_TAG, "isConnected? " + isConnected);


            if (isConnected) {
                postVolleyRequest();
            } else {
                new AlertDial().createMsgDialog(ActivityTripList.this, "No internet connection", "Please turn on internet connection").show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triplist);

        /*-------- INTENT -----------*/
        Intent intent = getIntent();
        originId = intent.getIntExtra(Stops.ORIGIN_ID, 0);
        destinationId = intent.getIntExtra(Stops.DESTINATION_ID, 0);
        time = intent.getStringExtra(Stops.TIME);
        date = intent.getStringExtra(Stops.DATE);

        /*---------- HOOKS ------------*/
        recyclerView = findViewById(R.id.recyclerView);
        from_to_TV = findViewById(R.id.from_to);

        /*-------- VOLLEY & DATA ---------*/
        mRequestQueue = Volley.newRequestQueue(this);
        parser = new JSONparser();
        journeyList = JourneyList.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();
        timerHandler.postDelayed(timerRunnable, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
        mRequestQueue.cancelAll(this);
    }

    public void postVolleyRequest() {
        Log.i(LOG_TAG, "postvolleyrequest entered");
        String mUrl = parser.setTripUrl(originId, destinationId, time, date);
            // get data
            JsonObjectRequest tripRequest = new JsonObjectRequest(Request.Method.GET,
                    mUrl,
                    null,
                    response1 -> {
                        try {
                            List<JourneyModel> newJourney = parser.getJourney(response1);
                            if (journeyList != null) {
                                journeyList.clear();
                                journeyList.addAll(newJourney);
                            } else journeyList = newJourney;

                            fillRecyclerView(journeyList);

                            String origin = journeyList.get(0).getOrigin();
                            String destination = journeyList.get(0).getDestination();
                            from_to_TV.setText(origin + " -> " + destination);
                            Toast.makeText(getApplicationContext(), "Download completed", Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Log.i("error whilst parsing", e.toString());
                            //createMsgDialog("Parsing error", "Corrupt data").show();
                        }
                    },
                    errorListener);
        tripRequest.setTag(this);
        mRequestQueue.add(tripRequest);
    }

    private void fillRecyclerView(List<JourneyModel> journeyData) {
        ArrayList<JourneyList> itemList = new ArrayList<>();
        for (JourneyModel instantJourney : journeyData) {
            String origin = instantJourney.getOrigin();
            String destination = instantJourney.getDestination();
            String departureTime = instantJourney.getDepartureTime();
            String arrivalTime = instantJourney.getArrivalTime();
            ArrayList<String> transportList = instantJourney.getTransportList();
            ArrayList<String> stopList = instantJourney.getStopList();
            itemList.add(new JourneyRecycler(origin, destination, departureTime, arrivalTime, transportList, stopList));
        }
        RecyclerView.Adapter<JourneyAdapter.ViewHolder> adapter = new JourneyAdapter(itemList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Log.i(LOG_TAG, "Filled recycler view");
    }

    private final Response.ErrorListener errorListener = error -> {
        Log.i("Volley error", error.toString());
        new AlertDial().createMsgDialog(ActivityTripList.this, "Network error", "Couldn't download data").show();
    };
}
