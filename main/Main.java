package main;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[]  args){

        boolean hockey = false;

        if(hockey){
            float[] s = {6f, 4f, 2f, 0f, 0f, 0f, 0f, 0f, 0f, 0.9f, 0f, 1f, 0f, 0f, 0f, 0f, 2f, 0f};
            float[] g = {0f, 0f, 0f, -3f, 0f, 0.6f, 5f};
            Env.setHockeyWeights(s, g);
        }else{
            float[] b = {0f,0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f,
                0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f,
                0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f};
            System.out.println(b.length);
            float[] p = {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f,
                0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f,
                0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f,
                0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f,
                0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f};
            System.out.println(p.length);
            Env.setBaseballWeights(b, p);
        }

        Env.setTotalParticipants(4);
        Env.setTotalRounds(4);

        for(int i = 0; i < 6; i++){
            Env.setPositionLimit(i, Env.getTotalRounds());
        }

        int humanPlayers = 0;
        int playerCount = Env.getTotalParticipants();
        int rounds = Env.getTotalRounds();
        Random random = new Random();

        //hockey
        Parser.parsePlayers(hockey);
       // Parser.parseSkaters();
        //Env.sortPlayerScores();

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
              //  DraftMenu.Draft(rounds, playerCount, false);
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
