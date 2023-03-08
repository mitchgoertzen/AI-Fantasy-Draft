package main.Baseball;

import java.util.Arrays;
import java.math.BigDecimal;
import java.math.MathContext;

import main.Env;

public class PitchingStats {

    private Float[] PartialInningArray;

    private Float[] weightedStats;

    private float gamesStarted = 0;
    private float IP = 0;
    private float wins = 0;
    private float losses = 0;
    private float completeGames = 0;
    private float shutouts = 0;
    private float saves = 0;
    private float outs = 0;
    private float hits = 0;
    private float totalBattersFaced = 0;
    private float runs = 0;
    private float earnedRuns = 0;
    private float homeRuns = 0;
    private float walks = 0;
    private float intentionalWalks = 0;
    private float hitBatters = 0;
    private float strikeouts = 0;
    private float wildPitches = 0;
    private float balks = 0;
    private float stolenBasesAllowed = 0;
    private float GIDP = 0;
    private float saveChances = 0;
    private float holds = 0;
    private float totalBasesAllowed = 0;
    private float earnedRunAverage = 0;
    private float WHIP = 0;
    private float KperBB = 0;
    private float Kper9 = 0;
    private float pitchCount = 0;
    private float singlesAllowed = 0;
    private float doublesAllowed = 0;
    private float triplesAllowed = 0;
    private float reliefWins = 0;
    private float reliefLosses = 0;
    private float pickoffs = 0;
    private float reliefAppearances = 0;
    private float OBP = 0;
    private float winningPercentage = 0;
    private float Hper9 = 0;
    private float BBper9 = 0;
    private float noHitters = 0;
    private float perfectGames = 0;
    private float savePercentage = 0;
    private float inheritedRunnersScored = 0;
    private float qualityStarts = 0;
    private float blownSaves = 0;
    private float netSaves = 0;
    private float savesPlusHolds = 0;
    private float netSavesandHolds = 0;
    private float netWins = 0;
    private Float[] stats;

    public Float[] getStats() {
        return stats;
    }

    public PitchingStats(){
        stats = new Float[51];
        PartialInningArray = new Float[] {0.0f, 0.1f, 0.2f, 1.0f, 1.1f};
        Arrays.fill(stats, 0);
    }

