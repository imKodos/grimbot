<%@ page import="stats.FetchStats" %>

<%@ page import="teams.TeamUtil" %>
<%@ page import="teams.TeamUtil.TeamName" %>
<%@ page import="teams.Team" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.TreeSet" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
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

    pre {
     /* text-align: left; */
     white-space: pre-line;   
    }

    </style>

    <body class="body">
        <div class="outerBorder">
            <div class="innerContent">
                <div class="topView">
                    <div>
                     <p>
                        <input id="t1" class="teamList" value="<%=team1.getTeamId()%>" /><br>
                        <label for="t1MotivationLoss"> Loss of Motivation</label><input type="checkbox" id="t1MotivationLoss" name="t1MotivationLoss" onchange="teamChange()"><br>
                        <label for="t1Motivation">Motivation</label><input type="checkbox" id="t1Motivation" name="t1Motivation"  onchange="teamChange()"><br>
                        <label for="t1ReturningStar"> Returning Star</label><input type="checkbox" id="t1ReturningStar" name="t1ReturningStar" onchange="teamChange()">
                      </p>
                      <p style="padding-top: 20px">
                        <input id="t2" class="teamList" value="<%=team2.getTeamId()%>" /><br>
                        <label for="t2MotivationLoss"> Loss of Motivation</label><input type="checkbox" id="t2MotivationLoss" name="t2MotivationLoss"  onchange="teamChange()"><br>
                        <label for="t2Motivation">Motivation</label><input type="checkbox" id="t2Motivation" name="t2Motivation"  onchange="teamChange()"><br>
                        <label for="t2ReturningStar"> Returning Star</label><input type="checkbox" id="t2ReturningStar" name="t2ReturningStar" onchange="teamChange()">
                      </p>
                    </div>
                    <div style="background: black">
                        <p>Projected Score: <%=p.getScorePrediction()%></p>
                        <p>Projected Total: <%=p.getTotalScorePrediction()%></p>
                        <p>Grim Score: <%=p.getGrimScorePrediction()%></p>
                        <p>Grim Total: <%=p.getGrimTotalScorePrediction()%></p>
                        <p>Luci Score: <%=p.getLuciScorePrediction()%></p>
                        <p>Luci Total: <%=p.getLuciTotalScorePrediction()%></p>
                        <p>Doom Score: <%=p.getDoomScorePrediction()%></p>
                        <p>Doom Total: <%=p.getDoomTotalScorePrediction()%></p>
                        <p><%=team1.getShortName() + " variance: " + p.getT1Variance()%></p>
                        <p><%=team2.getShortName() + " variance: " + p.getT2Variance()%></p>
                    </div>
                </div>
                <div class="midView">
                   <div>
                        <p id="t1Name">Team 1: <%=team1.getTeamName() + "  (" + team1.getShortName()+") " + team1.getTotalWins() +"-" +team1.getTotalLoss()%></p>
                        <p>Last Game Result: <%=team1.getLastGameInfo()%></p>
                        <p><%=team1.getNextGameInfo()%></p>
                        <p>O Rank: <%=team1.getORank()%></p>
                        <p>D Rank: <%=team1.getDRank()%></p>
                        <p>O Rank in last 10 games: <%=team1.getO10Rank()%></p>
                        <p>D Rank in last 10 games: <%=team1.getD10Rank()%></p>
                        <p>Last PF: <%=team1.getLastPF()%>&nbsp;(Normalized: <%=team1.getNormalizedLastPf()%>)</p>
                        <p>Last PA: <%=team1.getLastPA()%>&nbsp;(Normalized: <%=team1.getNormalizedLastPa()%>)</p>
                        <p>Last Opp O Rank: <%=team1.getLastOppORank()%></p>
                        <p>Last Opp D Rank: <%=team1.getLastOppDRank()%></p>
                        <p>Last 5 PF Average: <%=team1.getLast5PF()%>&nbsp;(Normalized: <%=team1.getNormalizedLast5Pf()%> Avg Opp Rank: <%=team1.getLast5OppDRank()%>)</p>
                        <p>Last 5 PA Average: <%=team1.getLast5PA()%>&nbsp;(Normalized: <%=team1.getNormalizedLast5Pa()%> Avg Opp Rank: <%=team1.getLast5OppORank()%>)</p>
                        <p>Last 10 PF Average: <%=team1.getLast10PF()%>&nbsp;(Normalized: <%=team1.getNormalizedLast10Pf()%> Avg Opp Rank: <%=team1.getLast10OppDRank()%>)</p>
                        <p>Last 10 PA Average: <%=team1.getLast10PA()%>&nbsp;(Normalized: <%=team1.getNormalizedLast10Pa()%> Avg Opp Rank: <%=team1.getLast10OppORank()%>)</p>
                        <p>Last 5 PF vs team over 500: <%=team1.getLast5Over500Pf()%></p>
                        <p>Last 5 PA vs team over 500: <%=team1.getLast5Over500Pa()%></p>
                        <p>Last 5 PF vs team under 500: <%=team1.getLast5Under500Pf()%></p>
                        <p>Last 5 PA vs team under 500: <%=team1.getLast5Under500Pa()%></p>
                        <p>Last 10 PF vs team over 500: <%=team1.getLast10Over500Pf()%></p>
                        <p>Last 10 PA vs team over 500: <%=team1.getLast10Over500Pa()%></p>
                        <p>Last 10 PF vs team under 500: <%=team1.getLast10Under500Pf()%></p>
                        <p>Last 10 PA vs team under 500: <%=team1.getLast10Under500Pa()%></p>
                        <p>Season PPG: <%=team1.getSeasonAvgPf()%></p>
                        <p>Season OPPG: <%=team1.getSeasonAvgPa()%></p>
                        <p>Days Rest: <%=team1.getDaysRested()%></p>  
                        <p>is home team: <%=team1.isHomeTeam()%></p>               
                        <p>Last 2 home PF: <%=team1.getLast2HomePf()%>&nbsp;(Normalized: <%=team1.getNormalizedLast2HomePf()%> Avg Opp Rank: <%=team1.getLast2HomeOppDRank()%>)</p>               
                        <p>Last 2 home PA: <%=team1.getLast2HomePa()%>&nbsp;(Normalized: <%=team1.getNormalizedLast2HomePa()%> Avg Opp Rank: <%=team1.getLast2HomeOppORank()%>)</p>  
                        <p>Last 5 home PF: <%=team1.getLast5HomePf()%>&nbsp;(Normalized: <%=team1.getNormalizedLast5HomePf()%> Avg Opp Rank: <%=team1.getLast5HomeOppDRank()%>)</p>                
                        <p>Last 5 home PA: <%=team1.getLast5HomePa()%>&nbsp;(Normalized: <%=team1.getNormalizedLast5HomePa()%> Avg Opp Rank: <%=team1.getLast5HomeOppORank()%>)</p>                  
                        <p>Last 2 away PF: <%=team1.getLast2AwayPf()%>&nbsp;(Normalized: <%=team1.getNormalizedLast2AwayPf()%> Avg Opp Rank: <%=team1.getLast2AwayOppDRank()%>)</p>                
                        <p>Last 2 away PA: <%=team1.getLast2AwayPa()%>&nbsp;(Normalized: <%=team1.getNormalizedLast2AwayPa()%> Avg Opp Rank: <%=team1.getLast2AwayOppORank()%>)</p>               
                        <p>Last 5 away PF: <%=team1.getLast5AwayPf()%>&nbsp;(Normalized: <%=team1.getNormalizedLast5AwayPf()%> Avg Opp Rank: <%=team1.getLast5AwayOppDRank()%>)</p>                
                        <p>Last 5 away PA: <%=team1.getLast5AwayPa()%>&nbsp;(Normalized: <%=team1.getNormalizedLast5AwayPa()%> Avg Opp Rank: <%=team1.getLast5AwayOppORank()%>)</p>  
                        <p>Win Streak: <%=team1.isHotStreak()%></p>
                        <p>Lose Streak: <%=team1.isColdStreak()%></p>      
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
                        <p id="t2Name">Team 2: <%=team2.getTeamName() + "  (" + team2.getShortName()+") " + team2.getTotalWins() +"-" +team2.getTotalLoss()%></p>
                        <p>Last Game Result: <%=team2.getLastGameInfo()%></p>
                        <p><%=team2.getNextGameInfo()%></p>
                        <p>O Rank: <%=team2.getORank()%></p>
                        <p>D Rank: <%=team2.getDRank()%></p>
                        <p>O Rank in last 10 games: <%=team2.getO10Rank()%></p>
                        <p>D Rank in last 10 games: <%=team2.getD10Rank()%></p>
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
                        <p>Win Streak: <%=team2.isHotStreak()%></p>
                        <p>Lose Streak: <%=team2.isColdStreak()%></p>       
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
                <h2><%=team1.getFullName() + (team1.isHomeTeam()?" vs ": " @ ") + team2.getFullName() + " NBA Prediction, Picks, and Stats (" + formatter.format(date) +")" %> </h2>
                </p>
                <p>
                Let's analyze the matchup between the <%=team1.getFullName() + " (" + team1.getTotalWins()+"-" + team1.getTotalLoss()+")"%>
                &nbsp;and the <%=team2.getFullName() + " (" + team2.getTotalWins()+"-" +team2.getTotalLoss()+")"%>
                </p>
                <h1>
                Game Line TBD
                </h1>
                <h4>
                <%=team1.getFullName()%>
                </h4>
                <p>
                The result from the last <%=team1.getTeamName()%> game was:<br>
                <%=team1.getLastGameInfo()%><br><br>
                If we take a look deeper into the team stats:
                <pre>
                Days Rest: <%=team1.getDaysRested()%>
                Offensive Rank: <%=team1.getORank()%>
                Offensive Rank in last 10 games: <%=team1.getO10Rank()%>
                Defensive Rank: <%=team1.getDRank()%>
                Defensive Rank in last 10 games: <%=team1.getD10Rank()%>
                Last 5 PF Average: <%=team1.getLast5PF()%>  (Avg Def Rank: <%=team1.getLast5OppDRank()%>)
                Last 5 PA Average: <%=team1.getLast5PA()%> (Avg Off Rank: <%=team1.getLast5OppORank()%>)
                Last 10 PF Average: <%=team1.getLast10PF()%> (Avg Def Rank: <%=team1.getLast10OppDRank()%>)
                Last 10 PA Average: <%=team1.getLast10PA()%> (Avg Off Rank: <%=team1.getLast10OppORank()%>)
                <% if(team2.getTotalWins()>team2.getTotalLoss()){ %>
                    Last 5 PF vs above .500 team: <%=team1.getLast5Over500Pf()%>
                    Last 5 PA vs above .500 team: <%=team1.getLast5Over500Pa()%>
                    Last 10 PF vs above .500 team: <%=team1.getLast10Over500Pf()%>
                    Last 10 Pa vs above .500 team: <%=team1.getLast10Over500Pa()%>
                <% }else{ %>
                    Last 5 PF vs below .500 team: <%=team1.getLast5Under500Pf()%>
                    Last 5 PA vs below .500 team: <%=team1.getLast5Under500Pa()%>
                    Last 10 PF vs below .500 team: <%=team1.getLast10Under500Pf()%>
                    Last 10 Pa vs below .500 team: <%=team1.getLast10Under500Pa()%>
                <% } %>
                <%if(team1.isHotStreak()){ %>
                    <%=team1.getFullName() + " are currently on a hot streak"%>
                <%} %>
                <%if(team1.isColdStreak()){ %>
                   <%=team1.getFullName() +" are currently on a cold streak"%>
                <%} %>
                <% if(team1.isHomeTeam()){ %>
                Last 2 home PF: <%=team1.getLast2HomePf()%>              
                Last 2 home PA: <%=team1.getLast2HomePa()%>
                Last 5 home PF: <%=team1.getLast5HomePf()%>              
                Last 5 home PA: <%=team1.getLast5HomePa()%>
                  <%if(team1.isHomeHotStreak()){ %>
                    <%=team1.getFullName() + " are currently on a hot streak at home."%>
                  <%} %>
                  <%if(team1.isHomeColdStreak()){ %>
                    <%=team1.getFullName() +" are currently on a cold streak at home."%>
                  <%} %>
                <% }else{ %>
                Last 2 away PF: <%=team1.getLast2AwayPf()%>              
                Last 2 away PA: <%=team1.getLast2AwayPa()%>             
                Last 5 away PF: <%=team1.getLast5AwayPf()%>              
                Last 5 away PA: <%=team1.getLast5AwayPa()%>
                    <%if(team1.isAwayHotStreak()){ %>
                    <%=team1.getFullName() +" are currently on a hot streak on the road."%>
                  <%} %>
                  <%if(team1.isAwayColdStreak()){ %>
                     <%=team1.getFullName() +" are currently on a cold streak on the road."%>
                  <%} %>
                <% } %>
                </pre>
                <p>There's <%=team1.getNumStartersInjured() +" injured players on the starting lineup"%>
                <%if(team1.getNumStartersInjured()>0){%>
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
                 <h4>
                <%=team2.getFullName()%>
                </h4>
                <p>
                The result from the last <%=team2.getTeamName()%> game was:<br>
                <%=team2.getLastGameInfo()%><br><br>
                If we take a look deeper into the team stats:
                <pre>
                Days Rest: <%=team2.getDaysRested()%>
                Offensive Rank: <%=team2.getORank()%>
                Offensive Rank in last 10 games: <%=team2.getO10Rank()%>
                Defensive Rank: <%=team2.getDRank()%>
                Defensive Rank in last 10 games: <%=team2.getD10Rank()%>
                Last 5 PF Average: <%=team2.getLast5PF()%>  (Avg Def Rank: <%=team2.getLast5OppDRank()%>)
                Last 5 PA Average: <%=team2.getLast5PA()%> (Avg Off Rank: <%=team2.getLast5OppORank()%>)
                Last 10 PF Average: <%=team2.getLast10PF()%> (Avg Def Rank: <%=team2.getLast10OppDRank()%>)
                Last 10 PA Average: <%=team2.getLast10PA()%> (Avg Off Rank: <%=team2.getLast10OppORank()%>)
                  <% if(team1.getTotalWins()>team1.getTotalLoss()){ %>
                    Last 5 PF vs above .500 team: <%=team2.getLast5Over500Pf()%>
                    Last 5 PA vs above .500 team: <%=team2.getLast5Over500Pa()%>
                    Last 10 PF vs above .500 team: <%=team2.getLast10Over500Pf()%>
                    Last 10 Pa vs above .500 team: <%=team2.getLast10Over500Pa()%>
                <% }else{ %>
                    Last 5 PF vs below .500 team: <%=team2.getLast5Under500Pf()%>
                    Last 5 PA vs below .500 team: <%=team2.getLast5Under500Pa()%>
                    Last 10 PF vs below .500 team: <%=team2.getLast10Under500Pf()%>
                    Last 10 Pa vs below .500 team: <%=team2.getLast10Under500Pa()%>
                <% } %>
                  <%if(team2.isHotStreak()){ %>
                    <%=team2.getFullName() + " are currently on a hot streak"%>
                  <%} %>
                  <%if(team2.isColdStreak()){ %>
                   <%=team2.getFullName() +" are currently on a cold streak"%>
                  <%} %>
                <% if(team2.isHomeTeam()){ %>
                Last 2 home PF: <%=team2.getLast2HomePf()%>              
                Last 2 home PA: <%=team2.getLast2HomePa()%>
                Last 5 home PF: <%=team2.getLast5HomePf()%>              
                Last 5 home PA: <%=team2.getLast5HomePa()%>
                  <%if(team2.isHomeHotStreak()){ %>
                    <%=team2.getFullName() + " are currently on a hot streak at home."%>
                  <%} %>
                  <%if(team2.isHomeColdStreak()){ %>
                   <%=team2.getFullName() +" are currently on a cold streak at home."%>
                  <%} %>
                <% }else{ %>
                Last 2 away PF: <%=team2.getLast2AwayPf()%>              
                Last 2 away PA: <%=team2.getLast2AwayPa()%>             
                Last 5 away PF: <%=team2.getLast5AwayPf()%>              
                Last 5 away PA: <%=team2.getLast5AwayPa()%>
                    <%if(team2.isAwayHotStreak()){ %>
                   <%=team2.getFullName() +" are currently on a hot streak on the road."%>
                  <%} %>
                  <%if(team2.isAwayColdStreak()){ %>
                    <%=team2.getFullName() +" are currently on a cold streak on the road."%>
                  <%} %>
                <% } %>
                </pre>
                <p>There's <%=team2.getNumStartersInjured() +" injured players on the starting lineup"%>
                <%if(team2.getNumStartersInjured()>0){%>
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
                <h4>
                NBA Prediction and Picks
                </h4>
                <p><b>Projected Score:</b> <%=p.getScorePrediction()%><br>
                <b>Projected Total:</b> <%=p.getTotalScorePrediction()%></p>
                <p></p>
                <p>
                <b><u>Grim Prediction Model</u></b><br>
                <b>Grim Score:</b> <%=p.getGrimScorePrediction()%><br>
                <b>Grim Total:</b> <%=p.getGrimTotalScorePrediction()%><br>
                <b>Grim Pick: TBD</b>
                </p>
                <p></p>
                <p>
                <b><u>Lucifer Prediction Model</u></b><br>
                <b>Lucifer Score:</b> <%=p.getLuciScorePrediction()%><br>
                <b>Lucifer Total:</b> <%=p.getLuciTotalScorePrediction()%><br>
                <b>Lucifer Pick: TBD</b>
                </p>
                <p></p>
                <p>
                <b><u>Doom Prediction Model</u></b><br>
                <b>Doom Score:</b> <%=p.getDoomScorePrediction()%><br>
                <b>Doom Total:</b> <%=p.getDoomTotalScorePrediction()%><br>
                <b>Doom Pick: TBD</b> 
                </p>
                <p><b>
                Pick: TBD
                </b></p>
                <p></p>
                </div>
            </div>
        </div> 
    </body>

<html>

<script>
    if(<%=request.getParameter("t1ml")!=null%>){
       $('#t1MotivationLoss').attr('checked', true); 
    }
    if(<%=request.getParameter("t1m")!=null%>){
       $('#t1Motivation').attr('checked', true); 
    }
    if(<%=request.getParameter("t1s")!=null%>){
       $('#t1ReturningStar').attr('checked', true); 
    }
    if(<%=request.getParameter("t2ml")!=null%>){
       $('#t2MotivationLoss').attr('checked', true); 
    }
    if(<%=request.getParameter("t2m")!=null%>){
       $('#t2Motivation').attr('checked', true); 
    }
    if(<%=request.getParameter("t2s")!=null%>){
       $('#t2ReturningStar').attr('checked', true); 
    }
</script>
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

      var t1MotivLoss = $("#t1MotivationLoss").is(':checked') ? "&t1ml" :"";
      var t1Motiv = $("#t1Motivation").is(':checked') ? "&t1m" :"";
      var t1Star = $("#t1ReturningStar").is(':checked') ? "&t1s" :"";
      var t1Attr = t1MotivLoss + t1Motiv + t1Star + "";
     
      var t2MotivLoss = $("#t2MotivationLoss").is(':checked') ? "&t2ml" :"";
      var t2Motiv = $("#t2Motivation").is(':checked') ? "&t2m" :"";
      var t2Star = $("#t2ReturningStar").is(':checked') ? "&t2s" :"";
      var t2Attr = t2MotivLoss + t2Motiv + t2Star + "";

     document.location.href = '?t1=' + t1Value + '&t2=' +t2Value + t1Attr + t2Attr;
    // alert(e.sender.element[0].id); // returns id of which ddl was changed
    }
</script>
