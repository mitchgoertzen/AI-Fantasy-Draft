package main;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Env { 

    private static float goalsWeight;
    private static float assistsWeight;
    private static float pointsWeight;
    private static float plusminusWeight;
    private static float pimWeight;
    private static float ppgoalsWeight;
    private static float ppassistsWeight;
    private static float pppointsWeight;
    private static float shgoalsWeight;
    private static float shassistsWeight;
    private static float shpointsWeight;
    private static float gwgWeight;
    private static float sogWeight;
    private static float toiWeight;
    private static float fowWeight;
    private static float folWeight;
    private static float hitsWeight;
    private static float blocksWeight;

    private static float[] skaterWeights = new float[18];
    private static float[] goalieWeights = new float[7];


    private static float goalsagainstWeight;
    private static float gamesstartedWeight;
    private static float lossesWeight;
    private static float shotsagainstWeight;
    private static float savesWeight;
    private static float shutoutWeight;
    private static float winsWeight;

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

    //set skater weights
    public static void setAssistsWeight(float assistsWeight) {
        Env.assistsWeight = assistsWeight;
    }

    public static void setBlocksWeight(float blocksWeight) {
        Env.blocksWeight = blocksWeight;
    }

    public static void setFolWeight(float folWeight) {
        Env.folWeight = folWeight;
    }

    public static void setFowWeight(float fowWeight) {
        Env.fowWeight = fowWeight;
    }

    public static void setGoalsWeight(float goalsWeight) {
        Env.goalsWeight = goalsWeight;
    }

    public static void setGwgWeight(float gwgWeight) {
        Env.gwgWeight = gwgWeight;
    }

    public static void setHitsWeight(float hitsWeight) {
        Env.hitsWeight = hitsWeight;
    }

    public static void setPimWeight(float pimWeight) {
        Env.pimWeight = pimWeight;
    }

    public static void setPlusminusWeight(float plusminusWeight) {
        Env.plusminusWeight = plusminusWeight;
    }

    public static void setPointsWeight(float pointsWeight) {
        Env.pointsWeight = pointsWeight;
    }

    public static void setPpassistsWeight(float ppassistsWeight) {
        Env.ppassistsWeight = ppassistsWeight;
    }

    public static void setPpgoalsWeight(float ppgoalsWeight) {
        Env.ppgoalsWeight = ppgoalsWeight;
    }

    public static void setPppointsWeight(float pppointsWeight) {
        Env.pppointsWeight = pppointsWeight;
    }

    public static void setShassistsWeight(float shassistsWeight) {
        Env.shassistsWeight = shassistsWeight;
    }

    public static void setShgoalsWeight(float shgoalsWeight) {
        Env.shgoalsWeight = shgoalsWeight;
    }

    public static void setShpointsWeight(float shpointsWeight) {
        Env.shpointsWeight = shpointsWeight;
    }

    public static void setSogWeight(float sogWeight) {
        Env.sogWeight = sogWeight;
    }

    //set goalie weights
    public static void setGamesstartedWeight(float gsWeight) {
        Env.gamesstartedWeight = gsWeight;
    }

    public static void setGoalsagainstWeight(float gaWeight) {
        Env.goalsagainstWeight = gaWeight;
    }

    public static void setLossesWeight(float lossesWeight) {
        Env.lossesWeight = lossesWeight;
    }

    public static void setSavesWeight(float savesWeight) {
        Env.savesWeight = savesWeight;
    }

    public static void setShotsagainstWeight(float saWeight) {
        Env.shotsagainstWeight = saWeight;
    }

    public static void setShutoutWeight(float soWeight) {
        Env.shutoutWeight = soWeight;
    }

    public static void setWinsWeight(float winsWeight) {
        Env.winsWeight = winsWeight;
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

    public static void setSkaterWeights() {
            skaterWeights[0] = goalsWeight;
            skaterWeights[1] = assistsWeight;
            skaterWeights[2] = plusminusWeight;
            skaterWeights[3] = pimWeight;
            skaterWeights[4] = ppgoalsWeight;
            skaterWeights[5] = shgoalsWeight;
            skaterWeights[6] = gwgWeight;
            skaterWeights[7] = ppassistsWeight;
            skaterWeights[8] = shassistsWeight;
            skaterWeights[9] = sogWeight;
            skaterWeights[10] = toiWeight;
            skaterWeights[11] = blocksWeight;
            skaterWeights[12] = hitsWeight;
            skaterWeights[13] = fowWeight;
            skaterWeights[14] = folWeight;
            skaterWeights[15] = pointsWeight;
            skaterWeights[16] = pppointsWeight;
            skaterWeights[17] = shpointsWeight;
    }

    public static void setGoalieWeights() {
        goalieWeights[0] = gamesstartedWeight;
        goalieWeights[1] = winsWeight;
        goalieWeights[2] = lossesWeight;
        goalieWeights[3] = goalsagainstWeight;
        goalieWeights[4] = shotsagainstWeight;
        goalieWeights[5] = savesWeight;
        goalieWeights[6] = shutoutWeight;
    }
}


