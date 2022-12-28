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
    
    private static boolean DEBUG = false;

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

    private static float goalsagainstWeight;
    private static float gamesstartedWeight;
    private static float lossesWeight;
    private static float shotsagainstWeight;
    private static float savesWeight;
    private static float shutoutWeight;
    private static float winsWeight;


    public static void setWinsWeight(float winsWeight) {
        Env.winsWeight = winsWeight;
    }
    
    public static void setGamesstartedWeight(float gsWeight) {
        Env.gamesstartedWeight = gsWeight;
    }

    public static void setLossesWeight(float lossesWeight) {
        Env.lossesWeight = lossesWeight;
    }

    public static void setGoalsagainstWeight(float gaWeight) {
        Env.goalsagainstWeight = gaWeight;
    }

    public static void setShotsagainstWeight(float saWeight) {
        Env.shotsagainstWeight = saWeight;
    }

    public static void setSavesWeight(float savesWeight) {
        Env.savesWeight = savesWeight;
    }

    public static void setShutoutWeight(float soWeight) {
        Env.shutoutWeight = soWeight;
    }

    private static int totalParticipants;
    private static int totalRounds;
    private static int currentRound;
    private static int currentPick;

    //lw,rw,c,d,g
    private static  Integer[] positionLimits = new Integer[6];

    public static LinkedHashMap<String, Float> SortedPlayerScores = new LinkedHashMap<>();
    public static Map<Integer, Integer> totalPicksInDraft = new HashMap<>();
    public static Map<String, Boolean> playerDrafted = new HashMap<>();
    public static Map<String, Float> PlayerScores = new HashMap<>();
    public static Map<String, Player> AllPlayers = new HashMap<>();
    public static Map<Integer, Participant> participants = new HashMap<>();

    //public static ArrayList<String> SortedPlayers = new ArrayList<>();

    public static float getSkaterWeights(int i){
        switch(i){
            case 0:{
                if(DEBUG)
                    System.out.println(" * goalsWeight of: " + goalsWeight);
                return goalsWeight;
            }
            case 1:{
                if(DEBUG)
                    System.out.println(" * assistsWeight of: " + assistsWeight);
                return assistsWeight;}
            case 2:{
                if(DEBUG)
                    System.out.println(" * plusminusWeight of: " + plusminusWeight);
                return plusminusWeight;}
            case 3:{
                if(DEBUG)
                    System.out.println(" * pimWeight of: " + pimWeight);
                return pimWeight;}
            case 4:{
                if(DEBUG)
                    System.out.println(" * ppgoalsWeight of: " + ppgoalsWeight);
                return ppgoalsWeight;}
            case 5:{
                if(DEBUG)
                    System.out.println(" * shgoalsWeight of: " + shgoalsWeight);
                return shgoalsWeight;}
            case 6:{
                if(DEBUG)
                    System.out.println(" * gwgWeight of: " + gwgWeight);
                return gwgWeight;}
            case 7:{
                if(DEBUG)
                    System.out.println(" * ppassistsWeight of: " + ppassistsWeight);
                return ppassistsWeight;}
            case 8:{
                if(DEBUG)
                    System.out.println(" * shassistsWeight of: " + shassistsWeight);
                return shassistsWeight;}
            case 9:{
                if(DEBUG)
                    System.out.println(" * sogWeight of: " + sogWeight);
                return sogWeight;}
            case 10:{
                if(DEBUG)
                    System.out.println(" * toiWeight of: " + toiWeight);
                return toiWeight;}
            case 11:{
                if(DEBUG)
                    System.out.println(" * blocksWeight of: " + blocksWeight);
                return blocksWeight;}
            case 12:{
                if(DEBUG)
                    System.out.println(" * hitsWeight of: " + hitsWeight);
                return hitsWeight;}
            case 13:{
                if(DEBUG)
                    System.out.println(" * fowWeight of: " + fowWeight);
                return fowWeight;}
            case 14:{
                if(DEBUG)
                    System.out.println(" * folWeight of: " + folWeight);
                return folWeight;}
            case 15:{
                if(DEBUG)
                System.out.println(" * pointsWeight of: " + pointsWeight);
                return pointsWeight;}
            case 16:{
                if(DEBUG)
                    System.out.println(" * pppointsWeight of: " + pppointsWeight);
                return pppointsWeight;}
            case 17:{
                if(DEBUG)
                    System.out.println(" * shpointsWeight of: " + shpointsWeight);
                return shpointsWeight;}
            default:
            return 0;
        }
    }  
    
    public static float getGoalieWeights(int i){
        switch(i){
            case 0:{
                if(DEBUG)
                    System.out.println(" * gamesstartedWeight of: " + gamesstartedWeight);
                return gamesstartedWeight;
            }
            case 1:{
                if(DEBUG)
                    System.out.println(" * winsWeight of: " + winsWeight);
                return winsWeight;
            }
            case 2:{
                if(DEBUG)
                    System.out.println(" * lossesWeight of: " + lossesWeight);
                return lossesWeight;
            }
            case 3:{
                if(DEBUG)
                    System.out.println(" * goalsagainstWeight of: " + goalsagainstWeight);
                return goalsagainstWeight;
            }
            case 4:{
                if(DEBUG)
                    System.out.println(" * shotsagainstWeight of: " + shotsagainstWeight);
                return shotsagainstWeight;
            }
            case 5:{
                if(DEBUG)
                    System.out.println(" * savesWeight of: " + savesWeight);
                return savesWeight;
            }
            case 6:{
                if(DEBUG)
                    System.out.println(" * shutoutWeight of: " + shutoutWeight);
                return shutoutWeight;
            }
            default:
            return 0;
        }
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
                //return 0;
            }  
        });  
        
        for (Entry<String, Float> entry : l1)   
        {  
            SortedPlayerScores.put(entry.getKey(), entry.getValue());
            //SortedPlayers.add(entry.getKey());
        }  

    }
   
    //get skater weights
    public static float getAssistsWeight() {
        return assistsWeight;
    }

    public static float getBlocksWeight() {
        return blocksWeight;
    }

    public static float getFolWeight() {
        return folWeight;
    }

    public static float getFowWeight() {
        return fowWeight;
    }

    public static float getGoalsWeight() {
        return goalsWeight;
    }

    public static float getGwgWeight() {
        return gwgWeight;
    }

    public static float getHitsWeight() {
        return hitsWeight;
    }

    public static float getPimWeight() {
        return pimWeight;
    }

    public static float getPlusminusWeight() {
        return plusminusWeight;
    }

    public static float getPointsWeight() {
        return pointsWeight;
    }

    public static float getPpassistsWeight() {
        return ppassistsWeight;
    }

    public static float getPpgoalsWeight() {
        return ppgoalsWeight;
    }

    public static float getPppointsWeight() {
        return pppointsWeight;
    }

    public static float getShassistsWeight() {
        return shassistsWeight;
    }

    public static float getShgoalsWeight() {
        return shgoalsWeight;
    }

    public static float getShpointsWeight() {
        return shpointsWeight;
    }

    public static float getSogWeight() {
        return sogWeight;
    }
    
    //get goalie weights
    public static float getGoalsagainstWeight() {
        return goalsagainstWeight;
    }

    public static float getGamesstartedWeight() {
        return gamesstartedWeight;
    }

    public static float getLossesWeight() {
        return lossesWeight;
    }
    
    public static float getWinsWeight() {
        return winsWeight;
    }

    public static float getShotsagainstWeight() {
        return shotsagainstWeight;
    }

    public static float getSavesWeight() {
        return savesWeight;
    }

    public static float getShutoutWeight() {
        return shutoutWeight;
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
}