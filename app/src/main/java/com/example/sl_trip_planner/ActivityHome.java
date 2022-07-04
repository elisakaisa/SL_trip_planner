package com.example.sl_trip_planner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class ActivityHome extends AppCompatActivity {

    public static String ORIGIN_ID = "Origin ID";
    public static String DESTINATION_ID = "Destination ID";
    public static int FLEMINGSBERG = 300107005, STOCKHOLM_CITY = 300101002, SODERTALJE = 300109520, HUDDINGE = 300109527,
            HELENELUND = 300109507;
    private final String Flemingsberg = "Flemingsberg", StockholmCity = "Stockholm City",
            Huddinge = "Huddinge", Sodertalje = "Södertälje";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*---------- HOOKS ------------*/
        Button button1 = findViewById(R.id.button);
        button1.setText(Flemingsberg + " -> " + Sodertalje);
        Button button2 = findViewById(R.id.button2);
        button2.setText(Flemingsberg + " -> " + StockholmCity);
        EditText dateET = findViewById(R.id.editTextDate);
        EditText timeET = findViewById(R.id.editTextTime);

        /*-------- LISTENERS ----------*/
        button1.setOnClickListener(v -> searchJourneys(FLEMINGSBERG, SODERTALJE));
        button2.setOnClickListener(v -> searchJourneys(FLEMINGSBERG, STOCKHOLM_CITY));
    }

    public void searchJourneys(int origin, int destination) {
        // todo add input from date & time
        Intent intent = new Intent(this, ActivityTripList.class);
        intent.putExtra(ORIGIN_ID, origin);
        intent.putExtra(DESTINATION_ID, destination);
        startActivity(intent);
    }
}