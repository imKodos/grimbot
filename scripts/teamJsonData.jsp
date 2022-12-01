<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="org.json.simple.JSONArray"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="teams.TeamUtil" %>
<%@ page import="teams.Team" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="teams.TeamUtil.TeamName" %>


<%
JSONObject returnJo = new JSONObject();
JSONObject teamJo;
JSONArray jArray = new JSONArray();


HashMap<TeamName, Team> teamMap = new HashMap<>();
teamMap = (HashMap<TeamName, Team>) session.getAttribute("teamMap");

 //for (Team curTeam : teamMap) {
  for (HashMap.Entry<TeamName,Team> teamEntry : teamMap.entrySet()) {
  teamJo = new JSONObject();
  teamJo.put("teamId", teamEntry.getValue().getTeamId());
  teamJo.put("teamName", teamEntry.getValue().getTeamName());
  jArray.add(teamJo);
 }   

//System.out.println(TeamUtil.TeamName.getTeamByName(teamList.get(0).getTeamId()));

returnJo.put("teamData", jArray);

out.print(returnJo);

//session.setAttribute("test1", "yessir");
//out.print(session.getAttribute("test2"));
%>