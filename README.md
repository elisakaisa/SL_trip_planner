# SL_trip_planner

Mobile application that mimicks SL's app. 
Trips by public transport in the stockholm region can be planned. The app allows to choose any stop as a starting point and destination, 
as well as date and time,
and also has some shortcuts to the user's most used trip (hardcoded in the app).<br>
The application uses Trafiklab's apis:
<ul>
  <li> https://www.trafiklab.se/api/trafiklab-apis/sl/route-planner-31/ for route planning </li>
  <li> https://www.trafiklab.se/api/trafiklab-apis/sl/stop-lookup/ for getting stop ids from stop names </li>
</ul>

Following files are gitignored:
<ul>
<li> app/java/com.example.sl_trip_planner/data/ApiKeys.java stores api keys that can be obtained from trafiklab's website </li>
<li> app/java/com.example.sl_trip_planner/data/Stops.java stores some stop ids to create shortcuts</li>
</ul>
