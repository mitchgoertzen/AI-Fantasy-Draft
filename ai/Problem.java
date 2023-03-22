package ai;

import java.util.ArrayList;
import java.util.Map;

import main.DraftSelection;
import main.DraftSlot;
import main.Env;
import main.Participant;
import main.Player;
import main.Baseball.Batter;
import main.Baseball.BattingStats;
import main.Baseball.Pitcher;
import main.Baseball.PitchingStats;
import main.Hockey.Goalie;
import main.Hockey.GoalieCountingStats;
import main.Hockey.Skater;
import main.Hockey.SkaterCountingStats;

@SuppressWarnings("unchecked")
public class Problem {
    
    private final boolean DEBUG = false;
    
    private ArrayList<DraftSlot> draftSlots;
    private ArrayList<DraftSelection> roster;
    private ArrayList<String> availablePlayers;
    private ArrayList<String>[] opponentRosters;

    private float[] activeRosterScore;
    private float[][] opponentRosterScores;

    private int currentPick;
    private int highestScoreIndex;

    private int statLength1;

    private int statLength2;

    private String[] draftedPlayers;

    public Problem(int rosterSize, int id, int length1, int length2) {

        statLength1 = length1;
        statLength2 = length2;

        draftSlots = new ArrayList<>();
        roster = new ArrayList<>();
        opponentRosterScores = new float[Env.participants.size()][length1 + length2];
        availablePlayers = new ArrayList<>();
        opponentRosters = new ArrayList[Env.participants.size()];

        int currentKey = -1;
        for(Map.Entry<Integer, Participant> p: Env.participants.entrySet()){
            currentKey = p.getKey();
            if(currentKey != id){
                opponentRosters[currentKey] = p.getValue().getRoster().getPlayers();
                float[] currentOpponentScore = new float[length1 + length2];
                for(String s : opponentRosters[currentKey]){
                    if(DEBUG)
                        System.out.println("currentOpponentScore");
                    currentOpponentScore = updateRosterScore(currentOpponentScore, Env.AllPlayers.get(s), length1, length2, opponentRosters[currentKey].size());
                }
                opponentRosterScores[currentKey] = currentOpponentScore;
            }
        }
        
        activeRosterScore = new float[length1 + length2];
        currentPick = Env.getCurrentPick();
        highestScoreIndex = 0;  
        draftedPlayers = new String[rosterSize];
    }

    public Problem(Problem problem, int rosterSize, int length1, int length2) {
        
        statLength1 = length1;
        statLength2 = length2;
       
        draftSlots = new ArrayList<>(problem.getDraftSlots());
        roster = new ArrayList<>(problem.getRoster());	
        availablePlayers = new ArrayList<>(problem.getAvailablePlayers());
        opponentRosters = new ArrayList[Env.participants.size()];
        opponentRosterScores = new float[Env.participants.size()][length1 + length2];

        int rosterLength = problem.getOpponentRosters().length;
        for(int i = 0; i < rosterLength; i++){
            if(problem.getOpponentRosters()[i] != null){
                opponentRosters[i] = (ArrayList<String>) problem.getOpponentRosters()[i].clone();
                opponentRosterScores[i] = problem.getOpponentRosterScores()[i].clone();
            }
        }

        activeRosterScore = problem.getActiveRosterScore().clone();
        currentPick = problem.getCurrentPick();
        highestScoreIndex = problem.getHighestScoreIndex();
        draftedPlayers = problem.getDraftedPlayers().clone();
    }

    //Utility methods
    public boolean addAvailablePlayer(String player) {
        if (availablePlayers.contains(player)  || playerAdded(player))
            return false;
        availablePlayers.add(player);
        return true;
    }

    public boolean addDraftSlot(DraftSlot newSlot) {
        if (draftSlots.contains(newSlot)  || slotAdded(newSlot))
            return false;
        draftSlots.add(newSlot);
        return true;
    }

