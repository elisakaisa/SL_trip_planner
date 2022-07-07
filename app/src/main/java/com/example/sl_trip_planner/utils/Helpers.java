package com.example.sl_trip_planner.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Helpers {

    public static String padWithZeroes(int number) {
        String sNumber = String.valueOf(number);
        if (number < 10) { sNumber = "0" + number; }
        return sNumber;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalTime timeFromString(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.parse(time, formatter);
    }
}
