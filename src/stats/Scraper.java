package stats;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper {

    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://www.espn.com/nba/team/stats/_/name/bos/boston-celtics").timeout(6000)
                .get();

        // System.out.println(body.outerHtml());
        // System.out.println(body.text());

        JSONArray retArray = new JSONArray();
        Elements body = doc.select("span.Stats__TotalRow.fw-bold");
        Elements bodySpanSel = body.select("span");
        Map<Integer, String> necessaryIndecies = new HashMap<>();
        // look for these particular indecies for the for loop below,
        // and attach them with a value for the json obj
        necessaryIndecies.put(4, "totalPoints");
        for (int i = 0; i < bodySpanSel.size(); i++) {
            Element e = bodySpanSel.get(i);
            JSONArray jo = new JSONArray();

            // System.out.println(e.select("span").text().trim());
        }
        // return retArray;

        // Document teamSchedDoc =
        // Jsoup.connect("https://www.espn.com/nba/team/schedule/_/name/bos/boston-celtics")
        // .timeout(6000)
        // .get();

        // Elements dataIdx = teamSchedDoc.select("[data-idx=1]");
        // for (Element e : tsBodyTdSel) {
        // System.out.println(e.select("span").text().trim());
        // }

        // System.out.println(dataIdx.select("td.Table__TD").get(0).text());
        // System.out.println(dataIdx.select("td.Table__TD").get(1).text());
        // System.out.println(dataIdx.select("td.Table__TD").get(2).text());
        // for (int i = 0; i < 3; i++) {
        // Element e = dataIdx.get(i);
        // JSONArray jo = new JSONArray();
        // System.out.println("INDEX" + i);
        // System.out.println(e.select("tr").text().trim());
        // }

        Document teamSchedDoc = Jsoup.connect("https://www.espn.com/nba/team/_/name/bos/boston-celtics")
                .timeout(6000)
                .get();
        // Elements tsBody = teamSchedDoc
        // .select("section.Schedule__Group");

        Elements tsBody = teamSchedDoc
                .select("a.Schedule__Game.pa3.Schedule__Game--post");

        int pf5 = 0;
        int pa5 = 0;
        String lastGameInfo = "";
        for (int i = 0; i < 5; i++) {
            String gameResult = tsBody.select("div.Schedule__Meta").get(i).text();
            if (i == 0) {
                lastGameInfo = tsBody.get(0).text();
            }
            // if(gameResult.startsWith(gameResult))
            // put results into a json obj and make this a function that gets passed the url
            // as a parm, and build a team
        }
    }
}