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

    private final boolean DEBUG = false;
    private final boolean DEBUG_VERBOSE = false;

	private AIParticipant ai;

	private Constr myConstr;

	private int[] eval;
	private int[] parent_eval;
    // private int[] currentRoundEval;
    // private int[] maxCurrentRoundEval;

	private int round;

	private Map<Integer, Boolean> draftedPlayerCombinations;

    private Problem problem;

	public ProblemState(Problem problem, ProblemState parent, AIParticipant ai) {
		round = Env.getCurrentRound();
        myConstr = new Constr();
		eval = new int[3];
		// currentRoundEval = new int[3];
		// maxCurrentRoundEval = new int[3];
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
		// if(parent_eval != null){
		// 	System.out.println("parent eval before: ");
		// 	System.out.println(parent_eval[0]);
		// 	System.out.println(parent_eval[1]);
		// 	System.out.println(parent_eval[2]);
		// }

		// int[] temp_eval = new int[3];
		// for(int k = 0; k < 3; k++){
		// 	temp_eval[k] = parent.getEval()[k];
		// }

         parent_eval = parent.getEval().clone();
		// System.out.println("parent eval after: ");
		// System.out.println(parent_eval[0]);
		// System.out.println(parent_eval[1]);
		// System.out.println(parent_eval[2]);
		// System.out.println("parent get eval after: ");
		// System.out.println(parent.getEval()[0]);
		// System.out.println(parent.getEval()[1]);
		// System.out.println(parent.getEval()[2]);
		// System.out.println("temp eval after: ");
		// System.out.println(temp_eval[0]);
		// System.out.println(temp_eval[1]);
		// System.out.println(temp_eval[2]);
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




		if (eval[0] > ai.getMaxScore()[0]) {
			ai.setMaxScore(eval);
			//maxCurrentRoundEval = currentRoundEval;
			if(DEBUG){
				System.out.println("isBestSolution");
				System.out.println(eval[0]);
				System.out.println(eval[1]);
				System.out.println(eval[2]);
			}
			return true;
		}else if(eval[0] == ai.getMaxScore()[0]){
			if(eval[1] > ai.getMaxScore()[1]){
				ai.setMaxScore(eval);
				//maxCurrentRoundEval = currentRoundEval;
				if(DEBUG){
					System.out.println("isBestSolution");
					System.out.println(eval[0]);
					System.out.println(eval[1]);
					System.out.println(eval[2]);
				}
				return true;
			}else if(eval[1] == ai.getMaxScore()[1]){
				if(eval[2] > ai.getMaxScore()[2]){
					ai.setMaxScore(eval);
				//	maxCurrentRoundEval = currentRoundEval;
					if(DEBUG){
						System.out.println("isBestSolution");
						System.out.println(eval[0]);
						System.out.println(eval[1]);
						System.out.println(eval[2]);
					}
					return true;
				}
			}
		}
		return false;
	}
			//  if(DEBUG_VERBOSE){
			// 	System.out.println("eval: " + eval);
			// 	System.out.println("max: " +  ai.getMaxScore());
			// 	System.out.println("-----");
			// 	for(String s : problem.getDraftedPlayers()){
			// 		System.out.println(s);
			// 	}
			// 	System.out.println("-----");
			//  }

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
			if(DEBUG_VERBOSE)
				System.out.println("player combo");
			return true;
		}

		draftedPlayerCombinations.put(hashCode, true);

		return false;
	}
	
    public void nextRound() {
		round++;
	}

	public int[] SimulateOpponentDraftPicks(){


		int totalWins = 0;
		int weeklyPoints = 0;
		int cumulativeScore = 0;
		/*
		passed value float[][] rosterScore
		float[][] skaterCumulativeWeeklyScore = new float[15][2]; --> do not need?
		*/

		if(DEBUG_VERBOSE){
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

			int firstPick = ((round - 1)* Env.participants.size()) + 1;
			int j = round * Env.participants.size();
			int currentPick = problem.getCurrentPick() - 1;
			for(int i = firstPick; i <= j; i++){


				if(i > currentPick){
					if(DEBUG_VERBOSE){
						System.out.println("simming picks");
						System.out.println("current simulated pick: " + i);
						System.out.println("--sim opponent's next pick in round--");
					}

					Iterator<Map.Entry<String,Float>> entry = DraftMenu.getPlayerScores().entrySet().iterator();
					//TODO: next player needs to fit under the position limit of the opponent picking
					for(int k = 0; k < problem.getHighestScoreIndex(); k++){
						 entry.next();
					}  

					String highestScorePlayer = entry.next().getKey();
					String[] roster = problem.getDraftedPlayers();
					int length = roster.length;
					for(int k = 0; k < length; k++){
						if(roster[k] == highestScorePlayer){
							if(DEBUG_VERBOSE)
								System.out.println("recent draft pick is highest score player");
							problem.incrementHighestScoreIndex();
							highestScorePlayer = entry.next().getKey();
							k = -1;
						}
					}

					if(DEBUG_VERBOSE)
						System.out.println("opponent will be drafting: " + highestScorePlayer);

					problem.removeAvailablePlayer(highestScorePlayer);
					problem.incrementHighestScoreIndex();
					problem.addOpponentPlayer(Env.totalPicksInDraft.get(i - 1), highestScorePlayer);
					problem.updateOpponentRosterScore(Env.totalPicksInDraft.get(i - 1), Env.AllPlayers.get(highestScorePlayer));
					problem.nextPick();


				}

				if(i != currentPick){
					
					int currentPoints = 0;
					int opponentPoints = 0;
					if(DEBUG){
						System.out.println("Roster of Participant " + Env.getTotalPicksInDraft().get(i-1));
						for(String s : problem.getOpponentRosters()[Env.totalPicksInDraft.get(i - 1)]){
							System.out.println(s);
						}
						System.out.println();
					}
					//System.out.println("comparing roster scores...");
					int oppID = Env.totalPicksInDraft.get(i - 1);
					for(int k = 0; k < 25; k++){
						cumulativeScore += problem.getActiveRosterScore()[k];
						if(Float.compare(problem.getActiveRosterScore()[k], problem.getOpponentRosterScores()[oppID][k]) > 0 ){
							currentPoints++;
							weeklyPoints++;
						}
						else if(Float.compare(problem.getActiveRosterScore()[k], problem.getOpponentRosterScores()[oppID][k]) < 0 )
							opponentPoints++;
					}

					if(currentPoints > opponentPoints)
						totalWins++;

				}
			}
			eval[0] = totalWins;
			eval[1] = weeklyPoints;
			eval[2] = cumulativeScore;

			// System.out.println("round: " + round);
			// System.out.println("env round: " + Env.getCurrentRound());

			// if(round == Env.getCurrentRound())
			// 	currentRoundEval = eval.clone();

			if(DEBUG){			 
			System.out.println("this roster will result in " + totalWins + " win(s), " + weeklyPoints + " weekly point(s)"+ ", and a roster score of: " + cumulativeScore);
			System.out.println();
			}

		}

		return eval;
	}

	public AIParticipant getAi() {
		return ai;
	}

	public Constr getConstr() {
		return myConstr;
	}

	public int[] getEval() {
		return eval;
	}

	public int[] getParentEval() {
		return parent_eval;
	}

	// public int[] getMaxCurrentRoundEval() {
	// 	return maxCurrentRoundEval;
	// }
	
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

		if(DEBUG_VERBOSE){
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

	public void setEval(int[] eval) {
		this.eval = eval;
		// System.out.println("set eval");
        // for(int k = 0; k < 3; k++){
        //     System.out.println(this.eval[k]);
		// }
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public void setParentEval(int[] parent_eval) {
		this.parent_eval = parent_eval;
	}
}