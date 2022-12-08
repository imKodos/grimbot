package teams;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import stats.FetchStats;
import stats.Games;
import stats.Scraper;

public class TeamUtil {
    // https://www.espn.com/nba/team/stats/_/name/bos/boston-celtics
    // https://www.espn.com/nba/standings/_/group/league

    public enum TeamName {
        CELTICS(2, "Celtics", "BOS", "boston-celtics"), NETS(3, "Nets", "BKN", "brooklyn-nets"),
        KNICKS(20, "Knicks", "NY", "new-york-knicks"), SIXERS(23, "76ers", "PHI", "philadelphia-76ers"),
        RAPTORS(28, "Raptors", "TOR", "toronto-raptors"), // atlantic
        BULLS(5, "Bulls", "CHI", "chicago-bulls"), CAVALIERS(6, "Cavaliers", "CLE", "cleveland-cavaliers"),
        PISTONS(9, "Pistons", "DET", "detroit-pistons"), PACERS(12, "Pacers", "IND", "indiana-pacers"),
        BUCKS(17, "Bucks", "MIL", "milwaukee-bucks"), // central
        HAWKS(1, "Hawks", "ATL", "atlanta-hawks"), HORNETS(4, "Hornets", "CHA", "charlotte-hornets"),
        HEAT(16, "Heat", "MIA", "miami-heat"), MAGIC(22, "Magic", "ORL", "orlando-magic"),
        WIZARDS(30, "Wizards", "WSH", "washington-wizards"), // Southeast
        NUGGETS(8, "Nuggets", "DEN", "denver-nuggets"),
        TIMBERWOLVES(18, "Timberwolves", "MIN", "minnesota-timberwolves"),
        BLAZERS(25, "Blazers", "POR", "portland-trail-blazers"), JAZZ(29, "Jazz", "UTAH", "utah-jazz"),
        THUNDER(21, "Thunder", "OKC", "oklahoma-city-thunder"), // northwest
        WARRIORS(10, "Warriors", "GS", "golden-state-warriors"), CLIPPERS(13, "Clippers", "LAC", "la-clippers"),
        LAKERS(14, "Lakers", "LAL", "los-angeles-lakers"), SUNS(24, "Suns", "PHX", "phoenix-suns"),
        KINGS(26, "Kings", "SAC", "sacramento-kings"), // pacific
        MAVERICKS(7, "Mavericks", "DAL", "dallas-mavericks"), ROCKETS(11, "Rockets", "HOU", "houston-rockets"),
        GRIZZLIES(15, "Grizzlies", "MEM", "memphis-grizzlies"), PELICANS(19, "Pelicans", "NO", "new-orleans-pelicans"),
        SPURS(27, "Spurs", "SA", "san-antonio-spurs"), // southwest
        UNKNOWN(-1, "N/A", "N/A", "N/A");

        private final int tId;
        private final String tName;
        private final String tShortName;
        private final String tEspnUrlCode;

        TeamName(final int id, final String name, final String shortName, final String espnUrlCode) {
            tId = id;
            tName = name;
            tShortName = shortName;
            tEspnUrlCode = espnUrlCode;
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
                if (e.equals(name))
                    return e.tId;
            }
            return -1;
        }

        public static String getShortName(int id) {
            for (TeamName e : values()) {
                if (e.tId == (id))
                    return e.tShortName;
            }
            return "";
        }

