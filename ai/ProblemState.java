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
		int i = 0;
		for(String p : ai.getRoster().getPlayers()){
			problem.setDraftedPlayer(i++, p);
		}
		draftedPlayerCombinations = parent.getDraftedPlayerCombinations();
        parent_eval = parent.getEval();
    }

	public boolean discardLeaf() {
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

	public boolean isBestSolution() {
		if (Float.compare(eval, ai.getMaxScore()) > 0) {
			 if(DEBUG){
				System.out.println("eval: " + eval);
				System.out.println("max: " +  ai.getMaxScore());
				System.out.println("-----");
				for(String s : problem.getDraftedPlayers()){
					System.out.println(s);
				}
				System.out.println("-----");
			 }
		
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

		Collections.sort(currentRoster);
		int hashCode = currentRoster.hashCode();

		if(draftedPlayerCombinations.containsKey(hashCode)){
			if(DEBUG)
				System.out.println("player combo");
			return true;
		}

		draftedPlayerCombinations.put(hashCode, true);

		return false;
	}
	
    public void nextRound() {
		round++;
	}

	public void SimulateOpponentDraftPicks(){

		/*
		passed value float[][] rosterScore
		float[][] cumulativeWeeklyScore = new float[15][2]; --> do not need?
		*/

		if(DEBUG){
			System.out.println("current: " + Env.getCurrentRound());
			System.out.println("total: " + Env.getTotalRounds());
		}

		//do not simulate rounds that are not in the draft 
		if(Env.getCurrentRound() <= Env.getTotalRounds()){

			if(DEBUG){
				for(String s : problem.getDraftedPlayers()){
					System.out.println(s);
				}
				System.out.println("vs.");
			}
			
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

			int firstPick = ((round - 1)* Env.participants.size()) + 1;
			int j = round * Env.participants.size();
			int currentPick = problem.getCurrentPick() - 1;
			for(int i = firstPick; i <= j; i++){
				if(i > currentPick){
					if(DEBUG){
						System.out.println("simming picks");
						System.out.println("current simulated pick: " + i);
						System.out.println("--sim opponent's next pick in round--");
					}

					Iterator<Map.Entry<String,Float>> entry = DraftMenu.getPlayerScores().entrySet().iterator();
					for(int k = 0; k < problem.getHighestScoreIndex(); k++){
						 entry.next();
					}  

					//needed?
					//if current ai has drafted player with highest score, increment index and iterator
					/*
					if(problem.getDraftedPlayers()[round- 1] == entry.next().getKey()){
						if(DEBUG)
							System.out.println("recent draft pick is highest score player");
						problem.incrementHighestScoreIndex();
						entry.next();
					}
					*/

					String highestScorePlayer = entry.next().getKey();

					if(DEBUG)
						System.out.println("opponent will be drafting: " + highestScorePlayer);

					problem.removeAvailablePlayer(highestScorePlayer);
					problem.incrementHighestScoreIndex();
					problem.addOpponentPlayer(Env.totalPicksInDraft.get(i - 1), highestScorePlayer);
					problem.nextPick();


				}

				//next player needs to fit under the position limit of the opponent picking

				if(DEBUG){				
					if(Env.totalPicksInDraft.get(i - 1) != ai.getId()){
						for(String s : problem.getOpponentRosters()[Env.totalPicksInDraft.get(i - 1)]){
							System.out.println(s);
						}
					}
					System.out.println();
					System.out.println("-----------\n");
				}
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

	public float getParentEval() {
		return parent_eval;
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

	public void setEval(float eval) {
		this.eval = eval;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public void setParentEval(float parent_eval) {
		this.parent_eval = parent_eval;
	}
}