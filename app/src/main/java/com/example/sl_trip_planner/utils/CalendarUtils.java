package com.example.sl_trip_planner.utils;

public class CalendarUtils {

    /* ------------ METHODS FOR EXPORTING EVENTS ------------ */
    //methods to change the date and time into a format accepted by the calendar provider API
    public static int exportMinutes(String time){
        return Integer.parseInt(time.substring(time.length() - 2));
    }

    public static int exportHours(String time){
        return Integer.parseInt(time.substring(0,2));
    }
}
