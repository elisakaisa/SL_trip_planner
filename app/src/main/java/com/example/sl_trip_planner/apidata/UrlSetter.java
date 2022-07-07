package com.example.sl_trip_planner.apidata;

public class UrlSetter {

    public static String setSearchUrl(String stopName) {
        String ApiKey = ApiKeys.API_KEY_stops;
        return "https://api.sl.se/api2/typeahead.json?key=" + ApiKey + "&searchstring=" + stopName;
    }

    public static String setTripUrl(int originId, int destinationId, String time, String date, int searchForArrival) {
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
}
