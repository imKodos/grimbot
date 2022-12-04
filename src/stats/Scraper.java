package stats;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
}