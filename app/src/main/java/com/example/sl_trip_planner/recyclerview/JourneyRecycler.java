package com.example.sl_trip_planner.recyclerview;

import com.example.sl_trip_planner.data.JourneyList;

import java.util.ArrayList;

public class JourneyRecycler extends JourneyList {
    private final String mTextArrivalTime, mTextDepartureTime,
            mTextStopTransportList, mTextTimeTransportList,
            mTextCombinedData, mTextDeltaT;

    public JourneyRecycler(
            String textDepartureTime,
            String textArrivalTime,
            ArrayList<String> stopTransportList,
            ArrayList<String> timeTransportList,
            String combinedData,
            String deltaT) {
        mTextDepartureTime = textDepartureTime;
        mTextArrivalTime = textArrivalTime;
        mTextStopTransportList = String.valueOf(stopTransportList);
        mTextTimeTransportList = String.valueOf(timeTransportList);
        mTextCombinedData = combinedData;
        mTextDeltaT = deltaT;
    }

    public String getTextDepartureTime() { return mTextDepartureTime; }
    public String getTextArrivalTime() { return mTextArrivalTime; }
    public String getTextStopTransportList() {return mTextStopTransportList; }
    public String getTextTimeTransportList() { return mTextTimeTransportList; }
    public String getTextCombinedData() { return mTextCombinedData; }
    public String getTextDeltaT() { return mTextDeltaT; }
}
