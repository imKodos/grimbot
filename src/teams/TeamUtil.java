package teams;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;

import stats.FetchStats;
import stats.Games;
import stats.Scraper;

public class TeamUtil {
    // https://www.espn.com/nba/team/stats/_/name/bos/boston-celtics
    // https://www.espn.com/nba/standings/_/group/league
    public static int STREAK_GAMES = 4;

    public enum TeamName {
        CELTICS(2, "Celtics", "BOS", "Boston", "boston-celtics"), NETS(3, "Nets", "BKN", "Brooklyn", "brooklyn-nets"),
        KNICKS(20, "Knicks", "NY", "New York", "new-york-knicks"),
        SIXERS(23, "76ers", "PHI", "Philadelphia", "philadelphia-76ers"),
        RAPTORS(28, "Raptors", "TOR", "Toronto", "toronto-raptors"), // atlantic
        BULLS(5, "Bulls", "CHI", "Chicago", "chicago-bulls"),
        CAVALIERS(6, "Cavaliers", "CLE", "Cleveland", "cleveland-cavaliers"),
        PISTONS(9, "Pistons", "DET", "Detroit", "detroit-pistons"),
        PACERS(12, "Pacers", "IND", "Indiana", "indiana-pacers"),
        BUCKS(17, "Bucks", "MIL", "Milwaukee", "milwaukee-bucks"), // central
        HAWKS(1, "Hawks", "ATL", "Atlanta", "atlanta-hawks"),
        HORNETS(4, "Hornets", "CHA", "Charlotte", "charlotte-hornets"),
        HEAT(16, "Heat", "MIA", "Miami", "miami-heat"), MAGIC(22, "Magic", "ORL", "Orlando", "orlando-magic"),
        WIZARDS(30, "Wizards", "WSH", "Washington", "washington-wizards"), // Southeast
        NUGGETS(8, "Nuggets", "DEN", "Denver", "denver-nuggets"),
        TIMBERWOLVES(18, "Timberwolves", "MIN", "Minnesota", "minnesota-timberwolves"),
        BLAZERS(25, "Blazers", "POR", "Portland", "portland-trail-blazers"),
        JAZZ(29, "Jazz", "UTAH", "Utah", "utah-jazz"),
        THUNDER(21, "Thunder", "OKC", "Oklahoma City", "oklahoma-city-thunder"), // northwest
        WARRIORS(10, "Warriors", "GS", "Golden State", "golden-state-warriors"),
        CLIPPERS(13, "Clippers", "LAC", "Los Angeles", "la-clippers"),
        LAKERS(14, "Lakers", "LAL", "Los Angeles", "los-angeles-lakers"),
        SUNS(24, "Suns", "PHX", "Phoenix", "phoenix-suns"),
        KINGS(26, "Kings", "SAC", "Sacramento", "sacramento-kings"), // pacific
        MAVERICKS(7, "Mavericks", "DAL", "Dallas", "dallas-mavericks"),
        ROCKETS(11, "Rockets", "HOU", "Houston", "houston-rockets"),
        GRIZZLIES(15, "Grizzlies", "MEM", "Memphis", "memphis-grizzlies"),
        PELICANS(19, "Pelicans", "NO", "New Orleans", "new-orleans-pelicans"),
        SPURS(27, "Spurs", "SA", "San Antonio", "san-antonio-spurs"), // southwest
        UNKNOWN(-1, "N/A", "N/A", "N/A", "N/A");

        private final int tId;
        private final String tName;
        private final String tShortName;
        private final String tFullName;
        private final String tEspnUrlCode;

        TeamName(final int id, final String name, final String shortName, final String city, final String espnUrlCode) {
            tId = id;
            tName = name;
            tShortName = shortName;
            tFullName = city + " " + name;
            tEspnUrlCode = espnUrlCode;
        }

        public String getName() {
            return tName;
        }

        public int getId() {
            return tId;
        }

