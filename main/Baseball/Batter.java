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
        if(year > 1)
            super.addGamesPlayed(Integer.parseInt(array[3]) / year);
            
        stats.addFieldingStats(array, year);
    }

    public void addBattingStats(String[] array, int year){
        countYears();
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
        , stats.getStats()[0], stats.getStats()[31], stats.getStats()[1], stats.getStats()[2], stats.getStats()[3]
        , stats.getStats()[7], stats.getStats()[4], stats.getStats()[5], stats.getStats()[6], stats.getStats()[8]
        , stats.getStats()[27], stats.getStats()[18], stats.getStats()[23], stats.getStats()[24], stats.getStats()[25]
        , stats.getStats()[26], stats.getStats()[16], stats.getStats()[13], stats.getStats()[14], stats.getStats()[15]
        , stats.getStats()[17], stats.getStats()[9], stats.getStats()[10], stats.getStats()[11], stats.getStats()[12]
        , stats.getStats()[29], stats.getStats()[28], stats.getStats()[19], stats.getStats()[20], stats.getStats()[21]
        , stats.getStats()[22], stats.getStats()[34]);
    }

    private float calculateScore(){

        float score = 0;
        Float[] array = stats.getStats();

        score += getGamesplayed() * Env.getBattingWeights(0);

        for(int i = 1; i < 23; i++){
            score += array[i] * Env.getBattingWeights(i);
        }

        for(int i = 23; i < 28; i++){
            score *= Math.max(1, Math.max(0.01f, array[i]) * Env.getBattingWeights(i));
        }
        //TODO: replace with += OPS+
       // score *= Math.max(0.01f, array[26]) * Env.getBattingWeights(26);

        score += array[28] * Env.getBattingWeights(28);
        score += array[29] * Env.getBattingWeights(29);
        //score *= Math.max(0.01f, array[29]) * Env.getBattingWeights(29);

        for(int i = 31; i < array.length; i++){
            score += array[i] * Env.getBattingWeights(i);
        }

        return (score / 162f);
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