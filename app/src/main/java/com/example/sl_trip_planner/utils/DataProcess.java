package com.example.sl_trip_planner.utils;

import java.util.ArrayList;

public class DataProcess {

    private ArrayList<String> mStopList = new ArrayList<>();
    private ArrayList<String> mTransportList= new ArrayList<>();
    private ArrayList<String> mTimeList = new ArrayList<>();
    private ArrayList<String> mTimeTransport = new ArrayList<>();
    private ArrayList<String> mStopTransport = new ArrayList<>();

    public void setData(ArrayList<String> stopList, ArrayList<String> transportList, ArrayList<String> timeList) {
        mStopList = stopList;
        mTransportList = transportList;
        mTimeList = timeList;
    }

    public ArrayList<String> combineTimeTransport() {
        for (int i = 0; i < mTransportList.size(); i++) {
            if (!mTransportList.get(i).equals("")) {
                mTimeTransport.add(mTimeList.get(i*2));
                mTimeTransport.add(mTransportList.get(i));
                mTimeTransport.add(mTimeList.get(i*2+1));
            }
        }
        return mTimeTransport;
    }

    public ArrayList<String> combineStopTransport() {
        for (int i = 0; i < mTransportList.size(); i++) {
            if (!mTransportList.get(i).equals("")) {
                mStopTransport.add(mStopList.get(i*2));
                mStopTransport.add(mTransportList.get(i));
                mStopTransport.add(mStopList.get(i*2+1));
            }
        }
        return mStopTransport;
    }

    public String combinedData() {
        String outcome = "";
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
                        " - " + stopTime + " " + stopStop;
            }
            outcome = outcome + oneLine + "\n";
        }
        return outcome;
    }
}