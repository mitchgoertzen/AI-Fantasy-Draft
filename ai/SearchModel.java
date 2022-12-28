package ai;

import java.util.ArrayList;
import java.util.Map;

import main.DraftSlot;
import main.Env;

public class SearchModel {
    public static ArrayList<Problem> Div(Problem prob, int max, int round, int id) {
       // System.out.println("Div problem round: " + round);
        //System.out.println("Env.currentRound : " + Env.getCurrentRound());
       

        if(round > Env.getCurrentRound()){
            //simulatePreviousPicksInRound();   
            int firstPick = ((round - 1) * Env.participants.size()) + 1;
            int j = firstPick + Env.participants.size();
            for(int i = firstPick; i < j; i++){
                if(Env.totalPicksInDraft.get(i - 1) == id){
                 //   System.out.println("this participant already has the first pick in this round");
                    break;
                }

                prob.nextPick();
              //  System.out.println("--sim previous picks in round--");
               // System.out.println("real current pick: " + Env.getCurrentPick());
               // System.out.println("current problem pick: " + prob.getCurrentPick());
               // System.out.println("# participants: " + Env.participants.size());
               // System.out.println("1st pick in round: " + (((round - 1) * Env.participants.size()) + 1));
    
                Map.Entry<String,Float> entry = prob.playerScores.entrySet().iterator().next();
                String highestScorePlayer = entry.getKey();
                prob.playerScores.remove(highestScorePlayer);
                prob.availablePlayers.remove(highestScorePlayer);
    
                
              //  System.out.println("in round " + round +" opponent has drafted: " + highestScorePlayer);
            }


        }

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
