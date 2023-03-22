package main;

import java.util.ArrayList;
import ai.*;

public class AIParticipant extends Participant {

    private final boolean DEBUG = true;
    private final boolean DEBUG_VERBOSE = false;

    private ArrayList<DraftSlot> draftSlots;

    public void restoreDraftSlot(int index) {
        draftSlots.add(0, new DraftSlot(index));
    }

    private boolean hockey;

    public boolean getHockey() {
        return hockey;
    }

    private float[] rosterEval;
    private float[] maxScore;

    private int maxDraftSlots;

    private Integer[] positionCounts = new Integer[11]; 
    private boolean[] positionDraftEligible = new boolean[11]; 

    private int extraBattersAvailable = 2; 

    private int extraPitchersAvailable = 4; 
    //lw, rw, c, d, g, f
    //C, 1B, 2B, 3B, SS, OF, D, SP, RP, B, P

    public AIParticipant(int id, boolean isHuman, int draftNumber, int maxDraftSlots, boolean hockey) {

        super(id, isHuman, draftNumber);
        this.maxDraftSlots = maxDraftSlots;
        maxScore = new float[3]; 
        rosterEval = new float[3];
        draftSlots = new ArrayList<>();
        for(int i = 0; i < positionCounts.length; i++){
            positionCounts[i] = 0;
            positionDraftEligible[i] = true;
        }
		for(int i = 0; i < maxDraftSlots; i++){
            draftSlots.add(new DraftSlot(i));
        }
        this.hockey = hockey;
    }

    @SuppressWarnings (value="unchecked")
    private ProblemState initializeProblemState(int l1, int l2) {
        
    	Problem initialProblem = new Problem(maxDraftSlots, super.getId(), l1, l2);

        initialProblem.setAvailablePlayers((ArrayList<String>) DraftMenu.getAvailablePlayers().clone());

        int numParticipants = Env.participants.size();
        for(int i = 0; i < numParticipants; i++){
            if(i != super.getId()){
                initialProblem.setOpponentRoster(i, Env.participants.get(i).getRoster().getPlayers());
            }
        }

        initialProblem.setDraftSlots((ArrayList<DraftSlot>) draftSlots.clone());

        int i = 0;
        for(String s : super.getRoster().getPlayers()){
            initialProblem.addDraftedPlayers(s, i++);
        }
        initialProblem.setRosterScore();

    	//root node
    	return new ProblemState(initialProblem, null, this);
    }

    public ProblemState runSearch(ProblemState initialState)  {

        if(DEBUG)
            System.out.println("run search");

		ProblemState bestState = null;
		ProblemState currentState;

		Control control = new Control(initialState);

        if(DEBUG)
            System.out.println("leaves: " + control.getLeaves().size());
    	while ((!control.getLeaves().isEmpty())) {

    		control.fleaf();

    		control.ftrans();
    		currentState = control.getCurrentLeaf();

    		//If the solution is complete and it has a better eval value than the current best state,
    		//then update the best state to the current state
    		if (!(currentState == null)) {
                if(DEBUG_VERBOSE)
                    System.out.println("current state not null");
    			bestState = currentState;
    		}
    	}
		
    	if (bestState == null) {
            if(DEBUG)
    		    System.out.println("best state null; No solution found.");
            return bestState;
    	}

        draftSlots.remove(0);
    	return bestState;
    }

    public String draftPlayer(int l1, int l2){

    	long StartTime = System.nanoTime();
        
        maxScore[0] = -1;
        maxScore[1] = -1;
        maxScore[2] = -1;

        ProblemState initialState = initializeProblemState(l1, l2);
        
        initialState.setEval(rosterEval);

        ProblemState solution = runSearch(initialState);
        String playerCode = solution.getMostRecentDraftSelection();

        super.addPlayer(playerCode);
        int index = -1;

        if(hockey){
            index = Env.getHockeyPositionIndex(Env.AllPlayers.get(playerCode).getPosition(), positionCounts);
        }
        else{
            index = Env.getBaseballPositionIndex(Env.AllPlayers.get(playerCode).getPosition());
            if(positionCounts[index] + 1 > Env.getPositionLimits()[index]){
                if(index < 7)
                    extraBattersAvailable--;
                else
                    extraPitchersAvailable--;
            }
            
            if(index < 7 && extraBattersAvailable == 0){
                if(DEBUG){

                    System.out.println("x batters 0");
                    System.out.println("index: " + index);
                    System.out.println("positionCounts[index] + 1: " + (positionCounts[index] + 1));
                }

                if((positionCounts[index] + 1) >= Env.getPositionLimits()[index]){
                    if(DEBUG){
                        System.out.println("count >= limit");
                    }
                    positionDraftEligible[index] = false;
                }

                for(int i = 0; i < 7; i++){
                    if(positionCounts[i] >= Env.getPositionLimits()[i])
                        positionDraftEligible[i] = false;
                }

            }
            
            if(index >= 7){
                if(extraPitchersAvailable == 0 ){
                    if(positionCounts[index] + 1 >= Env.getPositionLimits()[index])
                        positionDraftEligible[index] = false;
                    for(int i = 7; i < 9; i++){
                        if(positionCounts[i] >= Env.getPositionLimits()[i])
                            positionDraftEligible[i] = false;
                    }
                }else{
                    if(index == 7){
                        if(positionCounts[index] + 1 >= (Env.getPositionLimits()[10] - (Env.getPositionLimits()[8] - positionCounts[8])))
                            positionDraftEligible[index] = false;
                    }else if(index == 8){
                        if(positionCounts[index] + 1 >= (Env.getPositionLimits()[10] - (Env.getPositionLimits()[7] - positionCounts[7])))
                            positionDraftEligible[index] = false;
                    }
                }
            }  
        }

        positionCounts[index]++;

        if(DEBUG){ 
            
            for(int i = 0; i < positionDraftEligible.length; i++){
                if(positionDraftEligible[i] == true){
                    System.out.println("pos index available: " + i);
                }
            }
            System.out.println("player pos: " + Env.AllPlayers.get(playerCode).getPosition());
            System.out.println("new count: " + (positionCounts[index]));
            System.out.println("limit: " + Env.getPositionLimits()[index]);
            System.out.println("extra batters: " + getExtraBattersAvailable());
            System.out.println("extra pitchers: " + getExtraPitchersAvailable());

            System.out.println("\nRoster: ");
            for(String s : super.getRoster().getPlayers()){
                    System.out.println(Env.AllPlayers.get(s).getName() + ", " + Env.AllPlayers.get(s).getPosition());
            }
        }

        // System.out.println("score: " +  Env.PlayerScores.get(playerCode));
        // System.out.println("time elapsed: " + (System.nanoTime() - StartTime)/1000000000f + "s");
        return playerCode;
    }

    public boolean getPositionDraftEligible(int index) {
        return positionDraftEligible[index];
    }

    public float[] getMaxScore() {
        return maxScore;
    }
    
    public int getExtraBattersAvailable() {
        return extraBattersAvailable;
    }

    public int getExtraPitchersAvailable() {
        return extraPitchersAvailable;
    }

    public int getMaxDraftSlots() {
        return maxDraftSlots;
    }
    
    public Integer[] getPositionCounts() {
        return positionCounts;
    }

    public void setMaxScore(float[] maxScore) {
        this.maxScore = maxScore;
    }
}