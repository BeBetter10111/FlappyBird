package game;

import game.core.GameLoop;
import game.utils.GameConstants;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Flappy Bird");
            GameLoop gameLoop = new GameLoop();

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.add(gameLoop);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            gameLoop.start();
        });
    }
}
