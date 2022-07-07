package com.example.sl_trip_planner.data;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.sl_trip_planner.utils.DataProcess;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class JSONparser {
    String LOG_TAG = JSONparser.class.getSimpleName();
    private DataProcess cDataProcess;
    private static final String
            ORIGIN = "Origin",
            DESTINATION = "Destination",
            DEPARTURE_TIME = "departure time",
            ARRIVAL_TIME = "arrival time",
            NAME = "name",
            TIME = "time";

    public String setSearchUrl(String stopName) {
        String ApiKey = ApiKeys.API_KEY_stops;
        return "https://api.sl.se/api2/typeahead.json?key=" + ApiKey + "&searchstring=" + stopName;
    }

    public List<StopModel> getStops(JSONObject stopObj) throws JSONException {
        List <StopModel> stopData = new ArrayList<>();
        JSONArray responseData = stopObj.getJSONArray("ResponseData");
        ArrayList<String> stopArrayList = new ArrayList<>();
        for (int i = 0; i < responseData.length(); i++) {
            JSONObject details = responseData.getJSONObject(i);

            StopModel instantStop = new StopModel();
            stopData.add(instantStop);

            String name = details.getString("Name");
            String siteId = details.getString("SiteId");
            instantStop.setStopName(name);
            instantStop.setStopId(Integer.parseInt(siteId));
            stopArrayList.add(name);
        }
        if (responseData.length() == 0) {
            //new AlertDial().createMsgDialog(getContext(), "No internet connection", "Please turn on internet connection").show();
        }
        return stopData;
    }

    public String setTripUrl(int originId, int destinationId, String time, String date, int searchForArrival) {
        String ApiKey = ApiKeys.API_KEY_trip_planner;
        String url = "https://api.sl.se/api2/TravelplannerV3_1/trip.json?key=" + ApiKey + "&originId=" + originId + "&destId=" + destinationId;
        if (searchForArrival == 1 ) {
            url = "https://api.sl.se/api2/TravelplannerV3_1/trip.json?key=" + ApiKey + "&originId=" + originId +
                    "&destId=" + destinationId + "&searchForArrival=" + searchForArrival;
            if (!time.equals("time")) {
                url = "https://api.sl.se/api2/TravelplannerV3_1/trip.json?key=" +
                        ApiKey + "&originId=" + originId + "&destId=" + destinationId + "&Time=" + time + "&searchForArrival=" + searchForArrival;
                if (!date.equals("date")) {
                    url = "https://api.sl.se/api2/TravelplannerV3_1/trip.json?key=" +
                            ApiKey + "&originId=" + originId + "&destId=" + destinationId +
                            "&Time=" + time + "&Date=" + date + "&searchForArrival=" + searchForArrival;
                }
            }
        } else {
            if (!time.equals("time")) {
                url = "https://api.sl.se/api2/TravelplannerV3_1/trip.json?key=" +
                        ApiKey + "&originId=" + originId + "&destId=" + destinationId + "&Time=" + time;
                if (!date.equals("date")) {
                    url = "https://api.sl.se/api2/TravelplannerV3_1/trip.json?key=" +
                            ApiKey + "&originId=" + originId + "&destId=" + destinationId +
                            "&Time=" + time + "&Date=" + date;
                }
            }
        }
        return url;
    }

    public List<JourneyModel> getJourney(JSONObject journeyObj) throws JSONException, ParseException {

        List<JourneyModel> journeyData = new ArrayList<>();

        JSONArray trips = journeyObj.getJSONArray("Trip");
        for (int i = 0; i < trips.length(); i++) {
            // loop through different journey options
            JSONObject details = trips.getJSONObject(i);
            JSONObject legList = details.getJSONObject("LegList");
            JSONArray leg = legList.getJSONArray("Leg");

            /*------------ INIT -----------------*/
            cDataProcess = new DataProcess();
            JourneyModel instantJourney = new JourneyModel();
            journeyData.add(instantJourney);

            /* ------- INIT ARRAYLISTS -----------*/
            ArrayList<String> originNameList = new ArrayList<>();
            ArrayList<String> destinationNameList = new ArrayList<>();
            ArrayList<String> originTimeList = new ArrayList<>();
            ArrayList<String> destinationTimeList = new ArrayList<>();
            ArrayList<String> stopList = new ArrayList<>();
            ArrayList<String> timeList = new ArrayList<>();
            ArrayList<String> transportList = new ArrayList<>();
            ArrayList<String> timeTransport = new ArrayList<>();
            ArrayList<String> stopTransport = new ArrayList<>();

            for (int j = 0; j < leg.length(); j++) {
                JSONObject details2 = leg.getJSONObject(j);

                transportList.add(details2.getString(NAME));

                // Origin
                JSONObject origin = details2.getJSONObject(ORIGIN);
                originNameList.add(origin.getString(NAME));
                originTimeList.add(origin.getString(TIME).substring(0, origin.getString(TIME).length() - 3));
                stopList.add(origin.getString(NAME));
                timeList.add(origin.getString(TIME).substring(0, origin.getString(TIME).length() - 3));


                // Destination
                JSONObject destination = details2.getJSONObject(DESTINATION);
                destinationNameList.add(destination.getString(NAME));
                destinationTimeList.add(destination.getString(TIME).substring(0, destination.getString(TIME).length() - 3));
                stopList.add(destination.getString(NAME));
                timeList.add(destination.getString(TIME).substring(0, destination.getString(TIME).length() - 3));


            }
            instantJourney.setOrigin(originNameList.get(0));
            instantJourney.setDepartureTime(originTimeList.get(0));

            instantJourney.setDestination(destinationNameList.get(destinationNameList.size()-1));
            instantJourney.setArrivalTime(destinationTimeList.get(destinationTimeList.size()-1));

            instantJourney.setTransportList(transportList);
            instantJourney.setStopList(stopList);
            instantJourney.setTimeList(timeList);

            // DATA PROCESSING
            cDataProcess.setStopTimeTransport(stopList, transportList, timeList);
            timeTransport = cDataProcess.combineTimeTransport();
            stopTransport = cDataProcess.combineStopTransport();
            String outcome = cDataProcess.combinedData();

            instantJourney.setTimeTransportData(timeTransport);
            instantJourney.setStopTransportData(stopTransport);
            instantJourney.setCombinedData(outcome);

            String deltaT = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                deltaT = cDataProcess.setTime(originTimeList.get(0), destinationTimeList.get(destinationTimeList.size()-1));
            }
            instantJourney.setDeltaT(deltaT);
        }

        return journeyData;
    }
}
