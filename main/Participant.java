package main;
public class Participant {

    private boolean isHuman;

    private int draftNumber;
    private int id;
    
    private Roster roster;

    private String name;
    private String[] bench;
    private String[] goalies;
    private String[] injuredReserve;
    private String[] notActive;
    private String[] skaters;

    public Participant(int id, boolean isHuman, int draftNumber){
        this.id = id;
        this.isHuman = isHuman;
        this.draftNumber = draftNumber;
        name = "Participant " + this.id;
        roster = new Roster();
    }

    public boolean isHuman() {
        return isHuman;
    }

    public void addPlayer(String p){
        roster.addPlayer(p);
    }

    //Getters
    public int getId() {
        return id;
    }    
    
    public int getDraftNumber() {
        return draftNumber;
    }

    public Roster getRoster() {
        return roster;
    }

    public String getName() {
        return name;
    }

    public String[] getBench() {
        return bench;
    }  

    public String[] getGoalies() {
        return goalies;
    }

    public String[] getInjuredReserve() {
        return injuredReserve;
    }

    public String[] getNotActive() {
        return notActive;
    } 

    public String[] getSkaters() {
        return skaters;
    }

    //Setters
    public void setBench(String[] bench) {
        this.bench = bench;
    }

    public void setDraftNumber(int draftNumber) {
        this.draftNumber = draftNumber;
    }
    
    public void setGoalies(String[] goalies) {
        this.goalies = goalies;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setInjuredReserve(String[] injuredReserve) {
        this.injuredReserve = injuredReserve;
    }
    
    public void setIsHuman(boolean isHuman) {
        this.isHuman = isHuman;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNotActive(String[] notActive) {
        this.notActive = notActive;
    }

    public void setSkaters(String[] skaters) {
        this.skaters = skaters;
    }
}