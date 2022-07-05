package com.example.sl_trip_planner.data;

import java.util.ArrayList;
import java.util.List;

public class StopList {

    private static List<StopModel> theStop;

    // private constructor to force the use of getInstance() to get an/the object
    public StopList() {}

    public static List<StopModel> getInstance() {
        if (theStop == null)
            theStop = new ArrayList<>();
        return theStop;
    }
}
