package com.example.sl_trip_planner.data;

import java.util.ArrayList;
import java.util.List;

public class JourneyList {

    private static List<JourneyModel> theJourney;

    // private constructor to force the use of getInstance() to get an/the object
    public JourneyList() {}

    public static List<JourneyModel> getInstance() {
        if (theJourney == null)
            theJourney = new ArrayList<>();
        return theJourney;
    }
}
