package ai;

import java.util.ArrayList;

import main.AIParticipant;
import main.DraftSlot;
import main.Env;

public class SearchModel {

    private static boolean DEBUG = false;
    private static boolean limit = false;

    public static ArrayList<Problem> Div(Problem prob, int max, int round, AIParticipant ai) {

        DraftSlot nextDraftSlot = prob.nextDraftSlot();
        ArrayList<String> availablePlayers = prob.getAvailablePlayers();
        ArrayList<Problem> subProblems = new ArrayList<>();
       
        prob.nextPick();

        ArrayList<String> availablePlayersByPosition = new ArrayList<>();

        int posIndex = -1;
        for(String p : availablePlayers){
            posIndex = Env.getBaseballPositionIndex(Env.AllPlayers.get(p).getPosition());
            // if(DEBUG){
            //     System.out.println("pos: " + Env.AllPlayers.get(p).getPosition());
            //     System.out.println("posIndex: " + posIndex);
            // }
            if(ai.getPositionDraftEligible(posIndex)){
                availablePlayersByPosition.add(p);
            }else{
                if(DEBUG){
                    limit = true;
                    System.out.println("pos not eligible: " + posIndex);
                }
            }
        }

        if(limit){
            limit = false;
            System.out.println("\n\n");
        }

        int availablePlayerScope = Math.min(50, availablePlayersByPosition.size());
        for(String player: availablePlayersByPosition.subList(0, availablePlayerScope)) {
            int[] lengths = prob.getStatLengths();
            Problem subProblem = new Problem(prob, max, lengths[0], lengths[1]);
            if (subProblem.draftPlayer(Env.AllPlayers.get(player), nextDraftSlot, round));
                subProblems.add(subProblem);
            }
        return subProblems;
    }   
}