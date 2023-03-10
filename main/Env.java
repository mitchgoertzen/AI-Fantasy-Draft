package main;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import main.Baseball.Batter;
import main.Baseball.Pitcher;

public class Env { 
    
    //need for reference to make comments later on
    // private static float goalsWeight;
    // private static float assistsWeight;
    // private static float pointsWeight;
    // private static float plusminusWeight;
    // private static float pimWeight;
    // private static float ppgoalsWeight;
    // private static float ppassistsWeight;
    // private static float pppointsWeight;
    // private static float shgoalsWeight;
    // private static float shassistsWeight;
    // private static float shpointsWeight;
    // private static float gwgWeight;
    // private static float sogWeight;
    // private static float toiWeight;
    // private static float fowWeight;
    // private static float folWeight;
    // private static float hitsWeight;
    // private static float blocksWeight;

    //TODO: set size with variable
    private static float[] skaterWeights = new float[18];
    
    //TODO: set size with variable
    private static float[] goalieWeights = new float[7];


    //Games Played,Games Started,At Bats,Runs,Hits,Singles,Doubles,
    //Triples,Home Runs,Runs Batted In,Sacrifice Hits,Sacrifice Flys,
    //Stolen Bases,Caught Stealing,Walks,Intentional Walks,Hit By Pitch,
    //Strikeouts,Ground Into Double Play,Total Bases,Putouts,Assists,
    //Errors,Fielding Percentage,Batting Average,On-base Percentage,
    //Slugging Percentage,On-base + Slugging Percentage,Extra Base Hits,
    //Net Stolen Bases,Stolen Base Percentage,Hitting for the Cycle,
    //Plate Appearances,Grand Slam Home Runs,Outfield Assists,
    //Double Plays Turned,Catcher Interference
    
    //TODO: set size with variable
    private static float[] battingWeights = new float[37];

 
//    Pitching Appearances
// Games Started
// Innings Pitched
// Wins
// Losses
// Complete Games
// Shutouts
// Saves
// Outs
// Hits
// Total Batters Faced
// Runs
// Earned Runs
// Home Runs
// Walks
// Intentional Walks
// Hit Batters
// Strikeouts
// Wild Pitches
// Balks
// Stolen Bases Allowed
// Batters Grounded Into Double Plays
// Save Chances
// Holds
// Total Bases Allowed
// Earned Run Average
// (Walks + Hits)/ Innings Pitched
// Strikeouts per Walk Ratio
// Strikeouts per Nine Innings
// Pitch Count
// Singles Allowed
// Doubles Allowed
// Triples Allowed
// Relief Wins
// Relief Losses
// Pickoffs
// Relief Appearances
// On-base Percentage Against
// Winning Percentage
// Hits Per Nine Innings
// Walks Per Nine Innings
// No Hitters
// Perfect Games
// Save Percentage
// Inherited Runners Scored
// Quality Starts
// Blown Saves
// Net Saves
// Saves + Holds
// Net Saves and Holds
// Net Wins

    //TODO: set size with variable
    private static float[] pitchingWeights = new float[51];



    //need for reference to make comments later on
    // private static float goalsagainstWeight;
    // private static float gamesstartedWeight;
    // private static float lossesWeight;
    // private static float shotsagainstWeight;
    // private static float savesWeight;
    // private static float shutoutWeight;
    // private static float winsWeight;

    public static float[] getPitchingWeights() {
        return pitchingWeights;
    }

    private static int totalParticipants;
    private static int totalRounds;
    private static int currentRound;
    private static int currentPick;

    private static  Integer[] positionLimits = new Integer[6]; //lw,rw,c,d,g

    public static LinkedHashMap<String, Float> SortedPlayerScores = new LinkedHashMap<>();
    
    public static Map<Integer, Integer> totalPicksInDraft = new HashMap<>();
    public static Map<String, Boolean> playerDrafted = new HashMap<>();
    public static Map<String, Float> PlayerScores = new HashMap<>();
    public static Map<String, Player> AllPlayers = new HashMap<>();
    public static Map<Integer, Participant> participants = new HashMap<>();

