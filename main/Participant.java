package main;
public class Participant {
    private int id;
    private boolean isHuman;
    private int draftNumber;
    private String name;
    private Roster roster;
    private String[] skaters;
    private String[] goalies;
    private String[] injuredReserve;
    private String[] notActive;
    private String[] bench;

    public Participant(int id, boolean isHuman, int draftNumber){
        this.id = id;
        this.isHuman = isHuman;
        this.draftNumber = draftNumber;
        name = "Participant " + this.id;
        roster = new Roster();
    }

    public void addPlayer(String p){
        //System.out.println("particpant " + id + " has just added " + p);
        roster.addPlayer(p);
    }


    public boolean isHuman() {
        return isHuman;
    }

    public int getId() {
        return id;
    }    
    
    public int getDraftNumber() {
        return draftNumber;
    }

    public String getName() {
        return name;
    }

    public Roster getRoster() {
        return roster;
    }

    public String[] getSkaters() {
        return skaters;
    }

    public String[] getInjuredReserve() {
        return injuredReserve;
    }

    public String[] getNotActive() {
        return notActive;
    }

    public String[] getBench() {
        return bench;
    }

    public void setId(int id) {
        this.id = id;
    }   
    
    public void setIsHuman(boolean isHuman) {
        this.isHuman = isHuman;
    }

    public void setDraftNumber(int draftNumber) {
        this.draftNumber = draftNumber;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    // public void addPlayerToRoster(String[] roster) {
    //     this.roster = roster;
    // }

    public void setSkaters(String[] skaters) {
        this.skaters = skaters;
    }
    
    public void setGoalies(String[] goalies) {
        this.goalies = goalies;
    }

    public void setInjuredReserve(String[] injuredReserve) {
        this.injuredReserve = injuredReserve;
    }

    public void setNotActive(String[] notActive) {
        this.notActive = notActive;
    }

    public void setBench(String[] bench) {
        this.bench = bench;
    }

    public String[] getGoalies() {
        return goalies;
    }
}
