package stats;

import teams.Team;

public class Prediction {
        private int t1ScorePrediction = 0;
        private int t2ScorePrediction = 0;
        private int t1GrimScorePrediction = 0;
        private int t2GrimScorePrediction = 0;

        private int t1LuciScorePrediction = 0;
        private int t2LuciScorePrediction = 0;

        private int t1DoomScorePrediction = 0;
        private int t2DoomScorePrediction = 0;

        private double t1Variance = 0;
        private double t2Variance = 0;
        private double t1Differential = 0;
        private double t2Differential = 0;
        private double t1Last10Differential = 0;
        private double t2Last10Differential = 0;
        private String winner = "";
        private String grimWinner = "";
        private String luciWinner = "";
        private String doomWinner = "";

        private double totalWeight = 0;
        private double totalDivisor = 0;

        private double luciWeight = 18;
        private double luciDivisor = 0;

        private int spreadChecklist = 0; // will be positive for t1 outcomes, negative for t2 outcomes

        public String getScorePrediction() {
                return t1ScorePrediction + " - " + t2ScorePrediction + " " + winner;
        }

        public String getTotalScorePrediction() {
                return t1ScorePrediction + t2ScorePrediction + "";
        }

        public String getGrimScorePrediction() {
                return t1GrimScorePrediction + " - " + t2GrimScorePrediction + " " +
                                grimWinner;
        }

        public String getGrimTotalScorePrediction() {
                return t1GrimScorePrediction + t2GrimScorePrediction + "";
        }

        public String getLuciScorePrediction() {
                return t1LuciScorePrediction + " - " + t2LuciScorePrediction + " " +
                                luciWinner;
        }

        public String getLuciTotalScorePrediction() {
                return t1LuciScorePrediction + t2LuciScorePrediction + "";
        }

        public String getDoomScorePrediction() {
                return t1DoomScorePrediction + " - " + t2DoomScorePrediction + " " + doomWinner;
        }

        public String getDoomTotalScorePrediction() {
                return t1DoomScorePrediction + t2DoomScorePrediction + "";
        }

        public double scoreCalculation(double pointsFor, double pointsAgainst) {
                return (pointsFor + pointsAgainst) / 2;
        }

        public double grimScoreCalculation(double pointsFor, double pointsAgainst, double oRank, double oppDRank) {
                double rankMultiplier = (oppDRank - oRank) * 0.003 + 1;
                // ex: 30-1 = 29 * 0.003 = 0.087 + 1 = 1.087
                // 1.042 1 v 30 29
                // ..
                // 1.003
                // 1 15 v 15 0
                // 0.97
                // ..
                // 0.958 30 v 1 -29
                return ((rankMultiplier * pointsFor) + pointsAgainst) / 2;
        }

        public double doomScoreCalculation(double pointsFor, double pointsAgainst, double dRank, double oppORank) {
                double rankMultiplier = (oppORank - dRank) * 0.0009 + 1;

                return ((rankMultiplier * pointsFor) + pointsAgainst) / 2;
        }

        public double getT1Variance() {
                return t1Variance;
        }

        public double getT2Variance() {
                return t2Variance;
        }

        public Prediction(Team t1, Team t2) {
                if (t1 != null && t2 != null) {
                        try {
                                // reg prediction = v1
                                // grim prediction = v1 + use O/D ranks
                                // luci prediction = grim + reverse weights + remove playing to defense cap
                                // doom prediction = theory of odds -- if things are going well, they will soon
                                // fail(reverse double variance)

                                // get variance; little odds and ends to not rely solely on weighted stats.
                                getVariance(t1, t2);

                                // the bulk of the score prediction based on season averages
                                getWeightedStats(t1, t2);

                                // the math that brings the weighted stats and variance together.
                                calculatePrediction(t1, t2);

                        } catch (Exception e) {
                                System.out.println(e);
                        }
                }
        }

        private void getVariance(Team t1, Team t2) {

                double t1WinPercent = t1.getTeamId() != -1
                                ? (t1.getTotalWins() / (t1.getTotalWins() + t1.getTotalLoss()))
                                : -1;
                double t2WinPercent = t2.getTeamId() != -1
                                ? (t2.getTotalWins() / (t2.getTotalWins() + t2.getTotalLoss()))
                                : -1;

                t1Differential = t1.getSeasonAvgPf() - t1.getSeasonAvgPa();
                t2Differential = t2.getSeasonAvgPf() - t2.getSeasonAvgPa();

                t1Last10Differential = t1.getLast10PF() - t1.getLast10PA();
                t2Last10Differential = t2.getLast10PF() - t2.getLast10PA();

                if (t1Differential >= 15 || t1Last10Differential >= 15) {
                        t1Variance += 3;
                        t2Variance -= 1;
                } else if (t1Differential >= 10 || t1Last10Differential >= 10) {
                        t1Variance += 2;
                        t2Variance -= 1;
                } else if (t1Differential >= 5 || t1Last10Differential >= 5) {
                        t1Variance += 1;
                } else if (t1Differential <= -15 || t1Last10Differential <= -15) {
                        t1Variance -= 1;
                        t2Variance += 3;
                } else if (t1Differential <= -10 || t1Last10Differential <= -10) {
                        t1Variance -= 1;
                        t2Variance += 2;
                } else if (t1Differential <= -5 || t1Last10Differential <= -5) {
                        t2Variance += 1;
                }

                if (t2Differential >= 15 || t2Last10Differential >= 15) {
                        t2Variance += 3;
                        t1Variance -= 1;
                } else if (t2Differential >= 10 || t2Last10Differential >= 10) {
                        t2Variance += 2;
                        t1Variance -= 1;
                } else if (t2Differential >= 5 || t2Last10Differential >= 5) {
                        t2Variance += 1;
                } else if (t2Differential <= -15 || t2Last10Differential <= -15) {
                        t2Variance -= 1;
                        t1Variance += 3;
                } else if (t2Differential <= -10 || t2Last10Differential <= -10) {
                        t2Variance -= 1;
                        t1Variance += 2;
                } else if (t2Differential <= -5 || t2Last10Differential <= -5) {
                        t1Variance += 1;
                }

                // if one team has a higher than 0.55 win rate, and the other is below 0.45, add
                // positive variance to that team.
                if (t1WinPercent >= 0.55 && t2WinPercent <= 0.5) {
                        t1Variance += 1;
                }

                if (t2WinPercent >= 0.55 && t1WinPercent <= 0.5) {
                        t2Variance += 1;
                }

                if (t2WinPercent > 0.5) {
                        if (t1.getLast5Over500Pf() > t1.getLast5Over500Pa()) {
                                t1Variance += 0.5;
                        } else {
                                t2Variance += 0.5;
                        }
                        if (t1.getLast10Over500Pf() > t1.getLast10Over500Pa()) {
                                t1Variance += 0.5;
                        } else {
                                t2Variance += 0.5;
                        }
                } else {
                        if (t1.getLast5Under500Pf() > t1.getLast5Under500Pa()) {
                                t1Variance += 0.5;
                        } else {
                                t2Variance += 0.5;
                        }
                        if (t1.getLast10Under500Pf() > t1.getLast10Under500Pa()) {
                                t1Variance += 0.5;
                        } else {
                                t2Variance += 0.5;
                        }
                }

                if (t1WinPercent > 0.5) {
                        if (t2.getLast5Over500Pf() > t2.getLast5Over500Pa()) {
                                t2Variance += 0.5;
                        } else {
                                t1Variance += 0.5;
                        }
                        if (t2.getLast10Over500Pf() > t2.getLast10Over500Pa()) {
                                t2Variance += 0.5;
                        } else {
                                t1Variance += 0.5;
                        }
                } else {
                        if (t2.getLast5Under500Pf() > t2.getLast5Under500Pa()) {
                                t2Variance += 0.5;
                        } else {
                                t1Variance += 0.5;
                        }
                        if (t2.getLast10Under500Pf() > t2.getLast10Under500Pa()) {
                                t2Variance += 0.5;
                        } else {
                                t1Variance += 0.5;
                        }
                }

                if (t1.getDaysRested() > 0 && t1.getDaysRested() < 3) { // if a team is between 1-2 days rest, add
                                                                        // variance for fresh legs
                        t1Variance += t1.getDaysRested();
                }
                if (t2.getDaysRested() > 0 && t2.getDaysRested() < 3) { // if a team is between 1-2 days rest, add
                                                                        // variance
                        t2Variance += t2.getDaysRested();
                }

                if (t1.getNumStartersInjured() > 0) {
                        t1Variance -= t1.getNumStartersInjured();
                }
                if (t2.getNumStartersInjured() > 0) {
                        t2Variance -= t2.getNumStartersInjured();
                }

                if (t1.isHotStreak()) {
                        t1Variance += 2;
                }

                if (t1.isColdStreak()) {
                        t1Variance -= 2;
                }

                if (t2.isHotStreak()) {
                        t2Variance += 2;
                }

                if (t2.isColdStreak()) {
                        t2Variance -= 2;
                }

                if (t1.isHomeTeam()) {
                        if (t1.isHomeHotStreak()) {
                                t1Variance += 2;
                        }
                        if (t1.isHomeColdStreak()) {
                                t1Variance -= 2;
                        }
                } else {
                        if (t1.isAwayHotStreak()) {
                                t1Variance += 2;
                        }
                        if (t1.isAwayColdStreak()) {
                                t1Variance -= 2;
                        }
                }

                if (t2.isHomeTeam()) {
                        if (t2.isHomeHotStreak()) {
                                t2Variance += 2;
                        }
                        if (t2.isHomeColdStreak()) {
                                t2Variance -= 2;
                        }
                } else {
                        if (t2.isAwayHotStreak()) {
                                t2Variance += 2;
                        }
                        if (t2.isAwayColdStreak()) {
                                t2Variance -= 2;
                        }
                }

                if (t1.getORank() < 10) { // plays at a higher pace, so expect a few more points
                        t2Variance += 1;
                }
                if (t2.getORank() < 10) { // plays at a higher pace, so expect a few more points
                        t1Variance += 1;
                }
                if (t1.getORank() > 20) { // plays at a slower pace, so expect a few more points
                        t2Variance -= 1;
                }
                if (t2.getORank() > 20) { // plays at a slower pace, so expect a few more points
                        t1Variance -= 1;
                }

                if (t1.getDRank() > 20) { // plays at a higher pace, so expect a few more points
                        t2Variance += 1;
                }
                if (t2.getDRank() > 20) { // plays at a higher pace, so expect a few more points
                        t1Variance += 1;
                }

                if (t1.getDRank() < 10) { // plays at a slower pace, so expect a few more points
                        t2Variance -= 1;
                }
                if (t2.getDRank() < 10) { // plays at a higher pace, so expect a few more points
                        t1Variance -= 1;
                }

                // System.out.println(request.getParameter("t2s") != null);
        }

