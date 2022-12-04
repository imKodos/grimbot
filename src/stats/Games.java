package stats;

import java.util.List;
import teams.HomeTeamPojo;
import teams.VisitorTeamPojo;

public class Games {
    public int id;
    public String date;
    public HomeTeamPojo home_team;
    public VisitorTeamPojo visitor_team;
    public int home_team_score;
    public int period;
    public boolean postseason;
    public int season;
    public String status;
    public String time;
    public int visitor_team_score;

    // String status;
    // HomeTeam home;
    // VistitingTeam vistor;
    // String home_team_score;
    // String visitor_team_score;
}
