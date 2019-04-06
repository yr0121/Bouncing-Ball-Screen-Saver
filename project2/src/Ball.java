/**
 * Created by yuanhaoruan on 10/1/17.
 * Ball class inherited by the provided circle class
 */
import java.awt.*;
public class Ball extends Circle{
    private double xSpeed;
    private double ySpeed;

    public Ball(double x, double y, double r,Color c) {
        super(x,y,r);
       setColor(c);

    }

    public void setSpeedX( double xSpeed) {
        this.xSpeed=xSpeed;
    }

    public void setSpeedY( double ySpeed) {
        this.ySpeed=ySpeed;
    }

    public double getSpeedX() {
        return xSpeed;
    }

    public double getSpeedY() {
        return ySpeed;
    }

    public void updatePosition(double time) {
        setPos(getXPos()+xSpeed*time,getYPos()+ySpeed*time);
    }

    /* Method that test if two ball intersect.If the distance between the two ball is greater than the sum of their radius,
    then the two ball will not intersect.
     */
    public boolean intersect(Ball b) {
        double d = Math.sqrt((Math.pow(getXPos() - b.getXPos(), 2) + Math.pow(getYPos() - b.getYPos(), 2)));
        double sum = getRadius() + b.getRadius();
        if (d > sum) {
            return false;
        } else {
            return true;
        }
    }
    /* Method that update the two balls velocity after collision.
        if two ball intersects, then update the speed of the two balls based on the given equation.
     */
    public void collide(Ball b) {
        if(intersect(b)) {
            double xd = (getXPos() - b.getXPos()) * (getXPos() - b.getXPos());
            double yd = (getYPos() - b.getYPos()) * (getYPos() - b.getYPos());
            double d = Math.sqrt(xd + yd);
            double delta_x = (getXPos() - b.getXPos()) / d;
            double delta_y = (getYPos() - b.getYPos()) / d;
            double vix = getSpeedX();
            double viy = getSpeedY();
            double vjx = b.getSpeedX();
            double vjy = b.getSpeedY();
            double v1_x = (vjx * delta_x + vjy * delta_y) * delta_x - (-vix * delta_y + viy * delta_x) * delta_y;
            double v1_y = (vjx * delta_x + vjy * delta_y) * delta_y + (-vix * delta_y + viy * delta_x) * delta_x;
            double v2_x = (vix * delta_x + viy * delta_y) * delta_x - (-vjx * delta_y + vjy * delta_x) * delta_y;
            double v2_y = (vix * delta_x + viy * delta_y) * delta_y + (-vjx * delta_y + vjy * delta_x) * delta_x;
            setSpeedX(v1_x);
            setSpeedY(v1_y);
            b.setSpeedX(v2_x);
            b.setSpeedY(v2_y);
            }
        }
    }


