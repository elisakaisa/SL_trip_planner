package com.example.sl_trip_planner.recyclerview;

import com.example.sl_trip_planner.data.JourneyList;

public class JourneyRecycler extends JourneyList {
    private final String mTextOrigin, mTextDestination, mTextArrivalTime, mTextDepartureTime;

    public JourneyRecycler(String textOrigin, String textDestination, String textDepartureTime, String textArrivalTime) {
        mTextOrigin = textOrigin;
        mTextDestination = textDestination;
        mTextDepartureTime = textDepartureTime;
        mTextArrivalTime = textArrivalTime;
    }

    public String getTextOrigin() { return mTextOrigin; }
    public String getTextDestination() { return mTextDestination; }
    public String getTextDepartureTime() { return mTextDepartureTime; }
    public String getTextArrivalTime() { return mTextArrivalTime; }
}
