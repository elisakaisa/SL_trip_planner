package com.example.sl_trip_planner.data;

import java.util.ArrayList;

public class JourneyModel {
    private String mOrigin, mDestination, mDepartureTime, mArrivalTime;
    private ArrayList<String> mStopList;
    private ArrayList<String> mTimeList;
    private ArrayList<String> mTransportList;
    private ArrayList<String> mTimeTransportData;
    private ArrayList<String> mStopTransportData;

    // set methods
    public void setOrigin(String origin) { mOrigin = origin; }
    public void setDestination(String destination) { mDestination = destination; }
    public void setDepartureTime (String departureTime) { mDepartureTime = departureTime; }
    public void setArrivalTime (String arrivalTime) { mArrivalTime = arrivalTime; }
    public void setStopList (ArrayList<String> stopList) {mStopList = stopList; }
    public void setTimeList (ArrayList<String> timeList) {mStopList = timeList; }
    public void setTransportList (ArrayList<String> transportList) {mTransportList = transportList;}
    public void setTimeTransportData(ArrayList<String> timeTransportData) {
        mTimeTransportData = timeTransportData;}
    public void setStopTransportData(ArrayList<String> combinedData) {
        mStopTransportData = combinedData;}

    // get methods
    public String getOrigin() { return mOrigin; }
    public String getDestination() { return mDestination; }
    public String getDepartureTime() { return mDepartureTime; }
    public String getArrivalTime() { return mArrivalTime; }
    public ArrayList<String> getStopList() { return mStopList; }
    public ArrayList<String> getTimeList() { return mTimeList; }
    public ArrayList<String> getTransportList() { return mTransportList; }
    public ArrayList<String> getTimeTransportData() { return mTimeTransportData; }
    public ArrayList<String> getStopTransportData() { return mStopTransportData; }
}
