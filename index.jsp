<%@ page import="stats.FetchStats" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="org.json.simple.JSONArray"%>
<%
FetchStats stats = new FetchStats();
HashMap<String,String> playersPropertiesMap = new HashMap<>();
playersPropertiesMap.put("X-RapidAPI-Key", "0d5ab3a4bfmsh5174a54093fd0f6p12a4ffjsn47f368b3d6ea");
JSONObject players = stats.get("https://free-nba.p.rapidapi.com/players", "?page=0&per_page=25", playersPropertiesMap); 
JSONObject teams = stats.get("https://www.balldontlie.io/api/v1/teams"); 
//out.write(players.toString());
//out.write(teams.toString());

//out.write(teams.get("data").get("division"));

        
          //     JSONArray dataArr = (JSONArray) teams.get("data");
          //     System.out.println(dataArr.get());

              //for (int i = 0; i < teams.size(); i++) {
               // System.out.println(i);
              //      JSONObject new_obj = (JSONObject) teams.get(i);

                  //  if (new_obj.get("division").equals("Southeast")) {
                 //       System.out.println("team: " + new_obj.get("full_name"));
                //        break;
                //    }
              //  }

                
%>
<%-- {
    "data": [
        {
            "id": 1,
            "abbreviation": "ATL",
            "city": "Atlanta",
            "conference": "East",
            "division": "Southeast",
            "full_name": "Atlanta Hawks",
            "name": "Hawks"
        },
        {
            "id": 2,
            "abbreviation": "BOS",
            "city": "Boston",
            "conference": "East",
            "division": "Atlantic",
            "full_name": "Boston Celtics",
            "name": "Celtics"
        },
        {
            "id": 3,
            "abbreviation": "BKN",
            "city": "Brooklyn",
            "conference": "East",
            "division": "Atlantic",
            "full_name": "Brooklyn Nets",
            "name": "Nets"
        },
        {
            "id": 4,
            "abbreviation": "CHA",
            "city": "Charlotte",
            "conference": "East",
            "division": "Southeast",
            "full_name": "Charlotte Hornets",
            "name": "Hornets"
        }, --%>
<html>
    <head>
    </head>

    <style>
    </style>

    <body>
        <div>
            Test Grimmys
        </div>
    </body>

<html>
