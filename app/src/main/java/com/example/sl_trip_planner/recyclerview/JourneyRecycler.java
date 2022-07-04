package com.example.sl_trip_planner.recyclerview;

import com.example.sl_trip_planner.data.JourneyList;

import java.util.ArrayList;

public class JourneyRecycler extends JourneyList {
    private final String mTextOrigin, mTextDestination, mTextArrivalTime, mTextDepartureTime, mTextTransportList;

    public JourneyRecycler(String textOrigin, String textDestination, String textDepartureTime, String textArrivalTime, ArrayList<String> textTransportList) {
        mTextOrigin = textOrigin;
        mTextDestination = textDestination;
        mTextDepartureTime = textDepartureTime;
        mTextArrivalTime = textArrivalTime;
        mTextTransportList = String.valueOf(textTransportList);
    }

    public String getTextOrigin() { return mTextOrigin; }
    public String getTextDestination() { return mTextDestination; }
    public String getTextDepartureTime() { return mTextDepartureTime; }
    public String getTextArrivalTime() { return mTextArrivalTime; }
    public String getTextTransportList() {return mTextTransportList; }
}
