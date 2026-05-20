package game.core;

/**
 * Đếm điểm + lưu high score.
 * Mỗi tick gọi increment() — score sẽ tăng đều theo thời gian sống.
 */
public class ScoreManager implements Resettable {

    // TODO: 2 field score và highScore (kiểu double để tăng từ từ)
    private static final double SCORE_INCREMENT = 0.01;

    public ScoreManager() {
        // TODO: init score = 0, highScore = 0
    }

    public void increment() {
        // TODO: score += SCORE_INCREMENT;
    }

    public void updateHighScore() {
        // TODO: nếu score > highScore thì gán highScore = score
    }

    @Override
    public void reset() {
        // TODO: chỉ reset score (giữ highScore)
    }

    public int getScore() {
        // TODO: return (int) score;
        return 0;
    }

    public int getHighScore() {
        // TODO
        return 0;
    }
}
