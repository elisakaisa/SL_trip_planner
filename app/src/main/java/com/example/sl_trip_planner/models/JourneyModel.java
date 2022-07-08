package com.example.sl_trip_planner.models;

import java.util.ArrayList;

public class JourneyModel {
    private String mOrigin, mDestination, mDepartureTime, mArrivalTime, mRtDepartureTime, mRtArrivalTime;
    private ArrayList<String> mStopList;
    private ArrayList<String> mTimeList, mRtTimeList;
    private ArrayList<String> mTransportList;
    private ArrayList<String> mTimeTransportData;
    private ArrayList<String> mStopTransportData;
    private String mCombinedData, mRtCombinedData;
    private String mDeltaT, mRtDeltaT;

    // set methods
    public void setOrigin(String origin) { mOrigin = origin; }
    public void setDestination(String destination) { mDestination = destination; }
    public void setDepartureTime (String departureTime) { mDepartureTime = departureTime; }
    public void setArrivalTime (String arrivalTime) { mArrivalTime = arrivalTime; }
    public void setRtDepartureTime (String rtDepartureTime) { mRtDepartureTime = rtDepartureTime; }
    public void setRtArrivalTime (String rtArrivalTime) { mRtArrivalTime = rtArrivalTime; }
    public void setStopList (ArrayList<String> stopList) {mStopList = stopList; }
    public void setTimeList (ArrayList<String> timeList) {mTimeList = timeList; }
    public void setRtTimeList (ArrayList<String> rtTimeList) {mRtTimeList = rtTimeList; }
    public void setTransportList (ArrayList<String> transportList) {mTransportList = transportList;}
    public void setTimeTransportData(ArrayList<String> timeTransportData) {
        mTimeTransportData = timeTransportData;}
    public void setStopTransportData(ArrayList<String> stopTransportData) {
        mStopTransportData = stopTransportData;}
    public void setCombinedData(String combinedData) { mCombinedData = combinedData; }
    public void setRtCombinedData(String rtCombinedData) { mRtCombinedData = rtCombinedData; }
    public void setDeltaT(String deltaT) { mDeltaT = deltaT; }
    public void setRtDeltaT(String rtDeltaT) { mRtDeltaT = rtDeltaT; }

    // get methods
    public String getOrigin() { return mOrigin; }
    public String getDestination() { return mDestination; }
    public String getDepartureTime() { return mDepartureTime; }
    public String getArrivalTime() { return mArrivalTime; }
    public String getRtDepartureTime() { return mRtDepartureTime; }
    public String getRtArrivalTime() { return mRtArrivalTime; }
    public ArrayList<String> getStopList() { return mStopList; }
    public ArrayList<String> getTimeList() { return mTimeList; }
    public ArrayList<String> getTransportList() { return mTransportList; }
    public ArrayList<String> getTimeTransportData() { return mTimeTransportData; }
    public ArrayList<String> getStopTransportData() { return mStopTransportData; }
    public String getCombinedData() { return mCombinedData; }
    public String getDeltaT() { return mDeltaT; }
    public String getRtCombinedData() { return mRtCombinedData; }
    public String getRtDeltaT() { return mRtDeltaT; }
}
