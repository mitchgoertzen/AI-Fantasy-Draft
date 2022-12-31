package ai;

import java.util.ArrayList;
import java.util.Map;

import main.DraftSelection;
import main.DraftSlot;
import main.Env;
import main.Participant;
import main.Player;

@SuppressWarnings("unchecked")
public class Problem {
    
    private final boolean DEBUG = false;
    
    private ArrayList<DraftSlot> draftSlots;
    private ArrayList<DraftSelection> roster;
    private ArrayList<String> availablePlayers;
    private ArrayList<String>[] opponentRosters;

    private int currentPick;
    private int highestScoreIndex;

    private String[] draftedPlayers;

    public Problem(int rosterSize, int id) {

        availablePlayers = new ArrayList<>();
        currentPick = Env.getCurrentPick();
        draftedPlayers = new String[rosterSize];
        draftSlots = new ArrayList<>();
        highestScoreIndex = 0;  
        opponentRosters = new ArrayList[Env.participants.size()];

        int currentKey = -1;
        for(Map.Entry<Integer, Participant> p: Env.participants.entrySet()){
            currentKey = p.getKey();
            if(currentKey != id){
                opponentRosters[currentKey] = p.getValue().getRoster().getPlayers();
            
            }
        }
        roster = new ArrayList<>();
    }

    public Problem(Problem problem, int rosterSize) {
        
        opponentRosters = new ArrayList[Env.participants.size()];

        int rosterLength = problem.getOpponentRosters().length;
        for(int i = 0; i < rosterLength; i++){
            if(problem.getOpponentRosters()[i] != null){
                opponentRosters[i] = (ArrayList<String>) problem.getOpponentRosters()[i].clone();
            }
        }

        highestScoreIndex = problem.getHighestScoreIndex();
        currentPick = problem.getCurrentPick();
        draftedPlayers = problem.getDraftedPlayers().clone();
        availablePlayers = new ArrayList<>(problem.getAvailablePlayers());
        draftSlots = new ArrayList<>(problem.getDraftSlots());
        roster = new ArrayList<>(problem.getRoster());	
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
