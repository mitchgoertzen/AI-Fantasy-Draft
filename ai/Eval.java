package ai;

public class Eval {

    private final boolean DEBUG = false;

	public ProblemState evaluate(ProblemState leaf) {

        float[] newEval = leaf.SimulateOpponentDraftPicks(leaf.getProblem().getStatLengths());

        leaf.setEval(newEval);

		return leaf;
	}
}