    public PitchingStats(String[] array){

        //size to br adjusted based on stats tajen from txt
        stats = new Float[51];
        PartialInningArray = new Float[] {0.0f, 0.1f, 0.2f, 1.0f, 1.1f};

    //    weightedStats = new Float[37];
     //   setWeightedStats();
    }
    //3,4,5,6, 7, 8, 9, 10,11,12,13,14,15,16,17
    //W,L,G,GS,GF,CG,SHO,SV,IP,H,R,ER,HR,BB,IBB
    //18,19, 20,21,22,23,  24
    //SO,HBP,BK,WP,BF,ERA+,FIP
    public void addPitchingStats(String[] array, int year){

        stats[1] = gamesStarted += Float.parseFloat(array[4]) / year;
        stats[2] = IP = calculateInnings(Float.parseFloat(array[11]), year);
        // System.out.println(array[0]);
        // System.out.println(IP);
        // System.out.println();
        stats[3] = wins += Float.parseFloat(array[3]) / year;
        stats[4] = losses += Float.parseFloat(array[4]) / year;
        stats[5] = completeGames += Float.parseFloat(array[8]) / year;
        stats[6] = shutouts += Float.parseFloat(array[9]) / year;
        stats[7] = saves += Float.parseFloat(array[10]) / year;
        stats[8] = outs = (3 * (int)IP) + /* IP(no decimal) * 3 + decimal / */  year;
        stats[9] = hits += Float.parseFloat(array[12]) / year;
        stats[10] = totalBattersFaced += Float.parseFloat(array[22]) / year;
        stats[11] = runs += Float.parseFloat(array[13]) / year;
        stats[12] = earnedRuns += Float.parseFloat(array[14]) / year;
        stats[13] = homeRuns += Float.parseFloat(array[15]) / year;
        stats[14] = walks += Float.parseFloat(array[16]) / year;
        stats[15] = intentionalWalks += Float.parseFloat(array[17]) / year;
        stats[16] = hitBatters += Float.parseFloat(array[19]) / year;
        stats[17] = strikeouts += Float.parseFloat(array[18]) / year;
        stats[18] = wildPitches += Float.parseFloat(array[21]) / year;
        stats[19] = balks += Float.parseFloat(array[20]) / year;
        //stats[20] = stolenBasesAllowed += Float.parseFloat(array[0]) / year;
        //stats[21] = GIDP += Float.parseFloat(array[0]) / year;
        //stats[22] = saveChances += Float.parseFloat(array[0]) / year;
        //stats[23] = holds += Float.parseFloat(array[0]) / year;
        //stats[24] = totalBasesAllowed += Float.parseFloat(array[0]) / year;
        stats[25] = earnedRunAverage = 9 * earnedRuns / Math.max(1, IP);
        stats[26] = WHIP = (walks + hits) / Math.max(1, IP);
        stats[27] = KperBB = strikeouts / Math.max(1, walks);
        stats[28] = Kper9 =  9 * strikeouts / Math.max(1, IP);
        //stats[29] = pitchCount += Float.parseFloat(array[0]) / year;
        //stats[30] = singlesAllowed += Float.parseFloat(array[0]) / year;
        //stats[31] = doublesAllowed += Float.parseFloat(array[0]) / year;
        //stats[32] = triplesAllowed += Float.parseFloat(array[0]) / year;
        //stats[33] = reliefWins += Float.parseFloat(array[0]) / year;
        //stats[34] = reliefLosses += Float.parseFloat(array[0]) / year;
        //stats[35] = pickoffs += Float.parseFloat(array[0]) / year;
        //stats[36] = reliefAppearances += Float.parseFloat(array[0]) / year;
        //stats[37] = OBP += Float.parseFloat(array[0]) / year;
        stats[38] = winningPercentage = wins / Math.max(1, wins + losses);
        stats[39] = Hper9 =  9 * hits / Math.max(1, IP);
        stats[40] = BBper9 =  9 * walks / Math.max(1, IP);
        //stats[41] = noHitters += Float.parseFloat(array[0]) / year;
        //stats[42] = perfectGames += Float.parseFloat(array[0]) / year;
        //stats[43] = savePercentage += Float.parseFloat(array[0]) / year;
        //stats[44] = inheritedRunnersScored += Float.parseFloat(array[0]) / year;
        //stats[45] = qualityStarts += Float.parseFloat(array[0]) / year;
        //stats[46] = blownSaves += Float.parseFloat(array[0]) / year;
        //stats[47] = netSaves += Float.parseFloat(array[0]) / year;
        //stats[48] = savesPlusHolds += Float.parseFloat(array[0]) / year;
        //stats[49] = netSavesandHolds += Float.parseFloat(array[0]) / year;
        //stats[50] = netWins += Float.parseFloat(array[0]) / year;
    }

    public void addStats(Integer[] newStats, int gamesPlayed){
        for(int i = 0; i < stats.length; i++){
            stats[i] += newStats[i]/gamesPlayed;
        }
    }

    public void setWeightedStats(){
        for(int i = 0; i < 15; i++){
            weightedStats[i] = stats[i] * Env.getSkaterWeights(i);
        }
    }

    private float calculateInnings(float currentInnings, int year){
        MathContext m = new MathContext(1);

        int currentAdjustedFullInnings = (int) currentInnings / year;
        BigDecimal  currentPartialInnings = new BigDecimal(currentInnings % 1).round(m);

        float newInnings = IP + currentAdjustedFullInnings + currentPartialInnings.floatValue();
        int newFullInnings = (int) newInnings;
        BigDecimal newPartialInnings = new BigDecimal(newInnings % 1).round(m);

        return newFullInnings + PartialInningArray[(int)(newPartialInnings.floatValue() * 10)];
    }

}