package main;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class DraftMenu {
    
    private static ArrayList<String> availablePlayers = new ArrayList<>();
    private static LinkedHashMap<String, Float> playerScores = new LinkedHashMap<>();

    public static void Draft(int rounds, int playerCount, boolean snake){

        Env.setCurrentPick(1);
        Env.setCurrentRound(1);

        
        int currentPickInRound =  1;
        int currentPickInDraft =  Env.getCurrentPick();
        int currentRound = Env.getCurrentRound();
        

    //    availablePlayers = (ArrayList<String>) Env.SortedPlayers.clone();
        // for (String s : Env.SortedPlayers) {
            
        //    // System.out.println(s);
        //     availablePlayers.add(s);
        // }

        // for (Map.Entry<String,Player> mapElement : Env.AllPlayers.entrySet()) {
        //     availablePlayers.add(mapElement.getKey());
        // }
        
        for (Map.Entry<String,Float> mapElement : Env.SortedPlayerScores.entrySet()) {
            playerScores.put(mapElement.getKey(), mapElement.getValue());
            availablePlayers.add(mapElement.getKey());
        }


        Participant[] draftOrder = new Participant[playerCount];
        Participant currentParticipant;


        for(Map.Entry<Integer, Participant> entry : Env.participants.entrySet()){
            draftOrder[entry.getValue().getDraftNumber() - 1] = entry.getValue();
        }  
        
   //     System.out.println("original size: " + Env.totalPicksInDraft.size());
        int k = 0;
        for(int i = 0; i < rounds; i++){
            for(int j = 0; j < playerCount; j++){
                Env.totalPicksInDraft.put(k++,  draftOrder[j].getId());
            }
        }

    //    System.out.println("new size: " + Env.totalPicksInDraft.size());


        
        Scanner scanner = new Scanner(System.in);

        System.out.println("To draft a player: type their first and last name");
        while(Env.getCurrentRound() <= rounds){

            currentParticipant = draftOrder[currentPickInRound - 1];

            System.out.println("-------------------------------------------------");
            System.out.println("Round " + currentRound + " Pick " + currentPickInRound + " (" + currentPickInDraft + " overall)" +
                                " belongs to: " + currentParticipant.getName());
            System.out.println("-------------------------------------------------");
      //      System.out.println("To list all players: type \'L\'");

            String readString = "";
            String playerCode = "";

            while(readString != null){

                if(currentParticipant.isHuman()){
                    readString = scanner.nextLine().toLowerCase();
                    playerCode = checkForPlayer(readString);
                }else{
                    playerCode = ((AIParticipant) currentParticipant).draftPlayer();
                    //ai chooses player based on current conditions
                }
                if(readString.equals("exit")){
                    readString = null;
                    currentRound = 99;
                } else {
                    if(playerCode != null){
                        if(!Env.playerDrafted.containsKey(playerCode) || (Env.playerDrafted.containsKey(playerCode) && !Env.playerDrafted.get(playerCode))){
                            System.out.println("\nWith the " + currentPickInDraft + " overall pick, "
                            + currentParticipant.getName() + " has drafted "
                            + Env.AllPlayers.get(playerCode).getPosition() + " "
                            + Env.AllPlayers.get(playerCode).getName() + "\n");
                            Env.nextPick();
                            currentPickInDraft = Env.getCurrentPick();
                            currentPickInRound++;
                            availablePlayers.remove(playerCode);
                            playerScores.remove(playerCode);
                            Env.playerDrafted.put(playerCode, true);
                            readString = null;
                        }else{
                            System.out.println("This player has already been drafted.");
                        }
                    }else{
                        System.out.println("Could not find a player by this name.");
                    }
                }
            }
            if(currentPickInDraft - (currentRound * playerCount) > 0){
                Env.nextRound();
                currentRound = Env.getCurrentRound();
                currentPickInRound = 1;
            }
        }
        scanner.close();
        System.out.println("\nThe current NHL fantasy draft has concluded.");
    }

    public static String checkForPlayer(String name){

        for (String s : availablePlayers) {
 
            Player player = Env.AllPlayers.get(s);
 
            if(name.equals(player.getName().toLowerCase())){
            return s;
           }
        }

        return null;

    }

    public static ArrayList<String> getAvailablePlayers(){

        return availablePlayers;
    }
    
    

    public static LinkedHashMap<String, Float> getPlayerScores() {
        return playerScores;
    }
}
