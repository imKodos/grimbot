package teams;

import teams.TeamUtil.TeamName;

public class Team {
    private final TeamName name;
    private final String teamName;
    private final String shortName;
    // private final String espnUrl;
    private final String lastGameInfo;
    private final int teamId;
    private final int seasonAvgPf;
    private final int seasonAvgPa;
    private final int lastPF;
    private final int lastPA;
    private final int last5PF;
    private final int last5PA;
    private final int last10PF;
    private final int last10PA;
    private final double varianceWeight;
    private final boolean playConsecutiveDays;
    private final boolean isDivisionGame;
    private final boolean hasPositiveRecord;
    private final int defenseRating;
    private final int offenseRating;
    private final int avgTimeOfPosession;
    private final int paceRating;
    private final boolean hasStartingInjury;
    private final boolean isHomeTeam;

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

    public int getLast5PF() {
        return last5PF;
    }

    public int getLast5PA() {
        return last5PA;
    }

    public int getLast10PF() {
        return last10PF;
    }

    public int getLast10PA() {
        return last10PA;
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

    public static class Builder {
        // required fields
        private final TeamName name;
        private int teamId = -1;
        private String shortName = "";
        // private String espnUrl = "";

        // optional fields
        private String teamName = "";
        private String lastGameInfo = "";
        private int seasonAvgPf = -1;
        private int seasonAvgPa = -1;
        private int lastPF = -1;
        private int lastPA = -1;
        private int last5PF = -1;
        private int last5PA = -1;
        private int last10PF = -1;
        private int last10PA = -1;
        private double varianceWeight = 1.00; // will fluctuate depending on the booleans below. will multiply outcome
        private boolean playConsecutiveDays = false; // if true, bring down variance weight slightly. Was it OT? bring
                                                     // it down more.
        private boolean isDivisionGame = false; // increase weight
        private boolean hasPositiveRecord = false; // if over .500, add slight increase weight. vs an under 500 team
                                                   // should be a slight advantage
        // addtl stats
        private int defenseRating;
        private int offenseRating;
        private int avgTimeOfPosession;
        private int paceRating;
        private boolean hasStartingInjury = false;
        private boolean isHomeTeam = false;

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

        public Builder seasonAvgPf(int val) {
            seasonAvgPf = val;
            return this;
        }

        public Builder seasonAvgPa(int val) {
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

        public Builder last5PA(int val) {
            last5PA = val;
            return this;
        }

        public Builder last5PF(int val) {
            last5PF = val;
            return this;
        }

        public Builder last10PF(int val) {
            last10PF = val;
            return this;
        }

        public Builder last10PA(int val) {
            last10PA = val;
            return this;
        }

        public Builder varianceWeight(double val) {
            varianceWeight = val;
            return this;
        }

        public Builder playConsecutiveDays(boolean val) {
            playConsecutiveDays = val;
            return this;
        }

        public Builder isDivisionGame(boolean val) {
            isDivisionGame = val;
            return this;
        }

        public Builder hasPositiveRecord(boolean val) {
            hasPositiveRecord = val;
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
        varianceWeight = builder.varianceWeight;
        playConsecutiveDays = builder.playConsecutiveDays;
        isDivisionGame = builder.isDivisionGame;
        hasPositiveRecord = builder.hasPositiveRecord;
        defenseRating = builder.defenseRating;
        offenseRating = builder.offenseRating;
        avgTimeOfPosession = builder.avgTimeOfPosession;
        paceRating = builder.paceRating;
        hasStartingInjury = builder.hasStartingInjury;
        isHomeTeam = builder.isHomeTeam;
    }

}

    
        
        
    

    
        
        
    

    
        
        
    

    
        
    

    