package com.example.sl_trip_planner.recyclerview;

import com.example.sl_trip_planner.data.JourneyList;

import java.util.ArrayList;

public class JourneyRecycler extends JourneyList {
    private final String mTextArrivalTime, mTextDepartureTime, mTextRtArrivalTime, mTextRtDepartureTime,
            mTextCombinedData, mTextDeltaT, mTextRtCombinedData, mTextRtDeltaT,
            mTextArrivalDate, mTextDepartureDate;
    private final ArrayList<String> mLineList;
    private final ArrayList<String> mDestinationList, mTimeList, mRtTimeList, mStopList;

    public JourneyRecycler(
            String textDepartureDate,
            String textArrivalDate,
            String textDepartureTime,
            String textArrivalTime,
            String textRtDepartureTime,
            String textRtArrivalTime,
            String combinedData,
            String rtCombinedData,
            String deltaT,
            String rtDeltaT,
            ArrayList<String> lineList,
            ArrayList<String> destinationList,
            ArrayList<String> timeList,
            ArrayList<String> rtTimeList,
            ArrayList<String> stopList) {
        mTextDepartureDate = textDepartureDate;
        mTextArrivalDate = textArrivalDate;
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
        mRtTimeList = rtTimeList;
        mStopList = stopList;
    }

    public String getTextDepartureTime() { return mTextDepartureTime; }
    public String getTextArrivalTime() { return mTextArrivalTime; }
    public String getTextDepartureDate() { return mTextDepartureDate; }
    public String getTextArrivalDate() { return mTextArrivalDate; }
    public String getTextRtDepartureTime() { return mTextRtDepartureTime; }
    public String getTextRtArrivalTime() { return mTextRtArrivalTime; }
    public String getTextCombinedData() { return mTextCombinedData; }
    public String getTextDeltaT() { return mTextDeltaT; }
    public String getTextRtCombinedData() { return mTextRtCombinedData; }
    public String getTextRtDeltaT() { return mTextRtDeltaT; }
    public ArrayList<String> getTextLineList() { return mLineList; }
    public ArrayList<String> getTextDestinationList() { return mDestinationList; }
    public ArrayList<String> getTextTimeList() { return mTimeList; }
    public ArrayList<String> getTextRtTimeList() { return mRtTimeList; }
    public ArrayList<String> getTextStopList() { return mStopList; }
}
