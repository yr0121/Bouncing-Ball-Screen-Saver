import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * Created by yuanhaoruan on 10/5/17.
 * Ball screensave class, modified from the given BouncingBall.
 * ruanx054, ID:5085043
 */
public class BallScreenSaver extends AnimationFrame {
    private int number;
    Ball[] balls; //A ball array that holds the balls
    private int ballSize = 18;
    public final int BORDER = 30;
    private int saveCounter=0;
    private CollisionLogger collisionLogger;
    private static final int COLLISION_BUCKET_WIDTH = 20;

    public BallScreenSaver() {
        super();
        this.number = number;
        balls = new Ball[number];
        for (int i = 0; i < number; i++) {
            balls[i] = new Ball(randdouble(BORDER,getWidth()-BORDER),randdouble(BORDER,getHeight()-BORDER) , 18, Color.GREEN);
            balls[i].setSpeedX(Math.cos(randdouble(0,Math.PI*2))*300);
            balls[i].setSpeedY(Math.sin(randdouble(0,Math.PI*2))*300);
        }
        balls[0].setColor(Color.RED);
        this.collisionLogger = new CollisionLogger(200, 200, COLLISION_BUCKET_WIDTH);
    }


    public BallScreenSaver(int width, int height, String name, int number) {
        super(width, height, name);
        this.number = number;
        balls = new Ball[number];
        for (int i = 0; i < number; i++) {
            balls[i] = new Ball(randdouble(BORDER,getWidth()-BORDER),randdouble(BORDER,getHeight()-BORDER) , 18, Color.GREEN);
            balls[i].setSpeedX(Math.cos(randdouble(0,Math.PI*2))*300);
            balls[i].setSpeedY(Math.sin(randdouble(0,Math.PI*2))*300);
        }
        balls[0].setColor(Color.RED);
        collisionLogger = new CollisionLogger(800, 800, COLLISION_BUCKET_WIDTH);
    }


    @Override
    /*
    Method that update the position of the ball if they collide the border
    handle the collision of the balls and change the balls color
     */
    public void action() {
        for (int i = 0; i < number; i++) {
            balls[i].setPos((balls[i].getXPos()+balls[i].getSpeedX()/getFPS()),(balls[i].getYPos()+balls[i].getSpeedY()/getFPS()));
        }
        for (int i = 0; i < number; i++) {
            if (balls[i].getXPos() < BORDER || balls[i].getXPos() + ballSize > getHeight() - BORDER) {
                balls[i].setSpeedX(-balls[i].getSpeedX());
            }
            if (balls[i].getYPos() < BORDER || balls[i].getYPos() + ballSize > getWidth() - BORDER) {
                balls[i].setSpeedY(-balls[i].getSpeedY());
            }
        }
        for (int i = 0; i < number; i++) {
            for (int j = 0; j < number; j++) {
                if (i != j) {
                    if (balls[i].intersect(balls[j])) {
                        balls[i].collide(balls[j]);
                        if (balls[i].getColor().equals(Color.RED) || balls[j].getColor().equals(Color.RED)) {
                            balls[i].setColor(Color.RED);
                            balls[j].setColor(Color.RED);
                        }
                    }
                }
                collisionLogger.collide(balls[i], balls[j]);
            }
        }
    }




    @Override
    /*
    Method that draw the rectangle and ball, the balls color were set
     */
    public void draw(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.white);
        g.drawRect(BORDER, BORDER, getWidth() - BORDER*2, getHeight() - BORDER*2);
        for (int i = 0; i < number; i++) {
            g.setColor(balls[i].getColor());
            g.fillOval((int) balls[i].getXPos(), (int) balls[i].getYPos(), ballSize, ballSize);
        }
    }

    @Override
    /*
    Method that control the key pressing, if left key was pressed, the speed of both direction was decreased by 10%,
    if right key was pressed, the speed of both direction was increased by 10%
    if "p" key was pressed, it would print the normalized heatmap that recorded the collison of the balls,
    if other keys were pressed, print out information that reminds the user wrong key was pressed
     */
    protected void processKeyEvent(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            for (int i = 0; i < number; i++) {
                balls[i].setSpeedX(balls[i].getSpeedX()*0.9);
                balls[i].setSpeedY(balls[i].getSpeedX()*0.9);
            }
        }else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            for (int i = 0; i < number; i++) {
                balls[i].setSpeedX(balls[i].getSpeedX()*1.1);
                balls[i].setSpeedY(balls[i].getSpeedX()*1.1);
            }
        }else if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_P) {
            EasyBufferedImage image = EasyBufferedImage.createImage(collisionLogger.getNormalizedHeatMap());
            try {
                image.save("heatmap"+saveCounter+".png", EasyBufferedImage.PNG);
                saveCounter++;
            } catch (IOException exc) {
                exc.printStackTrace();
                System.exit(1);
            }
        }else if(e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode()!=KeyEvent.VK_P){
            System.out.println("Wrong key pressed, press p key");
        }
    }

    /*
    random number generated for speed and postion
     */
    public int randInt(int min, int max){
        //a utility method to get a random int between min and max.
        return (int)(Math.random()*(max-min)+min);
    }

    public double randdouble(double min, double max){
        //a utility method to get a random double between min and max.
        return (Math.random()*(max-min)+min);
    }


    /*
    Main method for the BallScreenSaver class
     */
    public static void main(String[] args){
        BallScreenSaver bs= new BallScreenSaver(800,800,"bss",100);
        bs.start();
    }
}