        public String getFullName() {
            return tFullName;
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

        public static String getFullName(int id) {
            for (TeamName e : values()) {
                if (e.tId == (id))
                    return e.tFullName;
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

    public static Map<TeamName, Team> generateTeamMap()
            throws KeyManagementException, NoSuchAlgorithmException, ParseException,
            org.json.simple.parser.ParseException, IOException {

        long start = System.nanoTime();
        System.out.println("entry generate Teams: " + (System.nanoTime() - start) / 1000000 + "ms");

        // build all the games
        // start with 1 page of games
        JSONObject gamesJo = FetchStats
                .get("https://www.balldontlie.io/api/v1/games?seasons[]=2022&per_page=100");
        JSONArray gamesJoArr = (JSONArray) gamesJo.get("data");
        JSONObject gamesMetaObj = (JSONObject) gamesJo.get("meta");
        Long gamesPages = (Long) gamesMetaObj.get("total_pages"); // total number of

        System.out.println("start retrieve games via api: " + (System.nanoTime() - start) / 1000000 + "ms");
        // pages to iterate
        for (int i = 2; i <= gamesPages.intValue(); i++) {
            JSONObject curGamesJo = FetchStats
                    .get("https://www.balldontlie.io/api/v1/games?seasons[]=2022&per_page=100&page="
                            + i);
            JSONArray curGamesArr = (JSONArray) curGamesJo.get("data");
            gamesJoArr.addAll(curGamesArr);
        }

        System.out.println("end retrieve games via api: " + (System.nanoTime() - start) / 1000000 + "ms");

        Gson gson = new Gson();
        Games[] gamesArray = gson.fromJson(gamesJoArr.toString(), Games[].class); // populate Game objects from array
        Collections.sort(Arrays.asList(gamesArray));

        System.out.println("parsed games to gson: " + (System.nanoTime() - start) / 1000000 + "ms");
        Map<TeamName, Team> teamMap = new HashMap<>();
        JSONObject teams = FetchStats.get("https://www.balldontlie.io/api/v1/teams");
        JSONArray teamsJson = (JSONArray) teams.get("data");
        int numTeams = teamsJson.size();

        Map<Integer, JSONObject> totalTeamStatsMap = generateGames(gamesArray); // returns teamId and details

        System.out.println("iterated all games and populated map: " + (System.nanoTime() - start) / 1000000 + "ms");

        System.out.println("start team builder iterator: " + (System.nanoTime() - start) / 1000000 + "ms");

        for (int i = 0; i < numTeams; i++) {
            JSONObject teamObj = (JSONObject) teamsJson.get(i);
            Long longId = (Long) teamObj.get("id");// comes from json as a long
            int teamId = longId.intValue();

            String shortName = TeamUtil.TeamName.getShortName(teamId);
            String fullName = TeamUtil.TeamName.getFullName(teamId);
            String espnUrl = TeamUtil.TeamName.getEspnUrl(teamId);
            String teamName = TeamUtil.TeamName.getTeamByName(teamId).get().getName();

            JSONObject startingInjuries = Scraper.getInjuries(shortName, espnUrl);
            int numStartersInjured = (int) startingInjuries.get("totalStartersInjured");
            Vector<String> injuredPlayerVec = (Vector<String>) startingInjuries.get("injuredPlayers");

            // TODO LIST
            // dive into stats to see what teams or what picks are being called correctly --
            // get more data on this for a chart

            // obtain team offense and defense rank based on seasonPpg and last 5 ppg
            // get a normalized oppg and ppg

            // fix ui
            // use ajax

            // figure out how to cut down time from scraping and api call
            // can i skip through the images and extra page load stuff on screen scrape?
            // get api to call to be more efficient by using the dates. serialize data to a
            // file, read file, get last finished date-- use search from there as a start
            // date, end date as finals game

            // get a readable WP template build every team switch with stats and text
            // (finish team 2 and prediction)

            Team team = new Team.Builder(TeamUtil.TeamName.getById(teamId))
                    .teamName(teamName)
                    .teamId(teamId)
                    .shortName(shortName)
                    .fullName(fullName)
                    .lastGameInfo((String) totalTeamStatsMap.get(teamId).get("lastGameInfo"))
                    .nextGameInfo((String) totalTeamStatsMap.get(teamId).get("nextGameInfo"))
                    .seasonAvgPf(Math.round((double) totalTeamStatsMap.get(teamId).get("seasonPpg")
                            / (int) totalTeamStatsMap.get(teamId).get("gamesPlayed") * 10) / 10.0)
                    .seasonAvgPa(Math.round((double) totalTeamStatsMap.get(teamId).get("seasonOppg")
                            / (int) totalTeamStatsMap.get(teamId).get("gamesPlayed") * 10) / 10.0)
                    .lastPF((int) totalTeamStatsMap.get(teamId).get("lastPf"))
                    .lastPA((int) totalTeamStatsMap.get(teamId).get("lastPa"))
                    .lastOppOffRank((int) totalTeamStatsMap.get(teamId).get("lastOppOffRank"))
                    .lastOppDefRank((int) totalTeamStatsMap.get(teamId).get("lastOppDefRank"))
                    .last5PF(Math.round((double) totalTeamStatsMap.get(teamId).get("last5Pf") / 5 * 10) / 10.0)
                    .last5PA(Math.round((double) totalTeamStatsMap.get(teamId).get("last5Pa") / 5 * 10) / 10.0)
                    .last10PF(Math.round((double) totalTeamStatsMap.get(teamId).get("last10Pf")) / 10.0)
                    .last10PA(Math.round((double) totalTeamStatsMap.get(teamId).get("last10Pa")) / 10.0)
                    .daysRested((long) totalTeamStatsMap.get(teamId).get("daysRested"))
                    .isHomeTeam((boolean) totalTeamStatsMap.get(teamId).get("isHomeTeam"))
                    .last2AwayPa(Math.round((double) totalTeamStatsMap.get(teamId).get("last2AwayPa") / 2 * 10) / 10.0)
                    .last2AwayPf(Math.round((double) totalTeamStatsMap.get(teamId).get("last2AwayPf") / 2 * 10) / 10.0)
                    .last5AwayPf(Math.round((double) totalTeamStatsMap.get(teamId).get("last5AwayPf") / 5 * 10) / 10.0)
                    .last5AwayPa(Math.round((double) totalTeamStatsMap.get(teamId).get("last5AwayPa") / 5 * 10) / 10.0)
                    .last2HomePa(Math.round((double) totalTeamStatsMap.get(teamId).get("last2HomePa") / 2 * 10) / 10.0)
                    .last2HomePf(Math.round((double) totalTeamStatsMap.get(teamId).get("last2HomePf") / 2 * 10) / 10.0)
                    .last5HomePf(Math.round((double) totalTeamStatsMap.get(teamId).get("last5HomePf") / 5 * 10) / 10.0)
                    .last5HomePa(Math.round((double) totalTeamStatsMap.get(teamId).get("last5HomePa") / 5 * 10) / 10.0)
                    .isHotStreak((int) totalTeamStatsMap.get(teamId).get("winStreak") == STREAK_GAMES)
                    .isColdStreak((int) totalTeamStatsMap.get(teamId).get("loseStreak") == STREAK_GAMES)
                    .isHomeColdStreak((int) totalTeamStatsMap.get(teamId).get("homeLoseStreak") == STREAK_GAMES)
                    .isHomeHotStreak((int) totalTeamStatsMap.get(teamId).get("homeWinStreak") == STREAK_GAMES)
                    .isAwayColdStreak((int) totalTeamStatsMap.get(teamId).get("awayLoseStreak") == STREAK_GAMES)
                    .isAwayHotStreak((int) totalTeamStatsMap.get(teamId).get("awayWinStreak") == STREAK_GAMES)
                    .totalWins((int) totalTeamStatsMap.get(teamId).get("totalWins"))
                    .totalLoss((int) totalTeamStatsMap.get(teamId).get("totalLoss"))
                    .numStartersInjured(numStartersInjured)
                    .injuredPlayers(injuredPlayerVec)
                    .teamJsonObj(totalTeamStatsMap.get(teamId))
                    .build();
            teamMap.put(TeamUtil.TeamName.getById(teamId), team);
        }
        System.out.println("all teams built: " + (System.nanoTime() - start) / 1000000 + "ms");

        System.out.println("start building rankings: " + (System.nanoTime() - start) / 1000000 + "ms");
        // really only care about season average and a smaller sample size of last 10
        // games, anything less is probably too volatile
        List<Double> offensiveRankings = new ArrayList<>();
        List<Double> defensiveRankings = new ArrayList<>();
        List<Double> last10OffensiveRankings = new ArrayList<>();
        List<Double> last10DefensiveRankings = new ArrayList<>();

        // build up active offense and defensive ranks
        for (Team curTeam : teamMap.values()) {
            offensiveRankings.add(curTeam.getSeasonAvgPf());
            defensiveRankings.add(curTeam.getSeasonAvgPa());
            last10OffensiveRankings.add(curTeam.getLast10PF());
            last10DefensiveRankings.add(curTeam.getLast10PA());
        }

        // offense needs to be descendingSet so we get higher points as highest rank
        Collections.sort(offensiveRankings, Collections.reverseOrder());
        Collections.sort(last10OffensiveRankings, Collections.reverseOrder());

        Collections.sort(defensiveRankings);
        Collections.sort(last10DefensiveRankings);

        // update each team with their ranks
        for (Team curTeam : teamMap.values()) {
            curTeam.setORank(offensiveRankings.indexOf(curTeam.getSeasonAvgPf()) + 1);
            curTeam.setDRank(defensiveRankings.indexOf(curTeam.getSeasonAvgPa()) + 1);
            curTeam.setO10Rank(last10OffensiveRankings.indexOf(curTeam.getLast10PF()) + 1);
            curTeam.setD10Rank(last10DefensiveRankings.indexOf(curTeam.getLast10PA()) + 1);
        }
        System.out.println("End building rankings: " + (System.nanoTime() - start) / 1000000 + "ms");

        return teamMap;
    }

    public static Map<Integer, JSONObject> generateGames(Games[] gamesArray) throws ParseException {
        Map<Integer, JSONObject> teamMap = new HashMap<>();
        List<Double> offensiveRankings = new ArrayList<>();
        List<Double> defensiveRankings = new ArrayList<>();
        for (Games game : gamesArray) {
            int homeTeamId = game.getHome_team().getId();
            int awayTeamId = game.getVisitor_team().getId();

            // get ranks before building up the latest game
            double homeSeasonPpg = teamMap.get(homeTeamId) != null ? (double) teamMap.get(homeTeamId).get("seasonPpg")
                    : 0;
            double homeSeasonOppg = teamMap.get(homeTeamId) != null ? (double) teamMap.get(homeTeamId).get("seasonOppg")
                    : 0;
            int homeSeasonOffRank = offensiveRankings.indexOf(homeSeasonPpg) + 1;
            int homeSeasonDefRank = defensiveRankings.indexOf(homeSeasonOppg) + 1;

            double awaySeasonPpg = teamMap.get(homeTeamId) != null ? (double) teamMap.get(homeTeamId).get("seasonPpg")
                    : 0;
            double awaySeasonOppg = teamMap.get(homeTeamId) != null ? (double) teamMap.get(homeTeamId).get("seasonOppg")
                    : 0;
            int awaySeasonOffRank = offensiveRankings.indexOf(awaySeasonPpg) + 1;
            int awaySeasonDefRank = defensiveRankings.indexOf(awaySeasonOppg) + 1;

            // once we pick up the rank,remove the average from the list and update it later
            if (homeSeasonOffRank > 0 && "Final".equals(game.getStatus())) {
                offensiveRankings.remove(homeSeasonPpg);
            }
            if (homeSeasonDefRank > 0 && "Final".equals(game.getStatus())) {
                defensiveRankings.remove(homeSeasonOppg);
            }

            // remove the old stat so we can update it at a later time
            if (awaySeasonOffRank > 0 && "Final".equals(game.getStatus())) {
                offensiveRankings.remove(awaySeasonPpg);
            }
            if (awaySeasonDefRank > 0 && "Final".equals(game.getStatus())) {
                defensiveRankings.remove(awaySeasonOppg);
            }

            for (int i = 0; i < 2; i++) { // iterate over the two teams in the game
                JSONObject teamStatsJo = new JSONObject();
                boolean isHomeTeam = i == 0;
                int teamId = isHomeTeam ? homeTeamId : awayTeamId;
                boolean nextGameHomeTeam = teamMap.get(teamId) != null
                        ? (boolean) teamMap.get(teamId).get("isHomeTeam")
                        : false;
                double seasonPpg = isHomeTeam ? homeSeasonPpg : awaySeasonPpg;
                double seasonOppg = isHomeTeam ? homeSeasonOppg : awaySeasonOppg;
                int gamesPlayed = teamMap.get(teamId) != null ? (int) teamMap.get(teamId).get("gamesPlayed") : 0;
                int lastPf = teamMap.get(teamId) != null ? (int) teamMap.get(teamId).get("lastPf") : 0;
                int lastPa = teamMap.get(teamId) != null ? (int) teamMap.get(teamId).get("lastPa") : 0;
                int lastOffRank = teamMap.get(teamId) != null ? (int) teamMap.get(teamId).get("lastOffRank") : 0;
                int lastDefRank = teamMap.get(teamId) != null ? (int) teamMap.get(teamId).get("lastDefRank") : 0;
                int lastOppOffRank = teamMap.get(teamId) != null ? (int) teamMap.get(teamId).get("lastOppOffRank") : 0;
                int lastOppDefRank = teamMap.get(teamId) != null ? (int) teamMap.get(teamId).get("lastOppDefRank") : 0;
                long daysRested = teamMap.get(teamId) != null ? (long) teamMap.get(teamId).get("daysRested") : -1;
                double last5Pf = teamMap.get(teamId) != null ? (double) teamMap.get(teamId).get("last5Pf") : 0;
                double last5Pa = teamMap.get(teamId) != null ? (double) teamMap.get(teamId).get("last5Pa") : 0;
                double last10Pf = teamMap.get(teamId) != null ? (double) teamMap.get(teamId).get("last10Pf") : 0;
                double last10Pa = teamMap.get(teamId) != null ? (double) teamMap.get(teamId).get("last10Pa") : 0;
                double homeGameIdx = teamMap.get(teamId) != null ? (double) teamMap.get(teamId).get("homeGameIdx") : 0;
                double awayGameIdx = teamMap.get(teamId) != null ? (double) teamMap.get(teamId).get("awayGameIdx") : 0;
                double last2HomePf = teamMap.get(teamId) != null ? (double) teamMap.get(teamId).get("last2HomePf") : 0;
                double last2HomePa = teamMap.get(teamId) != null ? (double) teamMap.get(teamId).get("last2HomePa") : 0;
                double last5HomePf = teamMap.get(teamId) != null ? (double) teamMap.get(teamId).get("last5HomePf") : 0;
                double last5HomePa = teamMap.get(teamId) != null ? (double) teamMap.get(teamId).get("last5HomePa") : 0;
                double last2AwayPf = teamMap.get(teamId) != null ? (double) teamMap.get(teamId).get("last2AwayPf") : 0;
                double last5AwayPf = teamMap.get(teamId) != null ? (double) teamMap.get(teamId).get("last5AwayPf") : 0;
                double last2AwayPa = teamMap.get(teamId) != null ? (double) teamMap.get(teamId).get("last2AwayPa") : 0;
                double last5AwayPa = teamMap.get(teamId) != null ? (double) teamMap.get(teamId).get("last5AwayPa") : 0;
                int winStreak = teamMap.get(teamId) != null ? (int) teamMap.get(teamId).get("winStreak") : 0;
                int loseStreak = teamMap.get(teamId) != null ? (int) teamMap.get(teamId).get("loseStreak") : 0;
                int homeWinStreak = teamMap.get(teamId) != null ? (int) teamMap.get(teamId).get("homeWinStreak") : 0;
                int homeLoseStreak = teamMap.get(teamId) != null ? (int) teamMap.get(teamId).get("homeLoseStreak") : 0;
                int awayWinStreak = teamMap.get(teamId) != null ? (int) teamMap.get(teamId).get("awayWinStreak") : 0;
                int awayLoseStreak = teamMap.get(teamId) != null ? (int) teamMap.get(teamId).get("awayLoseStreak") : 0;
                int totalWins = teamMap.get(teamId) != null ? (int) teamMap.get(teamId).get("totalWins") : 0;
                int totalLoss = teamMap.get(teamId) != null ? (int) teamMap.get(teamId).get("totalLoss") : 0;
                String lastGameInfo = teamMap.get(teamId) != null ? (String) teamMap.get(teamId).get("lastGameInfo")
                        : "";
                String nextGameInfo = teamMap.get(teamId) != null ? (String) teamMap.get(teamId).get("nextGameInfo")
                        : "";

                if (0 == game.getPeriod()) {// keep the next game state up to date
                    nextGameHomeTeam = game.getHome_team().getId() == teamId;

                    nextGameInfo = "The next game is " + game.getVisitor_team().getFull_name() + " at "
                            + game.getHome_team().getFull_name() + " on "
                            + game.getDate().replace("T00:00:00.000Z", "");
                }

                if ("Final".equals(game.getStatus())) {
                    gamesPlayed++;// can refactor this with homegameidx + awaygameidx
                    seasonPpg += isHomeTeam ? game.getHome_team_score() : game.getVisitor_team_score();
                    seasonOppg += isHomeTeam ? game.getVisitor_team_score() : game.getHome_team_score();

                    // build up active offense and defensive ranks
                    // offense needs to be descendingSet so we get higher points as highest rank
                    offensiveRankings.add(seasonPpg);
                    Collections.sort(offensiveRankings, Collections.reverseOrder());

                    defensiveRankings.add(seasonOppg);
                    Collections.sort(defensiveRankings);

                    if (isHomeTeam) {
                        homeGameIdx++;
                        if (game.getHome_team_score() > game.getVisitor_team_score()) {
                            totalWins++;
                            if (homeGameIdx <= STREAK_GAMES) {
                                homeWinStreak++;
                            }
                        } else {
                            totalLoss++;
                            if (homeGameIdx <= STREAK_GAMES) {
                                homeLoseStreak++;
                            }
                        }
                    } else {
                        awayGameIdx++;
                        if (game.getHome_team_score() < game.getVisitor_team_score()) {
                            totalWins++;
                            if (awayGameIdx <= STREAK_GAMES) {
                                awayWinStreak++;
                            }
                        } else {
                            totalLoss++;
                            if (awayGameIdx <= STREAK_GAMES) {
                                awayLoseStreak++;
                            }
                        }
                    }

                    if (gamesPlayed == 1) {
                        lastOffRank = isHomeTeam ? homeSeasonOffRank : awaySeasonOffRank;
                        lastDefRank = isHomeTeam ? homeSeasonDefRank : awaySeasonDefRank;
                        lastOppOffRank = isHomeTeam ? awaySeasonOffRank : homeSeasonOffRank;
                        lastOppDefRank = isHomeTeam ? awaySeasonDefRank : homeSeasonDefRank;

                        lastPf += isHomeTeam ? game.getHome_team_score() : game.getVisitor_team_score();
                        lastPa += isHomeTeam ? game.getVisitor_team_score() : game.getHome_team_score();

                        lastGameInfo = isHomeTeam ? "vs " : "@ ";
                        lastGameInfo += isHomeTeam ? game.getVisitor_team().getName()
                                : game.getHome_team().getName();
                        if (isHomeTeam) {
                            lastGameInfo += game.getHome_team_score() > game.getVisitor_team_score() ? " W" : " L";
                            lastGameInfo += game.getHome_team_score() + " - " + game.getVisitor_team_score();
                        } else {
                            lastGameInfo += game.getHome_team_score() < game.getVisitor_team_score() ? " W" : " L";
                            lastGameInfo += game.getVisitor_team_score() + " - " + game.getHome_team_score();
                        }

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Calendar lastGame = Calendar.getInstance();
                        Calendar today = Calendar.getInstance();
                        lastGame.setTime(sdf.parse(game.getDate().replace("T00:00:00.000Z", "")));
                        // subtract 1 in daysRested because we dont want to count today as a rest day
                        daysRested = TimeUnit.MILLISECONDS.toDays(today.getTimeInMillis() - lastGame.getTimeInMillis())
                                - 1;
                    }

                    if (gamesPlayed <= 5) {
                        last5Pf += isHomeTeam ? game.getHome_team_score() : game.getVisitor_team_score();
                        last5Pa += isHomeTeam ? game.getVisitor_team_score() : game.getHome_team_score();
                    }

                    if (gamesPlayed <= 10) {
                        last10Pf += isHomeTeam ? game.getHome_team_score() : game.getVisitor_team_score();
                        last10Pa += isHomeTeam ? game.getVisitor_team_score() : game.getHome_team_score();
                    }

                    if (isHomeTeam && homeGameIdx <= 2) {
                        last2HomePf += game.getHome_team_score();
                        last2HomePa += game.getVisitor_team_score();
                    }

                    if (isHomeTeam && homeGameIdx <= 5) { // can refactor this to be in the same block as other
                        last5HomePf += game.getHome_team_score();
                        last5HomePa += game.getVisitor_team_score();
                    }

                    if (!isHomeTeam && awayGameIdx <= 2) {
                        last2AwayPa += game.getHome_team_score();
                        last2AwayPf += game.getVisitor_team_score();
                    }

                    if (!isHomeTeam && awayGameIdx <= 5) { // can refactor this to be in the same block as other
                        last5AwayPa += game.getHome_team_score();
                        last5AwayPf += game.getVisitor_team_score();
                    }

                    if (gamesPlayed <= STREAK_GAMES) {
                        if (isHomeTeam) {
                            if (game.getHome_team_score() < game.getVisitor_team_score()) {
                                winStreak++;
                            } else {
                                loseStreak++;
                            }
                        } else {
                            if (game.getHome_team_score() > game.getVisitor_team_score()) {
                                winStreak++;
                            } else {
                                loseStreak++;
                            }
                        }
                    }
                }
                teamStatsJo.put("seasonPpg", seasonPpg);
                teamStatsJo.put("seasonOppg", seasonOppg);
                teamStatsJo.put("gamesPlayed", gamesPlayed);
                teamStatsJo.put("lastGameInfo", lastGameInfo);
                teamStatsJo.put("nextGameInfo", nextGameInfo);
                teamStatsJo.put("lastPf", lastPf);
                teamStatsJo.put("lastPa", lastPa);
                teamStatsJo.put("lastOppOffRank", lastOppOffRank);
                teamStatsJo.put("lastOppDefRank", lastOppDefRank);
                teamStatsJo.put("lastDefRank", lastDefRank);
                teamStatsJo.put("lastOffRank", lastOffRank);
                teamStatsJo.put("daysRested", daysRested);
                teamStatsJo.put("last5Pf", last5Pf);
                teamStatsJo.put("last5Pa", last5Pa);
                teamStatsJo.put("last10Pf", last10Pf);
                teamStatsJo.put("last10Pa", last10Pa);
                teamStatsJo.put("isHomeTeam", nextGameHomeTeam);
                teamStatsJo.put("homeGameIdx", homeGameIdx);
                teamStatsJo.put("awayGameIdx", awayGameIdx);
                teamStatsJo.put("last2HomePf", last2HomePf);
                teamStatsJo.put("last2HomePa", last2HomePa);
                teamStatsJo.put("last5HomePf", last5HomePf);
                teamStatsJo.put("last5HomePa", last5HomePa);
                teamStatsJo.put("last2AwayPf", last2AwayPf);
                teamStatsJo.put("last2AwayPa", last2AwayPa);
                teamStatsJo.put("last5AwayPf", last5AwayPf);
                teamStatsJo.put("last5AwayPa", last5AwayPa);
                teamStatsJo.put("winStreak", winStreak);
                teamStatsJo.put("loseStreak", loseStreak);
                teamStatsJo.put("homeWinStreak", homeWinStreak);
                teamStatsJo.put("homeLoseStreak", homeLoseStreak);
                teamStatsJo.put("awayWinStreak", awayWinStreak);
                teamStatsJo.put("awayLoseStreak", awayLoseStreak);
                teamStatsJo.put("totalLoss", totalLoss);
                teamStatsJo.put("totalWins", totalWins);
                teamMap.put(teamId, teamStatsJo);
            }
        }
        return teamMap;
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
