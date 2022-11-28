<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="org.json.simple.JSONArray"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="teams.TeamUtil" %>
<%@ page import="teams.Team" %>


<%
JSONObject returnJo = new JSONObject();
JSONObject teamJo;
JSONArray jArray = new JSONArray();


ArrayList<Team> teamList = new ArrayList<>();
teamList = (ArrayList<Team>) session.getAttribute("teamList");

 for (Team curTeam : teamList) {
  teamJo = new JSONObject();
  teamJo.put("teamId", curTeam.getTeamId());
  teamJo.put("teamName", curTeam.getTeamName());
  jArray.add(teamJo);
 }   

//System.out.println(TeamUtil.TeamName.getTeamByName(teamList.get(0).getTeamId()));

returnJo.put("teamData", jArray);

out.print(returnJo);

//session.setAttribute("test1", "yessir");
//out.print(session.getAttribute("test2"));
//out.print("callback([{\"EmployeeId\":2,\"FullName\":\"Andrew Fuller\"}])");
%>