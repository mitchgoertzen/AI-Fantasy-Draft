package ai;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import main.DraftSelection;
import main.DraftSlot;
import main.Env;
import main.Participant;
import main.Player;

public class Problem {
    
    ArrayList<String> availablePlayers;

    //LinkedHashMap<String, Float> playerScores;
    int highestScoreIndex;
    
    public void incrementHighestScoreIndex() {
        highestScoreIndex++;
    }


    public int getHighestScoreIndex() {
        return highestScoreIndex;
    }

    LinkedHashMap<Integer, ArrayList<String>> opponentRosters;

    public LinkedHashMap<Integer, ArrayList<String>> getOpponentRosters() {
        return opponentRosters;
    }

    private int currentPick;

    public int getCurrentPick() {
        return currentPick;
    }

    
    public void nextPick() {
        currentPick++;
    }

    // public LinkedHashMap<String, Float> getPlayerScores() {
    //     return playerScores;
    // }

    ArrayList<DraftSlot> draftSlots;

    ArrayList<DraftSelection> roster;
    String[] draftedPlayers;


    public void addDraftedPlayers(String player, int index) {
        draftedPlayers[index] = player;
    }


    public String[] getDraftedPlayers() {
        return draftedPlayers;
    }


    public Problem(int rosterSize, int id) {
        opponentRosters = new LinkedHashMap<>();
        int currentKey = -1;
       // System.out.println("id: " + id);
        for(Map.Entry<Integer, Participant> p: Env.participants.entrySet()){
            currentKey = p.getKey();
           // System.out.println("currentKey: " + currentKey);
            if(currentKey != id){
                opponentRosters.put(currentKey, p.getValue().getRoster().getPlayers());
                // System.out.println("opponent roster: ");
                // for(String s : opponentRosters.get(currentKey)){
                //     System.out.println("player: " + s);
                // }
            }
        }
        highestScoreIndex = 0;
        //playerScores = new LinkedHashMap<String, Float>();
        currentPick = Env.getCurrentPick();
        availablePlayers = new ArrayList<>();
        draftSlots = new ArrayList<>();
        roster = new ArrayList<>();
        draftedPlayers = new String[rosterSize];
    }

    @SuppressWarnings (value="unchecked")
    public Problem(Problem problem, int rosterSize) {
        opponentRosters = new LinkedHashMap<>((LinkedHashMap<Integer, ArrayList<String>>)problem.getOpponentRosters().clone());
        //opponentRosters =  ;
        highestScoreIndex = problem.getHighestScoreIndex();
        //playerScores = new LinkedHashMap<String, Float>();
        currentPick = problem.getCurrentPick();

        // for (Map.Entry<String,Float> mapElement : problem.getPlayerScores().entrySet()) {
        //     playerScores.put(mapElement.getKey(), mapElement.getValue());
        // }

       // playerScores = new LinkedHashMap<>(problem.getPlayerScores());
        //playerScores = (LinkedHashMap<String, Float>) problem.getPlayerScores().clone();
        
    	//availablePlayers = new ArrayList<>();
    	//draftSlots = new ArrayList<>();
    	//roster = new ArrayList<>();
        //draftedPlayers = new String[rosterSize];

        draftedPlayers = problem.getDraftedPlayers().clone();
        availablePlayers = new ArrayList<>(problem.getAvailablePlayers());// (ArrayList<String>)problem.getAvailablePlayers().clone();
        draftSlots = new ArrayList<>(problem.getDraftSlots());//(ArrayList<DraftSlot>) problem.getDraftSlots().clone();
        roster = new ArrayList<>(problem.getRoster());//(ArrayList<DraftSelection>) problem.getRoster().clone();
 	    		
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

    public boolean addDraftSlot(DraftSlot newSlot) {
        if (draftSlots.contains(newSlot)  || slotAdded(newSlot))
            return false;
        draftSlots.add(newSlot);
        return true;
    }

    

    public boolean addAvailablePlayer(String player) {
        if (availablePlayers.contains(player)  || playerAdded(player))
            return false;
        availablePlayers.add(player);
        return true;
    }

    // public void addPlayerScore(String key, float value){
    //     playerScores.put(key, value);
    // }


    public ArrayList<String> getAvailablePlayers() {
        return availablePlayers;
    }

    public ArrayList<DraftSlot> getDraftSlots() {
        return draftSlots;
    }

    public DraftSlot nextDraftSlot() {
        if (draftSlots.isEmpty())
            return null;
        DraftSlot draftSlot = draftSlots.get(0);
        return draftSlot;
    }

    public ArrayList<DraftSelection> getRoster() {
        return roster;
    }

    public boolean draftPlayer(Player player, DraftSlot slot, int round) {

        String id = player.getID();
        if (!draftSlots.contains(slot))      {
            System.out.println(slot + " does not exist");
            System.exit(1);
            return false;
        }

        if (!availablePlayers.contains(id))      {
            System.out.println(player + " does not exist");
            System.exit(1);
            return false;
        }

        DraftSelection newDraftSelection = new DraftSelection(player, slot);
        roster.add(newDraftSelection);

       // System.out.println("drafting: " + player.getID());
        
        //System.out.println("round " + (round + 1));
        draftedPlayers[round - 1] = id;

        draftSlots.remove(slot);
        availablePlayers.remove(id);
        //playerScores.remove(id);
        
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

    
    public void setAvailablePlayers(ArrayList<String> availablePlayers) {
        this.availablePlayers = availablePlayers;
    }
    
    public void setDraftSlots(ArrayList<DraftSlot> draftSlots) {
        this.draftSlots = draftSlots;
    }
    
    // public void setPlayerScores(LinkedHashMap<String, Float> playerScores) {
    //     this.playerScores = playerScores;
    // }
}
