package com.example.sl_trip_planner.apidata;

import android.content.Context;

import com.example.sl_trip_planner.models.ContextModel;
import com.example.sl_trip_planner.models.JourneyModel;
import com.example.sl_trip_planner.models.StopModel;
import com.example.sl_trip_planner.utils.AlertDial;
import com.example.sl_trip_planner.utils.DataProcess;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONParser {
    String LOG_TAG = JSONParser.class.getSimpleName();
    private ContextModel cContextModel;
    private static final String
            ORIGIN = "Origin",
            DESTINATION = "Destination",
            NAME = "name",
            TIME = "time",
            DATE = "date",
            RT_TIME = "rtTime";

    public List<StopModel> getStops(JSONObject stopObj, Context context) throws JSONException {
        List <StopModel> stopData = new ArrayList<>();
        JSONArray responseData = stopObj.getJSONArray("ResponseData");
        for (int i = 0; i < responseData.length(); i++) {
            JSONObject details = responseData.getJSONObject(i);
            StopModel instantStop = new StopModel();
            stopData.add(instantStop);
            instantStop.setStopName(details.getString("Name"));
            instantStop.setStopId(Integer.parseInt(details.getString("SiteId")));
        }
        if (responseData.length() == 0) {
            new AlertDial().createMsgDialog(context, "Server error", "Could not reach server").show();
        }
        return stopData;
    }


    public List<JourneyModel> getJourney(JSONObject journeyObj) throws JSONException {

        List<JourneyModel> journeyData = new ArrayList<>();

        JSONArray trips = journeyObj.getJSONArray("Trip");
        for (int i = 0; i < trips.length(); i++) {
            // loop through different journey options
            JSONObject details = trips.getJSONObject(i);
            JSONObject legList = details.getJSONObject("LegList");
            JSONArray leg = legList.getJSONArray("Leg");

            /*------------ INIT -----------------*/
            DataProcess cDataProcess = new DataProcess();
            JourneyModel instantJourney = new JourneyModel();
            journeyData.add(instantJourney);

            /* ------- INIT ARRAYLISTS -----------*/
            ArrayList<String> originNameList = new ArrayList<>();
            ArrayList<String> destinationNameList = new ArrayList<>();
            ArrayList<String> originTimeList = new ArrayList<>();
            ArrayList<String> originDateList = new ArrayList<>();
            ArrayList<String> originRtTimeList = new ArrayList<>();
            ArrayList<String> destinationTimeList = new ArrayList<>();
            ArrayList<String> destinationDateList = new ArrayList<>();
            ArrayList<String> destinationRtTimeList = new ArrayList<>();
            ArrayList<String> stopList = new ArrayList<>();
            ArrayList<String> timeList = new ArrayList<>();
            ArrayList<String> rtTimeList = new ArrayList<>();
            ArrayList<String> transportList = new ArrayList<>();
            ArrayList<String> lineList = new ArrayList<>();
            ArrayList<String> directionList = new ArrayList<>();

            for (int j = 0; j < leg.length(); j++) {
                JSONObject details2 = leg.getJSONObject(j);
                transportList.add(details2.getString(NAME));

                // Origin
                JSONObject origin = details2.getJSONObject(ORIGIN);
                originNameList.add(origin.getString(NAME));
                originTimeList.add(origin.getString(TIME).substring(0, origin.getString(TIME).length() - 3));
                originDateList.add(origin.getString(DATE));
                stopList.add(origin.getString(NAME));
                timeList.add(origin.getString(TIME).substring(0, origin.getString(TIME).length() - 3));

                // Origin real-time data
                if (!origin.has(RT_TIME)) {
                    originRtTimeList.add(origin.getString(TIME).substring(0, origin.getString(TIME).length() - 3));
                    rtTimeList.add(origin.getString(TIME).substring(0, origin.getString(TIME).length() - 3));
                } else {
                    originRtTimeList.add(origin.getString(RT_TIME).substring(0, origin.getString(RT_TIME).length() - 3));
                    rtTimeList.add(origin.getString(RT_TIME).substring(0, origin.getString(RT_TIME).length() - 3));
                }

                // Destination
                JSONObject destination = details2.getJSONObject(DESTINATION);
                destinationNameList.add(destination.getString(NAME));
                destinationTimeList.add(destination.getString(TIME).substring(0, destination.getString(TIME).length() - 3));
                destinationDateList.add(destination.getString(DATE));
                stopList.add(destination.getString(NAME));
                timeList.add(destination.getString(TIME).substring(0, destination.getString(TIME).length() - 3));

                //Destination real-time data
                if (!destination.has(RT_TIME)) {
                    destinationRtTimeList.add(destination.getString(TIME).substring(0, destination.getString(TIME).length() - 3));
                    rtTimeList.add(destination.getString(TIME).substring(0, destination.getString(TIME).length() - 3));
                } else {
                    destinationRtTimeList.add(destination.getString(RT_TIME).substring(0, destination.getString(RT_TIME).length() - 3));
                    rtTimeList.add(destination.getString(RT_TIME).substring(0, destination.getString(RT_TIME).length() - 3));
                }

                // Product
                if (details2.has("Product")) {
                    JSONObject product = details2.getJSONObject("Product");
                    lineList.add(product.getString("line"));
                }

                // destination
                if (details2.has("direction")) {
                    directionList.add(details2.getString("direction"));
                }

            }
            // to from (place)
            instantJourney.setOrigin(originNameList.get(0));
            instantJourney.setDestination(destinationNameList.get(destinationNameList.size()-1));
            // to from (date & time)
            instantJourney.setDepartureDate(originDateList.get(0));
            instantJourney.setArrivalDate(destinationDateList.get(destinationDateList.size()-1));
            instantJourney.setDepartureTime(originTimeList.get(0));
            instantJourney.setArrivalTime(destinationTimeList.get(destinationTimeList.size()-1));
            // to from (real-time)
            instantJourney.setRtDepartureTime(originRtTimeList.get(0));
            instantJourney.setRtArrivalTime(destinationRtTimeList.get(destinationRtTimeList.size()-1));

            // line and direction
            instantJourney.setLineList(lineList);
            instantJourney.setDirectionList(directionList);

            // DATA PROCESSING
            cDataProcess.setStopTimeTransport(stopList, transportList, timeList, rtTimeList);
            String outcome = cDataProcess.combinedData();
            String rtOutcome = cDataProcess.rtCombinedData();
            String deltaT = null;
            String rtDeltaT = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                deltaT = cDataProcess.setTime(originTimeList.get(0), destinationTimeList.get(destinationTimeList.size()-1));
                rtDeltaT = cDataProcess.setTime(originRtTimeList.get(0), destinationRtTimeList.get(destinationRtTimeList.size()-1));
            }
            timeList = cDataProcess.trimTimeList();
            rtTimeList = cDataProcess.trimRtTimeList();
            stopList = cDataProcess.trimStopList();

            // transport, stops, and times
            instantJourney.setTransportList(transportList);
            instantJourney.setStopList(stopList);
            instantJourney.setTimeList(timeList);
            instantJourney.setRtTimeList(rtTimeList);

            instantJourney.setCombinedData(outcome);
            instantJourney.setRtCombinedData(rtOutcome);
            instantJourney.setDeltaT(deltaT);
            instantJourney.setRtDeltaT(rtDeltaT);
        }

        //get references scrB & scrF for trips before and after
        cContextModel = new ContextModel();
        String scrB = journeyObj.getString("scrB");
        String scrF = journeyObj.getString("scrF");
        cContextModel.setScrB(scrB);
        cContextModel.setScrF(scrF);

        return journeyData;
    }

    public String getScrBFromParser() { return cContextModel.getScrB();}
    public String getScrFFromParser() { return cContextModel.getScrF();}
}
