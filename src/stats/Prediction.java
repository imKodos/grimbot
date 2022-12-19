package stats;

import org.json.simple.JSONObject;
import teams.Team;

public class Prediction {
    private int t1ScorePrediction = 0;
    private int t2ScorePrediction = 0;
    private String scorePrediction = "";
    private double t1Variance = 1;
    private double t2Variance = 1;

    public Prediction(Team t1, Team t2) {
        double t1WinPercent = t1.getTotalWins() / (t1.getTotalWins() + t1.getTotalLoss());
        double t2WinPercent = t2.getTotalWins() / (t2.getTotalWins() + t2.getTotalLoss());

        // if one team has a higher than 0.55 win rate, and the other is below 0.45, add
        // positive variance to that team.
        if (t1WinPercent >= 0.55 && t2WinPercent <= 0.45) {
            t1Variance += .03;
        }
        if (t2WinPercent >= 0.55 && t1WinPercent <= 0.45) {
            t2Variance += .03;
        }

        // more variance points:
        // daysRested
        // numStartersInjured
        // isHotStreak
        // isColdStreak
        // isHomeColdStreak
        // isHomeHotStreak
        // isAwayColdStreak
        // isAwayHotStreak
        // isHomeTeam

        // todo, find projected points for and points against.
        // match this up to the team being played against. take more value in the teams
        // projected total than how much the opposition allows.

        // weighted average scale:
        // multiply everything by a decimal that adds up to 1
        // or take weights, and divide total by weighted sum

        // lastPF / lastPA is highest weight
        // last2AwayPa / last2AwayPf
        // last2HomePa / last2HomePa
        // last5PF / last5PA
        // last5AwayPf / last5AwayPa
        // last5HomePf / last5HomePa
        // last10PF / last10PA
        // seasonAvgPf / seasonAvgPa season avg should be the base line average with the
        // lowest weight priority

    }

    public String getScorePrediction() {
        return scorePrediction;
    }
}
