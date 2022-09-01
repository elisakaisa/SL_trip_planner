package com.example.sl_trip_planner;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CalendarContract;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sl_trip_planner.apidata.JSONParser;
import com.example.sl_trip_planner.data.JourneyList;
import com.example.sl_trip_planner.models.JourneyModel;
import com.example.sl_trip_planner.data.Stops;
import com.example.sl_trip_planner.apidata.UrlSetter;
import com.example.sl_trip_planner.recyclerview.ExportButtonInterface;
import com.example.sl_trip_planner.recyclerview.JourneyAdapter;
import com.example.sl_trip_planner.recyclerview.JourneyRecycler;
import com.example.sl_trip_planner.recyclerview.RecyclerViewInterface;
import com.example.sl_trip_planner.utils.AlertDial;
import com.example.sl_trip_planner.utils.CalendarUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ActivityTripList extends AppCompatActivity implements RecyclerViewInterface, ExportButtonInterface {
    private static final String LOG_TAG = "ActivityTripList";

    // Volley & data
    private RequestQueue mRequestQueue;
    private List<JourneyModel> journeyList;

    private final Handler timerHandler = new Handler();
    private JSONParser parser;
    private String mUrl = "";

    /*-------- HOOKS -------*/
    private RecyclerView recyclerView;
    private TextView from_to_TV;

    /*-------- VAR -------*/
    private String scrB;
    private String scrF;
    private String date;
    private String date2;

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
                postVolleyRequest(mUrl);
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
        int originId = intent.getIntExtra(Stops.ORIGIN_ID, 0);
        int destinationId = intent.getIntExtra(Stops.DESTINATION_ID, 0);
        String time = intent.getStringExtra(Stops.TIME);
        date = intent.getStringExtra(Stops.DATE);
        int searchForArrival = intent.getIntExtra(Stops.SEARCHFORARRIVAL, 0);


        /*------- DATA ---------*/
        mUrl = UrlSetter.setTripUrl(originId, destinationId, time, date, searchForArrival);

        /*---------- HOOKS ------------*/
        recyclerView = findViewById(R.id.recyclerView);
        from_to_TV = findViewById(R.id.from_to);
        Button btn_previous = findViewById(R.id.btn_previous);
        Button btn_next = findViewById(R.id.btn_next);

        /*-------- VOLLEY & DATA ---------*/
        mRequestQueue = Volley.newRequestQueue(this);
        parser = new JSONParser();
        journeyList = JourneyList.getInstance();

        /*--------- LISTENERS ------------*/
        btn_previous.setOnClickListener(v -> postVolleyRequest(mUrl + "&Context=" + scrB));
        btn_next.setOnClickListener(v -> postVolleyRequest(mUrl + "&Context=" + scrF));
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

    public void postVolleyRequest(String url) {
        Log.i(LOG_TAG, "postVolleyRequest entered");
            // get data
            JsonObjectRequest tripRequest = new JsonObjectRequest(Request.Method.GET,
                    url,
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
                            String s = origin + " -> " + destination;
                            from_to_TV.setText(s);

                            // get context data for previous and following trips
                            scrB = parser.getScrBFromParser();
                            scrF = parser.getScrFFromParser();

                        } catch (Exception e) {
                            new AlertDial().createMsgDialog(ActivityTripList.this, "Parsing error", e.toString()).show();
                        }
                    },
                    errorListener);
        tripRequest.setTag(this);
        mRequestQueue.add(tripRequest);
    }

    private void fillRecyclerView(List<JourneyModel> journeyData) {
        ArrayList<JourneyList> itemList = new ArrayList<>();
        for (JourneyModel instantJourney : journeyData) {
            String departureTime = instantJourney.getDepartureTime();
            String arrivalTime = instantJourney.getArrivalTime();
            String rtDepartureTime = instantJourney.getRtDepartureTime();
            String rtArrivalTime = instantJourney.getRtArrivalTime();

            String combinedData = instantJourney.getCombinedData();
            String deltaT = instantJourney.getDeltaT();
            String rtCombinedData = instantJourney.getRtCombinedData();
            String rtDeltaT = instantJourney.getRtDeltaT();

            ArrayList<String> lineList = instantJourney.getLineList();
            ArrayList<String> destinationList = instantJourney.getDirectionList();
            ArrayList<String> timeList = instantJourney.getTimeList();
            ArrayList<String> rtTimeList = instantJourney.getRtTimeList();
            ArrayList<String> stopList = instantJourney.getStopList();
            itemList.add(new JourneyRecycler(
                    departureTime, arrivalTime, rtDepartureTime, rtArrivalTime,
                    combinedData, rtCombinedData,
                    deltaT, rtDeltaT,
                    lineList, destinationList,
                    timeList, rtTimeList, stopList));
        }
        RecyclerView.Adapter<JourneyAdapter.ViewHolder> adapter = new JourneyAdapter(itemList, this, this);
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

    @Override
    public void onItemClick(int position) {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onExportClick(boolean exported, String title, String description, String startTime, String endTime) {

        // make sure that there is a date, even if chosen date is today
        if (date.equals("date")) date = String.valueOf(LocalDate.now());
        Calendar beginTime = Calendar.getInstance();
        Calendar stopTime = Calendar.getInstance();

        beginTime.set(CalendarUtils.exportYear(date), CalendarUtils.exportMonth(date),
                CalendarUtils.exportDay(date), CalendarUtils.exportHours(startTime), CalendarUtils.exportMinutes(startTime));

        // account for dep arrival on diferent days
        if (CalendarUtils.depArrOnDifferentDays(startTime, endTime)) {
            stopTime.set(CalendarUtils.exportYear(date), CalendarUtils.exportMonth(date),
                    CalendarUtils.exportDayPlusOne(date), CalendarUtils.exportHours(endTime), CalendarUtils.exportMinutes(endTime));
        } else {
            stopTime.set(CalendarUtils.exportYear(date), CalendarUtils.exportMonth(date),
                    CalendarUtils.exportDay(date), CalendarUtils.exportHours(endTime), CalendarUtils.exportMinutes(endTime));
        }

        try {
            Intent intent = new Intent(Intent.ACTION_INSERT)
                    .setData(CalendarContract.Events.CONTENT_URI)
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, stopTime.getTimeInMillis())
                    .putExtra(CalendarContract.Events.TITLE, "trip") //TODO:
                    .putExtra(CalendarContract.Events.DESCRIPTION, "Group class"); //TODO: add WO description here
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "couldn't export event", Toast.LENGTH_SHORT).show();
        }
    }
}
