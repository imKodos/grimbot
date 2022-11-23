package stats;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ScraperTest {
    // https://www.youtube.com/watch?v=BEvRZUEQ3Dc&ab_channel=CamboTutorial
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://imdb.com/chart/top").timeout(6000).get();

        Elements body = doc.select("tbody.lister-list"); // in a tbody with class lister-list
        // System.out.println(body.select("tr").size());
        for (Element e : body.select("tr")) {
            String img = e.select("td.posterColumn img").attr("src"); // gets source image
            String title = e.select("td.posterColumn img").attr("alt");
            String year = e.select("td.titleColumn span.secondaryInfo").text().replaceAll("[^\\d]", ""); // get the
                                                                                                         // inner text
                                                                                                         // and remove
                                                                                                         // all brackets
                                                                                                         // https://regex101.com/
            String rate = e.select("td.ratingColumn.imdbRating").text().trim();
            System.out.println(rate);
        }
    }
}