package ai;

import java.util.ArrayList;
import main.DraftSlot;
import main.Env;

public class SearchModel {

    public static ArrayList<Problem> Div(Problem prob, int max, int round, int id) {

        DraftSlot nextDraftSlot = prob.nextDraftSlot();
        ArrayList<String> availablePlayers = prob.getAvailablePlayers();
        ArrayList<Problem> subProblems = new ArrayList<>();

        prob.nextPick();

        int availablePlayerScope = 50;
        for(String player: availablePlayers.subList(0, availablePlayerScope)) {
            Problem subProblem = new Problem(prob, max);
            if (subProblem.draftPlayer(Env.AllPlayers.get(player), nextDraftSlot, round));
                subProblems.add(subProblem);
            }
        return subProblems;
    }
    
}
