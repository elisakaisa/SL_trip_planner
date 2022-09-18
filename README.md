# SL_trip_planner

## Description

Android application that mimics SL's app. 
Trips by public transport in the Stockholm region can be planned. The app allows to choose any stop as a starting point and destination, 
as well as date and time, and also has some shortcuts to the user's most used trip (hardcoded in the app).
The trips can also be exported into the user's calendar.
This project started with me being annoyed at SL's app fro constantly deleting my favourite stops, which is why I decided to hardcode my most used trips instead.
The export to calendar function is one I added, because it was a feature I loved and used a lot in the swiss public transport app.

The application uses Trafiklab's apis:

- [route planning](https://www.trafiklab.se/api/trafiklab-apis/sl/route-planner-31/)
- [getting stop ids from stop names](https://www.trafiklab.se/api/trafiklab-apis/sl/stop-lookup/)

## Installation

Android Studio is required.

    git clone https://github.com/elisakaisa/SL_trip_planner.git

Then run gradle sync.

In order to be able to use the application, the ApiKeys can be obtained from [Trafiklab](https://www.trafiklab.se/api/).
This API key is stored in the class `ApiKey.java`, which is gitignored. The class looks like this:

```java
// app/java/com.example.sl_trip_planner/data/ApiKeys.java
public class ApiKeys {

    public static final String API_KEY_trip_planner = <API_KEY_1>;
    public static final String API_KEY_stops = <API_KEY_2>;
}
```

Also gitignored is the class `Stops.java`, where the user can store the hardcoded stops and their Ids
```java
// app/java/com.example.sl_trip_planner/data/Stops.java 
public class Stops {
    public static int STHLMC = 1080; // Stockholm city

    public static String ORIGIN_ID ="Origin ID", DESTINATION_ID = "Destination ID";
    public static String TIME = "time", DATE = "date";
    public static String SEARCHFORARRIVAL = "search for arrival";

    public static String SthlmC = "Sthlm City";
}
```

## Implementation

The home page offers the user the choice to setup the time and date or the trip and choose if they want any kind of trip or a hardcoded trip.
If the time and date are left empty, it is assumed that the user wants to leave now.
Choosing a hardcoded trip send the user immediately to the trip result page, whereas any kind of trip send the user to an activity where they have to choose the start and destination stops.
In order to get stop name suggestions, the user needs to type at least 7 characters.
Then once the user has filled all details, the trips can be viewed, the same way as with the hardcoded trips.
In the Trip RecyclerView, pressing on the trip shows real-time data and possible delays.
And from there the trip can be exported to the users calendar using calendar provider api.

<p float="left">
  <img src="https://user-images.githubusercontent.com/79315440/189426009-2bc0a748-857f-4a2a-aefe-808095351d83.jpg" width="300" />
  <img src="https://user-images.githubusercontent.com/79315440/189426103-fd73a39e-53f1-4856-badc-e5d84f5aedfb.jpg" width="300" /> 
</p>

## Known issues and improvements

Known issues:
- Some confusion between departure and arrival stops when choosing them.

Improvements:
- Add data about the train/bus line's trip (similar to the SL app)
- Add description text to the exported event
- Add data about what platform the bus/train leaves
- Fix date issue by fetching date and time from the api instead
- Improve UI and add theme

## Authors

Elisa Perini [github](https://github.com/elisakaisa) | [linkedIn](https://www.linkedin.com/in/elisa-perini-2759ba227/)
