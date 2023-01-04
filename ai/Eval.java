package ai;

import java.util.ArrayList;

import main.DraftSelection;


public class Eval {

    private final boolean DEBUG = false;

	public ProblemState evaluate(ProblemState leaf) {
        
        ArrayList<DraftSelection> newRoster = leaf.getProblem().getRoster();
        //Player newPlayer = newRoster.get(newRoster.size()-1).getPlayer();

        //int[] eval = leaf.getParentEval().clone();

        
		// System.out.println("new leaf eval");
        // for(int k = 0; k < 3; k++){
        //     System.out.println(eval[k]);
		// }
		

        // int[] temp_eval = new int[3];
		// for(int k = 0; k < 3; k++){
		// 	temp_eval[k] = leaf.getParentEval()[k];
		// }

        
		// System.out.println("temp eval: ");
		// System.out.println(temp_eval[0]);
		// System.out.println(temp_eval[1]);
		// System.out.println(temp_eval[2]);
    
       // eval += Env.PlayerScores.get(newPlayer.getID());

        int[] newEval = leaf.SimulateOpponentDraftPicks();

        // System.out.println("newEval");
        // for(int k = 0; k < 3; k++){
        //     System.out.println(newEval[k]);
        //     //eval[k] += newEval[k];
		// }

        // System.out.println("added eval");
        // for(int k = 0; k < 3; k++){
        //     System.out.println(eval[k]);
		// }

        leaf.setEval(newEval);

        if(DEBUG){
            System.out.println("Drafting " + newRoster.get(newRoster.size()-1).getPlayer().getName() + " will add a score of " + newEval);
            System.out.println("\n");
        }

		return leaf;
	}
}