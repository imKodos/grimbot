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
//https://www.balldontlie.io/api/v1/games?seasons[]=2022&page=50
//out.print(session.getAttribute("test1"));
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

    session.setAttribute("teamList", teamList);

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
   // for (Team curTeam : teamList) {
        //  out.println(curTeam.getName());
    //}     

    //prints out team info 
    for (Object curObj : teamsJson) {
        //JSONObject teamObj = (JSONObject) curObj;    
        //out.write(teamObj.get("id") + "     " + teamObj.get("name")+  "<br>"); 
    }           

%>
<html>
    <head>
    <link rel="stylesheet" href="scripts/kendo/styles/kendo.common.min.css" />
    <link rel="stylesheet" href="scripts/kendo/styles/kendo.default.min.css" />
    <link rel="stylesheet" href="scripts/kendo/styles/kendo.default.mobile.min.css" />
    <script src="scripts/jquery-3.6.1.js"></script>
    <script src="scripts/kendo/js/kendo.all.min.js"></script>
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

    .k-dropdown{
        text-align:left;
    }
    
    .k-i-arrow-60-down{
        position: initial;
    }
    </style>

    <body class="body">
        <div class="outerBorder">
            <div class="innerContent">
                <div class="topView">
                    <div>
                        <input id="team1" class="teamList" /><br><br>
                        <input id="team2" class="teamList" />
                    </div>
                    <div style="background: red">
                        <p>Match Info :</p>
                        <p>Projected score: </p>
                    </div>
                </div>
                <div class="midView">
                   <div>
                        <p>Team {1} stats</p>
                        <p>Last Game Result</p>
                        <p>Average PF</p>
                        <p>Average PA</p>
                        <p>Offensive Ranking</p>
                        <p>Defensive Ranking</p>
                    </div>
                </div>
                <div class="botView">
                   <div>
                        <p>Team {2} stats</p>
                        <p>Last Game Result</p>
                        <p>Average PF</p>
                        <p>Average PA</p>
                        <p>Offensive Ranking</p>
                        <p>Defensive Ranking</p>
                    </div>
                </div>
            </div>
        </div> 
    </body>

<html>

<%-- <script>
var dataSource = new kendo.data.DataSource({
  transport: {
    read: {
     url: "http://localhost:8080/grim/scripts/teamJsonData.jsp",
    //   read: "/grim/scripts/teamJsonData.jsp",
      dataType: "json",
      cache: false
    }
  },
 schema: {  
  model: {  
    id: "id",
  }
}  

});


$(".teamList").kendoDropDownList({
 // filter:"startswith",
  dataSource: dataSource,
  dataTextField: "id",
  dataValueField: "id"
});


//var filterInput = dropdownlist.filterInput;
</script> --%>

<script>
// need 2 datasource objects otherwise filtering one ddl will affect the other.
//todo need to figure out how to make a shared datasource obj and extract the data into a new data source obj,
// this will cut down on the code of having to do a full init on 2 data source
    var dataSource = new kendo.data.DataSource({
         transport: {
            read:  {
            url: "scripts/teamJsonData.jsp",
            dataType: "json"
            }
        },
         schema : {
             type: "json",
             data: "teamData",
    }
    });

var dataSource2 = new kendo.data.DataSource({
         transport: {
            read:  {
            url: "scripts/teamJsonData.jsp",
            dataType: "json" 
            }
        },
         schema : {
             type: "json",
             data: "teamData",
    }
    });





    $("#team1").kendoDropDownList({
         filter: "contains",
        dataSource: dataSource,
        dataTextField: "FullName",
        dataValueField: "EmployeeId"
    });


    $("#team2").kendoDropDownList({
         filter: "contains",
        dataSource: dataSource2,
        dataTextField: "FullName",
        dataValueField: "EmployeeId"
    });


</script>
