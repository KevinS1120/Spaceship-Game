//Kevin Stinnett
//Exam 2 Coding Portion, Spaceship Asteroid Game

//Import libraries
import javax.swing.*;
import java.awt.*;

public class Asteroid implements MoveableObject {
    //Image of meteors (asteroids)
    private ImageIcon meteor;
    private final JPanel canvas;
    private int x, y;
    //For changing the size of the asteroids based on the enum
    private final AsteroidSize size;

    //Enum AsteroidSize contains the options for the sizes of the asteroids to be spawned
    public enum AsteroidSize {
        SMALL, MEDIUM, LARGE
    }

    //Constructor
    public Asteroid(JPanel canvas, int x, int y, AsteroidSize size) {
        this.canvas = canvas;
        this.x = x;
        this.y = y;
        this.size = size;
        setAsteroidSize(size);
    }

    private void setAsteroidSize(AsteroidSize size) {
        //Here I implemented a switch statement, if SMALL, set meteor to the smallMeteor png
        switch(size) {
            case SMALL:
                meteor = new ImageIcon("smallMeteor.png");
                break;
                //If MEDIUM, set meteor to mediumMeteor png
            case MEDIUM:
                meteor = new ImageIcon("mediumMeteor.png");
                break;
                //If LARGE, set meteor to largeMeteor png
            case LARGE:
                meteor = new ImageIcon("largeMeteor.png");
                break;
        }
    }

    //draw method to display the meteor png
    @Override
    public void draw(Graphics2D g2) {
        meteor.paintIcon(canvas, g2, x, y);
    }

    //For the asteroids moving automatically along the screen
    @Override
    public void translate(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    //Return the x coord of the meteor
    public int getX() {
        return x;
    }

    //Return the y coord of the meteor
    public int getY() {
        return y;
    }

    public ImageIcon getMeteor() {
        return meteor;
    }
}
