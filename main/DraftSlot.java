package main;

public class DraftSlot implements Cloneable {

    private int number;
    private int type;

    public DraftSlot(int number) {
        this.number = number;
    }  
    
    @Override
    public DraftSlot clone() {
        DraftSlot clone = null;
        try {
            clone = (DraftSlot) super.clone();
        } catch (CloneNotSupportedException e) {
            
        }
        return clone;
    }

    public int getNumber() {
        return number;
    } 
    
    public int getType() {
        return type;
    }     
    
    public void setType(int type) {
        this.type = type;
    }
}
