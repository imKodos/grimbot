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
          Long longId=(Long)teamObj.get("id");//comes from json as a long
          int teamId = longId.intValue();
          teamList.add(new Team.Builder(TeamUtil.TeamName.getById(teamId))
            .teamName(TeamUtil.TeamName.getTeamByName(teamId).get().getName())
            .teamId(teamId)
            .last5PF(100)
            .last5PA(110)
            .build()
           );
    }

    session.setAttribute("teamList", teamList);
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
                     <p>
                        <input id="t1" class="teamList" />
                      </p>
                      <p style="padding-top: 20px">
                        <input id="t2" class="teamList" />
                      </p>
                    </div>
                    <div style="background: red">
                        <p>Projected Score: </p>
                        <p>Projected Total:</p>
                    </div>
                </div>
                <div class="midView">
                   <div>
                        <p id="t1Name">Team 1 Name</p>
                        <p>Last Game Result</p>
                        <p>Average PF</p>
                        <p>Average PA</p>
                        <p>Offensive Ranking</p>
                        <p>Defensive Ranking</p>
                    </div>
                </div>
                <div class="botView">
                   <div>
                        <p id="t2Name">Team 2 Name</p>
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

<script>
   //slightly lame, but need to pull the json data from teamJsonData first and then put that info into the datasource.
   //the 2 team DDLs cant share the same data source obj, otherwise it shares the same filter data.
   //this also saves an additional call to teamJsonData - I'm only doing one call now, compared to the initial 2 
   //see TestCodeAndComments ~33 [Data Source teamJsonData] for a handle on how I was doing it
   var fillTeamData = $.getJSON('scripts/teamJsonData.jsp', function(data) {
        var teamJsonData = data.teamData;
        $(".teamList").kendoDropDownList({
            filter: "contains",
            dataSource: {
                data: teamJsonData,
                sort: { field: "teamName", dir: "asc" },
            },
            dataTextField: "teamName",
            dataValueField: "teamId",
            text: "Select a Team",
            autoBind: false,
            change: teamChange
        });
    });

     function teamChange(e) {
        $("#"+e.sender.element[0].id+"Name").html('<%=TeamUtil.TeamName.getTeamByName(10).get().getName()%>');
    }

// <select id="items" onselect="javascript:reloadPage(this)">
//   <option name="item1">Item 1</option>
// </select>
    // function reloadPage(id) {
//    document.location.href = location.href + '?id=' + id.value;
// }
</script>
