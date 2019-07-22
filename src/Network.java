package snakenet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.google.gson.Gson;
import java.nio.file.Paths;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;



public class Network {
int inputs;
int hiddens;
int outputs;
Matrix inputWeights = new Matrix();
Matrix hiddenWeights = new Matrix();

public Network(int input, int hidden, int output  ) {
        inputs = input;
        hiddens = hidden;
        outputs = output;

        inputWeights.RandomMatrix(hidden, input);
        hiddenWeights.RandomMatrix(output, hidden);

}

public Matrix Predict(double[] inputData){
        Matrix inputs = new Matrix();
        inputs.EmptyMatrix(inputData.length, 1);
        for (int i = 0; i < inputData.length; i++) {
                inputs.matrix[i][0]= inputData[i];
        }

        Matrix hiddenInputs = inputWeights.DotProduct(inputs);
        Matrix hiddenOutputs = hiddenInputs.Sigmoid();
        Matrix finalInputs = hiddenWeights.DotProduct(hiddenOutputs);
        Matrix finalOutputs = finalInputs.Sigmoid();

        return finalOutputs;


}


public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        System.out.println("Train or Replay?(0/1)");
        int c = in.nextInt();
        in.nextLine();

        if (c == 0) {

                System.out.println("Creating Snake Network");
                Network net = new Network(12,13,4);
                System.out.println("Initialising Trainer");




                System.out.println("Population size? (press enter to use default:100)");
                String spops = in.nextLine();
                int pops;
                if (spops.length() == 0) {
                        pops = 100;
                }else{
                        pops = Integer.parseInt(spops);
                }

                System.out.println("#Elite individuals? (press enter to use default:7)");
                String sel = in.nextLine();
                int el;
                if (sel.length() == 0) {
                        el = 7;
                }else{
                        el = Integer.parseInt(sel);
                }

                System.out.println("#Worst individuals to kill? (press enter to use default:20)");
                String scutoff = in.nextLine();
                int cutoff;
                if (scutoff.length() == 0) {
                        cutoff = 20;
                }else{
                        cutoff = Integer.parseInt(scutoff);
                }

                System.out.println("Tournament size? (press enter to use default:3)");
                String stourns = in.nextLine();
                int tourns;
                if (stourns.length() == 0) {
                        tourns = 3;
                }else{
                        tourns = Integer.parseInt(stourns);
                }

                System.out.println("Mutationrate in %? (press enter to use default:5.0)");
                String smutrate = in.nextLine();
                double mutrate;
                if (smutrate.length() == 0) {
                        mutrate = 5;
                }else{
                        mutrate = Double.parseDouble(smutrate);
                }

                System.out.println(String.format("Population size: %s", pops));
                System.out.println(String.format("Elite size: %s", el));
                System.out.println(String.format("Cutoff size: %s", cutoff));
                System.out.println(String.format("Tournament size: %s", tourns));
                System.out.println(String.format("Mutation rate: %s", mutrate));

                System.out.println("Press enter to continue.");



                Trainer trainer = new Trainer(net, pops, el, cutoff, tourns, mutrate);



                System.out.println("How many generations to train? (press enter to use default:1000)");
                String sgens = in.nextLine();
                int gens;
                if (sgens.length() == 0) {
                        gens = 1000;
                }else{
                        gens = Integer.parseInt(sgens);
                }

                System.out.println("Observing framerate? (press enter to use default:60)");
                String sfps = in.nextLine();
                int fps;
                if (sfps.length() == 0) {
                        fps = 60;
                }else{
                        fps = Integer.parseInt(sfps);
                }

                System.out.println(String.format("Training %s generations, at %s observing framerate", gens, fps));
                System.out.println("Controls:");
                System.out.println("Up_Arrow: Train at max speed");
                System.out.println("Down_Arrow: Train at observing speed");

                System.out.println("Press enter start training.");
                in.nextLine();

                System.out.println("Training Network");
                trainer.Train(net, gens,fps, true);
        }

        if (c == 1) {


                Network net = new Network(12,13,4);
                Organism[] population = new Organism[1];

                System.out.println("Enter path to saved json file e.g. goodsnake.json");
                String path = in.nextLine();


                Gson gson = new Gson();

                // 1. JSON file to Java object
                try{
                        Organism o = gson.fromJson(new FileReader(Paths.get(".").toAbsolutePath().normalize().toString() + "/" + path), Organism.class);
                        population[0] = o;
                }catch(FileNotFoundException e) {

                }






                System.out.println("Observing framerate? (press enter to use default:60)");
                String sfps = in.nextLine();
                int fps;
                if (sfps.length() == 0) {
                        fps = 60;
                }else{
                        fps = Integer.parseInt(sfps);
                }

                System.out.println("Controls:");
                System.out.println("Up_Arrow: Replay at max speed");
                System.out.println("Down_Arrow: Replay at observing speed");

                System.out.println("Press enter Play.");
                in.nextLine();

                SnakeGame game = new SnakeGame();
                game.Launch(1, net, fps);
                game.GameReset(population);

                while (game.inGame) {
                        game.Run();
                }


        }






        System.out.println("Press 'Enter' key to exit.");
        in.nextLine();

}

}