        public static String getEspnUrl(int id) {
            for (TeamName e : values()) {
                if (e.tId == (id))
                    return e.tEspnUrlCode;
            }
            return "";
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
            org.json.simple.parser.ParseException, IOException {

        // build all the games
        // start with 1 page of games
        JSONObject gamesJo = FetchStats
                .get("https://www.balldontlie.io/api/v1/games?seasons[]=2022&per_page=100");
        JSONArray gamesJoArr = (JSONArray) gamesJo.get("data");
        JSONObject gamesMetaObj = (JSONObject) gamesJo.get("meta");
        Long gamesPages = (Long) gamesMetaObj.get("total_pages"); // total number of
        // pages to iterate

        for (int i = 2; i <= gamesPages.intValue(); i++) {
            JSONObject curGamesJo = FetchStats
                    .get("https://www.balldontlie.io/api/v1/games?seasons[]=2022&per_page=100&page="
                            + i);
            JSONArray curGamesArr = (JSONArray) curGamesJo.get("data");
            gamesJoArr.addAll(curGamesArr);
        }
        Gson gson = new Gson();
        Games[] gamesArray = gson.fromJson(gamesJoArr.toString(), Games[].class); // populate Game objects from array
        Collections.sort(Arrays.asList(gamesArray));

        HashMap<TeamName, Team> teamMap = new HashMap<>();
        JSONObject teams = FetchStats.get("https://www.balldontlie.io/api/v1/teams");
        JSONArray teamsJson = (JSONArray) teams.get("data");

        for (int i = 0; i < teamsJson.size(); i++) {
            JSONObject teamObj = (JSONObject) teamsJson.get(i);
            Long longId = (Long) teamObj.get("id");// comes from json as a long
            int teamId = longId.intValue();

            String shortName = TeamUtil.TeamName.getShortName(teamId);
            String espnUrl = TeamUtil.TeamName.getEspnUrl(teamId);
            String teamName = TeamUtil.TeamName.getTeamByName(teamId).get().getName();
            JSONObject recentGameStats = Scraper.getRecentGameStats(shortName, espnUrl);
            String lastGameInfo = (String) recentGameStats.get("lastGameInfo");

            double seasonPpg = 0;
            double seasonOppg = 0;
            int gamesPlayed = 0;
            int lastPf = 0;
            int lastPa = 0;
            double last5Pf = 0;
            double last5Pa = 0;
            double last10Pf = 0;
            double last10Pa = 0;
            for (Games game : gamesArray) {
                if ("Final".equals(game.getStatus()) && (game.getHome_team().getId() == teamId)) {
                    seasonPpg += game.getHome_team_score();
                    seasonOppg += game.getVisitor_team_score();
                    gamesPlayed++;

                    if (gamesPlayed == 1) {
                        lastPf += game.getHome_team_score();
                        lastPa += game.getVisitor_team_score();
                    }

                    if (gamesPlayed <= 5) {
                        last5Pf += game.getHome_team_score();
                        last5Pa += game.getVisitor_team_score();
                    }

                    if (gamesPlayed <= 10) {
                        last10Pf += game.getHome_team_score();
                        last10Pa += game.getVisitor_team_score();
                    }
                }

                if ("Final".equals(game.getStatus()) && (game.getVisitor_team().getId() == teamId)) {
                    seasonPpg += game.getVisitor_team_score();
                    seasonOppg += game.getHome_team_score();
                    gamesPlayed++;

                    if (gamesPlayed == 1) {
                        lastPa += game.getHome_team_score();
                        lastPf += game.getVisitor_team_score();
                    }
                    if (gamesPlayed <= 5) {
                        last5Pa += game.getHome_team_score();
                        last5Pf += game.getVisitor_team_score();
                    }
                    if (gamesPlayed <= 10) {
                        last10Pa += game.getHome_team_score();
                        last10Pf += game.getVisitor_team_score();
                    }
                }
            }

            // TODO LIST
            // get stat for Game object, next game
            // get playConsecutiveDays
            // get isHomeTeam
            // remove isDivisionGame
            // make a hot/cold streak (3-5g W or L streak -- also do for home/away)
            // remove pace and time of possession
            // can pull in ranks for variance here https://www.espn.com/nba/bpi
            // (defenseRating/offensiveRating)
            // refactor for loop
            // try scraping for hasStartingInjury

            Team team = new Team.Builder(TeamUtil.TeamName.getById(teamId))
                    .teamName(teamName)
                    .teamId(teamId)
                    .shortName(shortName)
                    .lastGameInfo(lastGameInfo)
                    .seasonAvgPf(Math.round(seasonPpg / gamesPlayed * 10) / 10.0)
                    .seasonAvgPa(Math.round(seasonOppg / gamesPlayed * 10) / 10.0)
                    .lastPF(lastPf)
                    .lastPA(lastPa)
                    .last5PF(Math.round(last5Pf / 5 * 10) / 10.0)
                    .last5PA(Math.round(last5Pa / 5 * 10) / 10.0)
                    .last10PF(Math.round(last10Pf / 10 * 10) / 10.0)
                    .last10PA(Math.round(last10Pa / 10 * 10) / 10.0)
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
