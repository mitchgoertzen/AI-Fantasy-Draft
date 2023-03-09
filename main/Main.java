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
                -1f, //K
                -0f, //GIDP
                0f, //TB
                0f, //PO
                0f, //A
                -0f, //E
                0f, //F%
                1f, //BA
                0f, //OBP
                0f, //SLG
                0f, //OPS
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
            System.out.println(b.length);
            float[] p = {1f, 1f, 1f, 1f, -1f, 1f, 1f, 1f, 1f, -1f, 1f,
                        -1f, -1f, -1f, -1f, 0f, -1f, 1f, -1f, -1f, 0f, 
                        0f, 0f, 0f, 0f, 1f, 1f, 1f, 1f, 0f, 0f, 
                        0f, 0f, 0f, 0f, 0f, 0f, 0f, 1f, 1f, 1f, 
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
        int i = 1;
        try {
            FileWriter myWriter = new FileWriter("sortedScores.txt");
            for (Map.Entry<String,Float> mapElement : Env.SortedPlayerScores.entrySet()) {
                if(mapElement.getKey().equals("judgeaa01")){
                    Env.AllPlayers.get(mapElement.getKey()).printInfo();
                }
                if(mapElement.getKey().equals("beninan01")){
                    Env.AllPlayers.get(mapElement.getKey()).printInfo();
                }
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
