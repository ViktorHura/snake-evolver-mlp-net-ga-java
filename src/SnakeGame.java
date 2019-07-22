package snakenet;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.Sys;
import java.util.Arrays;

public class SnakeGame {

public Snake[] snakes;
private int dot_size = 10;
private int gwidth = 640;
private int gheight = 480;
private int snakescount = 0;
private Organism[] organisms;
private Network network;

public boolean inGame = true;

int framerate;
int rframerate;

private int fps;
private long lastFPS;

public void GameSetup(int snakecount){

        snakes = new Snake[snakecount];
        this.snakescount = snakecount;
        for (int i = 0; i < snakecount; i++) {
                snakes[i] = new Snake();

                for (int z = 0; z < snakes[i].dots; z++) {

                        snakes[i].x[z] = 100 - z * dot_size;
                        snakes[i].y[z] = 400;
                }
        }
}

public void GameReset(Organism[] organisms){

        snakes = new Snake[snakescount];
        this.organisms = organisms;
        for (int i = 0; i < snakescount; i++) {
                snakes[i] = new Snake();

                for (int z = 0; z < snakes[i].dots; z++) {

                        snakes[i].x[z] = 100 - z * dot_size;
                        snakes[i].y[z] = 400;
                }
        }
        inGame = true;
}

public void DrawSnakes(){
        for (int i = 0; i < snakes.length; i++) {

                if (snakes[i].dead) {
                        continue;
                }

                for (int z = 0; z < snakes[i].dots; z++) {

                        if (z == 0) {
                                Drawer.Rect(snakes[i].x[z],snakes[i].y[z],dot_size,dot_size, 0.0f,1.0f,0.0f );
                        }else{
                                Drawer.Rect(snakes[i].x[z],snakes[i].y[z],dot_size,dot_size, 0.0f,0.5f,0.0f );
                        }
                }

                Drawer.Rect(snakes[i].apple_x,snakes[i].apple_y,dot_size,dot_size, 1.0f,0.0f,0.0f );

        }
}

public void MoveSnakes(){
        for (int i = 0; i < snakes.length; i++) {

                if (snakes[i].dead) {
                        continue;
                }

                for (int z = snakes[i].dots; z > 0; z--) {
                        snakes[i].x[z] = snakes[i].x[(z - 1)];
                        snakes[i].y[z] = snakes[i].y[(z - 1)];
                }

                if (snakes[i].leftDirection) {
                        snakes[i].x[0] -= dot_size;
                }

                if (snakes[i].rightDirection) {
                        snakes[i].x[0] += dot_size;
                }

                if (snakes[i].upDirection) {
                        snakes[i].y[0] += dot_size;
                }

                if (snakes[i].downDirection) {
                        snakes[i].y[0] -= dot_size;
                }
        }
}

public void pollInput() {



        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
                rframerate = 0;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
                rframerate = framerate;
        }



}

public void SnakeCollisions(){


        int deadcount = 0;

        for (int i = 0; i < snakes.length; i++) {

                if ((snakes[i].x[0] == snakes[i].apple_x) && (snakes[i].y[0] == snakes[i].apple_y)) {
                        if (snakes[i].dead) {
                                continue;
                        }

                        snakes[i].dots += 1;
                        snakes[i].lifetime += 300;
                        snakes[i].ResetApple(dot_size);
                        organisms[i].fitness += 10000;
                }

                for (int z = snakes[i].dots; z > 0; z--) {

                        if ((z > 4) && (snakes[i].x[0] == snakes[i].x[z]) && (snakes[i].y[0] == snakes[i].y[z])) {
                                snakes[i].dead = true;

                        }
                }

                if (snakes[i].y[0] >= gheight) {
                        snakes[i].dead = true;

                }

                if (snakes[i].y[0] < 0) {
                        snakes[i].dead = true;

                }

                if (snakes[i].x[0] >= gwidth) {
                        snakes[i].dead = true;

                }

                if (snakes[i].x[0] < 0) {
                        snakes[i].dead = true;

                }

                if (snakes[i].dead) {
                        deadcount += 1;
                }

        }

        if (deadcount == snakes.length) {

                inGame = false;
                //GameReset();
        }



}

