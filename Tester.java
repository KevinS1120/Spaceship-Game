//Kevin Stinnett
//Exam 2 Coding Portion, Spaceship Asteroid Game

import javax.swing.*;
import java.awt.*;

public class Tester {
    public static void main(String[] args) {
        //Frame for the game
        JFrame frame = new JFrame("Blast them");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.BLACK);

        AsteroidPanel panel = new AsteroidPanel();

        //Set the intial location for the rocket
        int rocketX = 20;
        int rocketY = panel.PANEL_HEIGHT / 2;
        Rocket rocket = new Rocket(panel, rocketX, rocketY);

        panel.addRocket(rocket);

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
