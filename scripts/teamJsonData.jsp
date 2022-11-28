<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="org.json.simple.JSONArray"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="teams.TeamUtil" %>
<%@ page import="teams.Team" %>


<%
JSONObject returnJo = new JSONObject();
JSONObject teamJo;
JSONArray ja = new JSONArray();


ArrayList<Team> teamList = new ArrayList<>();

teamList = (ArrayList<Team>) session.getAttribute("teamList");

 for (Team curTeam : teamList) {
    System.out.println(curTeam.getTeamId());
  }   

teamJo = new JSONObject();
teamJo.put("EmployeeId", 1);
teamJo.put("FullName", "Nets");
ja.add(teamJo);

teamJo = new JSONObject();
teamJo.put("EmployeeId", 2);
teamJo.put("FullName", "Knicks");
ja.add(teamJo);

returnJo.put("teamData", ja);

out.print(returnJo);

//session.setAttribute("test1", "yessir");
//out.print(session.getAttribute("test2"));
//out.print("callback([{\"EmployeeId\":2,\"FullName\":\"Andrew Fuller\"}])");
%>