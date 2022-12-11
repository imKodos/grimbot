package teams;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import teams.TeamUtil.TeamName;

public class Team {
    private final TeamName name;
    private final String teamName;
    private final String shortName;
    // private final String espnUrl;
    private final String lastGameInfo;
    private final int teamId;
    private final double seasonAvgPf;
    private final double seasonAvgPa;
    private final int lastPF;
    private final int lastPA;
    private final double last5PF;
    private final double last5PA;
    private final double last10PF;
    private final double last10PA;
    private final double last2AwayPa;
    private final double last2AwayPf;
    private final double last5AwayPa;
    private final double last5AwayPf;
    private final double last2HomePa;
    private final double last2HomePf;
    private final double last5HomePa;
    private final double last5HomePf;
    private final double varianceWeight;
    private final long daysRested;
    private final int defenseRating;
    private final int offenseRating;
    private final int avgTimeOfPosession;
    private final int paceRating;
    private final boolean hasStartingInjury;
    private final boolean isHomeTeam;
    private final JSONArray teamJsonArr;

    public TeamName getName() {
        return this.name;
    }

    public int getTeamId() {
        return teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getLastPF() {
        return lastPF;
    }

    public int getLastPA() {
        return lastPA;
    }

    public double getLast5PF() {
        return last5PF;
    }

    public double getLast5PA() {
        return last5PA;
    }

    public double getLast10PF() {
        return last10PF;
    }

    public double getLast10PA() {
        return last10PA;
    }

    public double getLast2AwayPa() {
        return last2AwayPa;
    }

    public double getLast2AwayPf() {
        return last2AwayPf;
    }

    public double getLast5AwayPa() {
        return last5AwayPa;
    }

    public double getLast5AwayPf() {
        return last5AwayPf;
    }

    public double getLast2HomePa() {
        return last2HomePa;
    }

    public double getLast2HomePf() {
        return last2HomePf;
    }

    public double getLast5HomePa() {
        return last5HomePa;
    }

    public double getLast5HomePf() {
        return last5HomePf;
    }

    public String getShortName() {
        return shortName;
    }

    // public String getEspnCode() {
    // return espnUrl;
    // }

    public String getLastGameInfo() {
        return lastGameInfo;
    }

    public double getSeasonAvgPa() {
        return seasonAvgPa;
    }

    public double getSeasonAvgPf() {
        return seasonAvgPf;
    }

    public JSONArray getTeamJsonArray() {
        return teamJsonArr;
    }

    public long getDaysRested() {
        return daysRested;
    }

    public boolean isHomeTeam() {
        return isHomeTeam;
    }

    public static class Builder {
        // required fields
        private final TeamName name;
        private int teamId = -1;
        private String shortName = "";
        // private String espnUrl = "";

        // optional fields
        private String teamName = "";
        private String lastGameInfo = "";
        private double seasonAvgPf = -1;
        private double seasonAvgPa = -1;
        private int lastPF = -1;
        private int lastPA = -1;
        private double last5PF = -1;
        private double last5PA = -1;
        private double last10PF = -1;
        private double last10PA = -1;
        private double last2AwayPa = -1;
        private double last2AwayPf = -1;
        private double last5AwayPa = -1;
        private double last5AwayPf = -1;
        private double last2HomePf = -1;
        private double last2HomePa = -1;
        private double last5HomePf = -1;
        private double last5HomePa = -1;
        private double varianceWeight = 1.00; // will fluctuate depending on the booleans below. will multiply outcome
        private long daysRested = -1;

        // addtl stats
        private int defenseRating;
        private int offenseRating;
        private int avgTimeOfPosession;
        private int paceRating;
        private boolean hasStartingInjury = false;
        private boolean isHomeTeam = false;
        private JSONArray teamJsonArr;

        public Builder(TeamName name) {
            this.name = name;
        }

        public Builder teamId(int val) {
            teamId = val;
            return this;
        }

        public Builder shortName(String val) {
            shortName = val;
            return this;
        }

        // public Builder espnUrl(String val) {
        // espnUrl = val;
        // return this;
        // }

        public Builder lastGameInfo(String val) {
            lastGameInfo = val;
            return this;
        }

        public Builder teamName(String val) {
            teamName = val;
            return this;
        }

        public Builder seasonAvgPf(double val) {
            seasonAvgPf = val;
            return this;
        }

        public Builder seasonAvgPa(double val) {
            seasonAvgPa = val;
            return this;
        }

        public Builder lastPA(int val) {
            lastPA = val;
            return this;
        }

        public Builder lastPF(int val) {
            lastPF = val;
            return this;
        }

        public Builder last5PA(double val) {
            last5PA = val;
            return this;
        }

        public Builder last5PF(double val) {
            last5PF = val;
            return this;
        }

        public Builder last10PF(double val) {
            last10PF = val;
            return this;
        }

        public Builder last10PA(double val) {
            last10PA = val;
            return this;
        }

        public Builder last2AwayPa(double val) {
            last2AwayPa = val;
            return this;
        }

        public Builder last2AwayPf(double val) {
            last2AwayPf = val;
            return this;
        }

        public Builder last5AwayPa(double val) {
            last5AwayPa = val;
            return this;
        }

        public Builder last5AwayPf(double val) {
            last5AwayPf = val;
            return this;
        }

        public Builder last2HomePa(double val) {
            last2HomePa = val;
            return this;
        }

        public Builder last2HomePf(double val) {
            last2HomePf = val;
            return this;
        }

        public Builder last5HomePa(double val) {
            last5HomePa = val;
            return this;
        }

        public Builder last5HomePf(double val) {
            last5HomePf = val;
            return this;
        }

        public Builder varianceWeight(double val) {
            varianceWeight = val;
            return this;
        }

        public Builder daysRested(long val) {
            daysRested = val;
            return this;
        }

        public Builder defenseRating(int val) {
            defenseRating = val;
            return this;
        }

        public Builder offenseRating(int val) {
            offenseRating = val;
            return this;
        }

        public Builder avgTimeOfPosession(int val) {
            avgTimeOfPosession = val;
            return this;
        }

        public Builder paceRating(int val) {
            paceRating = val;
            return this;
        }

        public Builder hasStartingInjury(boolean val) {
            hasStartingInjury = val;
            return this;
        }

        public Builder isHomeTeam(boolean val) {
            isHomeTeam = val;
            return this;
        }

        public Team build() {
            return new Team(this);
        }
    }

    private Team(Builder builder) {
        name = builder.name;
        shortName = builder.shortName;
        // espnUrl = builder.espnUrl;
        teamId = builder.teamId;
        lastGameInfo = builder.lastGameInfo;
        teamName = builder.teamName;
        seasonAvgPf = builder.seasonAvgPf;
        seasonAvgPa = builder.seasonAvgPa;
        lastPF = builder.lastPF;
        lastPA = builder.lastPA;
        last5PF = builder.last5PF;
        last5PA = builder.last5PA;
        last10PF = builder.last10PF;
        last10PA = builder.last10PA;
        last2AwayPa = builder.last2AwayPa;
        last2AwayPf = builder.last2AwayPf;
        last5AwayPa = builder.last5AwayPa;
        last5AwayPf = builder.last5AwayPf;
        last2HomePa = builder.last2HomePa;
        last2HomePf = builder.last2HomePf;
        last5HomePa = builder.last5HomePa;
        last5HomePf = builder.last5HomePf;
        varianceWeight = builder.varianceWeight;
        daysRested = builder.daysRested;
        defenseRating = builder.defenseRating;
        offenseRating = builder.offenseRating;
        avgTimeOfPosession = builder.avgTimeOfPosession;
        paceRating = builder.paceRating;
        hasStartingInjury = builder.hasStartingInjury;
        isHomeTeam = builder.isHomeTeam;

        // build a JSONArray on each team build
        JSONObject curTeamJo = new JSONObject();
        teamJsonArr = new JSONArray();
        curTeamJo.put("name", name);
        curTeamJo.put("shortName", shortName);
        curTeamJo.put("teamName", teamName);
        curTeamJo.put("teamId", teamId);
        curTeamJo.put("lastGameInfo", lastGameInfo);
        curTeamJo.put("lastPF", lastPF);
        curTeamJo.put("lastPA", lastPA);
        curTeamJo.put("last5PF", last5PF);
        curTeamJo.put("last5PA", last5PA);
        curTeamJo.put("last10PF", last10PF);
        curTeamJo.put("last10PA", last10PA);
        curTeamJo.put("seasonAvgPf", seasonAvgPf);
        curTeamJo.put("seasonAvgPa", seasonAvgPa);
        curTeamJo.put("daysRested", daysRested);
        curTeamJo.put("isHomeTeam", isHomeTeam);
        curTeamJo.put("last2AwayPa", last2AwayPa);
        curTeamJo.put("last2AwayPf", last2AwayPf);
        curTeamJo.put("last5AwayPa", last5AwayPa);
        curTeamJo.put("last5AwayPf", last5AwayPf);
        curTeamJo.put("last2HomePa", last2HomePa);
        curTeamJo.put("last2HomePf", last2HomePf);
        curTeamJo.put("last5HomePa", last5HomePa);
        curTeamJo.put("last5HomePf", last5HomePf);
        teamJsonArr.add(curTeamJo);

    }

}
