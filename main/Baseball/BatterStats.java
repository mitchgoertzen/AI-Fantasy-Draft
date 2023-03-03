package main.Baseball;

import java.util.Arrays;

import main.Env;

public class BatterStats {

    private Float[] weightedStats;

    private float gamesPlayed;
    private float gamesStarted;
    private float atBats;
    private float runs;
    private float hits;
    private float singles;
    private float doubles;
    private float triples;
    private float homeRuns;
    private float RBI;
    private float sacHits;
    private float sacFlys;
    private float stolenBases;
    private float caughtStealing;
    private float walks;
    private float intentionalWalks;
    private float hitByPitch;
    private float strikeouts;
    private float GIDP;
    private float totalBases;
    private float putouts;
    private float assists;
    private float errors;
    private float fieldingPercentage;
    private float battingAverage;
    private float onBasePercentage;
    private float sluggingPercentage;
    private float OPS;
    private float extraBaseHits;
    private float netStolenBases;
    private float stolenBasePercentage;
    private float hitForCycle;
    private float plateAppearances;
    private float grandSlams;
    private float outfieldAssists;
    private float doublePlaysTurned;
    private float catcherInterference;

    private Float[] stats;

    public BatterStats(){
        stats = new Float[37];
        Arrays.fill(stats, 0);
    }

    public BatterStats(String[] array){

        //size to br adjusted based on stats tajen from txt
        stats = new Float[37];

        stats[0] = gamesPlayed = Float.parseFloat(array[4]);
        stats[1] = gamesStarted = Float.parseFloat(array[5]);
        stats[2] = atBats = Float.parseFloat(array[6]);
        stats[3] = runs = Float.parseFloat(array[7]);
        stats[4] = hits = Float.parseFloat(array[8]);
        stats[6] = doubles = Float.parseFloat(array[10]);
        stats[7] = triples = Float.parseFloat(array[11]);
        stats[8] = homeRuns = Float.parseFloat(array[12]);
        stats[5] = singles = hits - homeRuns - triples - doubles;
        stats[9] = RBI = Float.parseFloat(array[13]);
        stats[10] = sacHits = Float.parseFloat(array[14]);
        stats[11] = sacFlys = Float.parseFloat(array[15]);
        stats[12] = stolenBases = Float.parseFloat(array[16]);
        stats[13] = caughtStealing = Float.parseFloat(array[17]);
        stats[14] = walks = Float.parseFloat(array[18]);
        stats[15] = intentionalWalks = Float.parseFloat(array[19]);
        stats[16] = hitByPitch = Float.parseFloat(array[20]);
        stats[17] = strikeouts = Float.parseFloat(array[21]);
        stats[18] = GIDP = Float.parseFloat(array[22]);
        stats[19] = totalBases = Float.parseFloat(array[23]);
        stats[20] = putouts = Float.parseFloat(array[24]);
        stats[21] = assists = Float.parseFloat(array[25]);
        stats[22] = errors = Float.parseFloat(array[26]);
        stats[23] = fieldingPercentage = (putouts + assists) / (putouts + assists + errors);
        stats[24] = battingAverage = hits / atBats;
        stats[25] = onBasePercentage = (hits + walks + hitByPitch) / (atBats + walks + hitByPitch + sacFlys);
        stats[26] = sluggingPercentage = (singles + 2*doubles + 3*triples + 4*homeRuns)/atBats;
        stats[27] = OPS = onBasePercentage + sluggingPercentage;
        stats[28] = extraBaseHits = Float.parseFloat(array[32]);
        stats[29] = netStolenBases = stolenBases - caughtStealing;
        stats[30] = stolenBasePercentage = stolenBases / (stolenBases + caughtStealing);
        stats[31] = hitForCycle = Float.parseFloat(array[35]);
        stats[32] = plateAppearances = Float.parseFloat(array[36]);
        stats[33] = grandSlams = Float.parseFloat(array[37]);
        stats[34] = outfieldAssists = Float.parseFloat(array[38]);
        stats[35] = doublePlaysTurned = Float.parseFloat(array[39]);
        stats[36] = catcherInterference = Float.parseFloat(array[40]);

    //    weightedStats = new Float[37];
     //   setWeightedStats();
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

}