package main;

import java.util.Arrays;

public class GoalieCountingStats {
   
    public float[] getStatScore() {
        return statScore;
    }
    private int gamesStarted;
    private int goalsAgainst;
    private int losses;
    private int saves;
    private int shotsAgainst;
    private int shutouts;
    private int wins;

    private float[] statScore;

    private Integer[] stats; //GS,W,L,GA,SA,SV,SO

    public GoalieCountingStats(){
        stats = new Integer[7];
        Arrays.fill(stats, 0);
    }

    public GoalieCountingStats(String[] array){
        stats = new Integer[7];
        gamesStarted = stats[0] = Integer.parseInt(array[3]);
        wins = stats[1] = Integer.parseInt(array[3]);
        losses = stats[2] = Integer.parseInt(array[3]);
        goalsAgainst = stats[3] = Integer.parseInt(array[4]);
        shotsAgainst = stats[4] = Integer.parseInt(array[5]);
        saves =  stats[5] = Integer.parseInt(array[6]);
        shutouts = stats[6] = Integer.parseInt(array[7]);
        
        statScore = new float[7];
        setStatScore();
    }

    public void addStats(Integer[] newStats, int gamesPlayed){

        for(int i = 0; i < stats.length; i++){
            stats[i] += newStats[i]/gamesPlayed;
        }
    }

    public int getGamesStarted() {
        return gamesStarted;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public int getLosses() {
        return losses;
    }

    public int getSaves() {
        return saves;
    }

    public int getShotsAgainst() {
        return shotsAgainst;
    }

    public int getShutouts() {
        return shutouts;
    }

    public int getWins() {
        return wins;
    }

    public Integer[] getStatsArray() {
        return stats;
    }

    public void setGamesStarted(int gamesStarted) {
        this.gamesStarted = gamesStarted;
    }

    public void setGoalsAgainst(int goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public void setSaves(int saves) {
        this.saves = saves;
    }

    public void setShotsAgainst(int shotsAgainst) {
        this.shotsAgainst = shotsAgainst;
    }

    public void setShutouts(int shutouts) {
        this.shutouts = shutouts;
    }

    public void setStatScore(){
        for(int i = 0; i < 7; i++){
            statScore[i] = stats[i] * Env.getGoalieWeights(i);
        }
    }

    public void setWins(int wins) {
        this.wins = wins;
    }
}
