//Kevin Stinnett
//Exam 2 Coding Portion, Spaceship Asteroid Game

//Import libraries
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AsteroidPanel extends JPanel {

    final int PANEL_WIDTH = 1000, PANEL_HEIGHT = 500;
    final int DELAY = 10;

    //Arraylist of all the asteroids
    private final ArrayList<Asteroid> roids;
    private Rocket rocket;

    public AsteroidPanel() {
        //Create the background
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.BLACK);

        //Declare the arrayList to hold the asteroids
        roids = new ArrayList<>();

        //Initialize the rocket on the left side of the panel at the middle
        int rocketX = 10;
        int rocketY = PANEL_HEIGHT / 2;
        rocket = new Rocket(this, rocketX, rocketY);

        //Start the timer for animation
        Timer t = new Timer(DELAY, event ->{
            for (Asteroid roid : roids) {
                roid.translate(-1, 0);
                repaint();
            }
            //For checking if there are collisions
            checkCollisions();
        });
        t.start();

        //Spawns the asteroids after interval
        Timer spawnTimer = new Timer(750, e-> {
            addAsteroid();
            for(Asteroid roid : roids)
            {
                roid.translate(-1, 0);
                repaint();
            }
        });
        spawnTimer.start();
    }

    public void addAsteroid() {
        //addAsteroid will choose a random number between 0 and 3, and will add a meteor to the arrayList
        // of size small, medium, or large based on which number was selected via switch statement.
        int tempY = (int) (Math.random() * PANEL_HEIGHT);
        Asteroid.AsteroidSize randomSize = Asteroid.AsteroidSize.SMALL;
        int sizeOptions = (int) (Math.random() * 3);
        switch(sizeOptions)
        {
            case 0:
                randomSize = Asteroid.AsteroidSize.SMALL;
                break;
            case 1:
                randomSize = Asteroid.AsteroidSize.MEDIUM;
                break;
            case 2:
                randomSize = Asteroid.AsteroidSize.LARGE;
                break;
        }
        roids.add(new Asteroid(this, PANEL_WIDTH, tempY, randomSize));
    }

    public void addRocket(Rocket rocket) {
        this.rocket = rocket;
    }

    @Override
    protected void paintComponent(Graphics g) {
        //Draw the rocket
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        rocket.draw(g2);

        //Draw the asteroids
        for (Asteroid roid : roids) {
            roid.draw(g2);
        }
    }

    private void checkCollisions() {
        //Create the hitbox for the asteroids
        //If an asteroid collides with the ship, it will be placed in this arrayList to be removed from the display
        ArrayList<Asteroid> removeAsteroid = new ArrayList<>();
        Rectangle rocketBounds = new Rectangle(rocket.getX(), rocket.getY(), rocket.getRocket().getIconWidth(), rocket.getRocket().getIconHeight());
        for (Asteroid asteroid : roids) {
            Rectangle asteroidBounds = new Rectangle(asteroid.getX(), asteroid.getY(), asteroid.getMeteor().getIconWidth(), asteroid.getMeteor().getIconHeight());

            //If the rocket collides with an asteroid, triggers explode function
            if (rocketBounds.intersects(asteroidBounds)) {
                rocket.explode();
                removeAsteroid.add(asteroid);
            }

            //Check if the laser collides with an asteroid, if so, both asteroid and laser will be removed.
            for (Rocket.Laser laser : rocket.lasers) {
                if (laser.intersects(asteroid)) {
                    rocket.lasers.remove(laser);
                    removeAsteroid.add(asteroid);
                    break;
                }
            }
        }
        //Remove the asteroids and lasers that need to be removed
        roids.removeAll(removeAsteroid);
    }
}
