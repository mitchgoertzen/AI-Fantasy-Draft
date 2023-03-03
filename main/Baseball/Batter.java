package main.Baseball;

import main.Player;

public class Batter extends Player {

    private BatterStats stats;

    public Batter(String id, String[] array) {
        super(id, array);
        stats = new BatterStats(array);
    }

    public BatterStats getStats() {
        return stats;
    }
    
    @Override
    public Batter clone() {
        return (Batter) super.clone();
    }

    public void setCountingStats(BatterStats stats) {
        this.stats = stats;
    }
    
}
