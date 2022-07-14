package com.example.sl_trip_planner.recyclerview;

import com.example.sl_trip_planner.data.JourneyList;

import java.util.ArrayList;

public class JourneyRecycler extends JourneyList {
    private final String mTextArrivalTime, mTextDepartureTime, mTextRtArrivalTime, mTextRtDepartureTime,
            mTextCombinedData, mTextDeltaT, mTextRtCombinedData, mTextRtDeltaT;
    private final ArrayList<Integer> mLineList;
    private final ArrayList<String> mDestinationList, mTimeList, mStopList;

    public JourneyRecycler(
            String textDepartureTime,
            String textArrivalTime,
            String textRtDepartureTime,
            String textRtArrivalTime,
            String combinedData,
            String rtCombinedData,
            String deltaT,
            String rtDeltaT,
            ArrayList<Integer> lineList,
            ArrayList<String> destinationList,
            ArrayList<String> timeList,
            ArrayList<String> stopList) {
        mTextDepartureTime = textDepartureTime;
        mTextArrivalTime = textArrivalTime;
        mTextRtDepartureTime = textRtDepartureTime;
        mTextRtArrivalTime = textRtArrivalTime;
        mTextCombinedData = combinedData;
        mTextDeltaT = deltaT;
        mTextRtCombinedData = rtCombinedData;
        mTextRtDeltaT = rtDeltaT;
        mLineList = lineList;
        mDestinationList = destinationList;
        mTimeList = timeList;
        mStopList = stopList;
    }

    public String getTextDepartureTime() { return mTextDepartureTime; }
    public String getTextArrivalTime() { return mTextArrivalTime; }
    public String getTextRtDepartureTime() { return mTextRtDepartureTime; }
    public String getTextRtArrivalTime() { return mTextRtArrivalTime; }
    public String getTextCombinedData() { return mTextCombinedData; }
    public String getTextDeltaT() { return mTextDeltaT; }
    public String getTextRtCombinedData() { return mTextRtCombinedData; }
    public String getTextRtDeltaT() { return mTextRtDeltaT; }
    public ArrayList<Integer> getTextLineList() { return mLineList; }
    public ArrayList<String> getTextDestinationList() { return mDestinationList; }
    public ArrayList<String> getTextTimeList() { return mTimeList; }
    public ArrayList<String> getTextStopList() { return mStopList; }
}
