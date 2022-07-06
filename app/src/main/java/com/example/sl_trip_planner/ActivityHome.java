package com.example.sl_trip_planner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.sl_trip_planner.data.Stops;

public class ActivityHome extends AppCompatActivity {

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
        setButton(button1, Stops.Flem, Stops.Sod, Stops.FLEM, Stops.SOD);
        setButton(button2, Stops.Flem, Stops.SthlmC, Stops.FLEM, Stops.STHLMC);
        setButton(button3, Stops.Gustav, Stops.Sskog, Stops.GUSTAV, Stops.SSKOG);
        setButton(button4, Stops.SthlmC, Stops.Flem, Stops.STHLMC, Stops.FLEM);
        setButton(button5, Stops.Sskog, Stops.Gustav, Stops.SSKOG, Stops.GUSTAV);
    }

    public void setButton(Button button, String origin, String destination, int originId, int destinationId) {
        String buttonText = origin + " -> " + destination;
        button.setText(buttonText);
        button.setOnClickListener(v -> searchJourneys(originId, destinationId));
    }

    public void searchJourneys(int origin, int destination) {
        // todo add input from date & time
        Intent intent = new Intent(this, ActivityTripList.class);
        intent.putExtra(Stops.TIME, "time");
        intent.putExtra(Stops.DATE, "date");
        intent.putExtra(Stops.ORIGIN_ID, origin);
        intent.putExtra(Stops.DESTINATION_ID, destination);
        startActivity(intent);
    }
}