public void DecideMove(){
        for (int i = 0; i < snakes.length; i++) {
                network.inputWeights = organisms[i].inputWeights;
                network.hiddenWeights = organisms[i].hiddenWeights;

                double[] inputs = new double[network.inputs];

                if (snakes[i].y[0] + dot_size >= gheight ) { //up clear?
                        inputs[0] = 1.0;
                }else{
                        inputs[0] = 0.0;

                        for (int z = snakes[i].dots; z > 0; z--) {

                                int x = snakes[i].x[0];
                                int y = snakes[i].y[0] + dot_size;

                                if ((z > 4) && (x == snakes[i].x[z]) && (y == snakes[i].y[z])) {
                                        inputs[0] = 1.0;

                                }
                        }

                }
                if (snakes[i].y[0] - dot_size < 0 ) { //down clear?
                        inputs[1] = 1.0;
                }else{
                        inputs[1] = 0.0;

                        for (int z = snakes[i].dots; z > 0; z--) {

                                int x = snakes[i].x[0];
                                int y = snakes[i].y[0] - dot_size;

                                if ((z > 4) && (x == snakes[i].x[z]) && (y == snakes[i].y[z])) {
                                        inputs[1] = 1.0;

                                }
                        }

                }

                if (snakes[i].x[0] - dot_size < 0 ) { //left clear?
                        inputs[2] = 1.0;
                }else{
                        inputs[2] = 0.0;

                        for (int z = snakes[i].dots; z > 0; z--) {

                                int x = snakes[i].x[0] - dot_size;
                                int y = snakes[i].y[0];

                                if ((z > 4) && (x == snakes[i].x[z]) && (y == snakes[i].y[z])) {
                                        inputs[2] = 1.0;

                                }
                        }

                }

                if (snakes[i].x[0] + dot_size >= gwidth ) { //right clear?
                        inputs[3] = 1.0;
                }else{
                        inputs[3] = 0.0;

                        for (int z = snakes[i].dots; z > 0; z--) {

                                int x = snakes[i].x[0] + dot_size;
                                int y = snakes[i].y[0];

                                if ((z > 4) && (x == snakes[i].x[z]) && (y == snakes[i].y[z])) {
                                        inputs[3] = 1.0;

                                }
                        }

                }

                //what direction am I going?
                inputs[4] = snakes[i].upDirection ? 1.0f : 0.0f;
                inputs[5] = snakes[i].downDirection ? 1.0f : 0.0f;
                inputs[6] = snakes[i].leftDirection ? 1.0f : 0.0f;
                inputs[7] = snakes[i].rightDirection ? 1.0f : 0.0f;

                if (snakes[i].apple_y >= snakes[i].y[0]) {
                        inputs[8] = 1.0;
                        inputs[9] = 0.0;
                }else{
                        inputs[8] = 0.0;
                        inputs[9] = 1.0;
                }

                if (snakes[i].apple_x <= snakes[i].x[0]) {
                        inputs[10] = 1.0;
                        inputs[11] = 0.0;
                }else{
                        inputs[10] = 0.0;
                        inputs[11] = 1.0;
                }

                //System.out.println(Arrays.toString(inputs));

                Double[][] prediction = network.Predict(inputs).matrix;
                int desiredmove = 0;

                for (int n = 0; n < prediction.length; n++) {
                        if (prediction[n][0] >= prediction[desiredmove][0]) {
                                desiredmove = n;
                        }
                }

                if (desiredmove == 0 && !snakes[i].downDirection) {
                        snakes[i].upDirection = true;
                        snakes[i].rightDirection = false;
                        snakes[i].leftDirection = false;
                }

                if (desiredmove == 1 && !snakes[i].upDirection) {
                        snakes[i].downDirection = true;
                        snakes[i].rightDirection = false;
                        snakes[i].leftDirection = false;
                }

                if (desiredmove == 2 && !snakes[i].rightDirection) {
                        snakes[i].leftDirection = true;
                        snakes[i].upDirection = false;
                        snakes[i].downDirection = false;
                }

                if (desiredmove == 3 && !snakes[i].leftDirection) {
                        snakes[i].rightDirection = true;
                        snakes[i].upDirection = false;
                        snakes[i].downDirection = false;
                }


        }
}

public void EvaluateFitness(){

        for (int i = 0; i < snakes.length; i++) {

                if (snakes[i].dead) {
                        continue;
                }

                snakes[i].dist = calculateDist(snakes[i].x[0], snakes[i].y[0], snakes[i].apple_x,snakes[i].apple_y);

                if (snakes[i].dist < snakes[i].olddist) {
                        organisms[i].fitness += 100;
                }else{
                        organisms[i].fitness -= 150;
                }
                snakes[i].olddist = snakes[i].dist;

                snakes[i].lifetime -= 1;


                if (snakes[i].lifetime < 0) {
                        snakes[i].dead = true;
                }



        }



}

public void Run(){
        Drawer.Clear();

        pollInput();
        DecideMove();
        MoveSnakes();
        SnakeCollisions();
        DrawSnakes();
        EvaluateFitness();

        Display.update();
        Display.sync(rframerate);
        updateFPS();

        if (Display.isCloseRequested()) {
                Display.destroy();
                System.exit(0);
        }
}



public void Launch(int snakecount, Network net, int fps) {
        try {
                Display.setDisplayMode(new DisplayMode(gwidth, gheight));
                Display.setTitle("Snake");
                Display.create();
                lastFPS = getTime();
        } catch (LWJGLException e) {
                System.err.println("Display wasn't initialized correctly.");
                System.exit(1);
        }
        // init OpenGL
        Drawer.Setup(gwidth, gheight);

        GameSetup(snakecount);
        this.network = net;
        this.framerate = fps;
        this.rframerate = this.framerate;
        // Display.destroy();
        // System.exit(0);
}

// public static void main(String[] args) {
//         SnakeGame game = new SnakeGame();
//         game.Launch(1);
//         while (game.inGame) {
//                 game.Run(15);
//         }
//
//
// }

public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
                Display.setTitle("FPS: " + fps);
                fps = 0;
                lastFPS += 1000;
        }
        fps++;
}
public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
}

public double calculateDist(
        double x1,
        double y1,
        double x2,
        double y2) {
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
}
}
