package com.example.sl_trip_planner.utils;

import android.util.Log;

public class CalendarUtils {

    /* ------------ METHODS FOR EXPORTING EVENTS ------------ */
    //methods to change the date and time into a format accepted by the calendar provider API
    public static int exportYear(String date) {
        Log.d("calY", date);
        Log.d("calY", date.split("-")[0]);
        return Integer.parseInt(date.split("-")[0]);
    }
    public static int exportMonth(String date) {
        return Integer.parseInt(date.split("-")[1]);
    }
    public static int exportDay(String date) {
        return Integer.parseInt(date.split("-")[2]);
    }
    public static int exportMinutes(String time){
        return Integer.parseInt(time.substring(time.length() - 2));
    }

    public static int exportHours(String time){
        return Integer.parseInt(time.substring(0,2));
    }
}
