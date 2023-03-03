package main.Hockey;

import main.Player;

public class Skater extends Player{

    private SkaterCountingStats countingStats;

    public Skater(String id, String[] array) {
        super(id, array);
        countingStats = new SkaterCountingStats(array);
    }
    
    @Override
    public Skater clone() {
        return (Skater) super.clone();
    }
    
    public SkaterCountingStats getCountingStats() {
        return countingStats;
    }

    public void setCountingStats(SkaterCountingStats countingStats) {
        this.countingStats = countingStats;
    }
}
