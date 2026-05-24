package game.core;

public class ScoreManager implements Resettable {
    private int score;
    private int highScore;

    public ScoreManager() {
        this.score = 0;
        this.highScore = 0;
    }

    public void addPoint() {
        score += 1;
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

    public int getScore()     { return score; }
    public int getHighScore() { return highScore; }
}