package main;

public class DraftSlot implements Cloneable {

    int type;
    int number;

    public DraftSlot(int number) {
        this.number = number;
    }  
    
    public int getType() {
        return type;
    }   

    public int getNumber() {
        return number;
    }   
    
    public void setType(int type) {
        this.type = type;
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
}
