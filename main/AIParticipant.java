package main;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import ai.*;

public class AIParticipant extends Participant {

    ArrayList<DraftSlot> draftSlots;
    int maxDraftSlots;
    public int getMaxDraftSlots() {
        return maxDraftSlots;
    }
    float rosterScore;
    //total # of catgeory wins, determined each week 
    int predictedSeasonScore;
    //lw, rw, c, d, g
    Integer[] positionCounts = new Integer[5];


    public Integer[] getPositionCounts() {
        return positionCounts;
    }

    public AIParticipant(int id, boolean isHuman, int draftNumber, int maxDraftSlots) {
        super(id, isHuman, draftNumber);
        this.maxDraftSlots = maxDraftSlots;
        draftSlots = new ArrayList<>();
        for(int i = 0; i < positionCounts.length; i++){
            positionCounts[i] = 0;
        }
		for(int i = 0; i < maxDraftSlots; i++){
            draftSlots.add(new DraftSlot(i));
        }


    }

    public String draftPlayer(){

    	long StartTime = System.nanoTime();

        ProblemState initialState = initializeProblemState();
        initialState.setEval(rosterScore);

    	//Run the search
        ProblemState solution = runSearch(initialState);
        String playerCode = solution.getMostRecentDraftSelection();
        // System.out.println(super.getRoster().hashCode());
        // System.out.println(super.hashCode());
        super.addPlayer(playerCode);
        rosterScore += Env.PlayerScores.get(playerCode);
        setMaxScore(rosterScore);
    
        int index = Env.getPositionIndex(Env.AllPlayers.get(playerCode).getPosition(), positionCounts);
        positionCounts[index]++;

        System.out.println("score: " +  Env.PlayerScores.get(playerCode));
        System.out.println((System.nanoTime() - StartTime)/1000000000f);
        return playerCode;
    }

    @SuppressWarnings (value="unchecked")
    private ProblemState initializeProblemState() {
        
    	Problem initialProblem = new Problem(maxDraftSlots, super.getId());

        initialProblem.setAvailablePlayers((ArrayList<String>) DraftMenu.getAvailablePlayers().clone());

        initialProblem.setPlayerScores((LinkedHashMap<String, Float>) DraftMenu.getPlayerScores().clone());

        for (Map.Entry<String,Float> mapElement : DraftMenu.getPlayerScores().entrySet()) {
            initialProblem.addPlayerScore(mapElement.getKey(), mapElement.getValue());
        }
        initialProblem.setDraftSlots((ArrayList<DraftSlot>) draftSlots.clone());

        int i = 0;
        // System.out.println(super.getRoster().hashCode());
        // System.out.println(super.hashCode());
        // System.out.println("Participant " + super.getId() + " has the roster of:");
        for(String s : super.getRoster().getPlayers()){
         //   System.out.println("player: " + s);
            initialProblem.addDraftedPlayers(s, i++);
        }

    	//root node
    	return new ProblemState(initialProblem, null, this);
    }

    public ProblemState runSearch(ProblemState initialState)  {

		ProblemState bestState = null;
		ProblemState currentState;

		Control control = new Control(initialState);

    	while ((!control.getLeaves().isEmpty())) {


    		//Select the best leaf to work on
    		control.fleaf();

    		//Decide what to do with the current leaf
    		control.ftrans();
    		currentState = control.getCurrentLeaf();
    		//If the solution is complete and it has a better eval value than the current best state,
    		//then update the best state to the current state
    		if (!(currentState == null)) {
    			bestState = currentState;
    		}
    	}
		
    	if (bestState == null) {
    		System.out.println("No solution found.");
            return bestState;
    	}

        draftSlots.remove(0);
    	return bestState;
    }
    
    public void addEval(float eval){
        rosterScore += eval;
    }
    private float maxScore = -Float.MAX_VALUE;

    public float getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(float maxScore) {
        this.maxScore = maxScore;
    }
}
