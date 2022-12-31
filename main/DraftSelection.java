package main;

public class DraftSelection {

    private DraftSlot draftSlot;
    private Player player;

    public DraftSelection(Player player, DraftSlot slot) {
        this.player = player;
        this.draftSlot = slot;
    }

    public DraftSelection(DraftSelection draftSelection) {
        this.player = draftSelection.getPlayer().clone();
        this.draftSlot = draftSelection.getDraftSlot().clone();
    }

    public DraftSlot getDraftSlot() {
        return draftSlot;
    }
    
    public Player getPlayer() {
        return player;
    }
}