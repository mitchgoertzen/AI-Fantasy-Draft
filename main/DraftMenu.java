package main;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import main.Baseball.Batter;
import main.Baseball.Pitcher;

public class DraftMenu {
    
    private static ArrayList<String> availablePlayers = new ArrayList<>();
    private static ArrayList<String> draftedPlayers = new ArrayList<>();

    private static LinkedHashMap<String, Float> playerScores = new LinkedHashMap<>();

    private static int length1;
    private static int length2;

    public static String checkForPlayer(String name){
        if(Env.AllPlayers.containsKey(name)){
            return name;
        }else{
            for (String s : availablePlayers) {
                Player player = Env.AllPlayers.get(s);
                if(name.equals(player.getName().toLowerCase())){
                    return s;
               }
            }
        }
        return null;
    }

    public static void Draft(int rounds, int playerCount, boolean snake, boolean hockey){

        if(hockey){
            length1 = 18;
            length2 = 7;
        }else{
            length1 = 37;
            length2 = 51;
        }

        int currentPickInRound =  1;
        Env.setCurrentPick(currentPickInRound);
        Env.setCurrentRound(1);

        int currentPickInDraft =  Env.getCurrentPick();
        int currentRound = Env.getCurrentRound();

        Participant[] draftOrder = new Participant[playerCount];
        Participant currentParticipant;

        String charset = "ISO-8859-1";
        
        Scanner scanner = new Scanner(System.in, charset);

        for (Map.Entry<String,Float> mapElement : Env.SortedPlayerScores.entrySet()) {
            playerScores.put(mapElement.getKey(), mapElement.getValue());
            availablePlayers.add(mapElement.getKey());
        }

        for(Map.Entry<Integer, Participant> entry : Env.participants.entrySet()){
            draftOrder[entry.getValue().getDraftNumber() - 1] = entry.getValue();
        }  
        
        int k = 0;
        for(int i = 0; i < rounds; i++){
            for(int j = 0; j < playerCount; j++){
                Env.totalPicksInDraft.put(k++,  draftOrder[j].getId());
            }
        }

        while(Env.getCurrentRound() <= rounds){

            currentParticipant = draftOrder[currentPickInRound - 1];

            System.out.println("-------------------------------------------------");
            System.out.println("Round " + currentRound + " Pick " + currentPickInRound + " (" + currentPickInDraft + " overall)" +
                                " belongs to: " + currentParticipant.getName());
            System.out.println("-------------------------------------------------");

            String readString = "";
            String playerCode = "";

            while(readString != null){

                if(currentParticipant.isHuman()){
                    System.out.println("To draft a player: type their first and last name");
                    //TODO: names with special characters do not work for search
                    readString = scanner.nextLine().toLowerCase();
                    
                    System.out.println("Your input: " + readString);
                    playerCode = checkForPlayer(readString);
                }else{
                  //  while(!readString.equals("c")){
                        playerCode = ((AIParticipant) currentParticipant).draftPlayer(length1, length2);
                    //     System.out.println(currentParticipant.getName() + " wants to draft " + Env.AllPlayers.get(playerCode).getName() + 
                    //     ". To confirm this selection press 'C', to deny press 'D'...");
                    //     readString = scanner.nextLine().toLowerCase();
                    //     if(readString.equals("d")){
                    //         availablePlayers.remove(playerCode);
                    //         playerScores.remove(playerCode);
                    //         ((AIParticipant) currentParticipant).restoreDraftSlot(currentRound - 1);
                    //         currentParticipant.removeRecentDraft(playerCode);
                    //     }
                    // }
                      
                }
                if(readString.equals("exit")){
                    readString = null;
                    currentRound = 99;
                } else {
                    if(playerCode != null){
                        if(!Env.playerDrafted.containsKey(playerCode) || (Env.playerDrafted.containsKey(playerCode) && !Env.playerDrafted.get(playerCode))){
                            System.out.println("\nWith the " + currentPickInDraft + " overall pick, "
                            + currentParticipant.getName() + " has drafted "
                            + Env.AllPlayers.get(playerCode).getName() + "\n");
                            if(!hockey){
                                if(Env.AllPlayers.get(playerCode).getClass().getName().equals("main.Baseball.Pitcher")){
                                    ((Pitcher)Env.AllPlayers.get(playerCode)).printInfo();
                                }else{
                                    ((Batter)Env.AllPlayers.get(playerCode)).printInfo();
                                }
                            }
                            Env.nextPick();
                            currentPickInDraft = Env.getCurrentPick();
                            currentPickInRound++;
                            availablePlayers.remove(playerCode);
                            playerScores.remove(playerCode);
                            Env.playerDrafted.put(playerCode, true);
                            draftedPlayers.add(Env.AllPlayers.get(playerCode).getName());
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
        System.out.println("\nThe current fantasy draft has concluded.");

        for(int i = 0; i < draftedPlayers.size(); i++){
            System.out.println((i + 1) + ". " + draftedPlayers.get(i));
        }
    }

    public static ArrayList<String> getAvailablePlayers(){
        return availablePlayers;
    }
    
    public static LinkedHashMap<String, Float> getPlayerScores() {
        return playerScores;
    }
}
