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
    public static int STREAK_GAMES = 3;

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
        Collections.sort(Arrays.asList(gamesArray), Collections.reverseOrder());

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
            // get stats for above 500 teams and below 500 teams

            // use normalized stats better in predictions
            // play to normalized score / rank
            // add diff variance
            // set up 3 models.

            // can refactor ranks calculations ie last5AwayTeamsPlayedArr etc.
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
                    .last5PF(Math.round((double) totalTeamStatsMap.get(teamId).get("last5Pf") / 5 * 10) / 10.0)
                    .last5PA(Math.round((double) totalTeamStatsMap.get(teamId).get("last5Pa") / 5 * 10) / 10.0)
                    .last10PF(Math.round((double) totalTeamStatsMap.get(teamId).get("last10Pf")) / 10.0)
                    .last10PA(Math.round((double) totalTeamStatsMap.get(teamId).get("last10Pa")) / 10.0)
                    .last5Over500Pf(
                            Math.round((double) totalTeamStatsMap.get(teamId).get("last5Over500Pf") / 5 * 10) / 10.0)
                    .last5Over500Pa(
                            Math.round((double) totalTeamStatsMap.get(teamId).get("last5Over500Pa") / 5 * 10) / 10.0)
                    .last5Under500Pf(
                            Math.round((double) totalTeamStatsMap.get(teamId).get("last5Under500Pf") / 5 * 10) / 10.0)
                    .last5Under500Pa(
                            Math.round((double) totalTeamStatsMap.get(teamId).get("last5Under500Pa") / 5 * 10) / 10.0)
                    .last10Over500Pf(Math.round((double) totalTeamStatsMap.get(teamId).get("last10Over500Pf")) / 10.0)
                    .last10Over500Pa(Math.round((double) totalTeamStatsMap.get(teamId).get("last10Over500Pa")) / 10.0)
                    .last10Under500Pf(Math.round((double) totalTeamStatsMap.get(teamId).get("last10Under500Pf")) / 10.0)
                    .last10Under500Pa(Math.round((double) totalTeamStatsMap.get(teamId).get("last10Under500Pa")) / 10.0)
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
                    .isHotStreak((int) totalTeamStatsMap.get(teamId).get("winStreak") > STREAK_GAMES)
                    .isColdStreak((int) totalTeamStatsMap.get(teamId).get("loseStreak") > STREAK_GAMES)
                    .isHomeColdStreak((int) totalTeamStatsMap.get(teamId).get("homeLoseStreak") > STREAK_GAMES)
                    .isHomeHotStreak((int) totalTeamStatsMap.get(teamId).get("homeWinStreak") > STREAK_GAMES)
                    .isAwayColdStreak((int) totalTeamStatsMap.get(teamId).get("awayLoseStreak") > STREAK_GAMES)
                    .isAwayHotStreak((int) totalTeamStatsMap.get(teamId).get("awayWinStreak") > STREAK_GAMES)
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

        // System.out.println("Offensive Rankings:");
        // for (Double double1 : offensiveRankings) {
        // System.out.println(double1);
        // }
        // System.out.println("Defensive Rankings:");
        // for (Double double1 : defensiveRankings) {
        // System.out.println(double1);
        // }

        // update each team with their ranks -- as well as their opponent ranks
        for (Team curTeam : teamMap.values()) {
            curTeam.setORank(offensiveRankings.indexOf(curTeam.getSeasonAvgPf()) + 1);
            curTeam.setDRank(defensiveRankings.indexOf(curTeam.getSeasonAvgPa()) + 1);
            curTeam.setO10Rank(last10OffensiveRankings.indexOf(curTeam.getLast10PF()) + 1);
            curTeam.setD10Rank(last10DefensiveRankings.indexOf(curTeam.getLast10PA()) + 1);

            // last 1, 5, and 10 team ranks
            double lastTeamOffRank = 0;
            double lastTeamDefRank = 0;
            double last5TeamOffRank = 0;
            double last5TeamDefRank = 0;
            double last10TeamOffRank = 0;
            double last10TeamDefRank = 0;
            double last2HomeOppOffRank = 0;
            double last2HomeOppDefRank = 0;
            double last5HomeOppOffRank = 0;
            double last5HomeOppDefRank = 0;
            double last2AwayOppOffRank = 0;
            double last2AwayOppDefRank = 0;
            double last5AwayOppOffRank = 0;
            double last5AwayOppDefRank = 0;

            int[] last10TeamsPlayedArr = (int[]) totalTeamStatsMap.get(curTeam.getTeamId()).get("last10TeamsPlayedArr");
            int validTeamIdx = 0;
            for (int i = 0; i < last10TeamsPlayedArr.length; i++) {
                Team opponent = teamMap.get(TeamUtil.TeamName.getById(last10TeamsPlayedArr[i]));
                double opponentPf = opponent.getSeasonAvgPf();
                double opponentPa = opponent.getSeasonAvgPa();

                if (opponentPf > 0 && opponentPa > 0) {// valid team
                    validTeamIdx++;
                    lastTeamOffRank = offensiveRankings.indexOf(opponentPf) + 1;// keep track of last valid as last team
                    lastTeamDefRank = defensiveRankings.indexOf(opponentPa) + 1;

                    if (validTeamIdx <= 5) {
                        last5TeamOffRank += offensiveRankings.indexOf(opponentPf) + 1;
                        last5TeamDefRank += defensiveRankings.indexOf(opponentPa) + 1;
                    }

                    last10TeamOffRank += offensiveRankings.indexOf(opponentPf) + 1;
                    last10TeamDefRank += defensiveRankings.indexOf(opponentPa) + 1;
                }
            }

            int[] last5AwayTeamsPlayedArr = (int[]) totalTeamStatsMap.get(curTeam.getTeamId())
                    .get("last5AwayTeamsPlayedArr");
            int validAwayTeamIdx = 0;
            for (int i = 0; i < last5AwayTeamsPlayedArr.length; i++) {
                Team opponent = teamMap.get(TeamUtil.TeamName.getById(last5AwayTeamsPlayedArr[i]));
                double opponentPf = opponent.getSeasonAvgPf();
                double opponentPa = opponent.getSeasonAvgPa();

                if (opponentPf > 0 && opponentPa > 0) {// valid team
                    validAwayTeamIdx++;
                    if (validAwayTeamIdx <= 2) {
                        last2AwayOppOffRank += offensiveRankings.indexOf(opponentPf) + 1;
                        last2AwayOppDefRank += defensiveRankings.indexOf(opponentPa) + 1;
                    }
                    last5AwayOppOffRank += offensiveRankings.indexOf(opponentPf) + 1;
                    last5AwayOppDefRank += defensiveRankings.indexOf(opponentPa) + 1;
                }
            }
            int[] last5HomeTeamsPlayedArr = (int[]) totalTeamStatsMap.get(curTeam.getTeamId())
                    .get("last5HomeTeamsPlayedArr");
            int validHomeTeamIdx = 0;
            for (int i = 0; i < last5HomeTeamsPlayedArr.length; i++) {
                Team opponent = teamMap.get(TeamUtil.TeamName.getById(last5HomeTeamsPlayedArr[i]));
                double opponentPf = opponent.getSeasonAvgPf();
                double opponentPa = opponent.getSeasonAvgPa();

                if (opponentPf > 0 && opponentPa > 0) {// valid team
                    validHomeTeamIdx++;
                    if (validHomeTeamIdx <= 2) {
                        last2HomeOppOffRank += offensiveRankings.indexOf(opponentPf) + 1;
                        last2HomeOppDefRank += defensiveRankings.indexOf(opponentPa) + 1;
                    }
                    last5HomeOppOffRank += offensiveRankings.indexOf(opponentPf) + 1;
                    last5HomeOppDefRank += defensiveRankings.indexOf(opponentPa) + 1;
                }
            }
            curTeam.setLastOppORank(lastTeamOffRank);
            curTeam.setLastOppDRank(lastTeamDefRank);
            curTeam.setNormalizedLastPf(
                    getNormalizedScore(curTeam.getLastPF(), lastTeamDefRank, defensiveRankings, true));
            curTeam.setNormalizedLastPa(
                    getNormalizedScore(curTeam.getLastPA(), lastTeamOffRank, offensiveRankings, false));

            // if we reached our cap of 5 teams, divide by 5, otherwise divide by the last
            // known number of teams
            curTeam.setLast5OppORank(validTeamIdx >= 5 ? last5TeamOffRank / 5 : last5TeamOffRank / validTeamIdx);
            curTeam.setLast5OppDRank(validTeamIdx >= 5 ? last5TeamDefRank / 5 : last5TeamDefRank / validTeamIdx);
            curTeam.setNormalizedLast5Pf(
                    getNormalizedScore(curTeam.getLast5PF(), curTeam.getLast5OppDRank(), defensiveRankings, true));
            curTeam.setNormalizedLast5Pa(
                    getNormalizedScore(curTeam.getLast5PA(), curTeam.getLast5OppORank(), offensiveRankings, false));

            curTeam.setLast10OppORank(validTeamIdx >= 10 ? last10TeamOffRank / 10 : last10TeamOffRank / validTeamIdx);
            curTeam.setLast10OppDRank(validTeamIdx >= 10 ? last10TeamDefRank / 10 : last10TeamDefRank / validTeamIdx);
            curTeam.setNormalizedLast10Pf(
                    getNormalizedScore(curTeam.getLast10PF(), curTeam.getLast10OppDRank(), defensiveRankings, true));
            curTeam.setNormalizedLast10Pa(
                    getNormalizedScore(curTeam.getLast10PA(), curTeam.getLast10OppORank(), offensiveRankings, false));

            curTeam.setLast2AwayOppORank(
                    validAwayTeamIdx >= 2 ? last2AwayOppOffRank / 2 : last2AwayOppOffRank / validAwayTeamIdx);
            curTeam.setLast2AwayOppDRank(
                    validAwayTeamIdx >= 2 ? last2AwayOppDefRank / 2 : last2AwayOppDefRank / validAwayTeamIdx);
            curTeam.setNormalizedLast2AwayPf(
                    getNormalizedScore(curTeam.getLast2AwayPf(), curTeam.getLast2AwayOppDRank(), defensiveRankings,
                            true));
            curTeam.setNormalizedLast2AwayPa(
                    getNormalizedScore(curTeam.getLast2AwayPa(), curTeam.getLast2AwayOppORank(), offensiveRankings,
                            false));

            curTeam.setLast5AwayOppORank(
                    validAwayTeamIdx >= 5 ? last5AwayOppOffRank / 5 : last5AwayOppOffRank / validAwayTeamIdx);
            curTeam.setLast5AwayOppDRank(
                    validAwayTeamIdx >= 5 ? last5AwayOppDefRank / 5 : last5AwayOppDefRank / validAwayTeamIdx);
            curTeam.setNormalizedLast5AwayPf(
                    getNormalizedScore(curTeam.getLast5AwayPf(), curTeam.getLast5AwayOppDRank(), defensiveRankings,
                            true));
            curTeam.setNormalizedLast5AwayPa(
                    getNormalizedScore(curTeam.getLast5AwayPa(), curTeam.getLast5AwayOppORank(), offensiveRankings,
                            false));

            curTeam.setLast2HomeOppORank(
                    validHomeTeamIdx >= 2 ? last2HomeOppOffRank / 2 : last2HomeOppOffRank / validHomeTeamIdx);
            curTeam.setLast2HomeOppDRank(
                    validHomeTeamIdx >= 2 ? last2HomeOppDefRank / 2 : last2HomeOppDefRank / validHomeTeamIdx);

            curTeam.setNormalizedLast2HomePf(
                    getNormalizedScore(curTeam.getLast2HomePf(), curTeam.getLast2HomeOppDRank(), defensiveRankings,
                            true));
            curTeam.setNormalizedLast2HomePa(
                    getNormalizedScore(curTeam.getLast2HomePa(), curTeam.getLast2HomeOppORank(), offensiveRankings,
                            false));

            curTeam.setLast5HomeOppORank(
                    validHomeTeamIdx >= 5 ? last5HomeOppOffRank / 5 : last5HomeOppOffRank / validHomeTeamIdx);
            curTeam.setLast5HomeOppDRank(
                    validHomeTeamIdx >= 5 ? last5HomeOppDefRank / 5 : last5HomeOppDefRank / validHomeTeamIdx);

            curTeam.setNormalizedLast5HomePf(
                    getNormalizedScore(curTeam.getLast5HomePf(), curTeam.getLast5HomeOppDRank(), defensiveRankings,
                            true));
            curTeam.setNormalizedLast5HomePa(
                    getNormalizedScore(curTeam.getLast5HomePa(), curTeam.getLast5HomeOppORank(), offensiveRankings,
                            false));
        }

        System.out.println("End building rankings: " + (System.nanoTime() - start) / 1000000 + "ms");

        return teamMap;
    }

    private static double getNormalizedScore(double score, double oppRank, List<Double> rankings, boolean pf) {
        double retScore = 0;
        double topRankPoints = rankings.get(0); // best team
        double midRankPoints = rankings.get(14); // mid team
        double botRankPoints = rankings.get(29); // worst team
        double pointPerRank = (topRankPoints + midRankPoints + botRankPoints) / 750; // avg points per team
        // 135 is average points (/3), per team (/30), and an arbitrary 2/3 for balance
        // 180 is average points (/3), per team (/30), and an arbitrary 1/2 for balance
        // 270 is average points (/3), per team (/30), and an arbitrary 1/3 for balance
        // 450 is average points (/3), per team (/30), and an arbitrary 1/5 for balance
        // 725-750 gets me the numbers i want

        // get the score if the opp rank was at 15.
        // For Points For:
        // if we are playing vs a top 15 opponent, score will be increased
        // if we are playing vs a bot 15 opponent, score will be decreased

        // For Points Against:
        // if we are playing vs a top 15 opponent, score will be decreased
        // if we are playing vs a bot 15 opponent, score will be increased
        int multiplier = 1;
        if ((pf && oppRank > 15) || (!pf && oppRank < 15)) { // meets conditions listed above, set multiplier to
                                                             // negative
            multiplier = -1;
        }

        double rankDifferential = Math.abs(15 - oppRank) * pointPerRank * multiplier; // get points to add or sub

        retScore = score + rankDifferential;

        return (double) Math.round(retScore * 10) / 10;
    }

    public static Map<Integer, JSONObject> generateGames(Games[] gamesArray) throws ParseException {
        Map<Integer, JSONObject> teamMap = new HashMap<>();
        for (Games game : gamesArray) {
            int homeTeamId = game.getHome_team().getId();
            int awayTeamId = game.getVisitor_team().getId();
            for (int i = 0; i < 2; i++) { // iterate over the two teams in the game
                JSONObject teamStatsJo = new JSONObject();
                boolean isHomeTeam = i == 0;
                int teamId = isHomeTeam ? homeTeamId : awayTeamId;
                int oppId = isHomeTeam ? awayTeamId : homeTeamId;
                boolean nextGameHomeTeam = teamMap.get(teamId) != null
                        ? (boolean) teamMap.get(teamId).get("isHomeTeam")
                        : false;
                double seasonPpg = teamMap.get(teamId) != null
                        ? (double) teamMap.get(teamId).get("seasonPpg")
                        : 0;
                double seasonOppg = teamMap.get(teamId) != null
                        ? (double) teamMap.get(teamId).get("seasonOppg")
                        : 0;
                int gamesPlayed = teamMap.get(teamId) != null ? (int) teamMap.get(teamId).get("gamesPlayed") : 0;
                int lastPf = teamMap.get(teamId) != null ? (int) teamMap.get(teamId).get("lastPf") : 0;
                int lastPa = teamMap.get(teamId) != null ? (int) teamMap.get(teamId).get("lastPa") : 0;
                long daysRested = teamMap.get(teamId) != null ? (long) teamMap.get(teamId).get("daysRested") : -1;
                double last5Pf = teamMap.get(teamId) != null ? (double) teamMap.get(teamId).get("last5Pf") : 0;
                double last5Pa = teamMap.get(teamId) != null ? (double) teamMap.get(teamId).get("last5Pa") : 0;
                double last10Pf = teamMap.get(teamId) != null ? (double) teamMap.get(teamId).get("last10Pf") : 0;
                double last10Pa = teamMap.get(teamId) != null ? (double) teamMap.get(teamId).get("last10Pa") : 0;
                double last10Over500Pf = teamMap.get(teamId) != null
                        ? (double) teamMap.get(teamId).get("last10Over500Pf")
                        : 0;
                double last10Over500Pa = teamMap.get(teamId) != null
                        ? (double) teamMap.get(teamId).get("last10Over500Pa")
                        : 0;
                double last10Under500Pf = teamMap.get(teamId) != null
                        ? (double) teamMap.get(teamId).get("last10Under500Pf")
                        : 0;
                double last10Under500Pa = teamMap.get(teamId) != null
                        ? (double) teamMap.get(teamId).get("last10Under500Pa")
                        : 0;
                double last5Over500Pf = teamMap.get(teamId) != null
                        ? (double) teamMap.get(teamId).get("last5Over500Pf")
                        : 0;
                double last5Over500Pa = teamMap.get(teamId) != null
                        ? (double) teamMap.get(teamId).get("last5Over500Pa")
                        : 0;
                double last5Under500Pf = teamMap.get(teamId) != null
                        ? (double) teamMap.get(teamId).get("last5Under500Pf")
                        : 0;
                double last5Under500Pa = teamMap.get(teamId) != null
                        ? (double) teamMap.get(teamId).get("last5Under500Pa")
                        : 0;
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
                int totalOppWins = teamMap.get(oppId) != null ? (int) teamMap.get(oppId).get("totalWins") : 0;
                int totalOppLosses = teamMap.get(oppId) != null ? (int) teamMap.get(oppId).get("totalLoss") : 0;
                boolean isOppOver500 = (totalOppWins - totalOppLosses) > 0;

                double[] last10GamesPfArr = teamMap.get(teamId) != null
                        ? (double[]) teamMap.get(teamId).get("last10GamesPfArr")
                        : new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
                double[] last10GamesPaArr = teamMap.get(teamId) != null
                        ? (double[]) teamMap.get(teamId).get("last10GamesPaArr")
                        : new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
                double[] last10Under500PaArr = teamMap.get(teamId) != null
                        ? (double[]) teamMap.get(teamId).get("last10Under500PaArr")
                        : new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
                double[] last10Under500PfArr = teamMap.get(teamId) != null
                        ? (double[]) teamMap.get(teamId).get("last10Under500PfArr")
                        : new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
                double[] last10Over500PfArr = teamMap.get(teamId) != null
                        ? (double[]) teamMap.get(teamId).get("last10Over500PfArr")
                        : new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
                double[] last10Over500PaArr = teamMap.get(teamId) != null
                        ? (double[]) teamMap.get(teamId).get("last10Over500PaArr")
                        : new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
                double[] last5HomeGamesPfArr = teamMap.get(teamId) != null
                        ? (double[]) teamMap.get(teamId).get("last5HomeGamesPfArr")
                        : new double[] { 0, 0, 0, 0, 0 };
                double[] last5HomeGamesPaArr = teamMap.get(teamId) != null
                        ? (double[]) teamMap.get(teamId).get("last5HomeGamesPaArr")
                        : new double[] { 0, 0, 0, 0, 0 };
                double[] last5AwayGamesPfArr = teamMap.get(teamId) != null
                        ? (double[]) teamMap.get(teamId).get("last5AwayGamesPfArr")
                        : new double[] { 0, 0, 0, 0, 0 };
                double[] last5AwayGamesPaArr = teamMap.get(teamId) != null
                        ? (double[]) teamMap.get(teamId).get("last5AwayGamesPaArr")
                        : new double[] { 0, 0, 0, 0, 0 };
                int[] last10TeamsPlayedArr = teamMap.get(teamId) != null
                        ? (int[]) teamMap.get(teamId).get("last10TeamsPlayedArr")
                        : new int[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
                int[] last10TeamsOver500Arr = teamMap.get(teamId) != null
                        ? (int[]) teamMap.get(teamId).get("last10TeamsOver500Arr")
                        : new int[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
                int[] last10TeamsUnder500Arr = teamMap.get(teamId) != null
                        ? (int[]) teamMap.get(teamId).get("last10TeamsUnder500Arr")
                        : new int[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
                int[] last5HomeTeamsPlayedArr = teamMap.get(teamId) != null
                        ? (int[]) teamMap.get(teamId).get("last5HomeTeamsPlayedArr")
                        : new int[] { -1, -1, -1, -1, -1 };
                int[] last5AwayTeamsPlayedArr = teamMap.get(teamId) != null
                        ? (int[]) teamMap.get(teamId).get("last5AwayTeamsPlayedArr")
                        : new int[] { -1, -1, -1, -1, -1 };
                String lastGameInfo = teamMap.get(teamId) != null ? (String) teamMap.get(teamId).get("lastGameInfo")
                        : "";
                String nextGameInfo = teamMap.get(teamId) != null ? (String) teamMap.get(teamId).get("nextGameInfo")
                        : "";

                // next game will be the game right before we increase our gamesPlayed stat so
                // nextGameInfo will be blank
                if (0 == game.getPeriod() && "".equals(nextGameInfo)) {
                    nextGameHomeTeam = game.getHome_team().getId() == teamId;

                    nextGameInfo = "The next game is " + game.getVisitor_team().getFull_name() + " at "
                            + game.getHome_team().getFull_name() + " on "
                            + game.getDate().replace("T00:00:00.000Z", "");
                }

                if ("Final".equals(game.getStatus())) {
                    gamesPlayed++;
                    seasonPpg += isHomeTeam ? game.getHome_team_score() : game.getVisitor_team_score();
                    seasonOppg += isHomeTeam ? game.getVisitor_team_score() : game.getHome_team_score();

                    // start update last 5 and 10 games stats
                    last5Pf = 0;
                    last5Pa = 0;
                    last10Pf = 0;
                    last10Pa = 0;
                    if (isOppOver500) {
                        last10Over500Pf = 0;
                        last10Over500Pa = 0;
                        last5Over500Pf = 0;
                        last5Over500Pa = 0;
                    } else {
                        last10Under500Pf = 0;
                        last10Under500Pa = 0;
                        last5Under500Pf = 0;
                        last5Under500Pa = 0;
                    }

                    if (isHomeTeam) { // if home team, update the home stats
                        last2HomePf = 0;
                        last2HomePa = 0;
                        last5HomePf = 0;
                        last5HomePa = 0;
                    } else { // otherwise update away stats
                        last5AwayPa = 0;
                        last5AwayPf = 0;
                        last2AwayPa = 0;
                        last2AwayPf = 0;
                    }

                    for (int idx = 0; idx <= 9; idx++) {
                        if (idx <= 8) {
                            last10GamesPfArr[idx] = last10GamesPfArr[(idx + 1)]; // shift last 10 games leftward
                            last10GamesPaArr[idx] = last10GamesPaArr[(idx + 1)];
                            last10TeamsPlayedArr[idx] = last10TeamsPlayedArr[(idx + 1)];
                            if (isOppOver500) { // opponent over .500 win
                                last10Over500PfArr[idx] = last10Over500PfArr[(idx + 1)];
                                last10Over500PaArr[idx] = last10Over500PaArr[(idx + 1)];
                                last10TeamsOver500Arr[idx] = last10TeamsOver500Arr[(idx + 1)];
                            } else { // .500 or less
                                last10Under500PaArr[idx] = last10Under500PaArr[(idx + 1)];
                                last10Under500PfArr[idx] = last10Under500PfArr[(idx + 1)];
                                last10TeamsUnder500Arr[idx] = last10TeamsUnder500Arr[(idx + 1)];
                            }
                        } else {// the last spot in the array, set to most recent game.
                            last10GamesPfArr[idx] = isHomeTeam ? game.getHome_team_score()
                                    : game.getVisitor_team_score();
                            last10GamesPaArr[idx] = isHomeTeam ? game.getVisitor_team_score()
                                    : game.getHome_team_score();
                            last10TeamsPlayedArr[idx] = isHomeTeam ? game.getVisitor_team().getId()
                                    : game.getHome_team().getId();
                            if (isOppOver500) { // opponent over .500 win
                                last10Over500PfArr[idx] = isHomeTeam ? game.getHome_team_score()
                                        : game.getVisitor_team_score();
                                last10Over500PaArr[idx] = isHomeTeam ? game.getVisitor_team_score()
                                        : game.getHome_team_score();
                                last10TeamsOver500Arr[idx] = isHomeTeam ? game.getVisitor_team().getId()
                                        : game.getHome_team().getId();
                            } else { // .500 or less
                                last10Under500PaArr[idx] = isHomeTeam ? game.getVisitor_team_score()
                                        : game.getHome_team_score();
                                last10Under500PfArr[idx] = isHomeTeam ? game.getHome_team_score()
                                        : game.getVisitor_team_score();
                                last10TeamsUnder500Arr[idx] = isHomeTeam ? game.getVisitor_team().getId()
                                        : game.getHome_team().getId();
                            }
                        }

                        if (idx > 4) {
                            last5Pf += last10GamesPfArr[idx];
                            last5Pa += last10GamesPaArr[idx];

                            if (isOppOver500) { // opponent over .500 win
                                last5Over500Pf += last10Over500PfArr[idx];
                                last5Over500Pa += last10Over500PaArr[idx];
                            } else { // .500 or less
                                last5Under500Pf += last10Under500PfArr[idx];
                                last5Under500Pa += last10Under500PaArr[idx];
                            }
                        }
                        last10Pf += last10GamesPfArr[idx];
                        last10Pa += last10GamesPaArr[idx];
                        if (isOppOver500) { // opponent over .500 win
                            last10Over500Pf += last10Over500PfArr[idx];
                            last10Over500Pa += last10Over500PaArr[idx];
                        } else { // .500 or less
                            last10Under500Pf += last10Under500PfArr[idx];
                            last10Under500Pa += last10Under500PaArr[idx];
                        }
                    }
                    // end update last 5 and 10 games stats

                    // start home and away stats
                    for (int idx = 0; idx <= 4; idx++) {
                        if (idx <= 3) {
                            if (isHomeTeam) {
                                last5HomeGamesPfArr[idx] = last5HomeGamesPfArr[(idx + 1)]; // shift last 5 games
                                last5HomeGamesPaArr[idx] = last5HomeGamesPaArr[(idx + 1)];
                                last5HomeTeamsPlayedArr[idx] = last5HomeTeamsPlayedArr[(idx + 1)];
                            } else {
                                last5AwayGamesPfArr[idx] = last5AwayGamesPfArr[(idx + 1)]; // shift last 5 games
                                last5AwayGamesPaArr[idx] = last5AwayGamesPaArr[(idx + 1)];
                                last5AwayTeamsPlayedArr[idx] = last5AwayTeamsPlayedArr[(idx + 1)];
                            }
                        } else {// the last spot in the array, set to most recent game.
                            if (isHomeTeam) {
                                last5HomeGamesPfArr[idx] = game.getHome_team_score();
                                last5HomeGamesPaArr[idx] = game.getVisitor_team_score();
                                last5HomeTeamsPlayedArr[idx] = game.getVisitor_team().getId();
                            } else {
                                last5AwayGamesPfArr[idx] = game.getVisitor_team_score();
                                last5AwayGamesPaArr[idx] = game.getHome_team_score();
                                last5AwayTeamsPlayedArr[idx] = game.getHome_team().getId();
                            }
                        }
                        if (isHomeTeam) {
                            if (idx > 2) {
                                last2HomePf += last5HomeGamesPfArr[idx];
                                last2HomePa += last5HomeGamesPaArr[idx];
                            }
                            last5HomePf += last5HomeGamesPfArr[idx];
                            last5HomePa += last5HomeGamesPaArr[idx];
                        } else {
                            if (idx > 2) {
                                last2AwayPf += last5AwayGamesPfArr[idx];
                                last2AwayPa += last5AwayGamesPaArr[idx];
                            }
                            last5AwayPf += last5AwayGamesPfArr[idx];
                            last5AwayPa += last5AwayGamesPaArr[idx];
                        }

                    }
                    // end home and away stats

                    if (isHomeTeam) {
                        if (game.getHome_team_score() > game.getVisitor_team_score()) {
                            totalWins++;
                            winStreak++;
                            homeWinStreak++;
                            homeLoseStreak = 0;
                            loseStreak = 0;
                        } else {
                            totalLoss++;
                            loseStreak++;
                            homeLoseStreak++;
                            homeWinStreak = 0;
                            winStreak = 0;
                        }
                    } else {
                        if (game.getHome_team_score() < game.getVisitor_team_score()) {
                            totalWins++;
                            winStreak++;
                            awayWinStreak++;
                            awayLoseStreak = 0;
                            loseStreak = 0;
                        } else {
                            totalLoss++;
                            loseStreak++;
                            awayLoseStreak++;
                            awayWinStreak = 0;
                            winStreak = 0;
                        }
                    }
                    // Start Last Game rankings and stats
                    lastPf = isHomeTeam ? game.getHome_team_score() : game.getVisitor_team_score();
                    lastPa = isHomeTeam ? game.getVisitor_team_score() : game.getHome_team_score();
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
                    // End Last Game
                }
                teamStatsJo.put("seasonPpg", seasonPpg);
                teamStatsJo.put("seasonOppg", seasonOppg);
                teamStatsJo.put("gamesPlayed", gamesPlayed);
                teamStatsJo.put("lastGameInfo", lastGameInfo);
                teamStatsJo.put("nextGameInfo", nextGameInfo);
                teamStatsJo.put("lastPf", lastPf);
                teamStatsJo.put("lastPa", lastPa);
                teamStatsJo.put("daysRested", daysRested);
                teamStatsJo.put("last5Pf", last5Pf);
                teamStatsJo.put("last5Pa", last5Pa);
                teamStatsJo.put("last10Pf", last10Pf);
                teamStatsJo.put("last10Pa", last10Pa);
                teamStatsJo.put("last5Over500Pf", last5Over500Pf);
                teamStatsJo.put("last5Over500Pa", last5Over500Pa);
                teamStatsJo.put("last5Under500Pf", last5Under500Pf);
                teamStatsJo.put("last5Under500Pa", last5Under500Pa);
                teamStatsJo.put("last10Over500Pf", last10Over500Pf);
                teamStatsJo.put("last10Over500Pa", last10Over500Pa);
                teamStatsJo.put("last10Under500Pf", last10Under500Pf);
                teamStatsJo.put("last10Under500Pa", last10Under500Pa);
                teamStatsJo.put("last10GamesPaArr", last10GamesPaArr);
                teamStatsJo.put("last10GamesPfArr", last10GamesPfArr);
                teamStatsJo.put("last10Over500PfArr", last10Over500PfArr);
                teamStatsJo.put("last10Over500PaArr", last10Over500PaArr);
                teamStatsJo.put("last10Under500PfArr", last10Under500PfArr);
                teamStatsJo.put("last10Under500PaArr", last10Under500PaArr);
                teamStatsJo.put("last10TeamsPlayedArr", last10TeamsPlayedArr);
                teamStatsJo.put("last10TeamsOver500Arr", last10TeamsOver500Arr);
                teamStatsJo.put("last10TeamsUnder500Arr", last10TeamsUnder500Arr);
                teamStatsJo.put("last5AwayTeamsPlayedArr", last5AwayTeamsPlayedArr);
                teamStatsJo.put("last5HomeTeamsPlayedArr", last5HomeTeamsPlayedArr);
                teamStatsJo.put("last5HomeGamesPfArr", last5HomeGamesPfArr);
                teamStatsJo.put("last5HomeGamesPaArr", last5HomeGamesPaArr);
                teamStatsJo.put("last5AwayGamesPfArr", last5AwayGamesPfArr);
                teamStatsJo.put("last5AwayGamesPaArr", last5AwayGamesPaArr);
                teamStatsJo.put("isHomeTeam", nextGameHomeTeam);
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
