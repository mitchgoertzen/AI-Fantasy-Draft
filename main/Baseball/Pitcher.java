package main.Baseball;

import main.Env;
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
        if(year > 0)
            super.addGamesPlayed(Integer.parseInt(array[5]) / ((year * 2) + 1));
        stats.addPitchingStats(array, year);
        setScore(calculateScore());
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
         , stats.getStatsArray()[3], stats.getStatsArray()[4], stats.getStatsArray()[38], stats.getStatsArray()[25]
         , stats.getStatsArray()[1], stats.getStatsArray()[5], stats.getStatsArray()[6], stats.getStatsArray()[7], stats.getStatsArray()[2], stats.getStatsArray()[8]
         , stats.getStatsArray()[9], stats.getStatsArray()[11], stats.getStatsArray()[12], stats.getStatsArray()[13], stats.getStatsArray()[14], stats.getStatsArray()[15]
         , stats.getStatsArray()[17], stats.getStatsArray()[16], stats.getStatsArray()[19], stats.getStatsArray()[18], stats.getStatsArray()[10]
         , stats.getStatsArray()[26], stats.getStatsArray()[27], stats.getStatsArray()[28], stats.getStatsArray()[39], stats.getStatsArray()[40]);

    }

    private float calculateScore(){

        float score = 0;
        Float[] array = stats.getStatsArray();
        // System.out.println(array.length);
        // System.out.println(Env.getPitchingWeights().length);


        score += getGamesPlayed() * Env.getPitchingWeights(0);
        for(int i = 1; i < 20; i++){
            // System.out.println(i);
            // System.out.println(array[i]);
            // System.out.println(Env.getPitchingWeights(i));
            
            score += (10 * array[i] * Env.getPitchingWeights(i)) / Math.max(1, array[2]);
        }

        int[] lower = new int[]{25, 26, 39, 40};

        for(int i : lower){
            if(Env.getPitchingWeights(i) > 0){
                score *= 1 - (array[i] * Env.getPitchingWeights(i)) / 10;
            }
        }

        int[] higher = new int[]{27, 28, 38};

        for(int i : higher){
            if(Env.getPitchingWeights(i) > 0){
                score *= Math.max(0.01f, array[i]) * Env.getPitchingWeights(i);
            }
        }


        // if(Env.getPitchingWeights(25) > 0){
        //     score *= 1 - (array[25] * Env.getPitchingWeights(25)) / 10;
        // }

        // if(Env.getPitchingWeights(26) > 0){
        //     score *= 1 - (array[26] * Env.getPitchingWeights(26)) / 10;
        // }
        
        // if(Env.getPitchingWeights(39) > 0){
        //     score *= 1 - (array[39]* Env.getPitchingWeights(39)) / 10 ;
        // }

        // if(Env.getPitchingWeights(40) > 0){
        //     score *= 1 - (array[40]* Env.getPitchingWeights(40)) / 10 ;
        // }


        
        // if(Env.getPitchingWeights(27) > 0){
        //     score *= Math.max(0.01f, array[27]) * Env.getPitchingWeights(27);
        // }

        // if(Env.getPitchingWeights(28) > 0){
        //     score *= Math.max(0.01f, array[28]) * Env.getPitchingWeights(28);
        // }

        // if(Env.getPitchingWeights(38) > 0){
        //     score *= Math.max(0.01f, array[38]) * Env.getPitchingWeights(38);
        // }

        float reliefMultiplier = 1f;

        if(getPosition().equals("RP")){
            reliefMultiplier = 1.5f;
        }

        return score * reliefMultiplier * array[2] / 6f;
//        return score * array[2] * ((float)getGamesPlayed() / 250) / 8;
    }
}
