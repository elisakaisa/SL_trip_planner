package com.example.sl_trip_planner.utils;

import android.util.Log;

public class CalendarUtils {

    /* ------------ METHODS FOR EXPORTING EVENTS ------------ */
    //methods to change the date and time into a format accepted by the calendar provider API
    public static int exportYear(String date) {
        return Integer.parseInt(date.split("-")[0]);
    }
    public static int exportMonth(String date) {
        return Integer.parseInt(date.split("-")[1]);
    }
    public static int exportDay(String date) {
        return Integer.parseInt(date.split("-")[2]);
    }
    public static int exportDayPlusOne(String date) {
        Log.d("utils", String.valueOf(Integer.parseInt(date.split("-")[2])));
        Log.d("utils", String.valueOf(Integer.parseInt(date.split("-")[2])+1));
        return Integer.parseInt(date.split("-")[2])+1;
    }
    public static int exportMinutes(String time){
        return Integer.parseInt(time.substring(time.length() - 2));
    }

    public static int exportHours(String time){
        return Integer.parseInt(time.substring(0,2));
    }

    public static Boolean depArrOnDifferentDays(String depTime, String arrTime) {
        return exportHours(depTime) > exportHours(arrTime); // true if depTime < arrTime
    }
}
