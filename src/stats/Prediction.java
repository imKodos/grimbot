package stats;

import teams.Team;

public class Prediction {
    private int t1ScorePrediction = 0;
    private int t2ScorePrediction = 0;
    private double t1Variance = 0;
    private double t2Variance = 0;
    private double t1Differential = 0;
    private double t2Differential = 0;

    String winner = "";

    public Prediction(Team t1, Team t2) {
        if (t1 != null && t2 != null) {
            try {

                // Start Variance
                double t1WinPercent = t1.getTeamId() != -1
                        ? (t1.getTotalWins() / (t1.getTotalWins() + t1.getTotalLoss()))
                        : -1;
                double t2WinPercent = t2.getTeamId() != -1
                        ? (t2.getTotalWins() / (t2.getTotalWins() + t2.getTotalLoss()))
                        : -1;

                t1Differential = t1.getSeasonAvgPf() - t1.getSeasonAvgPa();
                t2Differential = t2.getSeasonAvgPf() - t2.getSeasonAvgPa();
                if (t1Differential >= 15) {
                    t1Variance += 4;
                    t2Variance -= 4;
                } else if (t1Differential >= 10) {
                    t1Variance += 3;
                    t2Variance -= 3;
                } else if (t1Differential >= 5) {
                    t1Variance += 2;
                    t2Variance -= 2;
                } else if (t1Differential <= -5) {
                    t1Variance -= 2;
                    t2Variance += 2;
                } else if (t1Differential <= -10) {
                    t1Variance -= 3;
                    t2Variance += 3;
                } else if (t1Differential <= -15) {
                    t1Variance -= 4;
                    t2Variance += 4;
                }

                if (t2Differential >= 15) {
                    t2Variance += 4;
                    t1Variance -= 4;
                } else if (t2Differential >= 10) {
                    t2Variance += 3;
                    t1Variance -= 3;
                } else if (t2Differential >= 5) {
                    t2Variance += 2;
                    t1Variance -= 2;
                } else if (t2Differential <= -5) {
                    t2Variance -= 2;
                    t1Variance += 2;
                } else if (t2Differential <= -10) {
                    t2Variance -= 3;
                    t1Variance += 3;
                } else if (t2Differential <= -15) {
                    t2Variance -= 4;
                    t1Variance += 4;
                }

                // if one team has a higher than 0.55 win rate, and the other is below 0.45, add
                // positive variance to that team.
                if (t1WinPercent >= 0.55 && t2WinPercent <= 0.45) {
                    t1Variance += 2;
                    t2Variance -= 1;
                }

                if (t2WinPercent >= 0.55 && t1WinPercent <= 0.45) {
                    t2Variance += 2;
                    t1Variance -= 1;
                }

                if (t1.getDaysRested() > 0 && t1.getDaysRested() < 3) { // if a team is between 1-2 days rest, add
                                                                        // variance for fresh legs
                    t1Variance += t1.getDaysRested();
                    // t2Variance -= 1;
                }
                if (t2.getDaysRested() > 0 && t2.getDaysRested() < 3) { // if a team is between 1-2 days rest, add
                                                                        // variance
                    t2Variance += t2.getDaysRested();
                    // t1Variance -= 1;
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
                        // t2Variance -= 1;
                    }
                    if (t1.isAwayColdStreak()) {
                        t1Variance -= 2;
                        // t2Variance += 1;
                    }
                }

                if (t2.isHomeTeam()) {
                    if (t2.isHomeHotStreak()) {
                        t2Variance += 2;
                        // t1Variance -= 1;
                    }
                    if (t2.isHomeColdStreak()) {
                        t2Variance -= 2;
                        // t1Variance += 1;
                    }
                } else {
                    if (t2.isAwayHotStreak()) {
                        t2Variance += 2;
                        // t1Variance -= 1;
                    }
                    if (t2.isAwayColdStreak()) {
                        t2Variance -= 2;
                        // t1Variance += 1;
                    }
                }
                // End Variance

                // start with the lowest weight and work our way up in case we are missing some
                // data.
                double totalWeight = 0;
                double totalDivisor = 0;

                // last game
                if (t1.getLastPF() != -1 && t2.getLastPF() != -1 &&
                        t1.getLastPA() != -1 && t2.getLastPA() != -1) {
                    totalWeight += 1; // keep this a standalone value
                    totalDivisor += totalWeight; // will be the sum of total weights used to divide by later

                    t1ScorePrediction += totalWeight
                            * scoreCalculation(t1.getLastPF(), t2.getLastPA());
                    t2ScorePrediction += totalWeight
                            * scoreCalculation(t2.getLastPF(), t1.getLastPA());
                }

                // if t1 is home, t2 is away
                if (t1.isHomeTeam() && t1.getLast2HomePf() != -1 && t2.getLast2AwayPa() != -1
                        &&
                        t1.getLast2HomePa() != -1 && t2.getLast2AwayPf() != -1) {
                    totalWeight += 1; // keep this a standalone value
                    totalDivisor += totalWeight; // will be the sum of total weights used to divide by later
                    t1ScorePrediction += totalWeight
                            * scoreCalculation(t1.getLast2HomePf(), t2.getLast2AwayPa());
                    t2ScorePrediction += totalWeight
                            * scoreCalculation(t2.getLast2AwayPf(), t1.getLast2HomePa());
                }

                // t1 away, t2 home
                if (!t1.isHomeTeam() && t1.getLast2AwayPf() != -1 && t2.getLast2HomePa() != -1 &&
                        t1.getLast2AwayPa() != -1 && t2.getLast2HomePf() != -1) {
                    totalWeight += 1; // keep this a standalone value
                    totalDivisor += totalWeight; // will be the sum of total weights used to divide by later

                    t1ScorePrediction += totalWeight
                            * scoreCalculation(t1.getLast2AwayPf(), t2.getLast2HomePa());
                    t2ScorePrediction += totalWeight
                            * scoreCalculation(t2.getLast2HomePf(), t1.getLast2AwayPa());
                }

                // // last 5 home or away points
                // // if t1 is home, t2 is away
                // // if t2 is home, t1 is away
                if (t1.isHomeTeam() && t1.getLast5HomePf() != -1 && t2.getLast5AwayPa() != -1
                        &&
                        t1.getLast5HomePa() != -1 && t2.getLast5AwayPf() != -1) {
                    totalWeight += 1; // keep this a standalone value
                    totalDivisor += totalWeight; // will be the sum of total weights used to divide by later

                    t1ScorePrediction += totalWeight
                            * scoreCalculation(t1.getLast5HomePf(), t2.getLast5AwayPa());
                    t2ScorePrediction += totalWeight
                            * scoreCalculation(t2.getLast5AwayPf(), t1.getLast5HomePa());
                }

                // t1 away, t2 home
                if (!t1.isHomeTeam() && t1.getLast5AwayPf() != -1 && t2.getLast5HomePa() != -1 &&
                        t1.getLast5AwayPa() != -1 && t2.getLast5HomePf() != -1) {
                    totalWeight += 1; // keep this a standalone value
                    totalDivisor += totalWeight; // will be the sum of total weights used to divide by later

                    t1ScorePrediction += totalWeight
                            * scoreCalculation(t1.getLast5AwayPf(), t2.getLast5HomePa());
                    t2ScorePrediction += totalWeight
                            * scoreCalculation(t2.getLast5HomePf(), t1.getLast5AwayPa());
                }

                // last 5
                if (t1.getLast5PF() != -1 && t2.getLast5PF() != -1 &&
                        t1.getLast5PA() != -1 && t2.getLast5PA() != -1) {
                    totalWeight += 1; // keep this a standalone value
                    totalDivisor += totalWeight; // will be the sum of total weights used to divide by later

                    t1ScorePrediction += totalWeight
                            * scoreCalculation(t1.getLast5PF(), t2.getLast5PA());
                    t2ScorePrediction += totalWeight
                            * scoreCalculation(t2.getLast5PF(), t1.getLast5PA());
                }

                // // get last 10 averages
                if (t1.getLast10PF() != -1 && t2.getLast10PF() != -1 &&
                        t1.getLast10PA() != -1 && t2.getLast10PA() != -1) {
                    totalWeight += 1; // keep this a standalone value
                    totalDivisor += totalWeight; // will be the sum of total weights used to divide by later
                    // ex t1 pf 120, t2 pa 110 -- expected t1 pf is 115, same for t2 pa.
                    t1ScorePrediction += totalWeight
                            * scoreCalculation(t1.getLast10PF(), t2.getLast10PA());
                    t2ScorePrediction += totalWeight
                            * scoreCalculation(t2.getLast10PF(), t1.getLast10PA());
                }

                // get t1 season pf and t2 season pa comparison
                if (t1.getSeasonAvgPf() != -1 && t2.getSeasonAvgPf() != -1 &&
                        t1.getSeasonAvgPa() != -1 && t2.getSeasonAvgPa() != -1) {
                    totalWeight += 1; // keep this a standalone value
                    totalDivisor += totalWeight; // will be the sum of total weights used to divide by later
                    // ex t1 pf 120, t2 pa 110 -- expected t1 pf is 115, same for t2 pa.
                    t1ScorePrediction += totalWeight
                            * scoreCalculation(t1.getSeasonAvgPf(), t2.getSeasonAvgPa());
                    t2ScorePrediction += totalWeight
                            * scoreCalculation(t2.getSeasonAvgPf(), t1.getSeasonAvgPa());
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

                // lastPF / lastPA is highest weight
                // last2AwayPa / last2AwayPf
                // last2HomePa / last2HomePa
                // last5PF / last5PA
                // last5AwayPf / last5AwayPa
                // last5HomePf / last5HomePa
                // last10PF / last10PA
                // seasonAvgPf / seasonAvgPa season avg should be the base line average with the
                // lowest weight priority

                if (totalDivisor > 0) {
                    t1ScorePrediction /= totalDivisor;
                    t2ScorePrediction /= totalDivisor;
                } else {
                    t1ScorePrediction = 0;
                    t2ScorePrediction = 0;
                }

                // finally account for variance
                if (t1Variance != 0)
                    t1ScorePrediction += t1Variance;

                if (t2Variance != 0)
                    t2ScorePrediction += t2Variance;

                if (t1ScorePrediction == t2ScorePrediction) {
                    winner = "N/A";
                } else {
                    winner = t1ScorePrediction > t2ScorePrediction ? t1.getShortName() : t2.getShortName();
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    public String getScorePrediction() {
        return t1ScorePrediction + " - " + t2ScorePrediction + " " + winner;
    }

    public String getTotalScorePrediction() {
        return t1ScorePrediction + t2ScorePrediction + "";
    }

    public double scoreCalculation(double pointsFor, double pointsAgainst) {
        if (pointsFor > pointsAgainst) {// play closer to defense
            return ((0.96 * pointsFor) + pointsAgainst) / 2;
        } else {
            return (pointsFor + pointsAgainst) / 2;
        }
    }

    public double getT1Variance() {
        return t1Variance;
    }

    public double getT2Variance() {
        return t2Variance;
    }

    // private static <T> boolean isValidStat(T t1) {
    // String t1Type = t1.getClass().getTypeName();
    // if ("double".equalsIgnoreCase(t1Type)) {
    // if (t1.getClass().equals(-1)) {
    // return false;
    // } else {
    // return true;
    // }
    // }
    // return false;
    // }
}
