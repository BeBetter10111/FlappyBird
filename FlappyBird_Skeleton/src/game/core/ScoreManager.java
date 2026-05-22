package game.core;

import game.entities.Bird;
import game.entities.Pipe;

import java.util.List;

public class ScoreManager implements Resettable {

    private static final double SCORE_INCREMENT = 0.01;
    private double score;
    private double highScore;

    public ScoreManager() {
        this.score = 0;
        this.highScore = 0;
    }

    public void increment() {
        score++;
    }

    public void updateHighScore() {
        if (score > highScore) {
            highScore = score;
        }
    }

    @Override
    public void reset() {
        score = 0;
    }

    public int getScore() {
        return (int) score;
    }

    public int getHighScore() {
        return (int) highScore;
    }
}
