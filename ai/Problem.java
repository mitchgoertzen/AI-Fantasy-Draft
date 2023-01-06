package ai;

import java.util.ArrayList;
import java.util.Map;

import main.DraftSelection;
import main.DraftSlot;
import main.Env;
import main.Goalie;
import main.GoalieCountingStats;
import main.Participant;
import main.Player;
import main.Skater;
import main.SkaterCountingStats;

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

    private String[] draftedPlayers;

    public Problem(int rosterSize, int id) {

        draftSlots = new ArrayList<>();
        roster = new ArrayList<>();
        opponentRosterScores = new float[Env.participants.size()][25];
        availablePlayers = new ArrayList<>();
        opponentRosters = new ArrayList[Env.participants.size()];

        int currentKey = -1;
        for(Map.Entry<Integer, Participant> p: Env.participants.entrySet()){
            currentKey = p.getKey();
            if(currentKey != id){
                opponentRosters[currentKey] = p.getValue().getRoster().getPlayers();
                
                //currentKey opponent's roster score is empty float[]
                float[] currentOpponentScore = new float[25];
                //for player in opponent roster, update opp score
                for(String s : opponentRosters[currentKey]){
                    currentOpponentScore = updateRosterScore(currentOpponentScore, Env.AllPlayers.get(s));
                }
                opponentRosterScores[currentKey] = currentOpponentScore;
            }
        }
        
        activeRosterScore = new float[25];
        currentPick = Env.getCurrentPick();
        highestScoreIndex = 0;  
        draftedPlayers = new String[rosterSize];
    }

    public Problem(Problem problem, int rosterSize) {
        
       
        draftSlots = new ArrayList<>(problem.getDraftSlots());
        roster = new ArrayList<>(problem.getRoster());	
        availablePlayers = new ArrayList<>(problem.getAvailablePlayers());
        opponentRosters = new ArrayList[Env.participants.size()];
        opponentRosterScores = new float[Env.participants.size()][25];

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

        if (!draftSlots.contains(slot)){
            if(DEBUG)
                System.out.println(slot + " does not exist");
            System.exit(1);
            return false;
        }

        if (!availablePlayers.contains(id))      {
            if(DEBUG)
                System.out.println(player + " does not exist");
            System.exit(1);
            return false;
        }

        DraftSelection newDraftSelection = new DraftSelection(player, slot);

        if(DEBUG)
            System.out.println("drafting: " + player.getID());

        draftedPlayers[round - 1] = id;
        roster.add(newDraftSelection);
        draftSlots.remove(slot);
        availablePlayers.remove(id);
        
        activeRosterScore = updateRosterScore(activeRosterScore, player);

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

    public float[] updateRosterScore(float[] rosterScore, Player newPlayer){

        float[] newRosterScore = rosterScore;

        System.out.println("player: " + newPlayer.getName());
        // System.out.println("old");
        // for(int j  = 0; j < newRosterScore.length; j++){
        //     System.out.println("j " + j + ": " + newRosterScore[j]); 
        // }
        
        //TODO: make universal method? also used in parser
        if(newPlayer.getPosition().equals("G")){
            if(DEBUG)
                System.out.println("player is a goalie");
            Goalie newGoalie = (Goalie) newPlayer;
            GoalieCountingStats goalieStats  = newGoalie.getCountingStats();
            Integer[] stats = goalieStats.getStatsArray();
            int length = stats.length;
            for(int i = 0; i < length; i++){
                newRosterScore[i] += stats[i] * Env.getGoalieWeights(i);
            }
        }else{
            if(DEBUG)
                System.out.println("player is a skater");
            Skater newSkater = (Skater) newPlayer;
            SkaterCountingStats skaterStats  = newSkater.getCountingStats();
            Integer[] stats = skaterStats.getStatsArray();
            int length = stats.length;
            int i;
            for(i = 0; i < length; i++){
                newRosterScore[i] += stats[i] * Env.getSkaterWeights(i);
                System.out.println("newRosterScore[i]: " + newRosterScore[i]);
            }
    
            newRosterScore[i++] += skaterStats.getPoints() * Env.getSkaterWeights(i);
            newRosterScore[i++] += skaterStats.getPowerplaypoints() * Env.getSkaterWeights(i);
            newRosterScore[i] += skaterStats.getShpoints() * Env.getSkaterWeights(i);
        }

        // System.out.println("new");
        // for(int j  = 0; j < newRosterScore.length; j++){
        //     System.out.println("j " + j + ": " + newRosterScore[j]); 
        // }
        return newRosterScore;
    }
    
    public void addDraftedPlayers(String player, int index) {
        draftedPlayers[index] = player;
    }

    public void addOpponentPlayer(int opponentID, String player) {
        opponentRosters[opponentID].add(player);
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

    public void setRosterScore() {
        for(String s : draftedPlayers){
            if(s != null)
                activeRosterScore = updateRosterScore(activeRosterScore, Env.AllPlayers.get(s));
        }
    }

    public void updateOpponentRosterScore(int opponentID, Player newPlayer){
        opponentRosterScores[opponentID] = updateRosterScore(opponentRosterScores[opponentID], newPlayer);
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
