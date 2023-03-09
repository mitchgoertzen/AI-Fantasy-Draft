package main.Baseball;

import java.util.Arrays;

import main.Env;

public class BattingStats {

    private Float[] stats;
    private Float[] weightedStats;

    private float gamesStarted = 0;
    private float atBats = 0;
    private float runs = 0;
    private float hits = 0;
    private float singles = 0;
    private float doubles = 0;
    private float triples = 0;
    private float homeRuns = 0;
    private float RBI = 0;
    private float sacHits = 0;
    private float sacFlys = 0;
    private float stolenBases = 0;
    private float caughtStealing = 0;
    private float walks = 0;
    private float intentionalWalks = 0;
    private float hitByPitch = 0;
    private float strikeouts = 0;
    private float GIDP = 0;
    private float totalBases = 0;
    private float putouts = 0;
    private float assists = 0;
    private float errors = 0;
    private float fieldingPercentage = 0;
    private float battingAverage = 0;
    private float onBasePercentage = 0;
    private float sluggingPercentage = 0;
    private float OPS = 0;
    private float extraBaseHits = 0f;
    private float netStolenBases = 0;
    private float stolenBasePercentage = 0;
    private float hitForCycle = 0;
    private float plateAppearances = 0;
    private float grandSlams = 0;
    private float outfieldAssists = 0;
    private float doublePlaysTurned = 0;
    private float catcherInterference = 0;

    public Float[] getStats() {
        return stats;
    }

    public BattingStats(){
        stats = new Float[37];
        
        for(int i = 0; i < stats.length; i++){
            stats[i] = 0f;
        }
    }

    public BattingStats(String[] array, boolean fielding){

        //size to br adjusted based on stats tajen from txt
        stats = new Float[37];

        for(int i = 0; i < stats.length; i++){
            stats[i] = 0f;
        }
        
        //4, 5, 6,7,8, 9, 10,11, 12,13,14,15, 16, 17,18, 19, 20,21, 22
        //PA,AB,R,H,2B,3B,HR,RBI,SB,CS,BB,SO,OPS+,TB,GDP,HBP,SH,SF,IBB

        //TODO: change if 2023 stats become available
        if(fielding){
            addFieldingStats(array, 1);
        }else{
            addBattingStats(array, 1);
        }
        
        //UNUSED STATS
        //stats[31] = hitForCycle = Float.parseFloat(array[35]);
        //stats[33] = grandSlams = Float.parseFloat(array[37]);
        //stats[34] = outfieldAssists = Float.parseFloat(array[38]);
        //stats[36] = catcherInterference = Float.parseFloat(array[40]);

    //    weightedStats = new Float[36];
    //    setWeightedStats();

    }

    public void addBattingStats(String[] array, int year){
        stats[2] = atBats += Float.parseFloat(array[5]) / ((year * 2) + 1);
        stats[3] = runs += Float.parseFloat(array[6]) / ((year * 2) + 1);
        stats[4] = hits += Float.parseFloat(array[7]) / ((year * 2) + 1);
        stats[5] = doubles += Float.parseFloat(array[8]) / ((year * 2) + 1);
        stats[6] = triples += Float.parseFloat(array[9]) / ((year * 2) + 1);
        stats[7] = homeRuns += Float.parseFloat(array[10]) / ((year * 2) + 1);
        stats[8] = singles += (hits - homeRuns - triples - doubles) / ((year * 2) + 1);
        stats[9] = RBI += Float.parseFloat(array[11]) / ((year * 2) + 1);
        stats[10] = sacHits += Float.parseFloat(array[20]) / ((year * 2) + 1);
        stats[11] = sacFlys += Float.parseFloat(array[21]) / ((year * 2) + 1);
        stats[12] = stolenBases += Float.parseFloat(array[12]) / ((year * 2) + 1);
        stats[13] = caughtStealing += Float.parseFloat(array[13]) / ((year * 2) + 1);
        stats[14] = walks += Float.parseFloat(array[14]) / ((year * 2) + 1);
        stats[15] = intentionalWalks += Float.parseFloat(array[22]) / ((year * 2) + 1);
        stats[16] = hitByPitch += Float.parseFloat(array[19]) / ((year * 2) + 1);
        stats[17] = strikeouts += Float.parseFloat(array[15]) / ((year * 2) + 1);
        stats[18] = GIDP += Float.parseFloat(array[18]) / ((year * 2) + 1);
        stats[19] = totalBases += Float.parseFloat(array[17]) / ((year * 2) + 1);
        stats[24] = battingAverage = hits / Math.max(1,atBats);
        stats[25] = onBasePercentage = (hits + walks + hitByPitch) / Math.max(1,(atBats + walks + hitByPitch + sacFlys));
        stats[26] = sluggingPercentage = (singles + 2*doubles + 3*triples + 4*homeRuns)/ Math.max(1, atBats);
        stats[27] = OPS = (onBasePercentage + sluggingPercentage);
        stats[28] = extraBaseHits = (doubles + triples + homeRuns);
        stats[29] = netStolenBases = (stolenBases - caughtStealing);
        stats[30] = stolenBasePercentage = stolenBases / Math.max(1, (stolenBases + caughtStealing));
        stats[32] = plateAppearances += Float.parseFloat(array[4]) / ((year * 2) + 1);
    }

    public void addFieldingStats(String[] array, int year){
        stats[1] = gamesStarted += Float.parseFloat(array[4]) / ((year * 2) + 1);
        stats[20] = putouts += Float.parseFloat(array[5]) / ((year * 2) + 1);
        stats[21] = assists += Float.parseFloat(array[6]) / ((year * 2) + 1);
        stats[22] = errors += Float.parseFloat(array[7]) / ((year * 2) + 1);
        stats[23] = fieldingPercentage = (putouts + assists) / Math.max(1, (putouts + assists + errors));
        stats[35] = doublePlaysTurned += Float.parseFloat(array[8]) / ((year * 2) + 1);
    }

    //used for roster's total stats
    //need to move somewhere else
    public void addStats(Float[] newStats, int gamesPlayed){
        for(int i = 0; i < stats.length; i++){
            stats[i] += newStats[i]/gamesPlayed;
        }
    }

    public void setWeightedStats(){
        for(int i = 0; i < weightedStats.length; i++){
            weightedStats[i] = stats[i] * Env.getBattingWeights(i);
        }
    }

}