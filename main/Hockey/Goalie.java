package main.Hockey;

import main.Player;

public class Goalie extends Player {

    private GoalieCountingStats countingStats;

    public Goalie(String id, String[] info, String[] array) {
        super(id, info);
        countingStats = new GoalieCountingStats(array);
    }

    public GoalieCountingStats getCountingStats() {
        return countingStats;
    }
    
    @Override
    public Skater clone() {
        return (Skater) super.clone();
    }

    public void setCountingStats(GoalieCountingStats countingStats) {
        this.countingStats = countingStats;
    }
    
}
