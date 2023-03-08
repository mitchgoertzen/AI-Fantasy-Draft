package main.Baseball;

import main.Player;

public class Pitcher extends Player {

    private PitchingStats stats;

    public Pitcher(String id, String[] array, String[] info, boolean fielding) {
        super(id, info);
        stats = new PitchingStats(array);
    }

    public PitchingStats getStats() {
        return stats;
    }

    public void addPitchingStats(String[] array, int year){
        countYears();
        stats.addPitchingStats(array, year);
    }
    
    @Override
    public Pitcher clone() {
        return (Pitcher) super.clone();
    }

    public void setStats(PitchingStats stats) {
        this.stats = stats;
    }

    public void printInfo(){
        super.printInfo();
/*
        stats[1] = gamesStarted
        stats[2] = IP
        stats[3] = wins
        stats[4] = losses
        stats[5] = completeGames
        stats[6] = shutouts
        stats[7] = saves
        stats[8] = outs
        stats[9] = hits
        stats[10] = totalBattersFaced
        stats[11] = runs
        stats[12] = earnedRuns
        stats[13] = homeRuns
        stats[14] = walks
        stats[15] = intentionalWalks
        stats[16] = hitBatters
        stats[17] = strikeouts
        stats[18] = wildPitches
        stats[19] = balks
        stats[25] = earnedRunAverage
        stats[26] = WHIP
        stats[27] = KperBB
        stats[28] = Kper9
        stats[38] = winningPercentage
        stats[39] = Hper9
        stats[40] = BBper9
 */
        System.out.printf("W: %.0f, L: %.0f, W-L%%: %.3f, ERA: %.2f, \n" +
         "GS: %.0f, CG: %.0f, SO: %.0f, S: %.0f, IP: %.1f, O: %.0f\n" +
         "H: %.0f, R: %.0f, ER: %.0f, HR: %.0f, BB: %.0f, IBB: %.0f\n" +
         "K: %.0f, HBP: %.0f, BK: %.0f, WP: %.0f, BF: %.0f\n" +
         "WHIP: %.3f, K/BB: %.1f,  K/9: %.1f, H/9: %.1f, BB/9: %.1f\n\n"
         , stats.getStats()[3], stats.getStats()[4], stats.getStats()[38], stats.getStats()[25]
         , stats.getStats()[1], stats.getStats()[5], stats.getStats()[6], stats.getStats()[7], stats.getStats()[2], stats.getStats()[8]
         , stats.getStats()[9], stats.getStats()[11], stats.getStats()[12], stats.getStats()[13], stats.getStats()[14], stats.getStats()[15]
         , stats.getStats()[17], stats.getStats()[16], stats.getStats()[19], stats.getStats()[18], stats.getStats()[10]
         , stats.getStats()[26], stats.getStats()[27], stats.getStats()[28], stats.getStats()[39], stats.getStats()[40]);

    }
}
