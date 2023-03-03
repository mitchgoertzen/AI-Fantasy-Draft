package main.Baseball;

import java.util.Arrays;

import main.Env;

public class PitcherStats {

    private float[] statScore;

    private int pitchingAppearances;
    private int gamesStarted;
    private int IP;
    private int wins;
    private int losses;
    private int completeGames;
    private int shutouts;
    private int saves;
    private int outs;
    private int hits;
    private int totalBattersFaced;
    private int runs;
    private int earnedRuns;
    private int homeRuns;
    private int walks;
    private int intentionalWalks;
    private int hitBatters;
    private int strikeouts;
    private int wildPitches;
    private int balks;
    private int stolenBasesAllowed;
    private int GIDP;
    private int saveChances;
    private int holds;
    private int totalBasesAllowed;
    private int earnedRunAverage;
    private int WHIP;
    private int KperBB;
    private int Kper9;
    private int pitchCount;
    private int singlesAllowed;
    private int doublesAllowed;
    private int triplesAllowed;
    private int reliefWins;
    private int reliefLosses;
    private int pickoffs;
    private int reliefAppearances;
    private int OBP;
    private int winningPercentage;
    private int Hper9;
    private int BBper9;
    private int noHitters;
    private int perfectGames;
    private int savePercentage;
    private int inheritedRunnersScored;
    private int qualityStarts;
    private int blownSaves;
    private int netSaves;
    private int savesHolds;
    private int netSavesandHolds;
    private int netWins;

    private Integer[] stats;

    public PitcherStats(){
        stats = new Integer[50];
        Arrays.fill(stats, 0);
    }

    public PitcherStats(String[] array){

        //size to br adjusted based on stats tajen from txt
        stats = new Integer[50];

        statScore = new float[50];
       // setStatScore();
    }

    public void addStats(Integer[] newStats, int gamesPlayed){
        for(int i = 0; i < stats.length; i++){
            stats[i] += newStats[i]/gamesPlayed;
        }
    }

    public void setStatScore(){
        for(int i = 0; i < 15; i++){
            statScore[i] = stats[i] * Env.getSkaterWeights(i);
        }
    }

}