package main.Baseball;

import main.Player;

public class Pitcher extends Player {

    private PitchingStats stats;

    public Pitcher(String id, String[] array, String[] info, boolean fielding) {
        super(id, info);
        stats = new PitchingStats(array);
    }

    public PitchingStats getStats() {
        return stats;
    }

    public void addPitchingStats(String[] array, int year){
        countYears();
        stats.addPitchingStats(array, year);
    }
    
    @Override
    public Pitcher clone() {
        return (Pitcher) super.clone();
    }

    public void setStats(PitchingStats stats) {
        this.stats = stats;
    }

    public void printInfo(){
        super.printInfo();
        System.out.printf("\n");

        // System.out.printf("GS: %.0f, PA: %.0f, AB: %.0f, R: %.0f\n" +
        // "H: %.0f, 1B: %.0f, 2B: %.0f,3B: %.0f, HR: %.0f, RBI: %.0f, XBH: %.0f, TB: %.0f\n" +
        // "BA: %.3f, OBP: %.3f, SLG: %.3f, OPS: %.3f,\n" /*OPS+ */ +
        // "K: %.0f, BB: %.0f, IBB: %.0f, HBP: %.0f, GIDP: %.0f\n" +
        // "SH: %.0f, SF: %.0f, SB: %.0f, CS: %.0f, SB%% %.2f, NetSB: %.0f\n" +
        // "PO: %.0f , A: %.0f, E: %.0f, F%%: %.2f, DPT: %.0f\n\n"
        // , stats.getStats()[0], stats.getStats()[31], stats.getStats()[1], stats.getStats()[2], stats.getStats()[3]
        // , stats.getStats()[4], stats.getStats()[5], stats.getStats()[6], stats.getStats()[7], stats.getStats()[8]
        // , stats.getStats()[27], stats.getStats()[18], stats.getStats()[23], stats.getStats()[24], stats.getStats()[25]
        // , stats.getStats()[26], stats.getStats()[16], stats.getStats()[13], stats.getStats()[14], stats.getStats()[15]
        // , stats.getStats()[17], stats.getStats()[9], stats.getStats()[10], stats.getStats()[11], stats.getStats()[12]
        // , stats.getStats()[29], stats.getStats()[28], stats.getStats()[19], stats.getStats()[20], stats.getStats()[21]
        // , stats.getStats()[22], stats.getStats()[34]);
    }
}
