package main;

public class DraftSelection {
    Player player;
    DraftSlot draftSlot;

    public DraftSelection(Player player, DraftSlot slot) {
        this.player = player;
        this.draftSlot = slot;
    }

    public DraftSelection(DraftSelection draftSelection) {
        this.player = draftSelection.getPlayer().clone();
        this.draftSlot = draftSelection.getDraftSlot().clone();
    }
    
    public Player getPlayer() {
        return player;
    }

    public DraftSlot getDraftSlot() {
        return draftSlot;
    }
}
