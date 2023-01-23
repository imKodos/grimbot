package teams;

import java.util.HashMap;
import java.util.Map;
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
    private final double last5Over500Pf;
    private final double last5Over500Pa;
    private final double last5Under500Pf;
    private final double last5Under500Pa;
    private final double last10Over500Pf;
    private final double last10Over500Pa;
    private final double last10Under500Pf;
    private final double last10Under500Pa;
    private final double last2AwayPa;
    private final double last2AwayPf;
    private final double last5AwayPa;
    private final double last5AwayPf;
    private final double last2HomePa;
    private final double last2HomePf;
    private final double last5HomePa;
    private final double last5HomePf;
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

    // // to be set in the games creation
    // private Map<Double, Double> oppLastODR;
    // private Map<Double, Double> oppLast5ODR;
    // private Map<Double, Double> oppLast10ODR;
    // private Map<Double, Double> oppLast2AwayODR;
    // private Map<Double, Double> oppLast2HomeODR;
    // private Map<Double, Double> oppLast5AwayODR;
    // private Map<Double, Double> oppLast5HomeODR;
    // to set in post team creation

    private int oRank;
    private int dRank;
    private int o10Rank;
    private int d10Rank;

    private double lastOppORank;
    private double lastOppDRank;
    private double normalizedLastPf;
    private double normalizedLastPa;

    private double last5OppORank;
    private double last5OppDRank;
    private double normalizedLast5Pf;
    private double normalizedLast5Pa;

    private double last10OppORank;
    private double last10OppDRank;
    private double normalizedLast10Pf;
    private double normalizedLast10Pa;

    private double last2HomeOppORank;
    private double last2HomeOppDRank;
    private double normalizedLast2HomePf;
    private double normalizedLast2HomePa;

    private double last5HomeOppORank;
    private double last5HomeOppDRank;
    private double normalizedLast5HomePf;
    private double normalizedLast5HomePa;

    private double last2AwayOppORank;
    private double last2AwayOppDRank;
    private double normalizedLast2AwayPf;
    private double normalizedLast2AwayPa;

    private double last5AwayOppORank;
    private double last5AwayOppDRank;
    private double normalizedLast5AwayPf;
    private double normalizedLast5AwayPa;

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

    public double getLast10Over500Pf() {
        return last10Over500Pf;
    }

    public double getLast10Over500Pa() {
        return last10Over500Pa;
    }

    public double getLast10Under500Pf() {
        return last10Under500Pf;
    }

    public double getLast10Under500Pa() {
        return last10Under500Pa;
    }

    public double getLast5Over500Pf() {
        return last5Over500Pf;
    }

    public double getLast5Over500Pa() {
        return last5Over500Pa;
    }

    public double getLast5Under500Pf() {
        return last5Under500Pf;
    }

    public double getLast5Under500Pa() {
        return last5Under500Pa;
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

    public String getFullName() {
        return fullName;
    }

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

    public void setORank(int offRank) {
        oRank = offRank;
    }

    public void setDRank(int defRank) {
        dRank = defRank;
    }

    public void setO10Rank(int offRank) {
        o10Rank = offRank;
    }

    public void setD10Rank(int defRank) {
        d10Rank = defRank;
    }

    public int getORank() {
        return oRank;
    }

    public int getDRank() {
        return dRank;
    }

    public int getO10Rank() {
        return o10Rank;
    }

    public int getD10Rank() {
        return d10Rank;
    }

    public void setLastOppORank(double offRank) {
        lastOppORank = offRank;
    }

    public void setLastOppDRank(double defRank) {
        lastOppDRank = defRank;
    }

    public void setLast5OppORank(double offRank) {
        last5OppORank = offRank;
    }

    public void setLast5OppDRank(double defRank) {
        last5OppDRank = defRank;
    }

    public void setLast10OppORank(double offRank) {
        last10OppORank = offRank;
    }

    public void setLast10OppDRank(double defRank) {
        last10OppDRank = defRank;
    }

    public void setLast2HomeOppORank(double offRank) {
        last2HomeOppORank = offRank;
    }

    public void setLast2HomeOppDRank(double defRank) {
        last2HomeOppDRank = defRank;
    }

    public void setLast5HomeOppORank(double offRank) {
        last5HomeOppORank = offRank;
    }

    public void setLast5HomeOppDRank(double defRank) {
        last5HomeOppDRank = defRank;
    }

    public double getLastOppORank() {
        return lastOppORank;
    }

    public double getLastOppDRank() {
        return lastOppDRank;
    }

    public double getLast5OppORank() {
        return last5OppORank;
    }

    public double getLast5OppDRank() {
        return last5OppDRank;
    }

    public double getLast10OppORank() {
        return last10OppORank;
    }

    public double getLast10OppDRank() {
        return last10OppDRank;
    }

    public double getLast2HomeOppORank() {
        return last2HomeOppORank;
    }

    public double getLast2HomeOppDRank() {
        return last2HomeOppDRank;
    }

    public double getLast5HomeOppORank() {
        return last5HomeOppORank;
    }

    public double getLast5HomeOppDRank() {
        return last5HomeOppDRank;
    }

    public double getLast2AwayOppORank() {
        return this.last2AwayOppORank;
    }

    public void setLast2AwayOppORank(double value) {
        this.last2AwayOppORank = value;
    }

    public double getLast2AwayOppDRank() {
        return this.last2AwayOppDRank;
    }

    public void setLast2AwayOppDRank(double value) {
        this.last2AwayOppDRank = value;
    }

    public double getLast5AwayOppORank() {
        return this.last5AwayOppORank;
    }

    public void setLast5AwayOppORank(double value) {
        this.last5AwayOppORank = value;
    }

    public double getLast5AwayOppDRank() {
        return this.last5AwayOppDRank;
    }

    public void setLast5AwayOppDRank(double value) {
        this.last5AwayOppDRank = value;
    }

    public double getNormalizedLastPf() {
        return this.normalizedLastPf;
    }

    public void setNormalizedLastPf(double value) {
        this.normalizedLastPf = value;
    }

    public double getNormalizedLastPa() {
        return this.normalizedLastPa;
    }

    public void setNormalizedLastPa(double value) {
        this.normalizedLastPa = value;
    }

    public double getNormalizedLast5Pf() {
        return this.normalizedLast5Pf;
    }

    public void setNormalizedLast5Pf(double value) {
        this.normalizedLast5Pf = value;
    }

    public double getNormalizedLast5Pa() {
        return this.normalizedLast5Pa;
    }

    public void setNormalizedLast5Pa(double value) {
        this.normalizedLast5Pa = value;
    }

    public double getNormalizedLast10Pf() {
        return this.normalizedLast10Pf;
    }

    public void setNormalizedLast10Pf(double value) {
        this.normalizedLast10Pf = value;
    }

    public double getNormalizedLast10Pa() {
        return this.normalizedLast10Pa;
    }

    public void setNormalizedLast10Pa(double value) {
        this.normalizedLast10Pa = value;
    }

    public double getNormalizedLast2HomePf() {
        return this.normalizedLast2HomePf;
    }

    public void setNormalizedLast2HomePf(double value) {
        this.normalizedLast2HomePf = value;
    }

    public double getNormalizedLast2HomePa() {
        return this.normalizedLast2HomePa;
    }

    public void setNormalizedLast2HomePa(double value) {
        this.normalizedLast2HomePa = value;
    }

    public double getNormalizedLast5HomePf() {
        return this.normalizedLast5HomePf;
    }

    public void setNormalizedLast5HomePf(double value) {
        this.normalizedLast5HomePf = value;
    }

    public double getNormalizedLast5HomePa() {
        return this.normalizedLast5HomePa;
    }

    public void setNormalizedLast5HomePa(double value) {
        this.normalizedLast5HomePa = value;
    }

    public double getNormalizedLast2AwayPf() {
        return this.normalizedLast2AwayPf;
    }

    public void setNormalizedLast2AwayPf(double value) {
        this.normalizedLast2AwayPf = value;
    }

    public double getNormalizedLast2AwayPa() {
        return this.normalizedLast2AwayPa;
    }

    public void setNormalizedLast2AwayPa(double value) {
        this.normalizedLast2AwayPa = value;
    }

    public double getNormalizedLast5AwayPf() {
        return this.normalizedLast5AwayPf;
    }

    public void setNormalizedLast5AwayPf(double value) {
        this.normalizedLast5AwayPf = value;
    }

    public double getNormalizedLast5AwayPa() {
        return this.normalizedLast5AwayPa;
    }

    public void setNormalizedLast5AwayPa(double value) {
        this.normalizedLast5AwayPa = value;
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
        private long daysRested = -1;
        private double last10Over500Pf = -1;
        private double last10Over500Pa = -1;
        private double last10Under500Pf = -1;
        private double last10Under500Pa = -1;
        private double last5Over500Pf = -1;
        private double last5Over500Pa = -1;
        private double last5Under500Pf = -1;
        private double last5Under500Pa = -1;

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
        private Vector<String> injuredPlayers;
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

        public Builder last10Over500Pf(double val) {
            last10Over500Pf = val;
            return this;
        }

        public Builder last10Over500Pa(double val) {
            last10Over500Pa = val;
            return this;
        }

        public Builder last10Under500Pf(double val) {
            last10Under500Pf = val;
            return this;
        }

        public Builder last10Under500Pa(double val) {
            last10Under500Pa = val;
            return this;
        }

        public Builder last5Over500Pf(double val) {
            last5Over500Pf = val;
            return this;
        }

        public Builder last5Over500Pa(double val) {
            last5Over500Pa = val;
            return this;
        }

        public Builder last5Under500Pf(double val) {
            last5Under500Pf = val;
            return this;
        }

        public Builder last5Under500Pa(double val) {
            last5Under500Pa = val;
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
        last5Over500Pf = builder.last5Over500Pf;
        last5Over500Pa = builder.last5Over500Pa;
        last5Under500Pf = builder.last5Under500Pf;
        last5Under500Pa = builder.last5Under500Pa;
        last10Over500Pf = builder.last10Over500Pf;
        last10Over500Pa = builder.last10Over500Pa;
        last10Under500Pf = builder.last10Under500Pf;
        last10Under500Pa = builder.last10Under500Pa;
        last2AwayPa = builder.last2AwayPa;
        last2AwayPf = builder.last2AwayPf;
        last5AwayPa = builder.last5AwayPa;
        last5AwayPf = builder.last5AwayPf;
        last2HomePa = builder.last2HomePa;
        last2HomePf = builder.last2HomePf;
        last5HomePa = builder.last5HomePa;
        last5HomePf = builder.last5HomePf;
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
