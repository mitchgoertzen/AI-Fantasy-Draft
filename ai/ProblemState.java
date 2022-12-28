package ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import main.AIParticipant;
import main.DraftMenu;
import main.Env;

public class ProblemState {

    private boolean DEBUG = false;

	private AIParticipant ai;

	private Constr myConstr;

	private float eval;
	private float parent_eval;

	private int round;

	private Map<Integer, Boolean> draftedPlayerCombinations;

    private Problem problem;

	public ProblemState(Problem problem, ProblemState parent, AIParticipant ai) {
		round = Env.getCurrentRound();
        myConstr = new Constr();
        this.problem = problem;		
		this.ai = ai;
        if (parent == null){
			draftedPlayerCombinations = new HashMap<>();
			return;
		}
		round = parent.getRound();
		//problem.setDraftedPlayers((String[]) ai.getRoster().getPlayers().clone());

		int i = 0;
		for(String p : ai.getRoster().getPlayers()){
			problem.draftedPlayers[i++] = p;
		}
		draftedPlayerCombinations = parent.getDraftedPlayerCombinations();
        this.parent_eval = parent.getEval();

        //this.myConstr = new Constr(parent.getConstr());
    }


	// Returns true if this ProblemState does not meet all constraints
    //or its penalty is higher than the current lowest penalty(score is lower than current highest score),
    //therefore its leaf should be discarded
	public boolean discardLeaf() {
		//System.out.println(problem.getRoster().size());
		if (rosterCombinationExists() || !myConstr.meetsConstraints(problem, ai, round)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ProblemState))
			return false;
		ProblemState state = (ProblemState) obj;
		return state.getProblem().equals(problem);
	}

	// Returns true if this solution is the best solution
	public boolean isBestSolution() {
		//eval = myEval.evaluate(problem.getRoster(), eval);
		if (Float.compare(eval, ai.getMaxScore()) > 0) {
			// System.out.println("eval: " + eval);
			// System.out.println("max: " +  ai.getMaxScore());
			// if(DEBUG)
			// 	System.out.println("setting as new max");
			// System.out.println("-----");
			// for(String s : problem.getDraftedPlayers()){
			// 	System.out.println(s);
			// }
			// System.out.println("-----");
		
			ai.setMaxScore(eval);
			return true;
		}
		return false;
	}


    private boolean rosterCombinationExists() {

		ArrayList<String> currentRoster = new ArrayList<>();
		String[] p = problem.getDraftedPlayers();

		//save current round somehwerwe and use it for array index
		for(int i = 0; i < p.length; i++){
			if(p[i] != null){
				currentRoster.add(p[i]);
			}
		}
		//System.out.println("current roster size: " + currentRoster.size());

		Collections.sort(currentRoster);
		// if(DEBUG){
		// 	System.out.println("---");
		// 	System.out.println(currentRoster);
		// 	System.out.println("---");
		// }
		int hashCode = currentRoster.hashCode();

		if(draftedPlayerCombinations.containsKey(hashCode)){
			
			if(DEBUG)
			//	System.out.println("player combo");
			return true;

		}

		draftedPlayerCombinations.put(hashCode, true);

		return false;
	}

	public void SimulateOpponentDraftPicks(){
		//passed value float[][] rosterScore
		
		//float[][] cumulativeWeeklyScore = new float[15][2]; --> do not need?

		//do not simulate rounds that are not in the draft 
		if(Env.getCurrentRound() <= Env.getTotalRounds()){


			/*

			//used for tiebreakers
			//float totalRosterScore = 0;

			Skater skater = (Skater) Env.AllPlayers.get(newestPlayerDrafted);		
			float[] playerScore = skater.getCountingStats().getStatsArray();
			for(int i = 0; i < 15;i++){
				rosterScore[i][0] += playerScore[i] * Env.getSkaterWeights(i);
				totalRosterScore += rosterScore[i][0];
			}
			 */

			//System.out.println("round: " + round);

			int firstPick = ((round - 1)* Env.participants.size()) + 1;
			int j = round * Env.participants.size();
			 //change to: i = ((currentRound - 1) * #participants) + 1, j = i + #participants, i < j
			//for(int i = problem.getCurrentPick(); i <= Env.totalPicksInDraft.entrySet().size(); i++){
			int currentPick = problem.getCurrentPick() - 1;
			//System.out.println("first pick in round " + round + ": " + firstPick);
			//System.out.println("last pick: " + j);
			//System.out.println("current ai's pick: " + currentPick);
			for(int i = firstPick; i <= j; i++){
				if(i > currentPick){
					//System.out.println("current simulated pick: " + i);
					//System.out.println("--sim opponent's next pick in round--");
					//System.out.println("player score size: " + problem.playerScores.entrySet().size());
					// Map.Entry<String,Float> entry = problem.playerScores.entrySet().iterator().next();
					// String highestScorePlayer = entry.getKey();
					Iterator<Map.Entry<String,Float>> entry = DraftMenu.getPlayerScores().entrySet().iterator();
					for(int k = 0; k < problem.getHighestScoreIndex(); k++){
						 entry.next();
					}

					String highestScorePlayer = entry.next().getKey();
					problem.incrementHighestScoreIndex();
					//problem.playerScores.remove(highestScorePlayer);
					problem.availablePlayers.remove(highestScorePlayer);
					//System.out.println("opponent has drafted: " + highestScorePlayer);
					problem.nextPick();


				}


				// if(Env.totalPicksInDraft.get(i - 1) == ai.getId()){
				// 	System.out.println("calculate this ai's roster score vs Particpant " + Env.totalPicksInDraft.get(i-1) + "'s roster");
				// }

				// System.out.println("Participant " + Env.totalPicksInDraft.get(i-1) + ": ");
				// System.out.println("-----------");
				// for(String s : problem.getOpponentRosters().get(Env.totalPicksInDraft.get(i-1))){
				// 	System.out.println(s);
				// }
				// System.out.println("-----------");



				//next player needs to fit under the position limit of the opponent picking



				// int opponentID = ;

				// System.out.println(i-1);
				// //System.out.println("Participant " + p.getId() + ": ");
				// System.out.println("-----------");
				// for(String s : Env.participants.get(opponentID).getRoster().getPlayers()){
				// 	System.out.print("calculate weekly score for: ");
				// 	System.out.println(s);
				// }
				// System.out.print("calculate weekly score for: ");
				// System.out.println(highestScorePlayer);
				// System.out.println("-----------");
			

				// System.out.print("cumulative weekly score is: ");

				


				 
			 }
		}

		  
	}


	public AIParticipant getAi() {
		return ai;
	}

	public Constr getConstr() {
		return myConstr;
	}

	public float getEval() {
		return eval;
	}
	
	public int getRound() {
		return round;
	}

	public Map<Integer, Boolean> getDraftedPlayerCombinations() {
		return draftedPlayerCombinations;
	}

    public Problem getProblem() {
		return problem;
	}

	public String getMostRecentDraftSelection() {

		if(DEBUG){
			System.out.println("problem: " + problem);
			System.out.println("players: " + problem.getDraftedPlayers().length);
			System.out.println("---");
			for(String s : problem.getDraftedPlayers()){
				System.out.println(s);
			}
			System.out.println("---");
			System.out.println("round: " + Env.getCurrentRound());
		}
		
		return problem.getDraftedPlayers()[Env.getCurrentRound() - 1];
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

    public void nextRound() {
		round++;
	}

	public float getParentEval() {
		return parent_eval;
	}

	public void setParentEval(float parent_eval) {
		this.parent_eval = parent_eval;
	}

	public void setEval(float eval) {
		this.eval = eval;
	}
}
