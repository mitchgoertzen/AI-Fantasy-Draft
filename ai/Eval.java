package ai;

public class Eval {

    private final boolean DEBUG = false;

	public ProblemState evaluate(ProblemState leaf) {

        int[] newEval = leaf.SimulateOpponentDraftPicks();

        leaf.setEval(newEval);

		return leaf;
	}
}