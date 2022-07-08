package com.example.sl_trip_planner;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
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
import com.example.sl_trip_planner.data.StopList;
import com.example.sl_trip_planner.models.StopModel;
import com.example.sl_trip_planner.data.Stops;
import com.example.sl_trip_planner.apidata.UrlSetter;
import com.example.sl_trip_planner.recyclerview.StopAdapter;
import com.example.sl_trip_planner.recyclerview.StopRecycler;
import com.example.sl_trip_planner.recyclerview.RecyclerViewInterface;
import com.example.sl_trip_planner.utils.AlertDial;
import com.example.sl_trip_planner.utils.Helpers;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.List;

public class ActivityStopSearch extends AppCompatActivity implements RecyclerViewInterface {

    String LOG_TAG = ActivityStopSearch.class.getSimpleName();

    // Volley & data
    private RequestQueue mRequestQueue;
    private List<StopModel> stopList;
    private final Handler timerHandler = new Handler();
    private JSONParser parser;

    /* -------- HOOKS ---------*/
    private SearchView originSV;
    private SearchView destinationSV;
    private RecyclerView stopsRV;
    private TextView timeET, dateET, depArrTV;

    /*--------- VAR ----------*/
    private int originId = -1;
    private int destinationId = -1;
    boolean start = true;
    int searchForArrival = 0;

    //check network connection, runs in onStart
    private final Runnable timerRunnable = () -> {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplication()
                .getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        Log.i(LOG_TAG, "isConnected? " + isConnected);

        if (!isConnected) {
            new AlertDial().createMsgDialog(ActivityStopSearch.this, "No internet connection", "Please turn on internet connection").show();
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_search);

        /*------------ HOOKS --------------*/
        originSV = findViewById(R.id.origin_ET);
        destinationSV = findViewById(R.id.destination_ET);
        stopsRV = findViewById(R.id.list_view);
        Button btn_go = findViewById(R.id.btn_go);
        timeET = findViewById(R.id.time_ET);
        dateET = findViewById(R.id.date_ET);
        ImageButton tripTime = findViewById(R.id.img_btn_clock);
        ImageButton tripDate = findViewById(R.id.img_btn_calendar);
        SwitchMaterial depArrSwitch = findViewById(R.id.departure_arrival_switch);
        depArrTV = findViewById(R.id.departure_arrival_text);

        /*-------- VOLLEY & DATA ---------*/
        mRequestQueue = Volley.newRequestQueue(this);
        parser = new JSONParser();
        stopList = StopList.getInstance();

        /*--------- INIT -----------*/
        search(originSV, true);
        search(destinationSV, false);

        /*-------- LISTENERS ----------*/
        btn_go.setOnClickListener(v -> {
            String date = String.valueOf(dateET.getText());
            String time = String.valueOf(timeET.getText());
            searchJourneys(originId, destinationId, time, date);
        });
        tripTime.setOnClickListener(v -> timePickerDialog());
        tripDate.setOnClickListener(v -> datePickerDialog());
        depArrSwitch.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                searchForArrival = 1;
                depArrTV.setText(R.string.arrival);
            } else {
                searchForArrival = 0;
                depArrTV.setText(R.string.departure);
            }
        });
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
                if (s.length() > 7) postVolleyRequest(s);
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
        String mUrl = UrlSetter.setSearchUrl(searchKeys);
        // get data
        JsonObjectRequest stopRequest = new JsonObjectRequest(Request.Method.GET,
                mUrl,
                null,
                response1 -> {
                    try {
                        List<StopModel> newStops = parser.getStops(response1, getApplicationContext());
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
            String originText = stopList.get(position).getStopName();
            originId = stopList.get(position).getStopId();
            originSV.setQuery(originText, false);
            Toast.makeText(getApplicationContext(), originText + " chosen as start", Toast.LENGTH_SHORT).show();
        } else {
            String destinationText = stopList.get(position).getStopName();
            destinationId = stopList.get(position).getStopId();
            destinationSV.setQuery(destinationText, false);
            Toast.makeText(getApplicationContext(), destinationText + " chosen as destination", Toast.LENGTH_SHORT).show();
        }
    }

    public void searchJourneys(int origin, int destination, String time, String date) {
        if (originId == -1 || destinationId == -1 ) {
            // check for missing origin and/or destination
            new AlertDial().createMsgDialog(ActivityStopSearch.this, "Missing input", "Please fill in both origin and destination").show();
        } else {
            if ((time.equals("time") && (!date.equals("date")))) {
                new AlertDial().createMsgDialog(ActivityStopSearch.this, "Missing input", "Please provide a time").show();
            } else {
                Intent intent = new Intent(this, ActivityTripList.class);
                intent.putExtra(Stops.DATE, date);
                intent.putExtra(Stops.TIME, time);
                intent.putExtra(Stops.ORIGIN_ID, origin);
                intent.putExtra(Stops.DESTINATION_ID, destination);
                intent.putExtra(Stops.SEARCHFORARRIVAL, searchForArrival);
                startActivity(intent);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void timePickerDialog() {
        // opens time picker dialog
        Calendar mCurrentTime = Calendar.getInstance();
        int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mCurrentTime.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(ActivityStopSearch.this, (view, hourOfDay, minuteOfHour) -> {
            @SuppressLint("DefaultLocale") String time = String.format("%02d:%02d", hourOfDay, minuteOfHour);
            timeET.setText(time);
        }, hour, minute, true);//Yes 24 hour time
        timePickerDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void datePickerDialog(){
        // calender class's instance and get current date , month and year from calender
        final Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH); // ADD +1 to get actual month!!!
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityStopSearch.this,
                (view, year, month, day) -> {
                    String sMonth = Helpers.padWithZeroes(month + 1);
                    String sDay = Helpers.padWithZeroes(day);
                    String date = year + "-"  + sMonth + "-" + sDay;
                    dateET.setText(date);
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setFirstDayOfWeek(Calendar.MONDAY);
        datePickerDialog.show();
    }
}
