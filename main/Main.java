package main;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
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
            float[] b = {
                0f, //GP
                0f, //GS
                0f, //AB
                1f, //R
                1f, //H
                0f, //1B
                0f, //2B
                0f, //3B
                1f, //HR
                1f, //RBI
                0f, //SH
                0f, //SF
                1f, //SB
                -0f, //CS
                1f, //BB
                0f, //IBB
                0f, //HBP
                -0f, //K
                -0f, //GIDP
                0f, //TB
                0f, //PO
                0f, //A
                -0f, //E
                0f, //F%
                0f, //BA
                0f, //OBP
                0f, //SLG
                1f, //OPS
                0f, //XBH
                0f, //NetSB
                0f, //SB%
                0f, //HFC
                0f, //PA
                0f, //GS
                0f, //OA
                0f, //DP
                0f //CI
            };

            float[] p = {
                0f, //GP
                0f, //GS
                1f, //IP
                1f, //W
                0f, //L
                1f, //CG
                0f, //SO
                1f, //SV
                0f, //O
                0f, //H
                0f, //BF
                -0f, //R
                0f, //ER
                0f, //HR
                0f, //BB
                0f, //IBB
                0f, //HBP
                1f, //K
                0f, //WP
                0f, //BK
                0f, //SB
                0f, //GIDP
                0f, //SV CHANCES
                0f, //HOLDS
                0f, //TB
                1f, //ERA
                1f, //WHIP
                0f, //K/BB
                0f, //K/9
                0f, //pitch count
                0f, //1b
                0f, //2b
                0f, //3b
                0f, //relief w
                0f, //relief l
                0f, //pickoffs
                0f, //relief ap
                0f, //OBP
                0f, //WIN%
                0f, //H/9
                0f, //BB/9
                0f, //no hit
                0f, //perf
                0f, //sv%
                0f, //inherited runs
                0f, //QS
                0f, //blown sv
                0f, //net sv
                0f, //saves + holds
                0f, //net sv + h
                0f, //net wins
            };
            Env.setBaseballWeights(b, p);
        }

        Env.setTotalParticipants(4);
        Env.setTotalRounds(10);

        for(int i = 0; i < 6; i++){
            Env.setPositionLimit(i, Env.getTotalRounds());
        }

        int humanPlayers = 0;
        int playerCount = Env.getTotalParticipants();
        int rounds = Env.getTotalRounds();
        Random random = new Random();

        //hockey
        if(hockey){
            Parser.parseHockeyPlayers();
        }else{
            Parser.parsePlayers(hockey);
        }
        Env.sortPlayerScores();

        // Env.participants.put(0, new Participant(0, true, 1));
        // Env.participants.put(1, new Participant(1, true, 2));
        // Env.participants.put(2, new AIParticipant(2, false, 3, rounds));
        // Env.participants.put(3, new Participant(3, true, 4));
        // Env.participants.put(4, new Participant(4, true, 5));
        // Env.participants.put(5, new Participant(5, true, 6));
        // Env.participants.put(6, new Participant(6, true, 7));
        // Env.participants.put(7, new Participant(7, true, 8));
        // Env.participants.put(8, new Participant(8, true, 9));
        // Env.participants.put(9, new Participant(9, true, 10));

        List<Integer> draftNumbers = IntStream.rangeClosed(1, playerCount)
        .boxed().collect(Collectors.toList());

        for(int i = 0;i < playerCount;i++){
            int num = random.nextInt(draftNumbers.size());
            if(i < humanPlayers){
                Env.participants.put(i, new Participant(i, true, draftNumbers.get(num)));
            }else{
                Env.participants.put(i, new AIParticipant(i, false, draftNumbers.get(num), rounds, hockey));
            }
            draftNumbers.remove(num);
        }

        int i = 1;
        try {
            FileWriter myWriter = new FileWriter("sortedScores.txt");
            for (Map.Entry<String,Float> mapElement : Env.SortedPlayerScores.entrySet()) {
                // if(mapElement.getKey().equals("verlaju01")){
                //     Env.AllPlayers.get(mapElement.getKey()).printInfo();
                // }
                // if(mapElement.getKey().equals("diazya01")){
                //     Env.AllPlayers.get(mapElement.getKey()).printInfo();
                // }
                myWriter.write(i++ + ". " + Env.AllPlayers.get(mapElement.getKey()).getName() + ", " +  Env.AllPlayers.get(mapElement.getKey()).getPosition() + "\n");
                myWriter.write("Score: " + mapElement.getValue() + "\n\n");
                //myWriter.write(Env.AllPlayers.get(mapElement.getKey()).getInfo() + "\n\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }

        

        System.out.println("This fantasy pool currently has " + Env.participants.size() +
        " participants with " + Env.AllPlayers.size() + " available players to draft.");
    
        System.out.println("To begin the draft, type \'begin\'");
        Scanner scanner = new Scanner(System.in);
        String readString = "";

        while(readString != null){
            readString = scanner.nextLine();

            if(readString.equals("begin")){
                System.out.println();
                DraftMenu.Draft(rounds, playerCount, false, hockey);
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
