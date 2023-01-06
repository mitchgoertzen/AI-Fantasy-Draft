package main;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[]  args){
           
        Env.setGoalsWeight(6);
        Env.setAssistsWeight(4);
        Env.setPointsWeight(0);
        Env.setPlusminusWeight(2);
        Env.setPimWeight(0);
        Env.setPpgoalsWeight(0);
        Env.setPpassistsWeight(0);
        Env.setPppointsWeight(2);
        Env.setShgoalsWeight(0);
        Env.setShassistsWeight(0);
        Env.setShpointsWeight(0);
        Env.setGwgWeight(0);
        Env.setSogWeight(0.9f);
        Env.setFowWeight(0);
        Env.setFolWeight(0);
        Env.setHitsWeight(0);
        Env.setBlocksWeight(1);

        Env.setGamesstartedWeight(0);
        Env.setGoalsagainstWeight(-3);
        Env.setLossesWeight(0);
        Env.setShotsagainstWeight(0);
        Env.setSavesWeight(0.6f);
        Env.setShutoutWeight(5);
        Env.setWinsWeight(0);

        Env.setTotalParticipants(2);
        Env.setTotalRounds(2);

        for(int i = 0; i < 6; i++){
            Env.setPositionLimit(i, Env.getTotalRounds());
        }

        int humanPlayers = 0;
        int playerCount = Env.getTotalParticipants();
        int rounds = Env.getTotalRounds();
        Random random = new Random();

        Parser.parseSkaters();
        Env.sortPlayerScores();

        List<Integer> draftNumbers = IntStream.rangeClosed(1, playerCount)
        .boxed().collect(Collectors.toList());

        for(int i = 0;i < playerCount;i++){
            int num = random.nextInt(draftNumbers.size());
            if(i < humanPlayers){
                Env.participants.put(i, new Participant(i, (i < humanPlayers), draftNumbers.get(num)));
            }else{
                Env.participants.put(i, new AIParticipant(i, (i < humanPlayers), draftNumbers.get(num), rounds));
            }
            draftNumbers.remove(num);
        }

        System.out.println("This fantasy hockey pool currently has " + Env.participants.size() +
        " participants with " + Env.AllPlayers.size() + " available NHL players to draft.");
    
        System.out.println("To begin the draft, type \'begin\'");
        Scanner scanner = new Scanner(System.in);
        String readString = "";

        while(readString != null){
            readString = scanner.nextLine();

            if(readString.equals("begin")){
                System.out.println();
                DraftMenu.Draft(rounds, playerCount, false);
                readString = null;
                scanner.close();
            }else if(readString.equals("exit")){
                readString = null;
                scanner.close();
            }
            else{
                System.out.println("To begin the draft, type \'begin\'");
                System.out.println("Or type \'exit\' to exit the draft");
            }
        }
    }
}
