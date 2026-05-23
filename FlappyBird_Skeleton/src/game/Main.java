package game;

import game.core.GameLoop;
import game.core.GameLoopBuilder;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Flappy Bird");

            // Builder pattern — tạo GameLoop với toàn bộ dependencies
            GameLoop gameLoop = new GameLoopBuilder().build();

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
