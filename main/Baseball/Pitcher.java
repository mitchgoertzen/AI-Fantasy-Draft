package main.Baseball;

import main.Player;

public class Pitcher extends Player {

    private PitchingStats pitchingStats;

    public Pitcher(String id, String[] array, String[] info, boolean fielding) {
        super(id, info);
        pitchingStats = new PitchingStats(array, fielding);
    }

    public PitchingStats getCountingStats() {
        return pitchingStats;
    }

    public PitchingStats getBattingStats() {
        return pitchingStats;
    }
    
    @Override
    public Pitcher clone() {
        return (Pitcher) super.clone();
    }

    public void setCountingStats(PitchingStats stats) {
        this.pitchingStats = stats;
    }
    
}
