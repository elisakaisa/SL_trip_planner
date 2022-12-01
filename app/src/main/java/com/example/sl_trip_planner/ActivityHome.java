package com.example.sl_trip_planner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sl_trip_planner.data.Stops;
import com.example.sl_trip_planner.utils.Helpers;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class ActivityHome extends AppCompatActivity {

    private TextView timeET, dateET, depArrTV;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*---------- HOOKS ------------*/
        ImageButton tripTime = findViewById(R.id.img_btn_clock_main);
        ImageButton tripDate = findViewById(R.id.img_btn_calendar_main);
        timeET = findViewById(R.id.time_ET_main);
        dateET = findViewById(R.id.date_ET_main);
        SwitchMaterial depArrSwitch = findViewById(R.id.departure_arrival_switch_main);
        depArrTV = findViewById(R.id.departure_arrival_text);
        Button anyTripBtn = findViewById(R.id.button_any);
        Button button1 = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);

        /*-------- LISTENERS ----------*/
        tripTime.setOnClickListener(v -> timePickerDialog());
        tripDate.setOnClickListener(v -> datePickerDialog());
        anyTripBtn.setOnClickListener(v -> {
            String date = String.valueOf(dateET.getText());
            String time = String.valueOf(timeET.getText());
            Intent intent = new Intent(this, ActivityStopSearch.class);
            intent.putExtra(Stops.TIME, time);
            intent.putExtra(Stops.DATE, date);
            intent.putExtra(Stops.SEARCHFORARRIVAL, 0);
            startActivity(intent);
        });

        setButton(button1, Stops.Sskog, Stops.SthlmC, Stops.SSKOG, Stops.STHLMC);
        setButton(button2, Stops.SthlmC, Stops.Sskog, Stops.STHLMC, Stops.SSKOG);

        setButton(button3, Stops.Sskog, Stops.Tek, Stops.SSKOG, Stops.TEK);
        setButton(button4, Stops.Tek, Stops.Sskog, Stops.TEK, Stops.SSKOG);

        setButton(button5, Stops.Sod, Stops.SthlmC, Stops.SOD, Stops.STHLMC);
        setButton(button6, Stops.SthlmC, Stops.Sod, Stops.STHLMC, Stops.SOD);

        setButton(button7, Stops.Sskog, Stops.Mc, Stops.SSKOG, Stops.MC);
        setButton(button8, Stops.Mc, Stops.Sskog, Stops.MC, Stops.SSKOG);

    }

    public void setButton(Button button, String origin, String destination, int originId, int destinationId) {
        String buttonText = origin + " -> " + destination;
        button.setText(buttonText);
        button.setOnClickListener(v -> searchJourneys(originId, destinationId));
    }

    public void searchJourneys(int origin, int destination) {
        String date = String.valueOf(dateET.getText());
        String time = String.valueOf(timeET.getText());
        Intent intent = new Intent(this, ActivityTripList.class);
        intent.putExtra(Stops.TIME, time);
        intent.putExtra(Stops.DATE, date);
        intent.putExtra(Stops.ORIGIN_ID, origin);
        intent.putExtra(Stops.DESTINATION_ID, destination);
        intent.putExtra(Stops.SEARCHFORARRIVAL, 0);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void timePickerDialog() {
        // opens time picker dialog
        Calendar mCurrentTime = Calendar.getInstance();
        int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mCurrentTime.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(ActivityHome.this, (view, hourOfDay, minuteOfHour) -> {
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
        DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityHome.this,
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