        private void getWeightedStats(Team t1, Team t2) {
                // start with the lowest weight and work our way up in case we are missing some
                // data.

                // last game
                if (t1.getLastPF() != -1 && t2.getLastPF() != -1 &&
                                t1.getLastPA() != -1 && t2.getLastPA() != -1) {
                        totalWeight += 1; // keep this a standalone value
                        totalDivisor += totalWeight; // will be the sum of total weights used to divide by later
                        luciWeight -= 1; // keep this a standalone value
                        luciDivisor += luciWeight;

                        if (t1.getLastPF() - t1.getLastPA() > t2.getLastPF() - t2.getLastPA()) {
                                spreadChecklist++;
                        } else if (t2.getLastPF() - t2.getLastPA() > t1.getLastPF() - t1.getLastPA()) {
                                spreadChecklist--;
                        }

                        t1ScorePrediction += totalWeight
                                        * scoreCalculation(t1.getLastPF(), t2.getLastPA());
                        t2ScorePrediction += totalWeight
                                        * scoreCalculation(t2.getLastPF(), t1.getLastPA());

                        t1GrimScorePrediction += totalWeight
                                        * grimScoreCalculation(t1.getLastPF(), t2.getLastPA(), t1.getORank(),
                                                        t2.getDRank());
                        t2GrimScorePrediction += totalWeight
                                        * grimScoreCalculation(t2.getLastPF(), t1.getLastPA(), t2.getORank(),
                                                        t1.getDRank());

                        t1DoomScorePrediction += totalWeight
                                        * doomScoreCalculation(t1.getLastPF(), t2.getLastPA(),
                                                        t2.getDRank(), t1.getORank());

                        t2DoomScorePrediction += totalWeight
                                        * doomScoreCalculation(t2.getLastPF(), t1.getLastPA(),
                                                        t1.getDRank(), t2.getORank());

                        t1LuciScorePrediction += luciWeight
                                        * grimScoreCalculation(t1.getLastPF(), t2.getLastPA(), t1.getO10Rank(),
                                                        t2.getD10Rank());
                        t2LuciScorePrediction += luciWeight
                                        * grimScoreCalculation(t2.getLastPF(), t1.getLastPA(), t2.getO10Rank(),
                                                        t1.getD10Rank());
                }

                // if t1 is home, t2 is away
                if (t1.isHomeTeam() && t1.getLast2HomePf() != -1 && t2.getLast2AwayPa() != -1
                                &&
                                t1.getLast2HomePa() != -1 && t2.getLast2AwayPf() != -1) {
                        totalWeight += 1; // keep this a standalone value
                        totalDivisor += totalWeight; // will be the sum of total weights used to divide by later
                        luciWeight -= 1; // keep this a standalone value
                        luciDivisor += luciWeight;

                        if (t1.getLast2HomePf() - t1.getLast2HomePa() > t2.getLast2AwayPf() - t2.getLast2AwayPa()) {
                                spreadChecklist++;
                        } else if (t2.getLast2AwayPf() - t2.getLast2AwayPa() > t1.getLast2HomePf()
                                        - t1.getLast2HomePa()) {
                                spreadChecklist--;
                        }

                        t1ScorePrediction += totalWeight
                                        * scoreCalculation(t1.getLast2HomePf(), t2.getLast2AwayPa());
                        t2ScorePrediction += totalWeight
                                        * scoreCalculation(t2.getLast2AwayPf(), t1.getLast2HomePa());

                        t1GrimScorePrediction += totalWeight
                                        * grimScoreCalculation(t1.getLast2HomePf(), t2.getLast2AwayPa(), t1.getORank(),
                                                        t2.getDRank());
                        t2GrimScorePrediction += totalWeight
                                        * grimScoreCalculation(t2.getLast2AwayPf(), t1.getLast2HomePa(), t2.getORank(),
                                                        t1.getDRank());

                        t1DoomScorePrediction += totalWeight
                                        * doomScoreCalculation(t1.getLast2HomePf(), t2.getLast2AwayPa(), t2.getDRank(),
                                                        t1.getORank());
                        t2DoomScorePrediction += totalWeight
                                        * doomScoreCalculation(t2.getLast2AwayPf(), t1.getLast2HomePa(), t1.getDRank(),
                                                        t2.getORank());

                        t1LuciScorePrediction += luciWeight
                                        * grimScoreCalculation(t1.getLast2HomePf(), t2.getLast2AwayPa(),
                                                        t1.getO10Rank(),
                                                        t2.getD10Rank());
                        t2LuciScorePrediction += luciWeight
                                        * grimScoreCalculation(t2.getLast2AwayPf(), t1.getLast2HomePa(),
                                                        t2.getO10Rank(),
                                                        t1.getD10Rank());
                }

                // t1 away, t2 home
                if (t2.isHomeTeam() && t1.getLast2AwayPf() != -1 && t2.getLast2HomePa() != -1 &&
                                t1.getLast2AwayPa() != -1 && t2.getLast2HomePf() != -1) {
                        totalWeight += 1; // keep this a standalone value
                        totalDivisor += totalWeight; // will be the sum of total weights used to divide by later
                        luciWeight -= 1; // keep this a standalone value
                        luciDivisor += luciWeight;

                        if (t1.getLast2AwayPf() - t1.getLast2AwayPa() > t2.getLast2HomePf() - t2.getLast2HomePa()) {
                                spreadChecklist++;
                        } else if (t2.getLast2HomePf() - t2.getLast2HomePa() > t1.getLast2AwayPf()
                                        - t1.getLast2AwayPa()) {
                                spreadChecklist--;
                        }

                        t1ScorePrediction += totalWeight
                                        * scoreCalculation(t1.getLast2AwayPf(), t2.getLast2HomePa());
                        t2ScorePrediction += totalWeight
                                        * scoreCalculation(t2.getLast2HomePf(), t1.getLast2AwayPa());

                        t1GrimScorePrediction += totalWeight
                                        * grimScoreCalculation(t1.getLast2AwayPf(), t2.getLast2HomePa(), t1.getORank(),
                                                        t2.getDRank());
                        t2GrimScorePrediction += totalWeight
                                        * grimScoreCalculation(t2.getLast2HomePf(), t1.getLast2AwayPa(), t2.getORank(),
                                                        t1.getDRank());

                        t1DoomScorePrediction += totalWeight
                                        * doomScoreCalculation(t1.getLast2AwayPf(), t2.getLast2HomePa(),
                                                        t2.getDRank(), t1.getORank());
                        t2DoomScorePrediction += totalWeight
                                        * doomScoreCalculation(t2.getLast2HomePf(), t1.getLast2AwayPa(),
                                                        t1.getDRank(), t2.getORank());

                        t1LuciScorePrediction += luciWeight
                                        * grimScoreCalculation(t1.getLast2AwayPf(), t2.getLast2HomePa(),
                                                        t1.getO10Rank(),
                                                        t2.getD10Rank());
                        t2LuciScorePrediction += luciWeight
                                        * grimScoreCalculation(t2.getLast2HomePf(), t1.getLast2AwayPa(),
                                                        t2.getO10Rank(),
                                                        t1.getD10Rank());
                }

                // // last 5 home or away points
                // // if t1 is home, t2 is away
                // // if t2 is home, t1 is away
                if (t1.isHomeTeam() && t1.getLast5HomePf() != -1 && t2.getLast5AwayPa() != -1
                                &&
                                t1.getLast5HomePa() != -1 && t2.getLast5AwayPf() != -1) {
                        totalWeight += 1; // keep this a standalone value
                        totalDivisor += totalWeight; // will be the sum of total weights used to divide by later
                        luciWeight -= 1; // keep this a standalone value
                        luciDivisor += luciWeight;

                        if (t1.getLast5HomePf() - t1.getLast5HomePa() > t2.getLast5AwayPf() - t2.getLast5AwayPa()) {
                                spreadChecklist++;
                        } else if (t2.getLast5AwayPf() - t2.getLast5AwayPa() > t1.getLast5HomePf()
                                        - t1.getLast5HomePa()) {
                                spreadChecklist--;
                        }

                        t1ScorePrediction += totalWeight
                                        * scoreCalculation(t1.getLast5HomePf(), t2.getLast5AwayPa());
                        t2ScorePrediction += totalWeight
                                        * scoreCalculation(t2.getLast5AwayPf(), t1.getLast5HomePa());

                        t1GrimScorePrediction += totalWeight
                                        * grimScoreCalculation(t1.getLast5HomePf(), t2.getLast5AwayPa(), t1.getORank(),
                                                        t2.getDRank());
                        t2GrimScorePrediction += totalWeight
                                        * grimScoreCalculation(t2.getLast5AwayPf(), t1.getLast5HomePa(), t2.getORank(),
                                                        t1.getDRank());

                        t1DoomScorePrediction += totalWeight
                                        * doomScoreCalculation(t1.getLast5HomePf(), t2.getLast5AwayPa(), t2.getDRank(),
                                                        t1.getORank());
                        t2DoomScorePrediction += totalWeight
                                        * doomScoreCalculation(t2.getLast5AwayPf(), t1.getLast5HomePa(), t1.getDRank(),
                                                        t2.getORank());

                        t1LuciScorePrediction += luciWeight
                                        * grimScoreCalculation(t1.getLast5HomePf(), t2.getLast5AwayPa(),
                                                        t1.getO10Rank(),
                                                        t2.getD10Rank());
                        t2LuciScorePrediction += luciWeight
                                        * grimScoreCalculation(t2.getLast5AwayPf(), t1.getLast5HomePa(),
                                                        t2.getO10Rank(),
                                                        t1.getD10Rank());
                }

                // t1 away, t2 home
                if (t2.isHomeTeam() && t1.getLast5AwayPf() != -1 && t2.getLast5HomePa() != -1 &&
                                t1.getLast5AwayPa() != -1 && t2.getLast5HomePf() != -1) {
                        totalWeight += 1; // keep this a standalone value
                        totalDivisor += totalWeight; // will be the sum of total weights used to divide by later
                        luciWeight -= 1; // keep this a standalone value
                        luciDivisor += luciWeight;

                        if (t1.getLast5AwayPf() - t1.getLast5AwayPa() > t2.getLast5HomePf() - t2.getLast5HomePa()) {
                                spreadChecklist++;
                        } else if (t2.getLast5HomePf() - t2.getLast5HomePa() > t1.getLast5AwayPf()
                                        - t1.getLast5AwayPa()) {
                                spreadChecklist--;
                        }

                        t1ScorePrediction += totalWeight
                                        * scoreCalculation(t1.getLast5AwayPf(), t2.getLast5HomePa());
                        t2ScorePrediction += totalWeight
                                        * scoreCalculation(t2.getLast5HomePf(), t1.getLast5AwayPa());

                        t1GrimScorePrediction += totalWeight
                                        * grimScoreCalculation(t1.getLast5AwayPf(), t2.getLast5HomePa(), t1.getORank(),
                                                        t2.getDRank());
                        t2GrimScorePrediction += totalWeight
                                        * grimScoreCalculation(t2.getLast5HomePf(), t1.getLast5AwayPa(), t2.getORank(),
                                                        t1.getDRank());

                        t1DoomScorePrediction += totalWeight
                                        * doomScoreCalculation(t1.getLast5AwayPf(), t2.getLast5HomePa(), t2.getDRank(),
                                                        t1.getORank());
                        t2DoomScorePrediction += totalWeight
                                        * doomScoreCalculation(t2.getLast5HomePf(), t1.getLast5AwayPa(), t1.getDRank(),
                                                        t2.getORank());

                        t1LuciScorePrediction += luciWeight
                                        * grimScoreCalculation(t1.getLast5AwayPf(), t2.getLast5HomePa(),
                                                        t1.getO10Rank(),
                                                        t2.getD10Rank());
                        t2LuciScorePrediction += luciWeight
                                        * grimScoreCalculation(t2.getLast5HomePf(), t1.getLast5AwayPa(),
                                                        t2.getO10Rank(),
                                                        t1.getD10Rank());
                }

                if (t1.getLast10Over500Pf() != -1 && t1.getLast10Over500Pa() != -1 &&
                                t2.getLast10Over500Pf() != -1 && t2.getLast10Over500Pa() != -1 &&
                                t1.getLast10Under500Pf() != -1 && t1.getLast10Under500Pa() != -1 &&
                                t2.getLast10Under500Pf() != -1 && t2.getLast10Under500Pa() != -1) {
                        totalWeight += 1; // keep this a standalone value
                        totalDivisor += totalWeight; // will be the sum of total weights used to divide by later
                        luciWeight -= 1; // keep this a standalone value
                        luciDivisor += luciWeight;

                        if (t2.getTotalWins() > t2.getTotalLoss()) { // vs over 500
                                t1ScorePrediction += totalWeight
                                                * scoreCalculation(t1.getLast10Over500Pf(), t1.getLast10Over500Pa());
                                t1GrimScorePrediction += totalWeight
                                                * grimScoreCalculation(t1.getLast10Over500Pf(), t1.getLast10Over500Pa(),
                                                                t1.getORank(),
                                                                t2.getDRank());
                                t1DoomScorePrediction += totalWeight
                                                * doomScoreCalculation(t1.getLast10Over500Pf(), t1.getLast10Over500Pa(),
                                                                t2.getDRank(),
                                                                t1.getORank());
                                t1LuciScorePrediction += luciWeight
                                                * grimScoreCalculation(t1.getLast10Over500Pf(), t1.getLast10Over500Pa(),
                                                                t1.getO10Rank(),
                                                                t2.getD10Rank());
                        } else { // vs 500 or under
                                t1ScorePrediction += totalWeight
                                                * scoreCalculation(t1.getLast10Under500Pf(), t1.getLast10Under500Pa());
                                t1GrimScorePrediction += totalWeight
                                                * grimScoreCalculation(t1.getLast10Under500Pf(),
                                                                t1.getLast10Under500Pa(), t1.getORank(),
                                                                t2.getDRank());
                                t1DoomScorePrediction += totalWeight
                                                * doomScoreCalculation(t1.getLast10Under500Pf(),
                                                                t1.getLast10Under500Pa(), t2.getDRank(),
                                                                t1.getORank());
                                t1LuciScorePrediction += luciWeight
                                                * grimScoreCalculation(t1.getLast10Under500Pf(),
                                                                t1.getLast10Under500Pa(), t1.getO10Rank(),
                                                                t2.getD10Rank());
                        }

                        if (t1.getTotalWins() > t1.getTotalLoss()) { // vs over 500
                                t2ScorePrediction += totalWeight
                                                * scoreCalculation(t2.getLast10Over500Pf(), t2.getLast10Over500Pa());
                                t2GrimScorePrediction += totalWeight
                                                * grimScoreCalculation(t2.getLast10Over500Pf(), t2.getLast10Over500Pa(),
                                                                t2.getORank(),
                                                                t1.getDRank());
                                t2DoomScorePrediction += totalWeight
                                                * doomScoreCalculation(t2.getLast10Over500Pf(), t2.getLast10Over500Pa(),
                                                                t1.getDRank(),
                                                                t2.getORank());
                                t2LuciScorePrediction += luciWeight
                                                * grimScoreCalculation(t2.getLast10Over500Pf(), t2.getLast10Over500Pa(),
                                                                t2.getO10Rank(),
                                                                t1.getD10Rank());
                        } else { // vs 500 or under
                                t2ScorePrediction += totalWeight
                                                * scoreCalculation(t2.getLast10Under500Pf(), t2.getLast10Under500Pa());
                                t2GrimScorePrediction += totalWeight
                                                * grimScoreCalculation(t2.getLast10Under500Pf(),
                                                                t2.getLast10Under500Pa(), t2.getORank(),
                                                                t1.getDRank());
                                t2DoomScorePrediction += totalWeight
                                                * doomScoreCalculation(t2.getLast10Under500Pf(),
                                                                t2.getLast10Under500Pa(), t1.getDRank(),
                                                                t2.getORank());

                                t2LuciScorePrediction += luciWeight
                                                * grimScoreCalculation(t2.getLast10Under500Pf(),
                                                                t2.getLast10Under500Pa(), t2.getO10Rank(),
                                                                t1.getD10Rank());
                        }
                }

                if (t1.getLast5Over500Pf() != -1 && t1.getLast5Over500Pa() != -1 &&
                                t2.getLast5Over500Pf() != -1 && t2.getLast5Over500Pa() != -1 &&
                                t1.getLast5Under500Pf() != -1 && t1.getLast5Under500Pa() != -1 &&
                                t2.getLast5Under500Pf() != -1 && t2.getLast5Under500Pa() != -1) {
                        totalWeight += 1; // keep this a standalone value
                        totalDivisor += totalWeight; // will be the sum of total weights used to divide by later
                        luciWeight -= 1; // keep this a standalone value
                        luciDivisor += luciWeight;

                        if (t2.getTotalWins() > t2.getTotalLoss()) { // vs over 500
                                t1ScorePrediction += totalWeight
                                                * scoreCalculation(t1.getLast5Over500Pf(), t1.getLast5Over500Pa());
                                t1GrimScorePrediction += totalWeight
                                                * grimScoreCalculation(t1.getLast5Over500Pf(), t1.getLast5Over500Pa(),
                                                                t1.getORank(),
                                                                t2.getDRank());
                                t1DoomScorePrediction += totalWeight
                                                * doomScoreCalculation(t1.getLast5Over500Pf(), t1.getLast5Over500Pa(),
                                                                t2.getDRank(),
                                                                t1.getORank());
                                t1LuciScorePrediction += luciWeight
                                                * grimScoreCalculation(t1.getLast5Over500Pf(), t1.getLast5Over500Pa(),
                                                                t1.getO10Rank(),
                                                                t2.getD10Rank());
                        } else { // vs 500 or under
                                t1ScorePrediction += totalWeight
                                                * scoreCalculation(t1.getLast5Under500Pf(), t1.getLast5Under500Pa());
                                t1GrimScorePrediction += totalWeight
                                                * grimScoreCalculation(t1.getLast5Under500Pf(), t1.getLast5Under500Pa(),
                                                                t1.getORank(),
                                                                t2.getDRank());
                                t1DoomScorePrediction += totalWeight
                                                * doomScoreCalculation(t1.getLast5Under500Pf(), t1.getLast5Under500Pa(),
                                                                t2.getDRank(),
                                                                t1.getORank());
                                t1LuciScorePrediction += luciWeight
                                                * grimScoreCalculation(t1.getLast5Under500Pf(), t1.getLast5Under500Pa(),
                                                                t1.getO10Rank(),
                                                                t2.getD10Rank());
                        }

                        if (t1.getTotalWins() > t1.getTotalLoss()) { // vs over 500
                                t2ScorePrediction += totalWeight
                                                * scoreCalculation(t2.getLast5Over500Pf(), t2.getLast5Over500Pa());
                                t2GrimScorePrediction += totalWeight
                                                * grimScoreCalculation(t2.getLast5Over500Pf(), t2.getLast5Over500Pa(),
                                                                t2.getORank(),
                                                                t1.getDRank());
                                t2DoomScorePrediction += totalWeight
                                                * doomScoreCalculation(t2.getLast5Over500Pf(), t2.getLast5Over500Pa(),
                                                                t1.getDRank(),
                                                                t2.getORank());
                                t2LuciScorePrediction += luciWeight
                                                * grimScoreCalculation(t2.getLast5Over500Pf(), t2.getLast5Over500Pa(),
                                                                t2.getO10Rank(),
                                                                t1.getD10Rank());
                        } else { // vs 500 or under
                                t2ScorePrediction += totalWeight
                                                * scoreCalculation(t2.getLast5Under500Pf(), t2.getLast5Under500Pa());
                                t2GrimScorePrediction += totalWeight
                                                * grimScoreCalculation(t2.getLast5Under500Pf(), t2.getLast5Under500Pa(),
                                                                t2.getORank(),
                                                                t1.getDRank());
                                t2DoomScorePrediction += totalWeight
                                                * doomScoreCalculation(t2.getLast5Under500Pf(), t2.getLast5Under500Pa(),
                                                                t1.getDRank(),
                                                                t2.getORank());

                                t2LuciScorePrediction += luciWeight
                                                * grimScoreCalculation(t2.getLast5Under500Pf(), t2.getLast5Under500Pa(),
                                                                t2.getO10Rank(),
                                                                t1.getD10Rank());
                        }
                }

                // last 5
                if (t1.getLast5PF() != -1 && t2.getLast5PF() != -1 &&
                                t1.getLast5PA() != -1 && t2.getLast5PA() != -1) {
                        totalWeight += 1; // keep this a standalone value
                        totalDivisor += totalWeight; // will be the sum of total weights used to divide by later
                        luciWeight -= 1; // keep this a standalone value
                        luciDivisor += luciWeight;

                        if (t1.getLast5PF() - t1.getLast5PA() > t2.getLast5PF() - t2.getLast5PA()) {
                                spreadChecklist++;
                        } else if (t2.getLast5PF() - t2.getLast5PA() > t1.getLast5PF() - t1.getLast5PA()) {
                                spreadChecklist--;
                        }

                        t1ScorePrediction += totalWeight
                                        * scoreCalculation(t1.getLast5PF(), t2.getLast5PA());
                        t2ScorePrediction += totalWeight
                                        * scoreCalculation(t2.getLast5PF(), t1.getLast5PA());

                        t1GrimScorePrediction += totalWeight
                                        * grimScoreCalculation(t1.getLast5PF(), t2.getLast5PA(), t1.getORank(),
                                                        t2.getDRank());
                        t2GrimScorePrediction += totalWeight
                                        * grimScoreCalculation(t2.getLast5PF(), t1.getLast5PA(), t2.getORank(),
                                                        t1.getDRank());

                        t1DoomScorePrediction += totalWeight
                                        * doomScoreCalculation(t1.getLast5PF(), t2.getLast5PA(), t2.getDRank(),
                                                        t1.getORank());
                        t2DoomScorePrediction += totalWeight
                                        * doomScoreCalculation(t2.getLast5PF(), t1.getLast5PA(), t1.getDRank(),
                                                        t2.getORank());

                        t1LuciScorePrediction += luciWeight
                                        * grimScoreCalculation(t1.getLast5PF(), t2.getLast5PA(), t1.getO10Rank(),
                                                        t2.getD10Rank());
                        t2LuciScorePrediction += luciWeight
                                        * grimScoreCalculation(t2.getLast5PF(), t1.getLast5PA(), t2.getO10Rank(),
                                                        t1.getD10Rank());
                }

                // // get last 10 averages
                if (t1.getLast10PF() != -1 && t2.getLast10PF() != -1 &&
                                t1.getLast10PA() != -1 && t2.getLast10PA() != -1) {
                        totalWeight += 1; // keep this a standalone value
                        totalDivisor += totalWeight; // will be the sum of total weights used to divide by later
                        luciWeight -= 1; // keep this a standalone value
                        luciDivisor += luciWeight;

                        if (t1.getLast10PF() - t1.getLast10PA() > t2.getLast10PF() - t2.getLast10PA()) {
                                spreadChecklist++;
                        } else if (t2.getLast10PF() - t2.getLast10PA() > t1.getLast10PF() - t1.getLast10PA()) {
                                spreadChecklist--;
                        }

                        // ex t1 pf 120, t2 pa 110 -- expected t1 pf is 115, same for t2 pa.
                        t1ScorePrediction += totalWeight
                                        * scoreCalculation(t1.getLast10PF(), t2.getLast10PA());
                        t2ScorePrediction += totalWeight
                                        * scoreCalculation(t2.getLast10PF(), t1.getLast10PA());

                        t1GrimScorePrediction += totalWeight
                                        * grimScoreCalculation(t1.getLast10PF(), t2.getLast10PA(), t1.getORank(),
                                                        t2.getDRank());
                        t2GrimScorePrediction += totalWeight
                                        * grimScoreCalculation(t2.getLast10PF(), t1.getLast10PA(), t2.getORank(),
                                                        t1.getDRank());

                        t1DoomScorePrediction += totalWeight
                                        * doomScoreCalculation(t1.getLast10PF(), t2.getLast10PA(), t2.getDRank(),
                                                        t1.getORank());
                        t2DoomScorePrediction += totalWeight
                                        * doomScoreCalculation(t2.getLast10PF(), t1.getLast10PA(), t1.getDRank(),
                                                        t2.getORank());

                        t1LuciScorePrediction += luciWeight
                                        * grimScoreCalculation(t1.getLast10PF(), t2.getLast10PA(), t1.getO10Rank(),
                                                        t2.getD10Rank());
                        t2LuciScorePrediction += luciWeight
                                        * grimScoreCalculation(t2.getLast10PF(), t1.getLast10PA(), t2.getO10Rank(),
                                                        t1.getD10Rank());
                }

                // if t1 is home, t2 is away
                if (t1.isHomeTeam() && t1.getNormalizedLast2HomePf() != -1 && t2.getNormalizedLast2AwayPa() != -1
                                &&
                                t1.getNormalizedLast2HomePa() != -1 && t2.getNormalizedLast2AwayPf() != -1) {
                        totalWeight += 1; // keep this a standalone value
                        totalDivisor += totalWeight; // will be the sum of total weights used to divide by later
                        luciWeight -= 1; // keep this a standalone value
                        luciDivisor += luciWeight;

                        if (t1.getNormalizedLast2HomePf() - t1.getNormalizedLast2HomePa() > t2
                                        .getNormalizedLast2AwayPf() - t2.getNormalizedLast2AwayPa()) {
                                spreadChecklist++;
                        } else if (t2.getNormalizedLast2AwayPf() - t2.getNormalizedLast2AwayPa() > t1
                                        .getNormalizedLast2HomePf() - t1.getNormalizedLast2HomePa()) {
                                spreadChecklist--;
                        }

                        t1ScorePrediction += totalWeight
                                        * scoreCalculation(t1.getNormalizedLast2HomePf(),
                                                        t2.getNormalizedLast2AwayPa());
                        t2ScorePrediction += totalWeight
                                        * scoreCalculation(t2.getNormalizedLast2HomePa(),
                                                        t1.getNormalizedLast2AwayPf());

                        t1GrimScorePrediction += totalWeight
                                        * grimScoreCalculation(t1.getNormalizedLast2HomePf(),
                                                        t2.getNormalizedLast2AwayPa(), t1.getORank(),
                                                        t2.getDRank());
                        t2GrimScorePrediction += totalWeight
                                        * grimScoreCalculation(t2.getNormalizedLast2HomePa(),
                                                        t1.getNormalizedLast2AwayPf(), t2.getORank(),
                                                        t1.getDRank());

                        t1DoomScorePrediction += totalWeight
                                        * doomScoreCalculation(t1.getNormalizedLast2HomePf(),
                                                        t2.getNormalizedLast2AwayPa(), t2.getDRank(),
                                                        t1.getORank());
                        t2DoomScorePrediction += totalWeight
                                        * doomScoreCalculation(t2.getNormalizedLast2HomePa(),
                                                        t1.getNormalizedLast2AwayPf(), t1.getDRank(),
                                                        t2.getORank());

                        t1LuciScorePrediction += luciWeight
                                        * grimScoreCalculation(t1.getNormalizedLast2HomePf(),
                                                        t2.getNormalizedLast2AwayPa(),
                                                        t1.getO10Rank(),
                                                        t2.getD10Rank());
                        t2LuciScorePrediction += luciWeight
                                        * grimScoreCalculation(t2.getNormalizedLast2HomePa(),
                                                        t1.getNormalizedLast2AwayPf(),
                                                        t2.getO10Rank(),
                                                        t1.getD10Rank());
                }

                // t1 away, t2 home
                if (t2.isHomeTeam() && t1.getNormalizedLast2AwayPf() != -1 && t2.getNormalizedLast2HomePa() != -1 &&
                                t1.getNormalizedLast2AwayPa() != -1 && t2.getNormalizedLast2HomePf() != -1) {
                        totalWeight += 1; // keep this a standalone value
                        totalDivisor += totalWeight; // will be the sum of total weights used to divide by later
                        luciWeight -= 1; // keep this a standalone value
                        luciDivisor += luciWeight;

                        if (t1.getNormalizedLast2AwayPf() - t1.getNormalizedLast2AwayPa() > t2
                                        .getNormalizedLast2HomePf() - t2.getNormalizedLast2HomePa()) {
                                spreadChecklist++;
                        } else if (t2.getNormalizedLast2HomePf() - t2.getNormalizedLast2HomePa() > t1
                                        .getNormalizedLast2AwayPf() - t1.getNormalizedLast2AwayPa()) {
                                spreadChecklist--;
                        }

                        t1ScorePrediction += totalWeight
                                        * scoreCalculation(t1.getNormalizedLast2AwayPf(),
                                                        t2.getNormalizedLast2HomePa());
                        t2ScorePrediction += totalWeight
                                        * scoreCalculation(t2.getNormalizedLast2HomePf(),
                                                        t1.getNormalizedLast2AwayPa());

                        t1GrimScorePrediction += totalWeight
                                        * grimScoreCalculation(t1.getNormalizedLast2AwayPf(),
                                                        t2.getNormalizedLast2HomePa(), t1.getORank(),
                                                        t2.getDRank());
                        t2GrimScorePrediction += totalWeight
                                        * grimScoreCalculation(t2.getNormalizedLast2HomePf(),
                                                        t1.getNormalizedLast2AwayPa(), t2.getORank(),
                                                        t1.getDRank());

                        t1DoomScorePrediction += totalWeight
                                        * doomScoreCalculation(t1.getNormalizedLast2AwayPf(),
                                                        t2.getNormalizedLast2HomePa(), t2.getDRank(),
                                                        t1.getORank());
                        t2DoomScorePrediction += totalWeight
                                        * doomScoreCalculation(t2.getNormalizedLast2HomePf(),
                                                        t1.getNormalizedLast2AwayPa(), t1.getDRank(),
                                                        t2.getORank());

                        t1LuciScorePrediction += luciWeight
                                        * grimScoreCalculation(t1.getNormalizedLast2AwayPf(),
                                                        t2.getNormalizedLast2HomePa(),
                                                        t1.getO10Rank(),
                                                        t2.getD10Rank());
                        t2LuciScorePrediction += luciWeight
                                        * grimScoreCalculation(t2.getNormalizedLast2HomePf(),
                                                        t1.getNormalizedLast2AwayPa(),
                                                        t2.getO10Rank(),
                                                        t1.getD10Rank());
                }

                // if t1 is home, t2 is away
                if (t1.isHomeTeam() && t1.getNormalizedLast5HomePf() != -1 && t2.getNormalizedLast5AwayPa() != -1
                                &&
                                t1.getNormalizedLast5HomePa() != -1 && t2.getNormalizedLast5AwayPf() != -1) {
                        totalWeight += 1; // keep this a standalone value
                        totalDivisor += totalWeight; // will be the sum of total weights used to divide by later
                        luciWeight -= 1; // keep this a standalone value
                        luciDivisor += luciWeight;

                        if (t1.getNormalizedLast5HomePf() - t1.getNormalizedLast5HomePa() > t2
                                        .getNormalizedLast5AwayPf() - t2.getNormalizedLast5AwayPa()) {
                                spreadChecklist++;
                        } else if (t2.getNormalizedLast5AwayPf() - t2.getNormalizedLast5AwayPa() > t1
                                        .getNormalizedLast5HomePf() - t1.getNormalizedLast5HomePa()) {
                                spreadChecklist--;
                        }

                        t1ScorePrediction += totalWeight
                                        * scoreCalculation(t1.getNormalizedLast5HomePf(),
                                                        t2.getNormalizedLast5AwayPa());
                        t2ScorePrediction += totalWeight
                                        * scoreCalculation(t2.getNormalizedLast5HomePa(),
                                                        t1.getNormalizedLast5AwayPf());

                        t1GrimScorePrediction += totalWeight
                                        * grimScoreCalculation(t1.getNormalizedLast5HomePf(),
                                                        t2.getNormalizedLast5AwayPa(), t1.getORank(),
                                                        t2.getDRank());
                        t2GrimScorePrediction += totalWeight
                                        * grimScoreCalculation(t2.getNormalizedLast5HomePa(),
                                                        t1.getNormalizedLast5AwayPf(), t2.getORank(),
                                                        t1.getDRank());

                        t1DoomScorePrediction += totalWeight
                                        * doomScoreCalculation(t1.getNormalizedLast5HomePf(),
                                                        t2.getNormalizedLast5AwayPa(), t2.getDRank(),
                                                        t1.getORank());
                        t2DoomScorePrediction += totalWeight
                                        * doomScoreCalculation(t2.getNormalizedLast5HomePa(),
                                                        t1.getNormalizedLast5AwayPf(), t1.getDRank(),
                                                        t2.getORank());

                        t1LuciScorePrediction += luciWeight
                                        * grimScoreCalculation(t1.getNormalizedLast5HomePf(),
                                                        t2.getNormalizedLast5AwayPa(),
                                                        t1.getO10Rank(),
                                                        t2.getD10Rank());
                        t2LuciScorePrediction += luciWeight
                                        * grimScoreCalculation(t2.getNormalizedLast5HomePa(),
                                                        t1.getNormalizedLast5AwayPf(),
                                                        t2.getO10Rank(),
                                                        t1.getD10Rank());
                }

                // t1 away, t2 home
                if (t2.isHomeTeam() && t1.getNormalizedLast5AwayPf() != -1 && t2.getNormalizedLast5HomePa() != -1 &&
                                t1.getNormalizedLast5AwayPa() != -1 && t2.getNormalizedLast5HomePf() != -1) {
                        totalWeight += 1; // keep this a standalone value
                        totalDivisor += totalWeight; // will be the sum of total weights used to divide by later
                        luciWeight -= 1; // keep this a standalone value
                        luciDivisor += luciWeight;

                        if (t1.getNormalizedLast5AwayPf() - t1.getNormalizedLast5AwayPa() > t2
                                        .getNormalizedLast5HomePf() - t2.getNormalizedLast5HomePa()) {
                                spreadChecklist++;
                        } else if (t2.getNormalizedLast5HomePf() - t2.getNormalizedLast5HomePa() > t1
                                        .getNormalizedLast5AwayPf() - t1.getNormalizedLast5AwayPa()) {
                                spreadChecklist--;
                        }

                        t1ScorePrediction += totalWeight
                                        * scoreCalculation(t1.getNormalizedLast5AwayPf(),
                                                        t2.getNormalizedLast5HomePa());
                        t2ScorePrediction += totalWeight
                                        * scoreCalculation(t2.getNormalizedLast5HomePf(),
                                                        t1.getNormalizedLast5AwayPa());

                        t1GrimScorePrediction += totalWeight
                                        * grimScoreCalculation(t1.getNormalizedLast5AwayPf(),
                                                        t2.getNormalizedLast5HomePa(), t1.getORank(),
                                                        t2.getDRank());
                        t2GrimScorePrediction += totalWeight
                                        * grimScoreCalculation(t2.getNormalizedLast5HomePf(),
                                                        t1.getNormalizedLast5AwayPa(), t2.getORank(),
                                                        t1.getDRank());

                        t1DoomScorePrediction += totalWeight
                                        * doomScoreCalculation(t1.getNormalizedLast5AwayPf(),
                                                        t2.getNormalizedLast5HomePa(), t2.getDRank(),
                                                        t1.getORank());
                        t2DoomScorePrediction += totalWeight
                                        * doomScoreCalculation(t2.getNormalizedLast5HomePf(),
                                                        t1.getNormalizedLast5AwayPa(), t1.getDRank(),
                                                        t2.getORank());

                        t1LuciScorePrediction += luciWeight
                                        * grimScoreCalculation(t1.getNormalizedLast5AwayPf(),
                                                        t2.getNormalizedLast5HomePa(),
                                                        t1.getO10Rank(),
                                                        t2.getD10Rank());
                        t2LuciScorePrediction += luciWeight
                                        * grimScoreCalculation(t2.getNormalizedLast5HomePf(),
                                                        t1.getNormalizedLast5AwayPa(),
                                                        t2.getO10Rank(),
                                                        t1.getD10Rank());
                }

                // get t1 season pf and t2 season pa comparison
                if (t1.getSeasonAvgPf() != -1 && t2.getSeasonAvgPf() != -1 &&
                                t1.getSeasonAvgPa() != -1 && t2.getSeasonAvgPa() != -1) {
                        totalWeight += 1; // keep this a standalone value
                        totalDivisor += totalWeight; // will be the sum of total weights used to divide by later
                        luciWeight -= 1; // keep this a standalone value
                        luciDivisor += luciWeight;

                        if (t1.getSeasonAvgPf() - t1.getSeasonAvgPa() > t2.getSeasonAvgPf() - t2.getSeasonAvgPa()) {
                                spreadChecklist++;
                        } else if (t2.getSeasonAvgPf() - t2.getSeasonAvgPa() > t1.getSeasonAvgPf()
                                        - t1.getSeasonAvgPa()) {
                                spreadChecklist--;
                        }

                        // ex t1 pf 120, t2 pa 110 -- expected t1 pf is 115, same for t2 pa.
                        t1ScorePrediction += totalWeight
                                        * scoreCalculation(t1.getSeasonAvgPf(), t2.getSeasonAvgPa());
                        t2ScorePrediction += totalWeight
                                        * scoreCalculation(t2.getSeasonAvgPf(), t1.getSeasonAvgPa());

                        t1GrimScorePrediction += totalWeight
                                        * grimScoreCalculation(t1.getSeasonAvgPf(), t2.getSeasonAvgPa(), t1.getORank(),
                                                        t2.getDRank());
                        t2GrimScorePrediction += totalWeight
                                        * grimScoreCalculation(t2.getSeasonAvgPf(), t1.getSeasonAvgPa(), t2.getORank(),
                                                        t1.getDRank());

                        t1DoomScorePrediction += totalWeight
                                        * doomScoreCalculation(t1.getSeasonAvgPf(), t2.getSeasonAvgPa(), t2.getDRank(),
                                                        t1.getORank());
                        t2DoomScorePrediction += totalWeight
                                        * doomScoreCalculation(t2.getSeasonAvgPf(), t1.getSeasonAvgPa(), t1.getDRank(),
                                                        t2.getORank());

                        t1LuciScorePrediction += luciWeight
                                        * grimScoreCalculation(t1.getSeasonAvgPf(), t2.getSeasonAvgPa(),
                                                        t1.getO10Rank(),
                                                        t2.getD10Rank());
                        t2LuciScorePrediction += luciWeight
                                        * grimScoreCalculation(t2.getSeasonAvgPf(), t1.getSeasonAvgPa(),
                                                        t2.getO10Rank(),
                                                        t1.getD10Rank());
                }

                if (t1.getNormalizedLastPf() != -1 && t2.getNormalizedLastPf() != -1 &&
                                t1.getNormalizedLastPa() != -1 && t2.getNormalizedLastPa() != -1) {
                        totalWeight += 1;
                        totalDivisor += totalWeight;
                        luciWeight -= 1; // keep this a standalone value
                        luciDivisor += luciWeight;

                        if (t1.getNormalizedLastPf() - t1.getNormalizedLastPa() > t2.getNormalizedLastPf()
                                        - t2.getNormalizedLastPa()) {
                                spreadChecklist++;
                        } else if (t2.getNormalizedLastPf() - t2.getNormalizedLastPa() > t1.getNormalizedLastPf()
                                        - t1.getNormalizedLastPa()) {
                                spreadChecklist--;
                        }

                        t1ScorePrediction += totalWeight
                                        * scoreCalculation(t1.getNormalizedLastPf(), t2.getNormalizedLastPa());
                        t2ScorePrediction += totalWeight
                                        * scoreCalculation(t2.getNormalizedLastPf(), t1.getNormalizedLastPa());

                        t1GrimScorePrediction += totalWeight
                                        * grimScoreCalculation(t1.getNormalizedLastPf(), t2.getNormalizedLastPa(),
                                                        t1.getORank(),
                                                        t2.getDRank());
                        t2GrimScorePrediction += totalWeight
                                        * grimScoreCalculation(t2.getNormalizedLastPf(), t1.getNormalizedLastPa(),
                                                        t2.getORank(),
                                                        t1.getDRank());

                        t1DoomScorePrediction += totalWeight
                                        * doomScoreCalculation(t1.getNormalizedLastPf(), t2.getNormalizedLastPa(),
                                                        t2.getDRank(),
                                                        t1.getORank());
                        t2DoomScorePrediction += totalWeight
                                        * doomScoreCalculation(t2.getNormalizedLastPf(), t1.getNormalizedLastPa(),
                                                        t1.getDRank(),
                                                        t2.getORank());

                        t1LuciScorePrediction += luciWeight
                                        * grimScoreCalculation(t1.getNormalizedLastPf(), t2.getNormalizedLastPa(),
                                                        t1.getO10Rank(),
                                                        t2.getD10Rank());
                        t2LuciScorePrediction += luciWeight
                                        * grimScoreCalculation(t2.getNormalizedLastPf(), t1.getNormalizedLastPa(),
                                                        t2.getO10Rank(),
                                                        t1.getD10Rank());
                }

                if (t1.getNormalizedLast5Pf() != -1 && t2.getNormalizedLast5Pf() != -1 &&
                                t1.getNormalizedLast5Pa() != -1 && t2.getNormalizedLast5Pa() != -1) {
                        totalWeight += 1;
                        totalDivisor += totalWeight;
                        luciWeight -= 1; // keep this a standalone value
                        luciDivisor += luciWeight;

                        if (t1.getNormalizedLast5Pf() - t1.getNormalizedLast5Pa() > t2.getNormalizedLast5Pf()
                                        - t2.getNormalizedLast5Pa()) {
                                spreadChecklist++;
                        } else if (t2.getNormalizedLast5Pf() - t2.getNormalizedLast5Pa() > t1.getNormalizedLast5Pf()
                                        - t1.getNormalizedLast5Pa()) {
                                spreadChecklist--;
                        }

                        t1ScorePrediction += totalWeight
                                        * scoreCalculation(t1.getNormalizedLast5Pf(), t2.getNormalizedLast5Pa());
                        t2ScorePrediction += totalWeight
                                        * scoreCalculation(t2.getNormalizedLast5Pf(), t1.getNormalizedLast5Pa());

                        t1GrimScorePrediction += totalWeight
                                        * grimScoreCalculation(t1.getNormalizedLast5Pf(), t2.getNormalizedLast5Pa(),
                                                        t1.getORank(),
                                                        t2.getDRank());
                        t2GrimScorePrediction += totalWeight
                                        * grimScoreCalculation(t2.getNormalizedLast5Pf(), t1.getNormalizedLast5Pa(),
                                                        t2.getORank(),
                                                        t1.getDRank());

                        t1DoomScorePrediction += totalWeight
                                        * doomScoreCalculation(t1.getNormalizedLast5Pf(), t2.getNormalizedLast5Pa(),
                                                        t2.getDRank(),
                                                        t1.getORank());
                        t2DoomScorePrediction += totalWeight
                                        * doomScoreCalculation(t2.getNormalizedLast5Pf(), t1.getNormalizedLast5Pa(),
                                                        t1.getDRank(),
                                                        t2.getORank());

                        t1LuciScorePrediction += luciWeight
                                        * grimScoreCalculation(t1.getNormalizedLast5Pf(), t2.getNormalizedLast5Pa(),
                                                        t1.getO10Rank(),
                                                        t2.getD10Rank());
                        t2LuciScorePrediction += luciWeight
                                        * grimScoreCalculation(t2.getNormalizedLast5Pf(), t1.getNormalizedLast5Pa(),
                                                        t2.getO10Rank(),
                                                        t1.getD10Rank());
                }

                if (t1.getNormalizedLast10Pf() != -1 && t2.getNormalizedLast10Pf() != -1 &&
                                t1.getNormalizedLast10Pa() != -1 && t2.getNormalizedLast10Pa() != -1) {
                        totalWeight += 1;
                        totalDivisor += totalWeight;
                        luciWeight -= 1; // keep this a standalone value
                        luciDivisor += luciWeight;

                        if (t1.getNormalizedLast10Pf() - t1.getNormalizedLast10Pa() > t2.getNormalizedLast10Pf()
                                        - t2.getNormalizedLast10Pa()) {
                                spreadChecklist++;
                        } else if (t2.getNormalizedLast10Pf() - t2.getNormalizedLast10Pa() > t1.getNormalizedLast10Pf()
                                        - t1.getNormalizedLast10Pa()) {
                                spreadChecklist--;
                        }

                        t1ScorePrediction += totalWeight
                                        * scoreCalculation(t1.getNormalizedLast10Pf(), t2.getNormalizedLast10Pa());
                        t2ScorePrediction += totalWeight
                                        * scoreCalculation(t2.getNormalizedLast10Pf(), t1.getNormalizedLast10Pa());

                        t1GrimScorePrediction += totalWeight
                                        * grimScoreCalculation(t1.getNormalizedLast10Pf(), t2.getNormalizedLast10Pa(),
                                                        t1.getORank(),
                                                        t2.getDRank());
                        t2GrimScorePrediction += totalWeight
                                        * grimScoreCalculation(t2.getNormalizedLast10Pf(), t1.getNormalizedLast10Pa(),
                                                        t2.getORank(),
                                                        t1.getDRank());

                        t1DoomScorePrediction += totalWeight
                                        * doomScoreCalculation(t1.getNormalizedLast10Pf(), t2.getNormalizedLast10Pa(),
                                                        t2.getDRank(),
                                                        t1.getORank());
                        t2DoomScorePrediction += totalWeight
                                        * doomScoreCalculation(t2.getNormalizedLast10Pf(), t1.getNormalizedLast10Pa(),
                                                        t1.getDRank(),
                                                        t2.getORank());

                        t1LuciScorePrediction += luciWeight
                                        * grimScoreCalculation(t1.getNormalizedLast10Pf(), t2.getNormalizedLast10Pa(),
                                                        t1.getO10Rank(),
                                                        t2.getD10Rank());
                        t2LuciScorePrediction += luciWeight
                                        * grimScoreCalculation(t2.getNormalizedLast10Pf(), t1.getNormalizedLast10Pa(),
                                                        t2.getO10Rank(),
                                                        t1.getD10Rank());
                }

        }

