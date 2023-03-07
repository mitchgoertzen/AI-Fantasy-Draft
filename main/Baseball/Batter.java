package main.Baseball;

import main.Player;

public class Batter extends Player {

    private BattingStats stats;

    public Batter(String id, String[] array, String[] info, boolean fielding) {
        super(id, info);
        stats = new BattingStats(array, fielding);
    }

    public BattingStats getStats() {
        return stats;
    }

    public void addFieldingStats(String[] array, int year){
        stats.addFieldingStats(array, year);
    }

    public void addBattingStats(String[] array, int year){
        stats.addBattingStats(array, year);
    }
    
    @Override
    public Batter clone() {
        return (Batter) super.clone();
    }

    public void setCountingStats(BattingStats stats) {
        this.stats = stats;
    }
    
}
