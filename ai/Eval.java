package ai;

import java.util.ArrayList;

import main.DraftSelection;
import main.Env;
import main.Player;


public class Eval {

    private boolean DEBUG = false;

	public ProblemState evaluate(ProblemState leaf) {
        
        ArrayList<DraftSelection> newRoster = leaf.getProblem().getRoster();
        Player newPlayer = newRoster.get(newRoster.size()-1).getPlayer();
        
        //float[][] evalA = leaf.getParentEval(); //(ie.float[1][1]) --> [wins][weeklyRosterScore]

        float eval = leaf.getParentEval();
        eval += Env.PlayerScores.get(newPlayer.getID());
        leaf.setEval(eval);

        leaf.SimulateOpponentDraftPicks();//pass evalA

        //int[2] = wins, points
        //add player to current roster
        //for each week in season
        //compare current roster to opponents' roster
        //count each category won per week, 1 win = 1 point
        //return cumulative points, and wins vs opponents to be used as tie breaker

        if(DEBUG){
            System.out.println("Drafting " + newRoster.get(newRoster.size()-1).getPlayer().getName() + " will add a score of " + eval);
            System.out.println("\n");
        }

		return leaf;
	}
}