        private void calculatePrediction(Team t1, Team t2) {
                if (totalDivisor > 0) {
                        t1ScorePrediction /= totalDivisor;
                        t2ScorePrediction /= totalDivisor;
                        t1GrimScorePrediction /= totalDivisor;
                        t2GrimScorePrediction /= totalDivisor;
                        t1DoomScorePrediction /= totalDivisor;
                        t2DoomScorePrediction /= totalDivisor;
                } else {
                        t1ScorePrediction = 0;
                        t2ScorePrediction = 0;
                        t1GrimScorePrediction = 0;
                        t2GrimScorePrediction = 0;
                        t1DoomScorePrediction = 0;
                        t2DoomScorePrediction = 0;
                }

                if (luciDivisor > 0) {
                        t1LuciScorePrediction /= luciDivisor;
                        t2LuciScorePrediction /= luciDivisor;
                } else {
                        t1LuciScorePrediction = 0;
                        t2LuciScorePrediction = 0;
                }

                if (t1Variance != 0) {
                        t1ScorePrediction += t1Variance;
                        t1GrimScorePrediction += t1Variance;
                        t1DoomScorePrediction += t1Variance * -2;
                }

                if (t2Variance != 0) {
                        t2ScorePrediction += t2Variance;
                        t2GrimScorePrediction += t2Variance;
                        t2DoomScorePrediction += t2Variance * -2;
                }

                if (t1ScorePrediction == t2ScorePrediction) {
                        winner = "N/A";
                } else {
                        winner = t1ScorePrediction > t2ScorePrediction ? t1.getShortName() : t2.getShortName();
                }

                if (spreadChecklist > 10) {
                        winner += " certainty on " + t1.getShortName();
                } else if (spreadChecklist < -10) {
                        winner += " certainty on " + t2.getShortName();
                }

                if (t1GrimScorePrediction == t2GrimScorePrediction) {
                        grimWinner = "N/A";
                } else {
                        grimWinner = t1GrimScorePrediction > t2GrimScorePrediction ? t1.getShortName()
                                        : t2.getShortName();
                }

                if (t1LuciScorePrediction == t2LuciScorePrediction) {
                        luciWinner = "N/A";
                } else {
                        luciWinner = t1LuciScorePrediction > t2LuciScorePrediction ? t1.getShortName()
                                        : t2.getShortName();
                }

                if (t1DoomScorePrediction == t2DoomScorePrediction) {
                        doomWinner = "N/A";
                } else {
                        doomWinner = t1DoomScorePrediction > t2DoomScorePrediction ? t1.getShortName()
                                        : t2.getShortName();
                }
        }
}
