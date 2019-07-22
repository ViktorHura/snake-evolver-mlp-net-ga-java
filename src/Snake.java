package snakenet;

public class Snake {

public final int x[] = new int[3072];
public final int y[] = new int[3072];

public int dots;
public int lifetime = 300;

public int apple_x = 320;
public int apple_y = 10;
public int appleint = 0;
public double dist = 0;
public double olddist = 0;

public boolean dead = false;

public boolean leftDirection = false;
public boolean rightDirection = true;
public boolean upDirection = false;
public boolean downDirection = false;

public Snake(){
        this.dots = 3;
        this.olddist = calculateDist(x[0], y[0], apple_x,apple_y);
}

public void ResetApple(int dot_size){
        if (appleint == 0) {
                apple_x = 320;
                apple_y = 460;

        }else if (appleint == 1) {
                apple_x = 10;
                apple_y = 240;

        }else if (appleint == 2) {
                apple_x = 620;
                apple_y = 240;

        }else{

                int r = (int) (Math.random() * 63);
                apple_x = ((r * dot_size));

                r = (int) (Math.random() * 47);
                apple_y = ((r * dot_size));

        }
        appleint += 1;
}

public double calculateDist(
        double x1,
        double y1,
        double x2,
        double y2) {
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
}

}
