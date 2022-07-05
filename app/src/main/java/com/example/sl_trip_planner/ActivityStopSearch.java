package com.example.sl_trip_planner;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.SearchView;
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
import com.example.sl_trip_planner.data.StopList;
import com.example.sl_trip_planner.data.StopModel;
import com.example.sl_trip_planner.recyclerview.StopAdapter;
import com.example.sl_trip_planner.recyclerview.StopRecycler;
import com.example.sl_trip_planner.recyclerview.StopRecyclerViewInterface;

import java.util.ArrayList;
import java.util.List;

public class ActivityStopSearch extends AppCompatActivity implements StopRecyclerViewInterface {

    public static String ORIGIN_ID = "Origin ID";
    public static String DESTINATION_ID = "Destination ID";
    String LOG_TAG = ActivityStopSearch.class.getSimpleName();

    // Volley & data
    private RequestQueue mRequestQueue;
    private List<StopModel> stopList;
    private final Handler timerHandler = new Handler();
    private JSONparser parser;
    private ArrayList<String> stopArrayList = new ArrayList<>();

    private String currentSearchText = "";
    private SearchView originSV;
    private SearchView destinationSV;
    private RecyclerView stopsRV;
    private Button btn_go;

    private String originText;
    private int originId;
    private String destinationText;
    private int destinationId;
    boolean start = true;

    // runs in onStart
    private final Runnable timerRunnable = new Runnable() {
        //check network connection
        @Override
        public void run() {
            ConnectivityManager connectivityManager = (ConnectivityManager) getApplication()
                    .getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            Log.i(LOG_TAG, "isConnected? " + isConnected);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_search);

        /*------------ HOOKS --------------*/
        originSV = findViewById(R.id.origin_ET);
        destinationSV = findViewById(R.id.destination_ET);
        stopsRV = findViewById(R.id.list_view);
        btn_go = findViewById(R.id.btn_go);

        /*-------- VOLLEY & DATA ---------*/
        mRequestQueue = Volley.newRequestQueue(this);
        parser = new JSONparser();
        stopList = StopList.getInstance();

        search(originSV, true);
        search(destinationSV, false);

        /*-------- LISTENERS ----------*/
        //TODO: add check that both stations are selcetd
        btn_go.setOnClickListener(v -> searchJourneys(originId, destinationId));


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


    private void search(SearchView SV, boolean search) {
        SV.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) { return false; }

            @Override
            public boolean onQueryTextChange(String s) {
                start = search;
                currentSearchText = s;
                Log.i(LOG_TAG, s);
                if (s.length() > 7) {
                    postVolleyRequest(s);
                }
                return false;
            }
        });
    }

    public void fillRecyclerView(List<StopModel> stopData) {
        ArrayList<StopList> itemList = new ArrayList<>();
        for (StopModel instantStop : stopData) {
            String stop = instantStop.getStopName();
            itemList.add(new StopRecycler(stop));
        }
        RecyclerView.Adapter<StopAdapter.ViewHolder> adapter = new StopAdapter(itemList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        stopsRV.setLayoutManager(layoutManager);
        stopsRV.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    public void postVolleyRequest(String searchKeys) {
        Log.i(LOG_TAG, "postvolleyrequest entered");
        String mUrl = parser.setSearchUrl(searchKeys);
        // get data
        JsonObjectRequest stopRequest = new JsonObjectRequest(Request.Method.GET,
                mUrl,
                null,
                response1 -> {
                    try {
                        List<StopModel> newStops = parser.getStops(response1);
                        if (stopList != null) {
                            stopList.clear();
                            stopList.addAll(newStops);
                        } else stopList = newStops;

                        fillRecyclerView(stopList);

                    } catch (Exception e) {
                        Log.i("error whilst parsing", e.toString());
                        //createMsgDialog("Parsing error", "Corrupt data").show();
                    }
                },
                errorListener);
        stopRequest.setTag(this);
        mRequestQueue.add(stopRequest);
    }

    private final Response.ErrorListener errorListener = error -> {
        Log.i("Volley error", error.toString());
        //createMsgDialog("Network error", "Couldn't download the data").show();
    };

    @Override
    public void onItemClick(int position) {
        if (start) {
            originText = stopList.get(position).getStopName();
            originId = stopList.get(position).getStopId();
            Log.d(LOG_TAG, "stop selected: " + originText + " " + originId);
            // todo clear recycler view
            start = false;
            Toast.makeText(getApplicationContext(), originText + " chosen as start", Toast.LENGTH_SHORT).show();
        } else {
            destinationText = stopList.get(position).getStopName();
            destinationId = stopList.get(position).getStopId();
            Toast.makeText(getApplicationContext(), destinationText + " chosen as destination", Toast.LENGTH_SHORT).show();
        }

        Log.d(LOG_TAG, "origin: " + originText + " destinatiom" + destinationText);
    }

    public void searchJourneys(int origin, int destination) {
        // todo add input from date & time
        Intent intent = new Intent(this, ActivityTripList.class);
        intent.putExtra(ORIGIN_ID, origin);
        intent.putExtra(DESTINATION_ID, destination);
        startActivity(intent);
    }
}
