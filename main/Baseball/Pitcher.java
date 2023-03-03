package main.Baseball;

import main.Player;

public class Pitcher extends Player {

    private PitcherStats stats;

    public Pitcher(String id, String[] array) {
        super(id, array);
        stats = new PitcherStats(array);
    }

    public PitcherStats getCountingStats() {
        return stats;
    }
    
    @Override
    public Pitcher clone() {
        return (Pitcher) super.clone();
    }

    public void setCountingStats(PitcherStats stats) {
        this.stats = stats;
    }
    
}
