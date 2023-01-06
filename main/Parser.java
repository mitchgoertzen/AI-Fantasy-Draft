package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Parser {

    private static final boolean DEBUG = false;

    public static void parseSkaters(){

        Map<String, Player>  players = new HashMap<>();
        Player currentPlayer;
        String[] currentSkaterArray = new String[19];
        String[] currentGoalieArray = new String[10];

        try{
            
            File skaterFile = new File("stats/nhlstats_12_05_2022.txt");
            File goalieFile = new File("stats/goalieStats_12_14_2022.txt");

            File[] fileArray = new File[2];      
            fileArray[0] = skaterFile;  
            fileArray[1] = goalieFile;  

            for(int fileIndex = 0; fileIndex < 1; fileIndex++)
            {
               if(fileArray[fileIndex].getName().endsWith(".txt"))
                {       
                    int j;          
                    Scanner scanner = new Scanner(fileArray[fileIndex]);
                    String currentID = "";
                    String currentString = "";

                    if(fileIndex == 0)
                        currentSkaterArray = new String[19];
                    else
                        currentGoalieArray = new String[10];

                    while(scanner.hasNextLine()){

                        char[] currentLine = scanner.nextLine().toCharArray();

                        int lineSize = currentLine.length;
                        for(int i = lineSize - 1; currentLine[i] != ','; i--){
                            currentID = currentLine[i] + currentID;
                        }

                        j = 0;
                        for(int k = 0; k < lineSize - currentID.length(); k++){
                            if(currentLine[k] == ','){
                                if(fileIndex == 0){
                                    currentSkaterArray[j++] = currentString;
                                }else{
                                    currentGoalieArray[j++] = currentString;
                                }
                                currentString = "";
                            }else{
                                currentString += currentLine[k];
                            }
                        }
                        
                        float eval = -1;
                        if(fileIndex == 0){
                            currentPlayer = new Skater(currentID, currentSkaterArray);
                            eval = calculateSkaterScore((Skater)currentPlayer);
                            if(DEBUG)
                                System.out.println(currentPlayer.getName() + " has a score of : " + eval);
                        }else{

                                String[] infoArray = new String[4];
                                infoArray[0] = currentGoalieArray[0];
                                infoArray[1] = currentGoalieArray[1];
                                infoArray[2] = "G";
                                infoArray[3] = currentGoalieArray[2];
                       
                            currentPlayer = new Goalie(currentID, infoArray, currentGoalieArray);
                            eval = calculateGoalieScore((Goalie)currentPlayer);
                            if(DEBUG)
                                System.out.println(currentPlayer.getName() + " has a score of : " + eval);
                        }
                        Env.PlayerScores.put(currentID, eval);
                        Env.AllPlayers.put(currentID, currentPlayer);
                        players.put(currentID, currentPlayer);
                        currentID = "";
                    }
                    scanner.close();
                }
            }
        }catch(FileNotFoundException e){
            if(DEBUG)
                System.out.println("file not found");
            e.printStackTrace();
        }
    }

    private static float calculateSkaterScore(Skater skater){

        float score = 0;
        SkaterCountingStats counting = skater.getCountingStats();
        Integer[] stats = counting.getStatsArray();
        int statsLength = stats.length;

        for(int i = 0; i < statsLength; i++){
            score += stats[i] * Env.getSkaterWeights(i);
        }

        score += counting.getPoints() * Env.getSkaterWeights(15);
        score += counting.getPowerplaypoints() * Env.getSkaterWeights(16);
        score += counting.getShpoints() * Env.getSkaterWeights(17);

        return score / 82f ;
    }

    private static float calculateGoalieScore(Goalie goalie){

        float score = 0;
        GoalieCountingStats counting = goalie.getCountingStats();
        Integer[] stats = counting.getStatsArray();
        int statsLength = stats.length;

        for(int i = 0; i < statsLength; i++){
            if(DEBUG)
                System.out.print(stats[i]);
            score += stats[i] * Env.getGoalieWeights(i);
        }
        return score / 82f  * (1f/12f);
    }
}
