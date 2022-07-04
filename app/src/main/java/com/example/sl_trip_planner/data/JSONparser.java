package com.example.sl_trip_planner.data;

import android.util.Log;

import com.example.sl_trip_planner.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class JSONparser {
    String LOG_TAG = JSONparser.class.getSimpleName();
    private static final String
            ORIGIN = "origin",
            DESTINATION = "destination",
            DEPARTURE_TIME = "departure time",
            ARRIVAL_TIME = "arrival time",
            NAME = "name",
            TIME = "time";

    public String setUrl(int originId, int destinationId) {
        String ApiKey = ApiKeys.API_KEY_trip_planner;
        String url = "https://api.sl.se/api2/TravelplannerV3_1/trip.json?key=" + ApiKey + "&originExtId=" + originId + "&destExtId=" + destinationId;
        return url;
    }

    public List<JourneyModel> getJourney(JSONObject journeyObj) throws JSONException, ParseException {

        List<JourneyModel> journeyData = new ArrayList<>();
        Log.d(LOG_TAG, "parser initialized ");

        JSONArray trips = journeyObj.getJSONArray("Trip");
        for (int i = 0; i < trips.length(); i++) {
            // loop through different journey options
            JSONObject details = trips.getJSONObject(i);
            JSONObject legList = details.getJSONObject("LegList");
            Log.i(LOG_TAG, String.valueOf(legList));
            JSONArray leg = legList.getJSONArray("Leg");

            JourneyModel instantJourney = new JourneyModel();
            journeyData.add(instantJourney);

            ArrayList<String> originNameList = new ArrayList<>();
            ArrayList<String> destinationNameList = new ArrayList<>();
            ArrayList<String> originTimeList = new ArrayList<>();
            ArrayList<String> destinationTimeList = new ArrayList<>();
            ArrayList<String> stopList = new ArrayList<>();
            ArrayList<String> timeList = new ArrayList<>();
            ArrayList<String> transportList = new ArrayList<>();

            for (int j = 0; j < leg.length(); j++) {
                JSONObject details2 = leg.getJSONObject(j);

                transportList.add(details2.getString(NAME));

                // Origin
                JSONObject origin = details2.getJSONObject("Origin");
                originNameList.add(origin.getString(NAME));
                originTimeList.add(origin.getString(TIME));
                stopList.add(origin.getString(NAME));
                timeList.add(origin.getString(TIME));


                // Destination
                JSONObject destination = details2.getJSONObject("Destination");
                destinationNameList.add(destination.getString(NAME));
                destinationTimeList.add(destination.getString(TIME));
                stopList.add(destination.getString(NAME));
                timeList.add(destination.getString(TIME));


            }
            instantJourney.setOrigin(originNameList.get(0));
            instantJourney.setDepartureTime(originTimeList.get(0));

            instantJourney.setDestination(destinationNameList.get(destinationNameList.size()-1));
            instantJourney.setArrivalTime(destinationTimeList.get(destinationTimeList.size()-1));

            instantJourney.setTransportList(transportList);
            instantJourney.setStopList(stopList);
            instantJourney.setTimeList(timeList);

        }
        return journeyData;
    }
}
