<%@ page import="stats.FetchStats" %>
<%@ page import="teams.TeamUtil" %>
<%@ page import="teams.Team" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="org.json.simple.JSONArray"%>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Optional" %>
<%
FetchStats stats = new FetchStats();
HashMap<String,String> playersPropertiesMap = new HashMap<>();
playersPropertiesMap.put("X-RapidAPI-Key", "0d5ab3a4bfmsh5174a54093fd0f6p12a4ffjsn47f368b3d6ea");
JSONObject players = stats.get("https://free-nba.p.rapidapi.com/players", "?page=0&per_page=25", playersPropertiesMap); 
JSONObject teams = stats.get("https://www.balldontlie.io/api/v1/teams"); 
ArrayList<Team> teamList = new ArrayList<>();
JSONArray teamsJson = (JSONArray) teams.get("data");  
    for(int i=0; i<teamsJson.size();i++){ //build each team -- todo maybe look into making this a map where the key is the teamId or team name enum
          JSONObject teamObj = (JSONObject)teamsJson.get(i);
          Long teamId=(Long)teamObj.get("id"); //todo figure out why these are coming over as Long, instead of int -- needed to pull data as long and convert to int
          teamList.add(new Team.Builder(TeamUtil.TeamName.NETS)
            .teamId(teamId.intValue())
            .last5PF(100)
            .last5PA(110)
            .build()
           );
    }

    //list of all team and name
   // for(TeamUtil.TeamName team : TeamUtil.TeamName.values()) {
   //     out.print("Team " + team.getId() + " is: " + team.getName()+ " <br>");
   // }

    //get team name from id
   // Optional<TeamUtil.TeamName> teamEnum  = TeamUtil.TeamName.getTeamByName(20);
    //if(teamEnum.isPresent()) {
    //    out.print(teamEnum.get().getName()); //Knicks
    //out.print(teamEnum.get()); //KNICKS
   // }
    
    
    // get enum from name
 //   Optional<TeamUtil.TeamName> teamEnum  = TeamUtil.TeamName.getTeamByName("Knicks");
  //   if(teamEnum.isPresent()) {
    //out.print(teamEnum.get().getId()); //20
    //out.print(teamEnum.get()); //KNICKS
  //  }


    //list of teams built
    for (Team curTeam : teamList) {
        //  out.println(curTeam.getName());
    }     

    for (Object curObj : teamsJson) {
        JSONObject teamObj = (JSONObject) curObj;    
        out.write(teamObj.get("id") + "     " + teamObj.get("name")+  "<br>");
    }           

%>
<html>
    <head>
    </head>

    <style>
    .body{
        background:white;
    }
    .body > div{
        color:dodgerblue;
    }
    .outerBorder {
        border: solid black;
        border-radius: 15px;
        margin:4%;
        height: 80%;
    }
    .innerContent{
        display: flex;
        justify-content:center;
        flex-direction: column;
        min-height:100%;
    }
    .innerContent > div{
        flex:1;
        text-align: center;
        align-self: stretch;   
    }

    .topView{
        background:black;
        display:flex;
        flex-direction:row;
    }

    .topView > div {
        flex:1;
    }
    
    .midView{
        background: green;
    }

    .botView{
        background: yellow;
    }
    </style>

    <body class="body">
        <div class="outerBorder">
            <div class="innerContent">
                <div class="topView">
                    <div>
                        A1
                    </div>
                    <div style="background: red">
                        Match Info 
                    </div>
                </div>
                <div class="midView">
                   <div>
                        A2
                    </div>
                </div>
                <div class="botView">
                   <div>
                        A3
                    </div>
                </div>
            </div>
        </div> 
    </body>

<html>
