package com.example.sl_trip_planner;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sl_trip_planner.data.JSONparser;
import com.example.sl_trip_planner.data.JourneyList;
import com.example.sl_trip_planner.data.JourneyModel;
import com.example.sl_trip_planner.data.StopList;
import com.example.sl_trip_planner.data.StopModel;

import java.util.List;

public class ActivityStopSearch extends AppCompatActivity {

    String LOG_TAG = ActivityStopSearch.class.getSimpleName();

    // Volley & data
    private RequestQueue mRequestQueue;
    private List<StopModel> stopList;
    private final Handler timerHandler = new Handler();
    private JSONparser parser;

    private String currentSearchText = "";
    private SearchView originSV;

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


            if (isConnected) {

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_search);

        /*------------ HOOKS --------------*/
        originSV = findViewById(R.id.origin_ET);

        /*-------- VOLLEY & DATA ---------*/
        mRequestQueue = Volley.newRequestQueue(this);
        parser = new JSONparser();
        stopList = StopList.getInstance();

        search();


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


    private void search() {
        originSV.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                currentSearchText = s;
                Log.i(LOG_TAG, s);
                if (s.length() > 7) {
                    postVolleyRequest(s);
                }
                return false;
            }
        });
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

                        Log.i(LOG_TAG, String.valueOf(stopList));
                        //fillRecyclerView(journeyList);
                        //Toast.makeText(getApplicationContext(), "Download completed", Toast.LENGTH_SHORT).show();

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
}
