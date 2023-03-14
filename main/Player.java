package main;
public abstract class Player implements Cloneable {

    private float score = 0;

    private boolean fieldingCounted;

    public boolean isFieldingCounted() {
        return fieldingCounted;
    }

    public void setFieldingCounted(boolean fieldingCounted) {
        this.fieldingCounted = fieldingCounted;
    }

    private int gamesPlayed;

    private String id;
    private String name;
    private String position;
    private String team;//TODO: change to list

    private int statYearsCounted = 0;

    public int getStatYearsCounted() {
        return statYearsCounted;
    }

    public void resetStatYearsCounted() {
        statYearsCounted = 0;
    }
    public void countYears() {
        statYearsCounted++;
    }

    public Player(String id, String[] array){
        this.id = id;
        name = array[0];
        team = array[1];
        position = array[2];
        gamesPlayed = Integer.parseInt(array[3]);
    }
    
    @Override
    public Player clone() {
        try {
            return (Player) super.clone();
        } catch (CloneNotSupportedException e) {            
            return null;
        }
    }

    //Getters
    public float getScore() {
        return score;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getTeam() {
        return team;
    }

    //Setters
    public void addGamesPlayed(int gp){
        gamesPlayed += gp;
    }
    public void setGamesPlayed(int gamesplayed) {
        this.gamesPlayed = gamesplayed;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    
    public void setScore(float score) {
        this.score = score;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void printInfo(){
        System.out.printf("%s, %s, %s\n%s GP\n", name, team, position, gamesPlayed);
    }
}
