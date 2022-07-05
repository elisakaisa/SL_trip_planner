package com.example.sl_trip_planner.data;

import java.util.ArrayList;

public class StopModel {
    private String mStopName, mStopId;
    private ArrayList<String> mStopArrayList;

    public void setStopName(String stopName) { mStopName = stopName; }
    public void setStopId(String stopId) { mStopId = stopId; }
    public void setStopArrayList(ArrayList<String> stopArrayList) { mStopArrayList = stopArrayList;}

    public String getStopName() { return mStopName; }
    public String getStopId() { return mStopId; }
    public ArrayList<String> getStopArrayList() { return mStopArrayList; }
}
