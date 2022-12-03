package stats;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper {

    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://www.espn.com/nba/team/stats/_/name/bos/boston-celtics").timeout(6000)
                .get();
        // Document doc =
        // Jsoup.connect("https://www.nba.com/stats/teams/traditional?LastNGames=5").timeout(6000).get();

        // Elements body =
        // doc.select("section.Block_block__62M07.nba-stats-content-block"); // in a
        // tbody with class
        Elements body = doc.select("span.Stats__TotalRow.fw-bold");

        System.out.println(body.outerHtml());
        System.out.println(body.text());
        for (Element e : body.select("tr")) {
            System.out.println("body");
            String rate = e.select("tr").text().trim();
            System.out.println(rate);
        }
    }
}