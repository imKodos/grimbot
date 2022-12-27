package stats;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
// import javax.lang.model.util.Elements;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
// import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import teams.Team;

public class Scraper {

        // public static void main(String[] args) throws IOException {
        public static JSONObject getRecentGameStats(String teamShortName, String espnUrlString) throws IOException {
                Document teamSchedDoc = Jsoup
                                .connect("https://www.espn.com/nba/team/_/name/" + teamShortName + "/" + espnUrlString)
                                .timeout(6000)
                                .get();

                Elements tsBody = teamSchedDoc
                                .select("a.Schedule__Game.pa3.Schedule__Game--post");
                int pf1 = 0;
                int pa1 = 0;
                int pf5 = 0;
                int pa5 = 0;
                int pf10 = 0;
                int pa10 = 0;
                String lastGameInfo = "";
                for (int i = 0; i < 10; i++) {
                        try {
                                String gameResult = tsBody.select("div.Schedule__Meta").get(i).text();
                                String[] gameResultArr = gameResult.split("[WL-]");

                                if (i == 0) {
                                        lastGameInfo = tsBody.get(0).text().replace("vs", "vs ").replace("@", "@ ");
                                        pf1 += gameResult.startsWith("W") ? Integer.parseInt(gameResultArr[1])
                                                        : Integer.parseInt(gameResultArr[2]);
                                        pa1 += gameResult.startsWith("W") ? Integer.parseInt(gameResultArr[2])
                                                        : Integer.parseInt(gameResultArr[1]);
                                }

                                // if the prefix is a W, the first score is points for, second score is points
                                // against
                                if (i < 5) {
                                        pf5 += gameResult.startsWith("W") ? Integer.parseInt(gameResultArr[1])
                                                        : Integer.parseInt(gameResultArr[2]);
                                        pa5 += gameResult.startsWith("W") ? Integer.parseInt(gameResultArr[2])
                                                        : Integer.parseInt(gameResultArr[1]);
                                }

                                pf10 += gameResult.startsWith("W") ? Integer.parseInt(gameResultArr[1])
                                                : Integer.parseInt(gameResultArr[2]);
                                pa10 += gameResult.startsWith("W") ? Integer.parseInt(gameResultArr[2])
                                                : Integer.parseInt(gameResultArr[1]);

                        } catch (NumberFormatException e) {
                                System.out.println("Error handling score parsing: " + e);
                        }

                }

                JSONObject jo = new JSONObject();
                jo.put("lastGameInfo", lastGameInfo);
                jo.put("pf1", pf1);
                jo.put("pa1", pa1);
                jo.put("pf5", pf5);
                jo.put("pa5", pa5);
                jo.put("pf10", pf10);
                jo.put("pa10", pa10);

                return jo;
        }

        // public static void main(String[] args) throws IOException {
        public static JSONObject getInjuries(String teamShortName, String espnUrlString) throws IOException {
                // IOException {
                Document teamInjuries = Jsoup
                                .connect("https://www.espn.com/nba/team/depth/_/name/" + teamShortName + "/"
                                                + espnUrlString)
                                .timeout(6000)
                                .get();

                Elements injBody = teamInjuries
                                .select("tbody.Table__TBODY");
                Elements injRows = injBody.get(1)
                                .select("tr.Table__TR.Table__TR--sm.Table__even"); // get 1st table__tbody
                                                                                   // set, which contains players
                Elements players = injRows
                                .select("td:lt(2).Table__TD a.AnchorLink"); // get first 2 tds
                // set, which contains players
                Elements designation = injRows
                                .select("td:lt(2).Table__TD span.nfl-injuries-status.n8");
                // System.out.println(injRows.size());

                Map<String, String> injuryMap = new HashMap<>();
                int totalStartersInjured = 0;
                List<String> injuredPlayers = new Vector<>();
                for (int i = 0; i < players.size(); i++) {
                        try {
                                injuryMap.put(players.get(i).text(), designation.get(i).text());
                        } catch (NumberFormatException e) {
                                System.out.println("Error handling injury parsing: " + e);
                        }
                }

                for (Map.Entry<String, String> injuredPlayerEntry : injuryMap.entrySet()) {
                        if ("O".equals(injuredPlayerEntry.getValue())) {
                                injuredPlayers.add(injuredPlayerEntry.getKey());
                                totalStartersInjured++;
                        }
                }

                JSONObject jo = new JSONObject();
                jo.put("totalStartersInjured", totalStartersInjured);
                jo.put("injuredPlayers", injuredPlayers);

                return jo;
        }

        public static void main(String[] args) throws IOException {
                HashMap<Integer, JSONObject> teamMap = new HashMap<>();

                JSONObject jo = new JSONObject();
                jo.put("test", 2);
                jo.put(2, 3);
                jo.put("teamId", 3);
                teamMap.put(3, jo);

                jo = new JSONObject();
                jo.put("test", 2);
                jo.put(2, 3);
                jo.put("teamId", 2);
                teamMap.put(2, jo);
                System.out.println(teamMap.get(3).get("test"));
        }

}