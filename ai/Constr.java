package ai;

import main.AIParticipant;
import main.Env;
import main.Player;

public class Constr {

    public boolean meetsConstraints(Problem prob, AIParticipant ai, int round) {
        String[] roster = prob.getDraftedPlayers();
        if (roster[0] == null || roster == null || roster.length == 0)
            return true;
        if (prob.getRoster() == null || prob.getRoster().size() == 0)
            return true;

        int lastIndex = roster.length - 1;
        int s = prob.getRoster().size() - 1;

        String newSelection = prob.getRoster().get(s).getPlayer().getID();

        //save current round somehwerwe and use it for array index

    //    System.out.println(prob.getRoster().get(s).getPlayer().getID());
    //    System.out.println("size: " + prob.getRoster().size());

        // System.out.println("\n---");
        // for(int i = 0; i < roster.length; i++){
        //     System.out.println(roster[i]);
        // }
        // System.out.println("---\n");
        for(int i = 0; i < roster.length; i++){
            if(roster[i] == null){
                lastIndex = i - 1;
                break;
            }
        }
      //  System.out.println("round: " + round);
        
        //System.out.println("lastIndex: " + lastIndex);

        if (Env.playerDrafted.containsKey(newSelection) && Env.playerDrafted.get(newSelection)) {
            System.out.println(Env.AllPlayers.get(newSelection).getName() + " has been drafted by someone else");
            return false;
        }
        
        Player currentPlayer;
        for(int i = 0; i < lastIndex; i++){

            currentPlayer = Env.AllPlayers.get(roster[i]);
        
            if(newSelection.equals(roster[i])){
           //     System.out.println("player drafted by this ai");
                return false;
            }
           
        }

        currentPlayer = Env.AllPlayers.get(newSelection);

        int index = Env.getPositionIndex(currentPlayer.getPosition(), ai.getPositionCounts());

        if(ai.getPositionCounts()[index] + 1 > Env.getPositionLimits()[index]){
          //  System.out.println("limit for: " + currentPlayer.getPosition());
            return false;
        }


        // int lw = ai.getPositionCounts()[0];
        // int rw = ai.getPositionCounts()[1];
        // int c = ai.getPositionCounts()[2];
        // int d = ai.getPositionCounts()[3];
        // int g = ai.getPositionCounts()[4];
        
        // switch(currentPlayer.getPosition()){
        //     case "LW":{
        //         if(lw + 1 > Env.getPositionLimits()[0]){
        //         //    System.out.println("lw limit reached");
        //             return false; 
        //         }
        //     }
        //     break;
        //     case "RW":{
        //         if(rw + 1 > Env.getPositionLimits()[1]){
        //             //System.out.println("rw limit reached");
        //             return false; 
        //         }
        //     }
        //     break;
        //     case "C":{
        //         if(c + 1 > Env.getPositionLimits()[2]){
        //           //  System.out.println("c limit reached");
        //             return false; 
        //         }
        //     }
        //     break;
        //     case "F":{
        //         if(lw < rw){
        //             if(lw < c){
        //                 if(lw + 1 > Env.getPositionLimits()[0]){
        //                //     System.out.println("lw limit reached");
        //                     return false; 
        //                 }
        //             }
        //             else{
        //                 if(c > Env.getPositionLimits()[2]){
        //                  //   System.out.println("c limit reached");
        //                     return false; 
        //                 }
        //             }
        //         }else{
        //             if(rw < c){
        //                 if(rw + 1 > Env.getPositionLimits()[1]){
        //           //          System.out.println("rw limit reached");
        //                     return false; 
        //                 }
        //             }
        //             else{
        //                 if(c + 1 > Env.getPositionLimits()[2]){
        //                 //    System.out.println("c limit reached");
        //                     return false; 
        //                 }
        //             }
        //         }
        //     }
        //     break;
        //     case "W":{
        //         if(rw < lw){
        //             if(rw + 1 > Env.getPositionLimits()[1]){
        //       //          System.out.println("rw limit reached");
        //                 return false; 
        //             }
        //         }else{
        //             if(c + 1 > Env.getPositionLimits()[2]){
        //          //       System.out.println("c limit reached");
        //                 return false; 
        //             }
        //         }
        //     }
        //     break;
        //     case "D":{
        //         if(d + 1 > Env.getPositionLimits()[3]){
        //          //   System.out.println("d limit reached");
        //             return false; 
        //         }
        //     }
        //     break;
        //     case "G":{
        //         if(g + 1 > Env.getPositionLimits()[4]){
        //             //System.out.println("g limit reached");
        //             return false; 
        //         }
        //     }
        //     break;
        //     default:
        //         System.out.println("this position (" + currentPlayer.getPosition() + ") does not exist");
        //     break;
        // }

        return true;
    }
}