    public boolean draftPlayer(Player player, DraftSlot slot, int round) {

        String id = player.getID();

        DraftSelection newDraftSelection = new DraftSelection(player, slot);

        if(DEBUG)
            System.out.println("drafting: " + player.getID());

        draftedPlayers[round - 1] = id;
        roster.add(newDraftSelection);
        draftSlots.remove(slot);
        availablePlayers.remove(id);
        
        if(DEBUG)
                System.out.println("draftPlayer");
        activeRosterScore = updateRosterScore(activeRosterScore, player, statLength1, statLength2, roster.size());

        return true;
    } 

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof Problem))
            return false;
        Problem prob = (Problem)other;
        if (roster.size() == prob.getRoster().size()) {
			if (draftSlots.size() == prob.getDraftSlots().size()) {
				for (DraftSelection assign : roster) {
					if (!prob.getRoster().contains(assign)) {
						return false;
					}
				}
				for (DraftSlot slot : draftSlots) {
					if (!prob.getDraftSlots().contains(slot)) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
    }

    public boolean playerAdded(String player) {        
        for(DraftSelection draftSelection : roster) {
            if ( player.equals(draftSelection.getPlayer().getID()) ) 
                return true;
        }    
        return false;
    }

    public boolean slotAdded(DraftSlot draftSlot) {        
        for(DraftSelection draftSelection : roster) {
            if ( draftSlot.equals(draftSelection.getDraftSlot()) ) 
                return true;
        }    
        return false;
    }

    public DraftSlot nextDraftSlot() {
        if (draftSlots.isEmpty())
            return null;
        DraftSlot draftSlot = draftSlots.get(0);
        return draftSlot;
    }

    public float[] updateRosterScore(float[] rosterScore, Player newPlayer, int length1, int length2, float size){

        float[] newRosterScore = rosterScore;
        if(DEBUG)
            System.out.println(newPlayer);
        switch(newPlayer.getClass().getName()){
            case "main.Baseball.Batter":{
                if(DEBUG)
                    System.out.println("player is a batter");

                Batter newBatter = (Batter) newPlayer;
                BattingStats battingStats  = newBatter.getStats();
                Float[] stats = battingStats.getStatsArray();
                int gp = newBatter.getGamesPlayed();
                int yearsCounted = newBatter.getStatYearsCounted();
                
                for(int i = 0; i < 23; i++){
                    newRosterScore[i] += (10 * stats[i]* Env.getBattingWeights(i)) / (gp * Math.max(1, yearsCounted));
                }
        
                for(int i = 23; i < 29; i++){
                    if(DEBUG && Env.getBattingWeights(i) > 0){
                        System.out.println("i : " + i);
                        System.out.println("oldRosterScore[i] : " + newRosterScore[i]);
                    }
                    newRosterScore[i] = (newRosterScore[i] *  (size - 1)/size) + (stats[i] * (1 / size)) * Env.getBattingWeights(i);
                    if(DEBUG && Env.getBattingWeights(i) > 0){
                        System.out.println("size : " + size);
                        System.out.println("(size - 1)/size : " + (size - 1)/size);
                        System.out.println("stats[i] : " + stats[i]);
                        System.out.println("(1 / size) : " + (1 / size));
                        System.out.println("(stats[i] * (1 / size)) : " + (stats[i] * (1 / size)));
                        System.out.println("newRosterScore[i] *  (size - 1)/size) : " + newRosterScore[i] *  (size - 1)/size);
                        System.out.println("newRosterScore[i]: " + newRosterScore[i] + "\n\n");
                    }
                }
        
                for(int i = 28; i < length1; i++){
                    newRosterScore[i] += (10 * stats[i]* Env.getBattingWeights(i)) / (gp * Math.max(1, yearsCounted));
                }

            }
            break;
            case "main.Baseball.Pitcher":{
                if(DEBUG)
                    System.out.println("player is a pitcher");

                Pitcher newPitcher = (Pitcher) newPlayer;
                PitchingStats pitchingStats  = newPitcher.getStats();
                Float[] stats = pitchingStats.getStatsArray();
                int gp = newPitcher.getGamesPlayed();
                int yearsCounted = newPitcher.getStatYearsCounted();

                for(int i = 0; i < 20; i++){
                    newRosterScore[i + length1] += (10 * stats[i]* Env.getPitchingWeights(i)) / (gp * Math.max(1, yearsCounted));
                }

                for(int i = 25; i < 29; i++){

                    if(DEBUG && Env.getPitchingWeights(i) > 0){
                        System.out.println("i : " + i);
                        System.out.println("oldRosterScore[i] : " + newRosterScore[i + length1]);
                    }
                    newRosterScore[i + length1] = (newRosterScore[i + length1] *  (size - 1)/size) + (stats[i] * (1 / size)) * Env.getPitchingWeights(i);
                
                    if(DEBUG && Env.getPitchingWeights(i) > 0){
                        System.out.println("size : " + size);
                        System.out.println("(size - 1)/size : " + (size - 1)/size);
                        System.out.println("stats[i] : " + stats[i]);
                        System.out.println("(1 / size) : " + (1 / size));
                        System.out.println("(stats[i] * (1 / size)) : " + (stats[i] * (1 / size)));
                        System.out.println("newRosterScore[i] *  (size - 1)/size) : " + newRosterScore[i + length1] *  (size - 1)/size);
                        System.out.println("newRosterScore[i]: " + newRosterScore[i + length1] + "\n\n");
                    }
                
                }

                for(int i = 38; i < 41; i++){
                    newRosterScore[i + length1] = (newRosterScore[i + length1] *  (size - 1)/size) + (stats[i] * (1 / size)) * Env.getPitchingWeights(i);
                
                    if(DEBUG && Env.getPitchingWeights(i) > 0){
                        System.out.println("i : " + i);
                        System.out.println("oldRosterScore[i] : " + newRosterScore[i]);
                        System.out.println("size : " + size);
                        System.out.println("(size - 1)/size : " + (size - 1)/size);
                        System.out.println("stats[i] : " + stats[i]);
                        System.out.println("(1 / size) : " + (1 / size));
                        System.out.println("(stats[i] * (1 / size)) : " + (stats[i] * (1 / size)));
                        System.out.println("newRosterScore[i] *  (size - 1)/size) : " + newRosterScore[i] *  (size - 1)/size);
                        System.out.println("newRosterScore[i]: " + newRosterScore[i] + "\n\n");
                    }
                
                }
            }
            break;
            case "main.Hockey.Skater":{
                if(DEBUG)
                    System.out.println("player is a skater");
                Skater newSkater = (Skater) newPlayer;
                SkaterCountingStats skaterStats  = newSkater.getCountingStats();
                Integer[] stats = skaterStats.getStatsArray();
                int i;
                for(i = 0; i < length1; i++){
                    newRosterScore[i] += stats[i] * Env.getSkaterWeights(i);
                }
        
                newRosterScore[i++] += skaterStats.getPoints() * Env.getSkaterWeights(i);
                newRosterScore[i++] += skaterStats.getPowerplaypoints() * Env.getSkaterWeights(i);
                newRosterScore[i] += skaterStats.getShpoints() * Env.getSkaterWeights(i);
            }
            break;
            case "main.Hockey.Goalie":{
                if(DEBUG)
                    System.out.println("player is a goalie");
                Goalie newGoalie = (Goalie) newPlayer;
                GoalieCountingStats goalieStats  = newGoalie.getCountingStats();
                Integer[] stats = goalieStats.getStatsArray();
                int length = stats.length;
                for(int i = 0; i < length; i++){
                    newRosterScore[i + length1] += stats[i] * Env.getGoalieWeights(i);
                }
            }
            break;
        }

        return newRosterScore;
    }

    
    public int[] getStatLengths() {
        return new int[] {statLength1, statLength2};
    }
    
    public void addDraftedPlayers(String player, int index) {
        draftedPlayers[index] = player;
    }

    public void addOpponentPlayer(int opponentID, String player) {
        opponentRosters[opponentID].add(player);
    }

    

    public void addOpponentDraftPicks(int currentPick, int totalPicks, String[] players) {
        for(int i = currentPick + 1; i <= totalPicks; i++){
            int opponentID = Env.totalPicksInDraft.get(i - 1);
            String s = players[i - currentPick - 1];
            opponentRosters[opponentID].add(s);
            if(DEBUG)
                System.out.println("addOpponentDraftPicks");
            opponentRosterScores[opponentID] = updateRosterScore(opponentRosterScores[opponentID], Env.AllPlayers.get(s), statLength1, statLength2, opponentRosters[opponentID].size());  
        }
    }
    
    public void advancePick(int amount) {
        currentPick += amount;
    }
    
    public void incrementHighestScoreIndex() {
        highestScoreIndex++;
    }
    
    public void nextPick() {
        currentPick++;
    }
    
    public void removeAvailablePlayer(String s){
        availablePlayers.remove(s);
    }

    
    public void removeAvailablePlayers(String[] players){
        for(String s : players){
            availablePlayers.remove(s);
        }
    }

    public void setRosterScore() {
        if(DEBUG)
                System.out.println("setRosterScore");
        int i = 1;
        for(String s : draftedPlayers){
            if(s != null){
                activeRosterScore = updateRosterScore(activeRosterScore, Env.AllPlayers.get(s), statLength1, statLength2, i++);
            }
                
        }
    }

    public void updateOpponentRosterScore(int opponentID, Player newPlayer){
        if(DEBUG)
                System.out.println("update opponent score");
        opponentRosterScores[opponentID] = updateRosterScore(opponentRosterScores[opponentID], newPlayer, statLength1, statLength2, opponentRosters[opponentID].size());
    }

    //Getters
    public ArrayList<DraftSelection> getRoster() {
        return roster;
    }

    public ArrayList<DraftSlot> getDraftSlots() {
        return draftSlots;
    }

    public ArrayList<String> getAvailablePlayers() {
        return availablePlayers;
    }

    public ArrayList<String>[] getOpponentRosters() {
        return opponentRosters;
    }

    public float[] getActiveRosterScore() {
        return activeRosterScore;
    }

    public float[][] getOpponentRosterScores() {
        return opponentRosterScores;
    }

    public int getCurrentPick() {
        return currentPick;
    }

    public int getHighestScoreIndex() {
        return highestScoreIndex;
    }

    public String[] getDraftedPlayers() {
        return draftedPlayers;
    }

    //Setters
    public void setAvailablePlayers(ArrayList<String> availablePlayers) {
        this.availablePlayers = availablePlayers;
    }
    
    public void setDraftSlots(ArrayList<DraftSlot> draftSlots) {
        this.draftSlots = draftSlots;
    }

    public void setOpponentRoster(int index, ArrayList<String> opponentRoster) {
        this.opponentRosters[index] = opponentRoster;
    }

    public void setDraftedPlayer(int index, String player){
        draftedPlayers[index] = player;
    }
}