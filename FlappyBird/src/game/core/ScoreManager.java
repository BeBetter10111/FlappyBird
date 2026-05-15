package game.core;

public class ScoreManager implements Resettable {
    private double score;
    private double highScore;
    private static final double SCORE_INCREMENT = 0.01;

    public ScoreManager() {
        score = 0;
        highScore = 0;
    }

    public void increment() {
        score += SCORE_INCREMENT;
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
