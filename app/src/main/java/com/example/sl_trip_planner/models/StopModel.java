package com.example.sl_trip_planner.models;

import java.util.ArrayList;

public class StopModel {
    private String mStopName;
    private int mStopId;
    private ArrayList<String> mStopArrayList;

    public void setStopName(String stopName) { mStopName = stopName; }
    public void setStopId(int stopId) { mStopId = stopId; }
    public void setStopArrayList(ArrayList<String> stopArrayList) { mStopArrayList = stopArrayList;}

    public String getStopName() { return mStopName; }
    public int getStopId() { return mStopId; }
    public ArrayList<String> getStopArrayList() { return mStopArrayList; }
}
