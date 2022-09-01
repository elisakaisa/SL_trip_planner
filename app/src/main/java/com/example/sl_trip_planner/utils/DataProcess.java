package com.example.sl_trip_planner.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

public class DataProcess {

    private ArrayList<String> mStopList = new ArrayList<>();
    private ArrayList<String> mTransportList= new ArrayList<>();
    private ArrayList<String> mTimeList = new ArrayList<>();
    private ArrayList<String> mTimeTrimmed = new ArrayList<>();
    private ArrayList<String> mStopTrimmed = new ArrayList<>();
    private ArrayList<String> mRtTimeTrimmed = new ArrayList<>();
    private ArrayList<String> mRtTimeList = new ArrayList<>();


    public void setStopTimeTransport(
            ArrayList<String> stopList,
            ArrayList<String> transportList,
            ArrayList<String> timeList,
            ArrayList<String> rtTimeList) {
        mStopList = stopList;
        mTransportList = transportList;
        mTimeList = timeList;
        mRtTimeList = rtTimeList;
    }

    public ArrayList<String> trimTimeList() {
        // takes the times at stops
        for (int i = 0; i < mTransportList.size(); i++) {
            if (!mTransportList.get(i).equals("")) {
                mTimeTrimmed.add(mTimeList.get(i*2));
                mTimeTrimmed.add(mTimeList.get(i*2+1));
            }
        }
        return mTimeTrimmed;
    }

    public ArrayList<String> trimRtTimeList() {
        // takes the times at stops
        for (int i = 0; i < mTransportList.size(); i++) {
            if (!mTransportList.get(i).equals("")) {
                mRtTimeTrimmed.add(mRtTimeList.get(i*2));
                mRtTimeTrimmed.add(mRtTimeList.get(i*2+1));
            }
        }
        return mRtTimeTrimmed;
    }

    public ArrayList<String> trimStopList() {
        for (int i = 0; i < mTransportList.size(); i++) {
            if (!mTransportList.get(i).equals("")) {
                mStopTrimmed.add(mStopList.get(i*2));
                mStopTrimmed.add(mStopList.get(i*2+1));
            }
        }
        return mStopTrimmed;
    }

    public String combinedData() {
        StringBuilder outcome = new StringBuilder();
        for (int i = 0; i < mTransportList.size(); i++) {
            String oneLine = "";
            if (!mTransportList.get(i).equals("")) {
                // Line
                String line = mTransportList.get(i);

                //from
                String startTime = mTimeList.get(i*2);
                String startStop = mStopList.get(i*2);

                // to
                String stopStop = mStopList.get(i*2+1);
                String stopTime = mTimeList.get(i*2+1);

                // combine
                oneLine = line + ": " + startTime + " " + startStop +
                        " - " + stopTime + " " + stopStop + "\n";
            }
            outcome.append(oneLine);
        }
        String outcome2 = outcome.toString();
        return outcome2.trim();
    }

    public String rtCombinedData() {
        StringBuilder outcome = new StringBuilder();
        outcome.append("Real-time \n");
        for (int i = 0; i < mTransportList.size(); i++) {
            String oneLine = "";
            if (!mTransportList.get(i).equals("")) {
                // Line
                String line = mTransportList.get(i);

                //from
                String startTime = mRtTimeList.get(i*2);
                String startStop = mStopList.get(i*2);

                // to
                String stopStop = mStopList.get(i*2+1);
                String stopTime = mRtTimeList.get(i*2+1);

                // combine
                oneLine = line + ": " + startTime + " " + startStop +
                        " - " + stopTime + " " + stopStop + "\n";
            }
            outcome.append(oneLine);
        }
        String outcome2 = outcome.toString();
        return outcome2.trim();
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    public String setTime(String departure, String arrival) {
        LocalTime start = Helpers.timeFromString(departure);
        LocalTime stop = Helpers.timeFromString(arrival);
        Duration deltaT = Duration.between(start, stop);
        int minutes = deltaT.toMinutesPart();
        int hours = deltaT.toHoursPart();

        // take into account deltaT if date changes
        if (hours < 0) { hours = 23 + hours; }
        if (minutes < 0) { minutes = 60 + minutes; }

        return hours + ":" + Helpers.padWithZeroes(minutes);
    }



}
