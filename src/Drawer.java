package snakenet;

import org.lwjgl.opengl.GL11;

public class Drawer {

public static void Setup(int gwidth, int gheight){
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, gwidth, 0, gheight, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
}

public static void Rect(int x, int y, int w, int h, float r, float g, float b){


        // set the color of the quad (R,G,B,A)
        GL11.glColor3f(r,g,b);

        // draw quad
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(x,y);
        GL11.glVertex2f(x,y + h);
        GL11.glVertex2f(x + w,y + h);
        GL11.glVertex2f(x + w,y);
        GL11.glEnd();
}
public static void Clear(){
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
}



}
