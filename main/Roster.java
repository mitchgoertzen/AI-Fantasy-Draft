package main;

import java.util.ArrayList;

import main.Baseball.Batter;
import main.Baseball.BattingStats;
import main.Baseball.Pitcher;
import main.Baseball.PitchingStats;
import main.Hockey.Goalie;
import main.Hockey.GoalieCountingStats;
import main.Hockey.Skater;
import main.Hockey.SkaterCountingStats;

public class Roster {

    private ArrayList<String> players;
    
    private GoalieCountingStats totalGoalieCountingStats;

    private SkaterCountingStats totalSkaterCountingStats;
    
    private BattingStats totalBattingStats;

    private PitchingStats totalPitchingStats;

    public Roster() {
        players = new ArrayList<>();
        totalSkaterCountingStats = new SkaterCountingStats();
        totalGoalieCountingStats = new GoalieCountingStats();
        totalBattingStats = new BattingStats();
        totalPitchingStats = new PitchingStats();
    }

    public void addPlayer(String p){
        players.add(p);
        Player player = Env.AllPlayers.get(p);

        switch(player.getClass().getName()){
            case "main.Baseball.Batter":{
                totalBattingStats.addStats(((Batter)player).getStats().getStatsArray(), player.getGamesPlayed(), players.size());
            }
            break;
            case "main.Baseball.Pitcher":{
                //totalPitchingStats.addStats(((Pitcher)player).getStats().getStatsArray(), player.getGamesPlayed(), players.size());
            }
            break;
            case "main.Hockey.Skater":{
                totalSkaterCountingStats.addStats(((Skater)player).getCountingStats().getStatsArray(), player.getGamesPlayed());
            }
            break;
            case "main.Hockey.Goalie":{
                totalGoalieCountingStats.addStats(((Goalie)player).getCountingStats().getStatsArray(), player.getGamesPlayed());
            }
            break;
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