package teams;

import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import teams.TeamUtil.TeamName;

public class Team {
    private final TeamName name;
    private final String teamName;
    private final String shortName;
    private final String fullName;
    // private final String city;
    // private final String espnUrl;
    private final String lastGameInfo;
    private final String nextGameInfo;
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
    private final double normalizedPf;
    private final double normalizedPa;
    private final long daysRested;
    private final boolean isHotStreak;
    private final boolean isColdStreak;
    private final boolean isAwayHotStreak;
    private final boolean isHomeHotStreak;
    private final boolean isAwayColdStreak;
    private final boolean isHomeColdStreak;
    private final boolean isHomeTeam;
    private final int totalWins;
    private final int totalLoss;
    private final int numStartersInjured;
    private final Vector<String> injuredPlayers;
    private final JSONObject teamJsonObj;

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

    public double getNormalizedPf() {
        return normalizedPf;
    }

    public double getNormalizedPa() {
        return normalizedPa;
    }

    public String getShortName() {
        return shortName;
    }

    public String getFullName() {
        return fullName;
    }

    // public String getEspnCode() {
    // return espnUrl;
    // }

    public String getLastGameInfo() {
        return lastGameInfo;
    }

    public String getNextGameInfo() {
        return nextGameInfo;
    }

    public double getSeasonAvgPa() {
        return seasonAvgPa;
    }

    public double getSeasonAvgPf() {
        return seasonAvgPf;
    }

    public long getDaysRested() {
        return daysRested;
    }

    public boolean isHomeTeam() {
        return isHomeTeam;
    }

    public boolean isHotStreak() {
        return isHotStreak;
    }

    public boolean isColdStreak() {
        return isColdStreak;
    }

    public boolean isHomeColdStreak() {
        return isHomeColdStreak;
    }

    public boolean isHomeHotStreak() {
        return isHomeHotStreak;
    }

    public boolean isAwayHotStreak() {
        return isAwayHotStreak;
    }

    public boolean isAwayColdStreak() {
        return isAwayColdStreak;
    }

    public int getTotalWins() {
        return totalWins;
    }

    public int getTotalLoss() {
        return totalLoss;
    }

    public int getNumStartersInjured() {
        return numStartersInjured;
    }

    public Vector<String> getInjuredPlayers() {
        return injuredPlayers;
    }

    public JSONObject getTeamJsonObj() {
        return teamJsonObj;
    }

    public static class Builder {
        // required fields
        private final TeamName name;
        private int teamId = -1;
        private String shortName = "";
        private String fullName = "";
        // private String espnUrl = "";

        // optional fields
        private String teamName = "";
        private String lastGameInfo = "";
        private String nextGameInfo = "";
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
        private double normalizedPf = -1;
        private double normalizedPa = -1;
        private double varianceWeight = 1.00; // will fluctuate depending on the booleans below. will multiply outcome
        private long daysRested = -1;

        // addtl stats
        private boolean isHomeTeam = false;
        private boolean isHotStreak = false;
        private boolean isColdStreak = false;
        private boolean isHomeHotStreak = false;
        private boolean isAwayHotStreak = false;
        private boolean isHomeColdStreak = false;
        private boolean isAwayColdStreak = false;
        private int totalWins = 0;
        private int totalLoss = 0;
        private int numStartersInjured = 0;
        private Vector<String> injuredPlayers;;
        private JSONObject teamJsonObj;

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

        public Builder fullName(String val) {
            fullName = val;
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

        public Builder nextGameInfo(String val) {
            nextGameInfo = val;
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

        public Builder normalizedPf(double val) {
            normalizedPf = val;
            return this;
        }

        public Builder normalizedPa(double val) {
            normalizedPa = val;
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

        public Builder isHomeTeam(boolean val) {
            isHomeTeam = val;
            return this;
        }

        public Builder isHotStreak(boolean val) {
            isHotStreak = val;
            return this;
        }

        public Builder isColdStreak(boolean val) {
            isColdStreak = val;
            return this;
        }

        public Builder isHomeColdStreak(boolean val) {
            isHomeColdStreak = val;
            return this;
        }

        public Builder isAwayColdStreak(boolean val) {
            isAwayColdStreak = val;
            return this;
        }

        public Builder isHomeHotStreak(boolean val) {
            isHomeHotStreak = val;
            return this;
        }

        public Builder isAwayHotStreak(boolean val) {
            isAwayHotStreak = val;
            return this;
        }

        public Builder totalWins(int val) {
            totalWins = val;
            return this;
        }

        public Builder totalLoss(int val) {
            totalLoss = val;
            return this;
        }

        public Builder numStartersInjured(int val) {
            numStartersInjured = val;
            return this;
        }

        public Builder injuredPlayers(Vector<String> val) {
            injuredPlayers = val;
            return this;
        }

        public Builder teamJsonObj(JSONObject val) {
            teamJsonObj = val;
            return this;
        }

        public Team build() {
            return new Team(this);
        }
    }

    private Team(Builder builder) {
        name = builder.name;
        shortName = builder.shortName;
        fullName = builder.fullName;
        // espnUrl = builder.espnUrl;
        teamId = builder.teamId;
        lastGameInfo = builder.lastGameInfo;
        nextGameInfo = builder.nextGameInfo;
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
        normalizedPf = builder.normalizedPf;
        normalizedPa = builder.normalizedPa;
        daysRested = builder.daysRested;
        isHomeTeam = builder.isHomeTeam;
        isHotStreak = builder.isHotStreak;
        isColdStreak = builder.isColdStreak;
        isHomeColdStreak = builder.isHomeColdStreak;
        isAwayColdStreak = builder.isAwayColdStreak;
        isAwayHotStreak = builder.isAwayHotStreak;
        isHomeHotStreak = builder.isHomeHotStreak;
        totalWins = builder.totalWins;
        totalLoss = builder.totalLoss;
        numStartersInjured = builder.numStartersInjured;
        injuredPlayers = builder.injuredPlayers;
        teamJsonObj = builder.teamJsonObj;
    }

}
