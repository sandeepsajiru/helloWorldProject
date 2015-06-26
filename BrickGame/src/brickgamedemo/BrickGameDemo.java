package brickgamedemo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import static java.lang.System.exit;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class BrickPanel extends JPanel implements KeyListener, ComponentListener {

    final static int BALL_WIDTH = 20;
    final static int BALL_HEIGHT = 20;
    final static int SLIT_WIDTH = 70;
    final static int SLIT_HEIGHT = 20;
    final static int BRICK_WIDTH = 50;
    final static int BRICK_HEIGHT = 20;
    int slitTopX, slitTopY, keyDisplacement, keyDirection;
    int ballTopX, ballTopY;
    int ballBottomX, ballBottomY;
    int ballDisplacementX, ballDisplacementY;
    int brickTopX, brickTopY;
    int brickBottomX, brickBottomY;
    boolean bricksOver;
    int count = 0;
    Rectangle[] bricks;

    public BrickPanel() throws IOException {
        this.addKeyListener(this);
        slitTopX = SlitMotion.getSlitTopX();
        slitTopY = SlitMotion.getSlitTopY();
        keyDisplacement = SlitMotion.getKeyDisplacement();
        ballTopX = BallMotion.getBallTopX();
        ballTopY = BallMotion.getBallTopY();
        ballBottomX = ballTopX + BALL_WIDTH;
        ballBottomY = ballTopY + BALL_HEIGHT;
        ballDisplacementX = BallMotion.getBallDisplacementX();
        ballDisplacementY = BallMotion.getBallDisplacementY();
        brickTopX = Brick.getBrickTopX();
        brickTopY = Brick.getBrickTopY();
        brickBottomX = brickTopX + BRICK_WIDTH;
        brickBottomY = brickTopY + BRICK_HEIGHT;
        bricksOver = Brick.getBricksOver();
        bricks = Brick.getBricks();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.green);
        g.fill3DRect(slitTopX, slitTopY, SLIT_WIDTH, SLIT_HEIGHT, true);
        g.setColor(Color.red);
        g.fillOval(ballTopX, ballTopY, BALL_WIDTH, BALL_HEIGHT);
        for (int j = 0; j < bricks.length; j++) {
            if (bricks[j] != null) {
                g.setColor(Color.cyan);
                g.fill3DRect(bricks[j].x, bricks[j].y, bricks[j].width,
                        bricks[j].height, true);
            }
        }

    }

    public void animateBrick() throws InterruptedException {

        for (int i = 0; i < bricks.length; i++) {
            bricks[i] = new Rectangle(brickTopX, brickTopY, BRICK_WIDTH, BRICK_HEIGHT);
            if (i == 5) {
                brickTopX = 90;
                brickTopY = 72;
            }
            if (i == 9) {
                brickTopX = 140;
                brickTopY = 94;
            }
            brickTopX += BRICK_WIDTH + 1;

        }

        while (bricksOver == false) {

            for (int i = 0; i < bricks.length; i++) {
                if (bricks[i] != null) {
                    int brickTopXi = bricks[i].x;
                    int brickTopYi = bricks[i].y;
                    int brickBottomXi = bricks[i].x + BRICK_WIDTH;
                    int brickBottomYi = bricks[i].y + BRICK_HEIGHT;
                    if (brickBottomCheck(brickTopXi, brickTopYi, brickBottomXi, brickBottomYi)) {
                        bricks[i] = null;
                        ballDisplacementY = -ballDisplacementY;
                        count++;
                    }
                    if (brickTopCheck(brickTopXi, brickTopYi, brickBottomXi, brickBottomYi)) {
                        bricks[i] = null;
                        ballDisplacementY = -ballDisplacementY;
                        count++;
                    }
                    if (brickLeftCheck(brickTopXi, brickTopYi, brickBottomXi, brickBottomYi)) {
                        bricks[i] = null;
                        ballDisplacementX = -ballDisplacementX;
                        count++;
                    }
                    if (brickRightCheck(brickTopXi, brickTopYi, brickBottomXi, brickBottomYi)) {
                        bricks[i] = null;
                        ballDisplacementX = -ballDisplacementX;
                        count++;
                    }
                }

                if (count == bricks.length) {

                    JOptionPane.showMessageDialog(null, "WON");
                    bricksOver = true;
                    repaint();
                    // exit(0);
                }

                //repaint();
                if (bricksOver) {
                    exit(0);
                }
            }
            animateBall();
            Thread.sleep(7);

        }

    }

    public void animateBall() {

        System.out.printf("ANIMATE BALL : %d %d \n", ballTopX, ballTopY);

        if (ballBottomY >= slitTopY && ballBottomY < this.getWidth()) {
            if ((slitTopX <= ballBottomX && ballBottomX <= slitTopX + 70)
                    || (ballTopX >= slitTopX && ballTopX <= slitTopX + 70)) {
                ballDisplacementY = -ballDisplacementY;
            }
        }
        if (ballTopY < 0) {
            ballDisplacementY = -ballDisplacementY;
        }

        ballTopY += ballDisplacementY;
        ballBottomY += ballDisplacementY;

        if (ballBottomX >= this.getWidth() || ballTopX <= 0) {
            ballDisplacementX = -ballDisplacementX;
        }

        ballTopX += ballDisplacementX;
        ballBottomX += ballDisplacementX;

        if (ballBottomY >= this.getHeight()) {
            JOptionPane.showMessageDialog(null, "LOST");
            exit(0);
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        //      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        keyDirection = ke.getKeyCode();

        if (keyDirection == KeyEvent.VK_LEFT) {
            slitTopX -= keyDisplacement;
            if (slitTopX < 0) {
                slitTopX = this.getWidth();
            }
        } else if (keyDirection == KeyEvent.VK_RIGHT) {
            slitTopX += keyDisplacement;
            if (slitTopX >= getWidth()) {
                slitTopX = 0;
            }
        }

        repaint();

    }

    @Override
    public void keyReleased(KeyEvent ke) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void componentResized(ComponentEvent ce) {
        slitTopX = this.getWidth() / 2 - SLIT_WIDTH;
        slitTopY = this.getHeight() - SLIT_HEIGHT;

    }

    @Override
    public void componentMoved(ComponentEvent ce) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void componentShown(ComponentEvent ce) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void componentHidden(ComponentEvent ce) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean brickBottomCheck(int brickTopXi, int brickTopYi, int brickBottomXi, int brickBottomYi) {
        if (ballTopY == brickBottomYi) {
            if ((brickTopXi <= ballTopX && ballTopX <= brickBottomXi) || (brickTopXi <= ballBottomX && ballBottomX <= brickBottomXi)) {
                return true;
            }
        }
        return false;
    }

    private boolean brickTopCheck(int brickTopXi, int brickTopYi, int brickBottomXi, int brickBottomYi) {
        if (ballBottomY == brickTopYi) {
            if ((brickTopXi <= ballTopX && ballTopX <= brickBottomXi) || (brickTopXi <= ballBottomX && ballBottomX <= brickBottomXi)) {
                return true;
            }
        }
        return false;
    }

    private boolean brickLeftCheck(int brickTopXi, int brickTopYi, int brickBottomXi, int brickBottomYi) {
        if (ballBottomX == brickTopXi) {
            if ((brickTopYi <= ballTopY && ballTopY <= brickBottomYi) || (brickTopYi <= ballBottomY && ballBottomY <= brickBottomYi)) {
                return true;
            }
        }
        return false;
    }

    private boolean brickRightCheck(int brickTopXi, int brickTopYi, int brickBottomXi, int brickBottomYi) {
        if (ballTopX == brickBottomXi) {
            if ((brickTopYi <= ballTopY && ballBottomY <= brickBottomYi) || (brickTopYi <= ballBottomY && ballBottomY <= brickBottomYi)) {
                return true;
            }
        }
        return false;
    }

}

public class BrickGameDemo {

    public static void main(String[] args) throws IOException, InterruptedException {
        JFrame j = new JFrame("Brick Game");
        j.setBounds(400, 200, 500, 500);
        BrickPanel pnl = new BrickPanel();
        pnl.setBackground(Color.yellow);
        pnl.setFocusable(true);
        pnl.addComponentListener(pnl);
        pnl.setBounds(j.getContentPane().getBounds());
        j.add(pnl);
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.setVisible(true);
        System.out.println(pnl.bricksOver);
        pnl.animateBrick();

    }
}
