package main.Baseball;

import main.Env;
import main.Player;

public class Batter extends Player {

    private BattingStats stats;

    public Batter(String id, String[] array, String[] info, boolean fielding) {
        super(id, info);
        stats = new BattingStats(array, fielding);
        setScore(calculateScore());
    }

    public BattingStats getStats() {
        return stats;
    }

    public void addFieldingStats(String[] array, int year){
        //setScore(calculateScore());
        countYears();
            
        stats.addFieldingStats(array, year);
    }

    public void addBattingStats(String[] array, int year){
        countYears();
        if(year > 0)
            super.addGamesPlayed(Integer.parseInt(array[3]) / ((year * 2) + 1));
        else
            super.setGamesPlayed(Integer.parseInt(array[3]));
            
        stats.addBattingStats(array, year);
        setScore(calculateScore());
    }
    
    @Override
    public Batter clone() {
        return (Batter) super.clone();
    }

    public void setStats(BattingStats stats) {
        this.stats = stats;
    }

    

    public void printInfo(){
        super.printInfo();

        System.out.printf("GS: %.0f, PA: %.0f, AB: %.0f, R: %.0f, H: %.0f\n" +
        "1B: %.0f, 2B: %.0f, 3B: %.0f, HR: %.0f, RBI: %.0f, XBH: %.0f, TB: %.0f\n" +
        "BA: %.3f, OBP: %.3f, SLG: %.3f, OPS: %.3f,\n" /*OPS+ */ +
        "K: %.0f, BB: %.0f, IBB: %.0f, HBP: %.0f, GIDP: %.0f\n" +
        "SH: %.0f, SF: %.0f, SB: %.0f, CS: %.0f, SB%% %.2f, NetSB: %.0f\n" +
        "PO: %.0f, A: %.0f, E: %.0f, F%%: %.2f, DPT: %.0f\n\n"
        , stats.getStatsArray()[1], stats.getStatsArray()[32], stats.getStatsArray()[2], stats.getStatsArray()[3], stats.getStatsArray()[4]
        , stats.getStatsArray()[5], stats.getStatsArray()[6], stats.getStatsArray()[7], stats.getStatsArray()[8], stats.getStatsArray()[9], stats.getStatsArray()[28], stats.getStatsArray()[19]
        , stats.getStatsArray()[24], stats.getStatsArray()[25], stats.getStatsArray()[26], stats.getStatsArray()[27]
        , stats.getStatsArray()[17], stats.getStatsArray()[14], stats.getStatsArray()[15], stats.getStatsArray()[16], stats.getStatsArray()[18]
        , stats.getStatsArray()[10], stats.getStatsArray()[11], stats.getStatsArray()[12], stats.getStatsArray()[13], stats.getStatsArray()[30], stats.getStatsArray()[29]
        , stats.getStatsArray()[20], stats.getStatsArray()[21], stats.getStatsArray()[22], stats.getStatsArray()[23], stats.getStatsArray()[35]);
    }

    private float calculateScore(){

        float score = 0;
        Float[] array = stats.getStatsArray();

        //score += getGamesPlayed() * Env.getBattingWeights(0);

        for(int i = 1; i < 23; i++){
            score += array[i] / getGamesPlayed() * Env.getBattingWeights(i);
        }

        score += array[28] / getGamesPlayed() * Env.getBattingWeights(28);
        score += array[29] / getGamesPlayed() * Env.getBattingWeights(29);

        for(int i = 31; i < array.length; i++){
            score += array[i] / getGamesPlayed() * Env.getBattingWeights(i);
        }

        for(int i = 23; i < 28; i++){
            if(Env.getBattingWeights(i) > 0){
                score *= array[i] * Env.getBattingWeights(i);
            }
        }
        //TODO: replace with += OPS+
       // score *= Math.max(0.01f, array[26]) * Env.getBattingWeights(26);

        //score *= Math.max(0.01f, array[29]) * Env.getBattingWeights(29);

        return score * getGamesPlayed();
    }
    
}




// private float gamesStarted = 0;
// private float atBats = 0;
// private float runs = 0;
// private float hits = 0;
// private float singles = 0;
// private float doubles = 0;
// private float triples = 0;
// private float homeRuns = 0;
// private float RBI = 0;
// private float sacHits = 0;
// private float sacFlys = 0;
// private float stolenBases = 0;
// private float caughtStealing = 0;
// private float walks = 0;
// private float intentionalWalks = 0;
// private float hitByPitch = 0;
// private float strikeouts = 0;
// private float GIDP = 0;
// private float totalBases = 0;

// private float putouts = 0;
// private float assists = 0;
// private float errors = 0;

// private float fieldingPercentage = 0;

// private float battingAverage = 0;
// private float onBasePercentage = 0;
// private float sluggingPercentage = 0;
// private float OPS = 0;

// private float extraBaseHits = 0f;
// private float netStolenBases = 0;

// private float stolenBasePercentage = 0;

// private float hitForCycle = 0;
// private float plateAppearances = 0;
// private float grandSlams = 0;
// private float outfieldAssists = 0;
// private float doublePlaysTurned = 0;
// private float catcherInterference = 0;