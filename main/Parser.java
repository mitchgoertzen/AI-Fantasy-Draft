package main;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import main.Baseball.Batter;
import main.Baseball.Pitcher;
import main.Hockey.Goalie;
import main.Hockey.GoalieCountingStats;
import main.Hockey.Skater;
import main.Hockey.SkaterCountingStats;

public class Parser {

    private static final boolean DEBUG = false;

    //TODO: rename
    public static void parsePlayers(boolean hockey){
        //Map<String, Player>  players = new HashMap<>();

        System.out.println("parse players");

        //could replace dupe arrays with call to new parse method
        if(hockey){
            String[] hockeyFiles = {"stats/nhlstats_12_05_2022.txt",
            "stats/goalieStats_12_14_2022.txt"};

        }else{
            //fielding
            String[] baseballFiles = {
                    "stats/Baseball/mlb_fielding_2022.txt"};
            parseFiles(baseballFiles, hockey, 0);

            // , 
            //         "stats/Baseball/mlb_fielding_2021.txt",
            //         "stats/Baseball/mlb_fielding_2020.txt"

            //batting
            baseballFiles = 
                new String[]{
                    "stats/Baseball/mlb_batting_2022.txt"};
            parseFiles(baseballFiles, hockey, 1);

            // , 
            //         "stats/Baseball/mlb_batting_2021.txt",
            //         "stats/Baseball/mlb_batting_2020.txt"

            //pitching
            baseballFiles = 
                new String[]{
                    "stats/Baseball/mlb_pitching_2022.txt", 
                    "stats/Baseball/mlb_pitching_2021.txt",
                    "stats/Baseball/mlb_pitching_2020.txt"};
            parseFiles(baseballFiles, hockey, 2);
        }

    }

