package com.example.sl_trip_planner.recyclerview;

import com.example.sl_trip_planner.data.StopList;

public class StopRecycler extends StopList {
    private final String mtextStop;

    public StopRecycler(String textStop) {
        mtextStop = textStop;
    }

    public String getTextStop() { return mtextStop; }
}