    public static float getSkaterWeights(int i){
        return skaterWeights[i];
    }  
    
    public static float getGoalieWeights(int i){
            return goalieWeights[i];
    }   
    
    public static float getBattingWeights(int i){
            return battingWeights[i];
    } 
    
    public static float getPitchingWeights(int i){
            return pitchingWeights[i];
    }  
    
    public static int getPositionIndex(String position, Integer[] positionCounts){
        switch(position){
            case "LW":
                return 0;
            case "RW":
                return 1;
            case "C":
                return 2;
            case "F":{
                if(positionCounts[0] < positionCounts[1]){
                    if(positionCounts[0] < positionCounts[2])
                        return 0;
                    else
                        return 2;
                }else{
                    if(positionCounts[1] < positionCounts[2])
                        return 1;
                    else
                        return 2;
                }
            }
            case "W":{
                if(positionCounts[0] < positionCounts[1]){
                    return 0;
                }else{
                    return 1;
                }
            }
            case "D": return 3;
            case "G": return 4;
            default:
                System.out.println("this position (" + position + ") does not exist");
                
        }
        return -1;
    }
    
    public static void sortPlayerScores() {

        List<Entry<String, Float>> l1 = new LinkedList<Entry<String, Float>>(PlayerScores.entrySet());  

        Collections.sort(l1, new Comparator<Entry<String, Float>>() {
            @Override
            public int compare(Entry<String, Float> o1, Entry<String, Float> o2) {
                return o2.getValue().compareTo(o1.getValue()); 
            }  
        });  
        
        for (Entry<String, Float> entry : l1)   
        {  
            SortedPlayerScores.put(entry.getKey(), entry.getValue());
        }  

    }

    //get draft info
    public static int getCurrentPick() {
        return currentPick;
    }

    public static int getCurrentRound() {
        return currentRound;
    }

    public static int getTotalParticipants() {
        return totalParticipants;
    }

    public static int getTotalRounds() {
        return totalRounds;
    }

    public static Integer[] getPositionLimits() {
        return positionLimits;
    }

    public static Map<Integer, Integer> getTotalPicksInDraft() {
        return totalPicksInDraft;
    }


    //set draft info
    public static void setCurrentPick(int currentPickInRound) {
        Env.currentPick = currentPickInRound;
    }
    
    public static void setCurrentRound(int currentRound) {
        Env.currentRound = currentRound;
    }

    public static void setTotalParticipants(int totalParticipants) {
        Env.totalParticipants = totalParticipants;
    }
    
    public static void addPickToDraft(int key, int id) {
        Env.totalPicksInDraft.put(key, id);
    }

    public static void nextPick() {
        currentPick++;
    }

    public static void nextRound() {
        currentRound++;
    }

    public static void setPositionLimit(int index, int limit) {
        positionLimits[index] = limit;
    }

    public static void setTotalRounds(int totalRounds) {
        Env.totalRounds = totalRounds;
    }

    public static void setHockeyWeights(float[] skater, float[] goalie){
        for(int i = 0; i < skater.length; i++){
            skaterWeights[i] = skater[i];
        }

        for(int i = 0; i < goalie.length; i++){
            goalieWeights[i] = goalie[i];
        }
    }

    
    public static void setBaseballWeights(float[] batting, float[] pitching){
        for(int i = 0; i < batting.length; i++){
            battingWeights[i] = batting[i];
        }

        for(int i = 0; i < pitching.length; i++){
            pitchingWeights[i] = pitching[i];
        }
    }

    public static void updatePlayerStats(String id, String[] array,int statType, int year){
        Player player = AllPlayers.get(id);
        switch(statType){
            case 0: ((Batter)AllPlayers.get(id)).addFieldingStats(array, year);
            break;
            case 1: ((Batter)AllPlayers.get(id)).addBattingStats(array, year);
            break;
            case 2: ((Pitcher)AllPlayers.get(id)).addPitchingStats(array, year);
            break;
        }
        PlayerScores.put(id, player.getScore());
    }
}


