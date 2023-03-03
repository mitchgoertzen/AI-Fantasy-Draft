package main.Hockey;

import java.util.Arrays;

import main.Env;

public class SkaterCountingStats {
    
    //Player,Tm,Pos,GP,G,A,PTS,+/-,PIM,PPG,SHG,GW,PPA,SHA,S,TOI,BLK,HIT,FOW,FOL,playerid(lastnfi0_)
   
    private float[] statScore;

    private Integer[] stats;

    private int goals;
    private int assists;

    private int plusminus;
    private int penaltiesinminutes;

    private int powerplaygoals;
    private int shorthandedgoals;
    private int gamewinninggoals;

    private int shorthandedassists;
    private int powerplayassists;
    private int shots;

    private int timeoonice;

    private int faceoffswon;
    private int faceoffslost;

    private int hits;
    private int blocks;

    public SkaterCountingStats(){
        stats = new Integer[15];
        Arrays.fill(stats, 0);
    }

    public SkaterCountingStats(String[] array){
        stats = new Integer[15];
        //goals
        goals = stats[0] = Integer.parseInt(array[4]);
        //assists
        assists = stats[1] = Integer.parseInt(array[5]);
        //plusminus
        plusminus = stats[2] = Integer.parseInt(array[6]);
        //pim
        penaltiesinminutes = stats[3] = Integer.parseInt(array[7]);
        //ppgoal
        powerplaygoals = stats[4] = Integer.parseInt(array[8]);
        //shgoal
        shorthandedgoals =  stats[5] = Integer.parseInt(array[9]);
        //gwg
        gamewinninggoals = stats[6] = Integer.parseInt(array[10]);
        //ppassist
        powerplayassists = stats[7] = Integer.parseInt(array[11]);
        //shassist
        shorthandedassists = stats[8] = Integer.parseInt(array[12]);
        //s
        shots = stats[9] = Integer.parseInt(array[13]);
        //toi
        timeoonice = stats[10] = Integer.parseInt(array[14]);
        //blk
        blocks = stats[11] = Integer.parseInt(array[15]);
        //hit
        hits = stats[12] = Integer.parseInt(array[16]);
        //fow
        faceoffswon = stats[13] = Integer.parseInt(array[17]);
        //fol
        faceoffslost = stats[14] = Integer.parseInt(array[18]);

        statScore = new float[18];
        setStatScore();
    }

    public void addStats(Integer[] newStats, int gamesPlayed){
        for(int i = 0; i < stats.length; i++){
            stats[i] += newStats[i]/gamesPlayed;
        }
    }

    //Getters
    public float getShootingPercentage() {
        return goals/shots;
    }

    public float[] getStatScore() {
        return statScore;
    }

    public Integer[] getStatsArray(){
        return stats;
    }

    public int getGoals() {
        return goals;
    }

    public int getAssists() {
        return assists;
    }

    public int getPoints() {
        return goals + assists;
    }

    public int getPowerplaypoints() {
        return powerplaygoals + powerplayassists;
    }

    public int getShpoints() {
        return shorthandedgoals + shorthandedassists;
    }

    public int getPlusminus() {
        return plusminus;
    }

    public int getPowerplaygoals() {
        return powerplaygoals;
    }

    public int getPowerplayassists() {
        return powerplayassists;
    }

    public int getGamewinninggoals() {
        return gamewinninggoals;
    }

    public int getShorthandedgoals() {
        return shorthandedgoals;
    }

    public int getShorthandedassists() {
        return shorthandedassists;
    }

    public int getShots() {
        return shots;
    }

    public int getBlocks() {
        return blocks;
    }

    public int getFaceoffslost() {
        return faceoffslost;
    }

    public int getFaceoffswon() {
        return faceoffswon;
    }

    public int getHits() {
        return hits;
    }

    public int getPenaltiesinminutes() {
        return penaltiesinminutes;
    }

    public int getTimeoonice() {
        return timeoonice;
    }

    public float getFaceoffPercentage() {
        return faceoffswon/(faceoffswon + faceoffslost);
    }

    //Setters
    public void setAssists(int assists) {
        this.assists = assists;
    }

    public void setBlocks(int blocks) {
        this.blocks = blocks;
    }
    
    public void setFaceoffslost(int faceoffslost) {
        this.faceoffslost = faceoffslost;
    }

    public void setFaceoffswon(int faceoffswon) {
        this.faceoffswon = faceoffswon;
    }

    public void setGamewinninggoals(int gamewinninggoals) {
        this.gamewinninggoals = gamewinninggoals;
    }
    
    public void setGoals(int goals) {
        this.goals = goals;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public void setPenaltiesinminutes(int penaltiesinminutes) {
        this.penaltiesinminutes = penaltiesinminutes;
    }

    public void setPlusminus(int plusminus) {
        this.plusminus = plusminus;
    }

    public void setPowerplayassists(int powerplayassists) {
        this.powerplayassists = powerplayassists;
    }

    public void setPowerplaygoals(int powerplaygoals) {
        this.powerplaygoals = powerplaygoals;
    }

    public void setShorthandedassists(int shorthandedassists) {
        this.shorthandedassists = shorthandedassists;
    }

    public void setShorthandedgoals(int shorthandedgoals) {
        this.shorthandedgoals = shorthandedgoals;
    }

    public void setShots(int shots) {
        this.shots = shots;
    }
    
    public void setStatScore(){
        
        for(int i = 0; i < 15; i++){
            statScore[i] = stats[i] * Env.getSkaterWeights(i);
        }

        statScore[15] = getPoints() * Env.getSkaterWeights(15);
        statScore[16] = getPowerplaypoints() * Env.getSkaterWeights(16);
        statScore[17] = getShpoints() * Env.getSkaterWeights(17);
    }

    public void setTimeoonice(int timeoonice) {
        this.timeoonice = timeoonice;
    }
}