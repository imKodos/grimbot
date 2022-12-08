package stats;

import java.util.List;
import teams.HomeTeamPojo;
import teams.VisitorTeamPojo;

public class Games implements Comparable<Games> {
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

    @Override
    public int compareTo(Games g) {
        return Integer.compare(g.getId(), getId());
    }

    public int getId() {
        return this.id;
    }

    public void setId(int value) {
        this.id = value;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String value) {
        this.date = value;
    }

    public HomeTeamPojo getHome_team() {
        return this.home_team;
    }

    public void setHome_team(HomeTeamPojo value) {
        this.home_team = value;
    }

    public VisitorTeamPojo getVisitor_team() {
        return this.visitor_team;
    }

    public void setVisitor_team(VisitorTeamPojo value) {
        this.visitor_team = value;
    }

    public int getHome_team_score() {
        return this.home_team_score;
    }

    public void setHome_team_score(int value) {
        this.home_team_score = value;
    }

    public int getPeriod() {
        return this.period;
    }

    public void setPeriod(int value) {
        this.period = value;
    }

    public boolean getPostseason() {
        return this.postseason;
    }

    public void setPostseason(boolean value) {
        this.postseason = value;
    }

    public int getSeason() {
        return this.season;
    }

    public void setSeason(int value) {
        this.season = value;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String value) {
        this.status = value;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String value) {
        this.time = value;
    }

    public int getVisitor_team_score() {
        return this.visitor_team_score;
    }

    public void setVisitor_team_score(int value) {
        this.visitor_team_score = value;
    }
}
