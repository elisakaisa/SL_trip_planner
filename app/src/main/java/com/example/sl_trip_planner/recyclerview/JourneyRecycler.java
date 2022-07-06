package com.example.sl_trip_planner.recyclerview;

import com.example.sl_trip_planner.data.JourneyList;

import java.util.ArrayList;

public class JourneyRecycler extends JourneyList {
    private final String mTextOrigin, mTextDestination, mTextArrivalTime, mTextDepartureTime, mTextStopTransportList, mTextTimeTransportList, mTextCombinedData;

    public JourneyRecycler(
            String textOrigin,
            String textDestination,
            String textDepartureTime,
            String textArrivalTime,
            ArrayList<String> stopTransportList,
            ArrayList<String> timeTransportList, String combinedData) {
        mTextOrigin = textOrigin;
        mTextDestination = textDestination;
        mTextDepartureTime = textDepartureTime;
        mTextArrivalTime = textArrivalTime;
        mTextStopTransportList = String.valueOf(stopTransportList);
        mTextTimeTransportList = String.valueOf(timeTransportList);
        mTextCombinedData = combinedData;
    }

    public String getTextOrigin() { return mTextOrigin; }
    public String getTextDestination() { return mTextDestination; }
    public String getTextDepartureTime() { return mTextDepartureTime; }
    public String getTextArrivalTime() { return mTextArrivalTime; }
    public String getTextStopTransportList() {return mTextStopTransportList; }
    public String getTextTimeTrabnsportList() { return mTextTimeTransportList; }
    public String getTextCombinedData() { return mTextCombinedData; }
}
