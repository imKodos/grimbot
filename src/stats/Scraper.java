package stats;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper {

    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://www.nba.com/stats/teams/traditional?LastNGames=5").timeout(6000).get();

        Elements body = doc.select("tbody.Crom_body__UYOcU"); // in a tbody with class Crom_body__UYOcU
        // System.out.println(body.select("tr").size());
        for (Element e : body.select("tr")) {
            String rate = e.select("tr").text().trim();
            System.out.println(rate);
        }
    }
}