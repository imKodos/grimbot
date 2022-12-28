<%@ page import="stats.FetchStats" %>

<%@ page import="teams.TeamUtil" %>
<%@ page import="teams.TeamUtil.TeamName" %>
<%@ page import="teams.Team" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="org.json.simple.JSONArray"%>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Optional" %>
<%@ page import="stats.Prediction" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>

<%
 SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");  
 Date date = new Date();  
//on entry, if theres no handle on the list of teams, generate them -- prevents unnecessary api calls

if(session.getAttribute("teamMap") == null){ 
        session.setAttribute("teamMap",  TeamUtil.generateTeamMap());
}

//if(session.getAttribute("OffrankingsList") == null){ 
       // session.setAttribute("teamMap",  TeamUtil.generateTeamMap());
    //  List<Double> offensiveRankings= new ArrayList<Double>();
       //for (HashMap.Entry<TeamName,Team> teamEntry : teamMap.entrySet()) {
  //     for(Team curTeam: (HashMap<TeamName,Team>) session.getAttribute("teamMap")){

    //   }
//}

Team team1 = TeamUtil.validateTeam(request.getParameter("t1"), (HashMap<TeamName, Team>) session.getAttribute("teamMap"));
Team team2 = TeamUtil.validateTeam(request.getParameter("t2"), (HashMap<TeamName, Team>) session.getAttribute("teamMap")); 
Prediction p = new Prediction(team1, team2);

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
        background: black;
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
                        <input id="t1" class="teamList" value="<%=team1.getTeamId()%>" />
                      </p>
                      <p style="padding-top: 20px">
                        <input id="t2" class="teamList" value="<%=team2.getTeamId()%>" />
                      </p>
                    </div>
                    <div style="background: black">
                        <p>Projected Score: <%=p.getScorePrediction()%></p>
                        <p>Projected Total: <%=p.getTotalScorePrediction()%></p>
                        <p><%=team1.getShortName() + " variance: " + p.getT1Variance()%></p>
                        <p><%=team2.getShortName() + " variance: " + p.getT2Variance()%></p>
                    </div>
                </div>
                <div class="midView">
                   <div>
                        <p id="t1Name">Team 1: <%=team1.getTeamName() + "  (" + team1.getShortName()+") " + team1.getTotalWins() +"-" +team1.getTotalLoss()%></p>
                        <p>Last Game Result: <%=team1.getLastGameInfo()%></p>
                        <p><%=team1.getNextGameInfo()%></p>
                        <p>Last PF: <%=team1.getLastPF()%></p>
                        <p>Last PA: <%=team1.getLastPA()%></p>
                        <p>Last 5 PF Average: <%=team1.getLast5PF()%></p>
                        <p>Last 5 PA Average: <%=team1.getLast5PA()%></p>
                        <p>Last 10 PF Average: <%=team1.getLast10PF()%></p>
                        <p>Last 10 PA Average: <%=team1.getLast10PA()%></p>
                        <p>Season PPG: <%=team1.getSeasonAvgPf()%></p>
                        <p>Season OPPG: <%=team1.getSeasonAvgPa()%></p>
                        <p>Days Rest: <%=team1.getDaysRested()%></p>  
                        <p>is home team: <%=team1.isHomeTeam()%></p>               
                        <p>Last 2 home PF: <%=team1.getLast2HomePf()%></p>               
                        <p>Last 2 home PA: <%=team1.getLast2HomePa()%></p> 
                        <p>Last 5 home PF: <%=team1.getLast5HomePf()%></p>               
                        <p>Last 5 home PA: <%=team1.getLast5HomePa()%></p>                 
                        <p>Last 2 away PF: <%=team1.getLast2AwayPf()%></p>               
                        <p>Last 2 away PA: <%=team1.getLast2AwayPa()%></p>              
                        <p>Last 5 away PF: <%=team1.getLast5AwayPf()%></p>               
                        <p>Last 5 away PA: <%=team1.getLast5AwayPa()%></p>               
                        <p>home Streak <%=team1.isHomeHotStreak()%></p>               
                        <p>home cold Streak <%=team1.isHomeColdStreak()%></p>                
                        <p>away Streak <%=team1.isAwayHotStreak()%></p>               
                        <p>away cold Streak <%=team1.isAwayColdStreak()%></p>               
                        <p>num Injuries <%=team1.getNumStartersInjured()%></p>  

                        <p>Injured Players List: 
                        <%
                        if(team1.getInjuredPlayers()==null || team1.getInjuredPlayers().isEmpty()){
                             out.print(" None");
                        }else{
                            for(String s: team1.getInjuredPlayers()){
                                out.print("<br>"+s);
                            }
                        }                   
                        %>
                        </p>               
                    </div>
                </div>
                <div class="botView">
                  <div>
                        <p id="t2Name">Team 2: <%=team2.getTeamName() + "  (" + team2.getShortName()+") " + team2.getTotalWins() +"-" +team1.getTotalLoss()%></p>
                        <p>Last Game Result: <%=team2.getLastGameInfo()%></p>
                        <p><%=team2.getNextGameInfo()%></p>
                        <p>Last PF: <%=team2.getLastPF()%></p>
                        <p>Last PA: <%=team2.getLastPA()%></p>
                        <p>Last 5 PF Average: <%=team2.getLast5PF()%></p>
                        <p>Last 5 PA Average: <%=team2.getLast5PA()%></p>
                        <p>Last 10 PF Average: <%=team2.getLast10PF()%></p>
                        <p>Last 10 PA Average: <%=team2.getLast10PA()%></p>
                        <p>Season PPG: <%=team2.getSeasonAvgPf()%></p>
                        <p>Season OPPG: <%=team2.getSeasonAvgPa()%></p>
                        <p>Days Rest: <%=team2.getDaysRested()%></p>  
                        <p>is home team: <%=team2.isHomeTeam()%></p>               
                        <p>Last 2 home PF: <%=team2.getLast2HomePf()%></p>               
                        <p>Last 2 home PA: <%=team2.getLast2HomePa()%></p>  
                        <p>Last 5 home PF: <%=team2.getLast5HomePf()%></p>               
                        <p>Last 5 home PA: <%=team2.getLast5HomePa()%></p>              
                        <p>Last 2 away PF: <%=team2.getLast2AwayPf()%></p>               
                        <p>Last 2 away PA: <%=team2.getLast2AwayPa()%></p>                           
                        <p>Last 5 away PF: <%=team2.getLast5AwayPf()%></p>               
                        <p>Last 5 away PA: <%=team2.getLast5AwayPa()%></p>               
                        <p>home Streak <%=team2.isHomeHotStreak()%></p>               
                        <p>home cold Streak <%=team2.isHomeColdStreak()%></p>                
                        <p>away Streak <%=team2.isAwayHotStreak()%></p>               
                        <p>away cold Streak <%=team2.isAwayColdStreak()%></p>               
                        <p>num Injuries <%=team2.getNumStartersInjured()%></p>  

                        <p>Injured Players List: 
                        <%
                        if(team2.getInjuredPlayers()==null || team2.getInjuredPlayers().isEmpty()){
                             out.print(" None");
                        }else{
                            for(String s: team2.getInjuredPlayers()){
                                out.print("<br>"+s);
                            }
                        }                   
                        %>
                        </p>               
                    </div>
                </div>
                <div id="wpWriteUp">
                <p>
                <%=team1.getFullName() + (team1.isHomeTeam()?" vs ": " @ ") + team2.getFullName() + " Prediction and Picks NBA (" + formatter.format(date) +")" %> 
                </p>
                <p>
                Let's analyze the matchup between the <%=team1.getFullName() + " (" + team1.getTotalWins()+"-" + team1.getTotalLoss()+")"%>
                &nbsp;and the <%=team2.getFullName() + " (" + team2.getTotalWins()+"-" +team2.getTotalLoss()+")"%>
                </p>
                <h2>
                <%=team1.getFullName()%>
                </h2>
                <p>
                The result from the last <%=team1.getTeamName()%> game was:<br>
                <%=team1.getLastGameInfo()%><br>
                If we take a look deeper into the team stats:
                <p>Days Rest: <%=team1.getDaysRested()%></p>
                <p>Last 5 PF Average: <%=team1.getLast5PF()%></p>
                <p>Last 5 PA Average: <%=team1.getLast5PA()%></p>
                <p>Last 10 PF Average: <%=team1.getLast10PF()%></p>
                <p>Last 10 PA Average: <%=team1.getLast10PA()%></p>
                <% if(team1.isHomeTeam()){ %>
                <p>Last 2 home PF: <%=team1.getLast2HomePf()%></p>               
                <p>Last 2 home PA: <%=team1.getLast2HomePa()%></p> 
                <p>Last 5 home PF: <%=team1.getLast5HomePf()%></p>               
                <p>Last 5 home PA: <%=team1.getLast5HomePa()%></p> 
                  <%if(team1.isHomeHotStreak()){ %>
                   <p> <%=team1.getTeamName() + " is currently on a hot streak at home."%></p>
                  <%} %>
                  <%if(team1.isHomeColdStreak()){ %>
                    <p> <%=team1.getTeamName() +" is currently on a cold streak at home."%></p>
                  <%} %>
                <% }else{ %>
                <p>Last 2 away PF: <%=team1.getLast2AwayPf()%></p>               
                <p>Last 2 away PA: <%=team1.getLast2AwayPa()%></p>              
                <p>Last 5 away PF: <%=team1.getLast5AwayPf()%></p>               
                <p>Last 5 away PA: <%=team1.getLast5AwayPa()%></p> 
                    <%if(team1.isAwayHotStreak()){ %>
                   <p> <%=team1.getTeamName() +" is currently on a hot streak on the road."%></p>
                  <%} %>
                  <%if(team1.isAwayColdStreak()){ %>
                    <p> <%=team1.getTeamName() +" is currently on a cold streak on the road."%></p>
                  <%} %>
                <% } %>
                </p>
                <p>There's <%=team1.getNumStartersInjured() +" injured players on the starting lineup"%></p>  
                <p><%if(team1.getNumStartersInjured()>0){%>
                <%
                if(team1.getInjuredPlayers()==null || team1.getInjuredPlayers().isEmpty()){
                        out.print(" None");
                }else{
                    for(String s: team1.getInjuredPlayers()){
                        out.print("<br>"+s);
                    }
                }                   
                }%>
                </p> 
                 <h2>
                <%=team2.getFullName()%>
                </h2>
                <p>
                The result from the last <%=team2.getTeamName()%> game was:<br>
                <%=team2.getLastGameInfo()%><br>
                If we take a look deeper into the team stats:
                <p>Days Rest: <%=team2.getDaysRested()%></p>
                <p>Last 5 PF Average: <%=team2.getLast5PF()%></p>
                <p>Last 5 PA Average: <%=team2.getLast5PA()%></p>
                <p>Last 10 PF Average: <%=team2.getLast10PF()%></p>
                <p>Last 10 PA Average: <%=team2.getLast10PA()%></p>
                <% if(team2.isHomeTeam()){ %>
                <p>Last 2 home PF: <%=team2.getLast2HomePf()%></p>               
                <p>Last 2 home PA: <%=team2.getLast2HomePa()%></p> 
                <p>Last 5 home PF: <%=team2.getLast5HomePf()%></p>               
                <p>Last 5 home PA: <%=team2.getLast5HomePa()%></p> 
                  <%if(team2.isHomeHotStreak()){ %>
                   <p> <%=team2.getTeamName() + " is currently on a hot streak at home."%></p>
                  <%} %>
                  <%if(team2.isHomeColdStreak()){ %>
                    <p> <%=team2.getTeamName() +" is currently on a cold streak at home."%></p>
                  <%} %>
                <% }else{ %>
                <p>Last 2 away PF: <%=team2.getLast2AwayPf()%></p>               
                <p>Last 2 away PA: <%=team2.getLast2AwayPa()%></p>              
                <p>Last 5 away PF: <%=team2.getLast5AwayPf()%></p>               
                <p>Last 5 away PA: <%=team2.getLast5AwayPa()%></p> 
                    <%if(team2.isAwayHotStreak()){ %>
                   <p> <%=team2.getTeamName() +" is currently on a hot streak on the road."%></p>
                  <%} %>
                  <%if(team2.isAwayColdStreak()){ %>
                    <p> <%=team2.getTeamName() +" is currently on a cold streak on the road."%></p>
                  <%} %>
                <% } %>
                </p>
                <p>There's <%=team2.getNumStartersInjured() +" injured players on the starting lineup"%></p>  
                <p><%if(team2.getNumStartersInjured()>0){%>
                <%
                if(team2.getInjuredPlayers()==null || team2.getInjuredPlayers().isEmpty()){
                        out.print(" None");
                }else{
                    for(String s: team2.getInjuredPlayers()){
                        out.print("<br>"+s);
                    }
                }                   
                }%>
                </p> 
                <h2>
                Prediction and Picks
                </h2>
                <p>Projected Score: <%=p.getScorePrediction()%></p>
                <p>Projected Total: <%=p.getTotalScorePrediction()%></p>
                <p></p>
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
            change: teamChange,
            autoBind:false
        });

        //read in / set default values of the DDL on refresh if we have a value
        if($('#t1').val() != "" && $('#t1').val() != -1 && $('#t1').val() != "null"){
            $('#t1').data('kendoDropDownList').dataSource.read();
        }
          if($('#t2').val() != "" && $('#t2').val() != -1 && $('#t2').val() != "null"){
            $('#t2').data('kendoDropDownList').dataSource.read();
        }
    });

     function teamChange(e) {
    // I could avoid doing a page refresh here, but would be awkward to change the ui text and use the compare teams from TeamUtils
    //pros: this is more light weight, only uses the teams as needed
    //cons: does a page refresh and looks awful
      var t1Value= $("#t1").val() != "" ? $("#t1").val() : "-1";
      var t2Value= $("#t2").val() != "" ? $("#t2").val() : "-1";
      document.location.href = '?t1=' + t1Value + '&t2=' +t2Value;
    // alert(e.sender.element[0].id); // returns id of which ddl was changed
    }
</script>
