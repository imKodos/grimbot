package teams;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import stats.FetchStats;

public class TeamUtil {

    public enum TeamName {
        CELTICS(2, "Celtics"), NETS(3, "Nets"), KNICKS(20, "Knicks"), SIXERS(23, "76ers"), RAPTORS(28, "Raptors"), // atlantic
        BULLS(5, "Bulls"), CAVALIERS(6, "Cavaliers"), PISTONS(9, "Pistons"), PACERS(12, "Pacers"), BUCKS(17, "Bucks"), // central
        HAWKS(1, "Hawks"), HORNETS(4, "Hornets"), HEAT(16, "Heat"), MAGIC(22, "Magic"), WIZARDS(30, "Wizards"), // Southeast
        NUGGETS(8, "Nuggets"), TIMBERWOLVES(18, "Timberwolves"), BLAZERS(25, "Blazers"), JAZZ(29, "Jazz"),
        THUNDER(21, "Thunder"), // northwest
        WARRIORS(10, "Warriors"), CLIPPERS(13, "Clippers"), LAKERS(14, "Lakers"), SUNS(24, "Suns"), KINGS(26, "Kings"), // pacific
        MAVERICKS(7, "Mavericks"), ROCKETS(11, "Rockets"), GRIZZLIES(15, "Grizzlies"), PELICANS(19, "Pelicans"),
        SPURS(27, "Spurs"), // southwest
        UNKNOWN(-1, "N/A");

        private final int tId;
        private final String tName;

        TeamName(final int id, final String name) {
            tId = id;
            tName = name;
        }

        public String getName() {
            return tName;
        }

        public int getId() {
            return tId;
        }

        public static Optional<TeamName> getTeamByName(String value) {
            return Arrays.stream(TeamName.values())
                    .filter(teamName -> teamName.tName.equals(value))
                    .findFirst();
        }

        public static Optional<TeamName> getTeamByName(int value) {
            return Arrays.stream(TeamName.values())
                    .filter(teamName -> teamName.tId == value)
                    .findFirst();
        }

        public static TeamName getById(int id) {
            for (TeamName e : values()) {
                if (e.tId == (id))
                    return e;
            }
            return UNKNOWN;
        }

        public static int getId(TeamName name) {
            for (TeamName e : values()) {
                // System.out.println(e);
                if (e.equals(name))
                    return e.tId;
            }
            return -1;
        }

    }

    public static ArrayList<Team> generateTeamList()
            throws KeyManagementException, NoSuchAlgorithmException, ParseException,
            org.json.simple.parser.ParseException {
        HashMap<String, String> playersPropertiesMap = new HashMap<>();
        playersPropertiesMap.put("X-RapidAPI-Key", "0d5ab3a4bfmsh5174a54093fd0f6p12a4ffjsn47f368b3d6ea");
        // JSONObject players = stats.get("https://free-nba.p.rapidapi.com/players",
        // "?page=0&per_page=25", playersPropertiesMap);
        JSONObject teams = FetchStats.get("https://www.balldontlie.io/api/v1/teams");
        // https://www.balldontlie.io/api/v1/games?seasons[]=2022&page=50
        ArrayList<Team> teamList = new ArrayList<>();
        JSONArray teamsJson = (JSONArray) teams.get("data");
        for (int i = 0; i < teamsJson.size(); i++) { // build each team -- todo maybe look into making this a map where
                                                     // the key is the teamId or team name enum
            JSONObject teamObj = (JSONObject) teamsJson.get(i);
            Long longId = (Long) teamObj.get("id");// comes from json as a long
            int teamId = longId.intValue();
            Team team = new Team.Builder(TeamUtil.TeamName.getById(teamId))
                    .teamName(TeamUtil.TeamName.getTeamByName(teamId).get().getName())
                    .teamId(teamId)
                    .last5PF(100)
                    .last5PA(110)
                    .build();

            teamList.add(team);
        }
        return teamList;
    }

    public static HashMap<TeamName, Team> generateTeamMap()
            throws KeyManagementException, NoSuchAlgorithmException, ParseException,
            org.json.simple.parser.ParseException {
        HashMap<String, String> playersPropertiesMap = new HashMap<>();
        playersPropertiesMap.put("X-RapidAPI-Key", "0d5ab3a4bfmsh5174a54093fd0f6p12a4ffjsn47f368b3d6ea");
        // JSONObject players = stats.get("https://free-nba.p.rapidapi.com/players",
        // "?page=0&per_page=25", playersPropertiesMap);
        JSONObject teams = FetchStats.get("https://www.balldontlie.io/api/v1/teams");
        // https://www.balldontlie.io/api/v1/games?seasons[]=2022&page=50
        HashMap<TeamName, Team> teamMap = new HashMap<>();
        JSONArray teamsJson = (JSONArray) teams.get("data");
        for (int i = 0; i < teamsJson.size(); i++) { // build each team -- todo maybe look into making this a map where
                                                     // the key is the teamId or team name enum
            JSONObject teamObj = (JSONObject) teamsJson.get(i);
            Long longId = (Long) teamObj.get("id");// comes from json as a long
            int teamId = longId.intValue();
            Team team = new Team.Builder(TeamUtil.TeamName.getById(teamId))
                    .teamName(TeamUtil.TeamName.getTeamByName(teamId).get().getName())
                    .teamId(teamId)
                    .last5PF(100)
                    .last5PA(110)
                    .build();

            teamMap.put(TeamUtil.TeamName.getById(teamId), team);
        }
        return teamMap;
    }

    public boolean compareTeams(int a, int b) {
        return a > b;
    }

    public static Team validateTeam(String request, HashMap<TeamName, Team> teamMap) {
        if (request != null && !request.equals("-1") && !request.equals("") && !request.equals("null")) {
            return teamMap.get(TeamUtil.TeamName.getById(Integer.parseInt(request)));// get from teamMap
        } else {
            Team team = new Team.Builder(TeamName.UNKNOWN)
                    .teamId(-1)
                    .teamName(TeamUtil.TeamName.getTeamByName(-1).get().getName())
                    .build();
            return team;
        }

    }

}
