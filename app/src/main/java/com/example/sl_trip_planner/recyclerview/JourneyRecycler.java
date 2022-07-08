package com.example.sl_trip_planner.recyclerview;

import com.example.sl_trip_planner.data.JourneyList;

import java.util.ArrayList;

public class JourneyRecycler extends JourneyList {
    private final String mTextArrivalTime, mTextDepartureTime, mTextRtArrivalTime, mTextRtDepartureTime,
            mTextCombinedData, mTextDeltaT, mTextRtCombinedData, mTextRtDeltaT;

    public JourneyRecycler(
            String textDepartureTime,
            String textArrivalTime,
            String textRtDepartureTime,
            String textRtArrivalTime,
            String combinedData,
            String rtCombinedData,
            String deltaT,
            String rtDeltaT) {
        mTextDepartureTime = textDepartureTime;
        mTextArrivalTime = textArrivalTime;
        mTextRtDepartureTime = textRtDepartureTime;
        mTextRtArrivalTime = textRtArrivalTime;
        mTextCombinedData = combinedData;
        mTextDeltaT = deltaT;
        mTextRtCombinedData = rtCombinedData;
        mTextRtDeltaT = rtDeltaT;
    }

    public String getTextDepartureTime() { return mTextDepartureTime; }
    public String getTextArrivalTime() { return mTextArrivalTime; }
    public String getTextRtDepartureTime() { return mTextRtDepartureTime; }
    public String getTextRtArrivalTime() { return mTextRtArrivalTime; }
    public String getTextCombinedData() { return mTextCombinedData; }
    public String getTextDeltaT() { return mTextDeltaT; }
    public String getTextRtCombinedData() { return mTextRtCombinedData; }
    public String getTextRtDeltaT() { return mTextRtDeltaT; }
}
