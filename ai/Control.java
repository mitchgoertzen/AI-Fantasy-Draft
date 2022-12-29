package ai;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.Map;

import main.DraftMenu;
import main.Env;

public class Control {

    private boolean DEBUG = false;

    private Eval myEval;

    private int currentRound;
    private int roundLimit;

    private LeafComparator leafComparator;

    private ProblemState currentLeaf;
    private ProblemState root;

    private TreeSet<ProblemState> leaves;

    public Control(ProblemState root) {
        myEval = new Eval();
        if(DEBUG)
            System.out.println("env round: " + Env.getCurrentRound());
        currentRound = Env.getCurrentRound();
        roundLimit = Math.min(currentRound + 2, Env.getTotalRounds());
        this.leafComparator = new LeafComparator();
        this.currentLeaf = null;
        this.root = root;
        this.leaves = new TreeSet<>(this.leafComparator);
        this.leaves.add(root);
    }

    public void fleaf() {
        if (leaves.isEmpty()){
            return;
        }
        currentLeaf = leaves.pollFirst();
    }

    public void ftrans() {

        if (currentLeaf.discardLeaf()) {
               if(DEBUG){
                    System.out.println("discard");
               }
                    
                // if(Env.getCurrentPick() == 9){
                //     System.out.println("discard");
                // }
                currentLeaf = null;
                return;
        }

        
        roundLimit = Env.getCurrentRound() + 3;
        // if(Env.getCurrentPick() == 9){
        //     System.out.println("slots size: " + currentLeaf.getProblem().getDraftSlots().size());
        // }
        // if(Env.getCurrentPick() == 9){
        //     System.out.println("round: " + currentLeaf.getRound());
        // }
        //System.out.println(currentLeaf.getRound());
        if (currentLeaf.getRound() > roundLimit || currentLeaf.getProblem().getDraftSlots().isEmpty()){
            
        

             if(DEBUG){
                if(currentRound > roundLimit){
                
                    System.out.println("leaf round: " + currentLeaf.getRound());
                    System.out.println("current round: " + currentRound);
                
            }
             }
            //     System.out.println("empty");
            // if(Env.getCurrentPick() == 9){
            //     System.out.println("empty");
            // }
            if (!currentLeaf.isBestSolution()) {
                // if(Env.getCurrentPick() == 9){
                //     System.out.println("not best solutuion");
                // }
                //no solution found
                currentLeaf = null;
            }
            return;
        }

        SimulatePreviousPicks();

        ArrayList<Problem> subProblems = SearchModel.Div(currentLeaf.getProblem(), currentLeaf.getAi().getMaxDraftSlots(), currentLeaf.getRound(), currentLeaf.getAi().getId());
        // If the node cannot be divided into more leaves, discard it
        if (subProblems.isEmpty()) {
            // if(Env.getCurrentPick() == 9){
            //     System.out.println("no subproblems");
            // }
            currentLeaf = null;
            return;
        }

//        currentLeaf.nextRound();
        for (Problem subProblem : subProblems) {

            //System.out.println();
            ProblemState newLeaf =  myEval.evaluate(new ProblemState(subProblem, currentLeaf, currentLeaf.getAi())) ;

           // ArrayList<DraftSelection> newRoster = newLeaf.getProblem().getRoster();

            
            
            // float newEval  = myEval.evaluate(newLeaf.getParentEval(), newRoster.get(newRoster.size()-1).getPlayer(), newLeaf.getAi().getId());
            // newLeaf.setEval(newEval);
            
            // if(DEBUG){
            //     System.out.println("Drafting " + newRoster.get(newRoster.size()-1).getPlayer().getName() + " will add a score of " + newEval);
            //     System.out.println("\n");
            // }

         //   newLeaf.SimulateOpponentDraftPicks();
            newLeaf.nextRound();
            leaves.add(newLeaf);
        //    System.out.println();
            
        }
        currentRound++;
        currentLeaf = null;        
        return;
    }

    private void SimulatePreviousPicks() {

        if(currentLeaf.getRound() > Env.getCurrentRound()){
            //simulatePreviousPicksInRound();   
            int firstPick = ((currentLeaf.getRound() - 1) * Env.participants.size()) + 1;
            int j = firstPick + Env.participants.size();
            for(int i = firstPick; i < j; i++){
                if(Env.totalPicksInDraft.get(i - 1) == currentLeaf.getAi().getId()){
                 //   System.out.println("this participant already has the first pick in this round");
                    break;
                }

                currentLeaf.getProblem().nextPick();
              //  System.out.println("--sim previous picks in round--");
               // System.out.println("real current pick: " + Env.getCurrentPick());
               // System.out.println("current problem pick: " + prob.getCurrentPick());
               // System.out.println("# participants: " + Env.participants.size());
               // System.out.println("1st pick in round: " + (((round - 1) * Env.participants.size()) + 1));
    
               Iterator<Map.Entry<String,Float>> entry = DraftMenu.getPlayerScores().entrySet().iterator();
                for(int k = 0; k < currentLeaf.getProblem().getHighestScoreIndex(); k++){
                        entry.next();
                }


              //  Map.Entry<String,Float> next = currentLeaf.getProblem().getPlayerScores().entrySet().iterator().next();
                //String highestScorePlayer = next.getKey();
                String highestScorePlayer = entry.next().getKey();
                currentLeaf.getProblem().incrementHighestScoreIndex();
                if(DEBUG)
                    System.out.println("opponent has drafted: " + highestScorePlayer);
                currentLeaf.getProblem().addOpponentPlayer(Env.totalPicksInDraft.get(i - 1), highestScorePlayer);
                //currentLeaf.getProblem().playerScores.remove(highestScorePlayer);
                currentLeaf.getProblem().availablePlayers.remove(highestScorePlayer);
    
                
              //  System.out.println("in round " + round +" opponent has drafted: " + highestScorePlayer);
            }


        }
    }

    public ProblemState getCurrentLeaf() {
        return currentLeaf;
    }

    public ProblemState getRoot() {
        return root;
    }

    public TreeSet<ProblemState> getLeaves() {
        return leaves;
    }

}

class LeafComparator implements Comparator<ProblemState> {

    @Override
    public int compare(ProblemState node1, ProblemState node2) {
        if (node1.equals(node2))
            return 0;

        int result = Integer.compare(node1.getProblem().getDraftSlots().size(),
                        node2.getProblem().getDraftSlots().size());
        if (result == 0)
            result = Float.compare(node1.getEval(), node2.getEval());

        if (result == 0)
            result = Integer.compare(node1.hashCode(), node2.hashCode());
        return result;
    }

}
