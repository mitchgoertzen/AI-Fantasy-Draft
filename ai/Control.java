package ai;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

import main.Env;

public class Control {

    private boolean DEBUG = true;

    private Eval myEval;

    private int currentRound;
    private int roundLimit;

    private LeafComparator leafComparator;

    private ProblemState currentLeaf;
    private ProblemState root;

    private TreeSet<ProblemState> leaves;

    public Control(ProblemState root) {
        myEval = new Eval();
        currentRound = Env.getCurrentRound();
        roundLimit = 3;
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
                if(DEBUG)
                currentLeaf = null;
                return;
        }

        //System.out.println(currentLeaf.getRound());
        if (currentRound > roundLimit || currentLeaf.getProblem().getDraftSlots().isEmpty()){
            if (!currentLeaf.isBestSolution()) {
                //no solution found
                currentLeaf = null;
            }
            return;
        }

        ArrayList<Problem> subProblems = SearchModel.Div(currentLeaf.getProblem(), currentLeaf.getAi().getMaxDraftSlots(), currentLeaf.getRound(), currentLeaf.getAi().getId());
        // If the node cannot be divided into more leaves, discard it
        if (subProblems.isEmpty()) {
            currentLeaf = null;
            return;
        }

        
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
