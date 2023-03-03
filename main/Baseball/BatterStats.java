package main.Baseball;

import java.util.Arrays;

import main.Env;

public class BatterStats {

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

    public BatterStats(){
        stats = new Float[37];
        Arrays.fill(stats, 0);
    }

    public BatterStats(String[] array){

        //size to br adjusted based on stats tajen from txt
        stats = new Float[37];

        for(int i = 0; i < stats.length; i++){
            stats[i] = 0f;
        }
        
        //4, 5, 6,7,8, 9, 10,11, 12,13,14,15, 16, 17,18, 19, 20,21, 22
        //PA,AB,R,H,2B,3B,HR,RBI,SB,CS,BB,SO,OPS+,TB,GDP,HBP,SH,SF,IBB
        
        stats[1] = atBats = Float.parseFloat(array[5]);
        stats[2] = runs = Float.parseFloat(array[6]);
        stats[3] = hits = Float.parseFloat(array[7]);
        stats[4] = doubles = Float.parseFloat(array[8]);
        stats[5] = triples = Float.parseFloat(array[9]);
        stats[6] = homeRuns = Float.parseFloat(array[10]);
        stats[7] = singles = hits - homeRuns - triples - doubles;
        stats[8] = RBI = Float.parseFloat(array[11]);
        stats[9] = sacHits = Float.parseFloat(array[20]);
        stats[10] = sacFlys = Float.parseFloat(array[21]);
        stats[11] = stolenBases = Float.parseFloat(array[12]);
        stats[12] = caughtStealing = Float.parseFloat(array[13]);
        stats[13] = walks = Float.parseFloat(array[14]);
        stats[14] = intentionalWalks = Float.parseFloat(array[22]);
        stats[15] = hitByPitch = Float.parseFloat(array[19]);
        stats[16] = strikeouts = Float.parseFloat(array[15]);
        stats[17] = GIDP = Float.parseFloat(array[18]);
        stats[18] = totalBases = Float.parseFloat(array[17]);


        stats[23] = battingAverage = hits / atBats;
        stats[24] = onBasePercentage = (hits + walks + hitByPitch) / (atBats + walks + hitByPitch + sacFlys);
        stats[25] = sluggingPercentage = (singles + 2*doubles + 3*triples + 4*homeRuns)/atBats;
        stats[26] = OPS = onBasePercentage + sluggingPercentage;
        stats[27] = extraBaseHits = doubles + triples + homeRuns;
        stats[28] = netStolenBases = stolenBases - caughtStealing;
        stats[29] = stolenBasePercentage = stolenBases / (stolenBases + caughtStealing);
        stats[31] = plateAppearances = Float.parseFloat(array[4]);


        //UNUSED STATS
        //stats[30] = hitForCycle = Float.parseFloat(array[35]);
        //stats[32] = grandSlams = Float.parseFloat(array[37]);
        //stats[33] = outfieldAssists = Float.parseFloat(array[38]);
        // stats[34] = doublePlaysTurned = Float.parseFloat(array[39]);
        //stats[35] = catcherInterference = Float.parseFloat(array[40]);

    //    weightedStats = new Float[37];
     //   setWeightedStats();
    }


//fielding
    //stats[0] = gamesStarted = Float.parseFloat(array[5]);
    // stats[19] = putouts = Float.parseFloat(array[24]);
    // stats[20] = assists = Float.parseFloat(array[25]);
    // stats[21] = errors = Float.parseFloat(array[26]);
    // stats[22] = fieldingPercentage = (putouts + assists) / (putouts + assists + errors);

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