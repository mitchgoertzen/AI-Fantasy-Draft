package ai;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.Map;

import main.DraftMenu;
import main.Env;

public class Control {

    private final boolean DEBUG = false;

    private Eval myEval;

    private int currentRound;
    private int roundLimit;

    private LeafComparator leafComparator;

    private ProblemState currentLeaf;
    private ProblemState root;

    private TreeSet<ProblemState> leaves;

    public Control(ProblemState root) {
        myEval = new Eval();
        leafComparator = new LeafComparator();
        currentLeaf = null;
        this.root = root;
        leaves = new TreeSet<>(this.leafComparator);
        leaves.add(root);
        
        if(DEBUG)
            System.out.println("env round: " + Env.getCurrentRound());
        currentRound = Env.getCurrentRound();
        roundLimit = Math.min(currentRound + 2, Env.getTotalRounds());
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
                    System.out.println("discard");
                currentLeaf = null;
                return;
        }

        if(DEBUG)
            System.out.println(currentLeaf.getRound());

        roundLimit = Env.getCurrentRound() + 3;
        if (currentLeaf.getRound() > roundLimit || currentLeaf.getProblem().getDraftSlots().isEmpty()){
            
             if(DEBUG){
                if(currentRound > roundLimit){
                    System.out.println("leaf round: " + currentLeaf.getRound());
                    System.out.println("current round: " + currentRound);    
                }
             }

            if (!currentLeaf.isBestSolution()) {
                if(DEBUG)
                    System.out.println("not best solution");
                currentLeaf = null;
            }
            return;
        }

        SimulatePreviousPicks();

        ArrayList<Problem> subProblems = SearchModel.Div(currentLeaf.getProblem(), currentLeaf.getAi().getMaxDraftSlots(), currentLeaf.getRound(), currentLeaf.getAi().getId());
        
        // If the node cannot be divided into more leaves, discard it
        if (subProblems.isEmpty()) {
            if(DEBUG)
                System.out.println("no subproblems");
            
            currentLeaf = null;
            return;
        }

        for (Problem subProblem : subProblems) {
            ProblemState newLeaf =  myEval.evaluate(new ProblemState(subProblem, currentLeaf, currentLeaf.getAi())) ;
            newLeaf.nextRound();
            leaves.add(newLeaf);
        }

        currentRound++;
        currentLeaf = null;        
        return;
    }

    private void SimulatePreviousPicks() {

        if(currentLeaf.getRound() > Env.getCurrentRound()){
            int firstPick = ((currentLeaf.getRound() - 1) * Env.participants.size()) + 1;
            int j = firstPick + Env.participants.size();
            for(int i = firstPick; i < j; i++){
                if(Env.totalPicksInDraft.get(i - 1) == currentLeaf.getAi().getId()){
                    if(DEBUG)
                        System.out.println("this participant already has the first pick in this round");
                    break;
                }

                currentLeaf.getProblem().nextPick();

                if(DEBUG){
                    System.out.println("--sim previous picks in round--");
                    System.out.println("real current pick: " + Env.getCurrentPick());
                    System.out.println("current problem pick: " + currentLeaf.getProblem().getCurrentPick());
                    System.out.println("# participants: " + Env.participants.size());
                    System.out.println("1st pick in round: " + (((currentLeaf.getRound() - 1) * Env.participants.size()) + 1));
                }
                Iterator<Map.Entry<String,Float>> entry = DraftMenu.getPlayerScores().entrySet().iterator();
                int highestScoreIndex = currentLeaf.getProblem().getHighestScoreIndex();

                for(int k = 0; k < highestScoreIndex; k++){
                        entry.next();
                }

                String highestScorePlayer = entry.next().getKey();
                String[] roster = currentLeaf.getProblem().getDraftedPlayers();

                int length = roster.length;

                for(int k = 0; k < length; k++){
                    if(roster[k] == highestScorePlayer){
                            currentLeaf.getProblem().incrementHighestScoreIndex();
                        highestScorePlayer = entry.next().getKey();
                        k = -1;
                    }
                }



                if(DEBUG)
                    System.out.println("opponent has drafted: " + highestScorePlayer);

                currentLeaf.getProblem().incrementHighestScoreIndex();
                currentLeaf.getProblem().addOpponentPlayer(Env.totalPicksInDraft.get(i - 1), highestScorePlayer);
                currentLeaf.getProblem().removeAvailablePlayer(highestScorePlayer);
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
            result = Integer.compare(node1.getEval()[0], node2.getEval()[0]);

        if (result == 0)
            result = Integer.compare(node1.hashCode(), node2.hashCode());
        return result;
    }

}
