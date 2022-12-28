package main;

import java.util.ArrayList;

public class Roster {
    private ArrayList<String> players;
    private SkaterCountingStats totalSkaterCountingStats;
    private GoalieCountingStats totalGoalieCountingStats;


    public Roster() {
        players = new ArrayList<>();
        totalSkaterCountingStats = new SkaterCountingStats();
        totalGoalieCountingStats = new GoalieCountingStats();
    }

    public void addPlayer(String p){
        players.add(p);
        //System.out.println(p + " has been added");
        Player player = Env.AllPlayers.get(p);
        if(player.getPosition().equals("G")){
            totalGoalieCountingStats.addStats(((Goalie)player).getCountingStats().getStatsArray(), player.getGamesplayed());
        }else{  
            totalSkaterCountingStats.addStats(((Skater)player).getCountingStats().getStatsArray(), player.getGamesplayed());
        }
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<String> players) {
        this.players = players;
    }

    public GoalieCountingStats getTotalGoalieCountingStats() {
        return totalGoalieCountingStats;
    }
    
    public SkaterCountingStats getTotalSkaterCountingStats() {
        return totalSkaterCountingStats;
    }

    public void setTotalGoalieCountingStats(GoalieCountingStats totalCountingStats) {
        this.totalGoalieCountingStats = totalCountingStats;
    }

    public void setTotalSkaterCountingStats(SkaterCountingStats totalCountingStats) {
        this.totalSkaterCountingStats = totalCountingStats;
    }


}
