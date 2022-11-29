package stats;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestCodeAndComments {
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

/*
 * ------------------------------------------------------------------
 * START [Data Source teamJsonData]
 * ------------------------------------------------------------------
 */
// need 2 datasource objects otherwise filtering one ddl will affect the other.
// todo need to figure out how to make a shared datasource obj and extract the
// data into a new data source obj,
// this will cut down on the code of having to do a full init on 2 data source

// var dataSource = new kendo.data.DataSource({
// transport: {
// read: {
// url: "scripts/teamJsonData.jsp",
// dataType: "json"
// }
// },
// sort: { field: "teamName", dir: "asc" },
// schema : {
// type: "json",
// data: "teamData",
// }
// });

// var dataSource2 = new kendo.data.DataSource({
// transport: {
// read: {
// url: "scripts/teamJsonData.jsp",
// dataType: "json"
// }
// },
// sort: { field: "teamName", dir: "asc" },
// schema : {
// type: "json",
// data: "teamData",
// }
// });

// $("#team1").kendoDropDownList({
// filter: "contains",
// dataSource: dataSource,
// dataTextField: "teamName",
// dataValueField: "teamId"
// });

// $("#team2").kendoDropDownList({
// filter: "contains",
// dataSource: dataSource2,
// dataTextField: "teamName",
// dataValueField: "teamId"
// });
/*
 * ------------------------------------------------------------------
 * End [Data Source teamJsonData]
 * ------------------------------------------------------------------
 */

/*
 * ------------------------------------------------------------------
 * Start [TeamName test code]
 * ------------------------------------------------------------------
 */

// list of all team and name
// for(TeamUtil.TeamName team : TeamUtil.TeamName.values()) {
// out.print("Team " + team.getId() + " is: " + team.getName()+ " <br>");
// }

// get team name from id
// Optional<TeamUtil.TeamName> teamEnum = TeamUtil.TeamName.getTeamByName(20);
// if(teamEnum.isPresent()) {
// out.print(teamEnum.get().getName()); //Knicks
// out.print(teamEnum.get()); //KNICKS
// }

// get enum from name
// Optional<TeamUtil.TeamName> teamEnum =
// TeamUtil.TeamName.getTeamByName("Knicks");
// if(teamEnum.isPresent()) {
// out.print(teamEnum.get().getId()); //20
// out.print(teamEnum.get()); //KNICKS
// }

// list of teams built
// for (Team curTeam : teamList) {
// out.println(curTeam.getName());
// }

// prints out team info
// for (Object curObj : teamsJson) {
// JSONObject teamObj = (JSONObject) curObj;
// out.write(teamObj.get("id") + " " + teamObj.get("name")+ "<br>");
// }
/*
 * ------------------------------------------------------------------
 * End [TeamName test code]
 * ------------------------------------------------------------------
 */