    private static void parseFiles(String[] playerFiles, boolean hockey, int statType){

    //    int pitcherHitters = 1;
    //    int designatedHitters = 1;
        
        String[] currentPlayerArray;
        Player currentPlayer;

        for(int fileIndex = 0; fileIndex < playerFiles.length; fileIndex++){
                   
           // Scanner scanner = new Scanner(playerFiles[fileIndex]);
            String currentID = "";

            if(hockey){
                if(fileIndex == 0)
                    currentPlayerArray = new String[19];
                else
                    currentPlayerArray = new String[10];
            }else{
                switch(statType){
                    case 0: currentPlayerArray = new String[11];
                    break;
                    case 1: currentPlayerArray = new String[40];
                    break;
                    case 2: currentPlayerArray = new String[54];
                    break;
                    default: currentPlayerArray = new String[11];
                }
            }

           // Path path = Paths.get(playerFiles[fileIndex]);

            try (FileReader fr = new FileReader(playerFiles[fileIndex], StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(fr)) {

                String str;
                reader.readLine();
                while ((str = reader.readLine()) != null) {
                    char[] currentLine = str.toCharArray();

                    int lineSize = currentLine.length;

                    for(int i = lineSize - 1; currentLine[i] != ','; i--){
                        currentID = currentLine[i] + currentID;
                    }

                    float eval = -1;
                    currentPlayer = Env.AllPlayers.get(currentID);

                    //player has not been added yet
                    if(currentPlayer == null){
                        //if file is for current year
                        if(fileIndex == 0){
                            // if(statType == 1){
                            //     System.out.println(designatedHitters++);
                            // }
                            currentPlayerArray = parseStatLine(currentLine, currentPlayerArray.length, lineSize, currentID.length());
                            createPlayer(currentPlayerArray, currentID, statType, fileIndex, hockey);
                        }
                    }
                    //player has been added
                    else{ 
                        currentPlayerArray = parseStatLine(currentLine, currentPlayerArray.length, lineSize, currentID.length());
                        //if the player is a pitcher and has batting stats, new batter must be created
                        if(statType == 1  && currentPlayer.getPosition().equals("P")){
                            
                            if(fileIndex == 0){
                                currentID += "_h";
                                if(Env.AllPlayers.get(currentID) == null){
                                    // System.out.println(currentID);
                                    // System.out.println(pitcherHitters++);
                                    currentPlayerArray[0] += "_h";
                                    createPlayer(currentPlayerArray, currentID, statType, fileIndex, hockey);
                                }
                            }
                        }else{
                            //update stats
                            //TODO: update teams for player
                            // if(currentPlayer.getTeam().equals("TOT")){

                            // }

                            if(currentPlayer.getStatYearsCounted() <= fileIndex){
                                if(statType == 2){
                                    if(currentPlayer.getPosition().equals("P")){
                                        Env.updatePlayerStats(currentID, currentPlayerArray, statType, fileIndex);
                                        // System.out.println("Pitcher");
                                        // System.out.println(currentPlayer.getName());
                                        // System.out.println("counted years " + currentPlayer.getStatYearsCounted() + "/" + (fileIndex + 1));
                                        // System.out.println();
                                    }
                                }else{
                                    Env.updatePlayerStats(currentID, currentPlayerArray, statType, fileIndex);
                                    // System.out.println("Batter");
                                    // System.out.println(currentPlayer.getName());
                                    // System.out.println("counted years " + currentPlayer.getStatYearsCounted() + "/" + (fileIndex + 1));
                                    // System.out.println();
                                }
                            }
                            
                            
                        }
                    }
                    currentID = "";
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void createPlayer(String[] currentPlayerArray, String currentID, int statType, int fileIndex, boolean hockey){
         //create player
         Player currentPlayer;
         float eval = -1;

         if(hockey){
             if(fileIndex == 0){
                 currentPlayer = new Skater(currentID, currentPlayerArray);
                 eval = calculateSkaterScore((Skater)currentPlayer);
                 if(DEBUG)
                     System.out.println(currentPlayer.getName() + " has a score of : " + eval);
             }else{

                     String[] infoArray = new String[4];
                     infoArray[0] = currentPlayerArray[0];
                     infoArray[1] = currentPlayerArray[1];
                     infoArray[2] = "G";
                     infoArray[3] = currentPlayerArray[2];
             
                 currentPlayer = new Goalie(currentID, infoArray, currentPlayerArray);
                 eval = calculateGoalieScore((Goalie)currentPlayer);
                 if(DEBUG)
                     System.out.println(currentPlayer.getName() + " has a score of : " + eval);
             }
         }else{

            String[] info = {currentPlayerArray[0], currentPlayerArray[1], currentPlayerArray[9], currentPlayerArray[3]};

            switch(statType){
                case 0: { 
                    if(currentPlayerArray[9].equals("P")){
                        info = new String[]{currentPlayerArray[0], currentPlayerArray[1], "P", currentPlayerArray[3]};
                        currentPlayer = new Pitcher(currentID, currentPlayerArray, info, true);
                        if(DEBUG)
                            System.out.println(currentPlayer.getName() + " has a score of : " + eval);
                     }else{
                         currentPlayer = new Batter(currentID, currentPlayerArray, info, true);
                     }
                 }
                 break;
                 case 1:  { 
                     info = new String[]{currentPlayerArray[0], currentPlayerArray[1], currentPlayerArray[23], currentPlayerArray[3]};
                     currentPlayer = new Batter(currentID, currentPlayerArray, info, false);
                 }
                 break;
                 case 2:  { 
                     info = new String[]{currentPlayerArray[0], currentPlayerArray[1], "P", currentPlayerArray[6]};
                     currentPlayer = new Pitcher(currentID, currentPlayerArray, info, false);
                 }
                 break;
                 default: currentPlayer = new Batter(currentID, currentPlayerArray, info, false);
             }
         }

         Env.PlayerScores.put(currentID, currentPlayer.getScore());
         Env.AllPlayers.put(currentID, currentPlayer);
    }

    private static String[] parseStatLine(char[]currentLine, int arraySize, int lineSize, int idLength){
        String[] array = new String[arraySize];
        String currentString = "";
        int j = 0;  
        for(int k = 0; k < lineSize - idLength; k++){
            if(currentLine[k] == ','){
                if(currentString.isEmpty())
                    currentString = "0";
                array[j++] = currentString;
                currentString = "";
            }else{
                if(currentLine[k] != '*' && currentLine[k] != '#' )
                    currentString += currentLine[k];
            }
        }
        return array;
    }

 //   public static void parseHockeyPlayers(){

    //     Map<String, Player>  players = new HashMap<>();
    //     Player currentPlayer;
    //     String[] currentSkaterArray = new String[19];
    //     String[] currentGoalieArray = new String[10];

    //     try{
            

    //         // File[] fileArray = new File[2];      
    //         // fileArray[0] = skaterFile;  
    //         // fileArray[1] = goalieFile;  

    //         for(int fileIndex = 0; fileIndex < 2; fileIndex++)
    //         {
    //            if(fileArray[fileIndex].getName().endsWith(".txt"))
    //             {       
    //                 int j;          
    //                 Scanner scanner = new Scanner(fileArray[fileIndex]);
    //                 String currentID = "";
    //                 String currentString = "";

    //                 if(fileIndex == 0)
    //                     currentSkaterArray = new String[19];
    //                 else
    //                     currentGoalieArray = new String[10];

    //                 while(scanner.hasNextLine()){

    //                     char[] currentLine = scanner.nextLine().toCharArray();

    //                     int lineSize = currentLine.length;
    //                     for(int i = lineSize - 1; currentLine[i] != ','; i--){
    //                         currentID = currentLine[i] + currentID;
    //                     }

    //                     j = 0;
    //                     for(int k = 0; k < lineSize - currentID.length(); k++){
    //                         if(currentLine[k] == ','){
    //                             if(fileIndex == 0){
    //                                 currentSkaterArray[j++] = currentString;
    //                             }else{
    //                                 currentGoalieArray[j++] = currentString;
    //                             }
    //                             currentString = "";
    //                         }else{
    //                             currentString += currentLine[k];
    //                         }
    //                     }
                        
    //                     float eval = -1;
    //                     if(fileIndex == 0){
    //                         currentPlayer = new Skater(currentID, currentSkaterArray);
    //                         eval = calculateSkaterScore((Skater)currentPlayer);
    //                         if(DEBUG)
    //                             System.out.println(currentPlayer.getName() + " has a score of : " + eval);
    //                     }else{

    //                             String[] infoArray = new String[4];
    //                             infoArray[0] = currentGoalieArray[0];
    //                             infoArray[1] = currentGoalieArray[1];
    //                             infoArray[2] = "G";
    //                             infoArray[3] = currentGoalieArray[2];
                       
    //                         currentPlayer = new Goalie(currentID, infoArray, currentGoalieArray);
    //                         eval = calculateGoalieScore((Goalie)currentPlayer);
    //                         if(DEBUG)
    //                             System.out.println(currentPlayer.getName() + " has a score of : " + eval);
    //                     }
    //                     Env.PlayerScores.put(currentID, eval);
    //                     Env.AllPlayers.put(currentID, currentPlayer);
    //                     players.put(currentID, currentPlayer);
    //                     currentID = "";
    //                 }
    //                 scanner.close();
    //             }
    //         }
    //     }catch(FileNotFoundException e){
    //         if(DEBUG)
    //             System.out.println("file not found");
    //         e.printStackTrace();
    //     }
    // }

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
