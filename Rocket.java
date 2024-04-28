//Kevin Stinnett
//Exam 2 Coding Portion, Spaceship Asteroid Game

//Import libraries
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Rocket implements MoveableObject {
    private ImageIcon rocket;
    private final JPanel game;
    private int x, y;
    private JLabel gameOver;
    ArrayList<Laser> lasers;

    public Rocket(JPanel canvas, int x, int y) {
        this.game = canvas;
        rocket = new ImageIcon("rocket.png");
        this.x = x;
        this.y = y;
        lasers = new ArrayList<>();

        //Uses WASD to move the rocket around the screen, this creates the keyListeners
        game.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        translate(0, -10);
                        break;
                    case KeyEvent.VK_S:
                        translate(0, 10);
                        break;
                    case KeyEvent.VK_D:
                        translate(10, 0);
                        break;
                    case KeyEvent.VK_A:
                        translate(-10, 0);
                        break;
                }
                game.repaint();
            }
        });

        //Left click mouse to shoot a laser from the ship
        game.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addLaser();
            }
        });

        game.setFocusable(true);
    }

    //Draw the rocket and the lasers when shot
    @Override
    public void draw(Graphics2D g2) {
        rocket.paintIcon(game, g2, x, y);
        for (Laser laser : lasers) {
            laser.draw(g2);
        }
    }

    //For moving things
    @Override
    public void translate(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    //Add the laser at the location of the rocket when mouse is clicked
    private void addLaser() {
        int laserX = x + rocket.getIconWidth();
        int laserY = y + rocket.getIconHeight() / 2;
        lasers.add(new Laser(laserX, laserY, game));
        game.repaint();
    }

    //This is for the bonus points, when the ship collides with an asteroid, it blows up
    public void explode() {
        //Replace rocket png with explosion png
        rocket = new ImageIcon("explosion.png");
        game.repaint();

        //Stop the keyListeners if the ship explodes
        for(KeyListener k1 : game.getKeyListeners())
        {
            game.removeKeyListener(k1);
        }

        //Create a JLabel to display "Game Over!" at the location of explosion
        gameOver = new JLabel("Game Over!");
        gameOver.setFont(new Font("Arial", Font.BOLD, 30));
        gameOver.setForeground(Color.RED);
        gameOver.setBounds(game.getWidth() / 2 - 100, game.getHeight() / 2 - 50, 200, 100);
        game.add(gameOver);

        //Wait for 5 seconds after explosion to end the program so you can see the explosion
        Timer timer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public ImageIcon getRocket() {
        return rocket;
    }

    //X coord for the rocket
    public int getX() {
        return x;
    }

    //Y coord for the rocket
    public int getY() {
        return y;
    }

    public static class Laser {
        private int x, y;
        private final int shiftLaser = 5;
        private final JPanel game;
        private final ImageIcon laserIcon;

        public Laser(int x, int y, JPanel game) {
            this.x = x;
            this.y = y;
            this.game = game;
            laserIcon = new ImageIcon("laser.png");
        }

        public void draw(Graphics2D g2) {
            laserIcon.paintIcon(game, g2, x, y);
            move();
        }

        //Moves the laser across the screen when shot
        public void move() {
            x += shiftLaser;
        }

        //Creates the hitbox for the lasers to check for collisions
        public boolean intersects(Asteroid asteroid) {
            Rectangle laserBounds = new Rectangle(x, y, laserIcon.getIconWidth(), laserIcon.getIconHeight());
            return laserBounds.intersects(asteroid.getX(), asteroid.getY(), asteroid.getMeteor().getIconWidth(), asteroid.getMeteor().getIconHeight());
        }
    }
}
