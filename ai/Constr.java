package ai;

import main.AIParticipant;
import main.Env;
import main.Player;

public class Constr {
    private final boolean DEBUG = false;

    public boolean meetsConstraints(Problem prob, AIParticipant ai, int round) {
        
        String[] roster = prob.getDraftedPlayers();

        if (roster[0] == null || roster == null || roster.length == 0)
            return true;
        if (prob.getRoster() == null || prob.getRoster().size() == 0)
            return true;

        //pick from previous round(- 1), convert round number to index (- 1)
        int previousPickIndex = round - 2;
        String newSelection = roster[previousPickIndex];

        if (Env.playerDrafted.containsKey(newSelection) && Env.playerDrafted.get(newSelection)) {
            if(DEBUG)
                System.out.println(Env.AllPlayers.get(newSelection).getName() + " has been drafted by someone else");
            return false;
        }
        
        Player currentPlayer;

        //checking if player has previously been drafted
        for(int i = 0; i < previousPickIndex; i++){

            currentPlayer = Env.AllPlayers.get(roster[i]);
        
            if(newSelection.equals(roster[i])){
                if(DEBUG)
                    System.out.println("player drafted by this ai");
                return false;
            }
           
        }

        currentPlayer = Env.AllPlayers.get(newSelection);

        //index to be used for currentPlayer's position
        int positionIndex = Env.getPositionIndex(currentPlayer.getPosition(), ai.getPositionCounts());

        //checking forward position limit
        if(positionIndex < 3){

            int forwardCount = ai.getPositionCounts()[0] + ai.getPositionCounts()[1] + ai.getPositionCounts()[2];

            if(forwardCount + 1> Env.getPositionLimits()[5])
                return false;
            
        //checking individual position limit
        } else if(ai.getPositionCounts()[positionIndex] + 1 > Env.getPositionLimits()[positionIndex]){

            if(DEBUG){
                System.out.println("Player pos: " + currentPlayer.getPosition());
                System.out.println("index: " + positionIndex);
                System.out.println("count: " + ai.getPositionCounts()[positionIndex]);
                System.out.println("limit: " + Env.getPositionLimits()[positionIndex]);
                System.out.println("limit for: " + currentPlayer.getPosition());
            }
            return false;
        }
        return true;
    }
}
