package main.Baseball;

import java.util.Arrays;

import main.Env;

public class BattingStats {

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

    private Float[] stats;

    public BattingStats(){
        stats = new Float[37];
        Arrays.fill(stats, 0);
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
        //stats[30] = hitForCycle = Float.parseFloat(array[35]);
        //stats[32] = grandSlams = Float.parseFloat(array[37]);
        //stats[33] = outfieldAssists = Float.parseFloat(array[38]);
        //stats[35] = catcherInterference = Float.parseFloat(array[40]);

    //    weightedStats = new Float[37];
     //   setWeightedStats();

    }

    public void addBattingStats(String[] array, int year){
        stats[1] += atBats = Float.parseFloat(array[5]) / year;
        stats[2] += runs = Float.parseFloat(array[6]) / year;
        stats[3] += hits = Float.parseFloat(array[7]) / year;
        stats[4] += doubles = Float.parseFloat(array[8]) / year;
        stats[5] += triples = Float.parseFloat(array[9]) / year;
        stats[6] += homeRuns = Float.parseFloat(array[10]) / year;
        stats[7] += singles = (hits - homeRuns - triples - doubles) / year;
        stats[8] += RBI = Float.parseFloat(array[11]) / year;
        stats[9] += sacHits = Float.parseFloat(array[20]) / year;
        stats[10] += sacFlys = Float.parseFloat(array[21]) / year;
        stats[11] += stolenBases = Float.parseFloat(array[12]) / year;
        stats[12] += caughtStealing = Float.parseFloat(array[13]) / year;
        stats[13] += walks = Float.parseFloat(array[14]) / year;
        stats[14] += intentionalWalks = Float.parseFloat(array[22]) / year;
        stats[15] += hitByPitch = Float.parseFloat(array[19]) / year;
        stats[16] += strikeouts = Float.parseFloat(array[15]) / year;
        stats[17] += GIDP = Float.parseFloat(array[18]) / year;
        stats[18] += totalBases = Float.parseFloat(array[17]) / year;
        stats[23] += battingAverage = hits / Math.max(1,atBats) / year;
        stats[24] += onBasePercentage = (hits + walks + hitByPitch) / Math.max(1,(atBats + walks + hitByPitch + sacFlys)) / year;
        stats[25] += sluggingPercentage = (singles + 2*doubles + 3*triples + 4*homeRuns)/ Math.max(1, atBats) / year;
        stats[26] += OPS = (onBasePercentage + sluggingPercentage) / year;
        stats[27] += (extraBaseHits = doubles + triples + homeRuns) / year;
        stats[28] += (netStolenBases = stolenBases - caughtStealing) / year;
        stats[29] += stolenBasePercentage = stolenBases / Math.max(1, (stolenBases + caughtStealing)) / year;
        stats[31] += plateAppearances = Float.parseFloat(array[4]) / year;
    }

    public void addFieldingStats(String[] array, int year){
        stats[0] += gamesStarted = Float.parseFloat(array[4]) / year;
        stats[19] += putouts = Float.parseFloat(array[5]) / year;
        stats[20] += assists = Float.parseFloat(array[6]) / year;
        stats[21] += errors = Float.parseFloat(array[7]) / year;
        stats[22] += fieldingPercentage = (putouts + assists) / Math.max(1, (putouts + assists + errors)) / year;
        stats[34] += doublePlaysTurned = Float.parseFloat(array[8]) / year;
    }

    //used for roster's total stats
    //need to move somewhere else
    public void addStats(Float[] newStats, int gamesPlayed){
        for(int i = 0; i < stats.length; i++){
            stats[i] += newStats[i]/gamesPlayed;
        }
    }

    public void setWeightedStats(){
        for(int i = 0; i < 15; i++){
            weightedStats[i] = stats[i] * Env.getSkaterWeights(i);
        }
    }

}