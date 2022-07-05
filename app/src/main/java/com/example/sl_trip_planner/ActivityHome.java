package com.example.sl_trip_planner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.sl_trip_planner.data.StopIds;

public class ActivityHome extends AppCompatActivity {

    public static String ORIGIN_ID = "Origin ID";
    public static String DESTINATION_ID = "Destination ID";
    private final String Flemingsberg = "Flemingsberg", StockholmCity = "Stockholm City",
            Huddinge = "Huddinge", Sodertalje = "Södertälje", Gustav = "Gustav Adolfsvägen",
            Saltskog = "Saltskogs centrum";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*---------- HOOKS ------------*/
        Button anyTripBtn = findViewById(R.id.button_any);
        Button button1 = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);


        /*-------- LISTENERS ----------*/
        anyTripBtn.setOnClickListener(v -> startActivity(new Intent(this, ActivityStopSearch.class)));
        setButton(button1, Flemingsberg, Sodertalje, StopIds.FLEMINGSBERG, StopIds.SODERTALJE);
        setButton(button2, Flemingsberg, StockholmCity, StopIds.FLEMINGSBERG, StopIds.STOCKHOLM_CITY);
        setButton(button3, Gustav, Saltskog, StopIds.GUSTAV, StopIds.SALTSKOG);
        setButton(button4, StockholmCity, Flemingsberg, StopIds.STOCKHOLM_CITY, StopIds.FLEMINGSBERG);
        setButton(button5, Saltskog, Gustav, StopIds.SALTSKOG, StopIds.GUSTAV);
    }

    public void setButton(Button button, String origin, String destination, int originId, int destinationId) {
        String buttonText = origin + " -> " + destination;
        button.setText(buttonText);
        button.setOnClickListener(v -> searchJourneys(originId, destinationId));
    }

    public void searchJourneys(int origin, int destination) {
        // todo add input from date & time
        Intent intent = new Intent(this, ActivityTripList.class);
        intent.putExtra(ORIGIN_ID, origin);
        intent.putExtra(DESTINATION_ID, destination);
        startActivity(intent);
    }
}