package main;

import java.util.ArrayList;

import main.Hockey.Goalie;
import main.Hockey.GoalieCountingStats;
import main.Hockey.Skater;
import main.Hockey.SkaterCountingStats;

public class Roster {

    private ArrayList<String> players;
    
    private GoalieCountingStats totalGoalieCountingStats;

    private SkaterCountingStats totalSkaterCountingStats;

    public Roster() {
        players = new ArrayList<>();
        totalSkaterCountingStats = new SkaterCountingStats();
        totalGoalieCountingStats = new GoalieCountingStats();
    }

    public void addPlayer(String p){
        players.add(p);
        Player player = Env.AllPlayers.get(p);
        if(player.getPosition().equals("G")){
            totalGoalieCountingStats.addStats(((Goalie)player).getCountingStats().getStatsArray(), player.getGamesPlayed());
        }else{  
            totalSkaterCountingStats.addStats(((Skater)player).getCountingStats().getStatsArray(), player.getGamesPlayed());
        }
    }

    //Getters
    public ArrayList<String> getPlayers() {
        return players;
    }

    public GoalieCountingStats getTotalGoalieCountingStats() {
        return totalGoalieCountingStats;
    }
    
    public SkaterCountingStats getTotalSkaterCountingStats() {
        return totalSkaterCountingStats;
    }

    //Setters
    public void setPlayers(ArrayList<String> players) {
        this.players = players;
    }

    public void setTotalGoalieCountingStats(GoalieCountingStats totalCountingStats) {
        this.totalGoalieCountingStats = totalCountingStats;
    }

    public void setTotalSkaterCountingStats(SkaterCountingStats totalCountingStats) {
        this.totalSkaterCountingStats = totalCountingStats;
    }

    public void removePlayer(String id){
        players.remove(id);
    }
}