package main;
public abstract class Player implements Cloneable {

    private float score;

    private int gamesplayed;

    private String id;
    private String name;
    private String position;
    private String team;

    public Player(String id, String[] array){
        this.id = id;
        name = array[0];
        team = array[1];
        position = array[2];
        gamesplayed = Integer.parseInt(array[3]);
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

    public int getGamesplayed() {
        return gamesplayed;
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
    public void setGamesplayed(int gamesplayed) {
        this.gamesplayed = gamesplayed;
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
}
