package brickgamedemo;

import static brickgamedemo.SlitMotion.slitTopX;
import java.awt.Rectangle;

public class Brick {

 //   BrickPanel pnl = new BrickPanel();
    static int brickTopX = 90;
    static int brickTopY = 50;
    static boolean bricksOver = false;

    public static int getBrickTopX() {
        return brickTopX;
    }

    public static int getBrickTopY() {
        return brickTopY;
    }

    public static boolean getBricksOver() {
        return bricksOver;
    }
    public static Rectangle[] getBricks()
    {
    Rectangle[] bricks = new Rectangle[12];
    return bricks;
    }

}
