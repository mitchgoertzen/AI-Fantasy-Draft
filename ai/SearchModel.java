package ai;

import java.util.ArrayList;
import main.DraftSlot;
import main.Env;

public class SearchModel {
    public static ArrayList<Problem> Div(Problem prob, int max, int round, int id) {
       // System.out.println("Div problem round: " + round);
        //System.out.println("Env.currentRound : " + Env.getCurrentRound());
       



        DraftSlot nextDraftSlot = prob.nextDraftSlot();
        ArrayList<String> availablePlayers = prob.getAvailablePlayers();
        ArrayList<Problem> subProblems = new ArrayList<>();

        prob.nextPick();
        for(String player: availablePlayers) {
            Problem subProblem = new Problem(prob, max);
            //if element is able to be added to the slot
            if (subProblem.draftPlayer(Env.AllPlayers.get(player), nextDraftSlot, round));
                subProblems.add(subProblem);
            }

        return subProblems;
    }
}
