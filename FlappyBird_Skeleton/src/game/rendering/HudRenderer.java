package game.rendering;

import game.core.AssetLoader;
import game.core.ScoreManager;
import game.utils.AssetPaths;
import game.utils.GameConstants;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Vẽ HUD: điểm số trong game, màn hình game over.
 */
public class HudRenderer {

    private final Font          gameFont;
    private final BufferedImage messageOverlay;
    private final BufferedImage gameOverImage;

    public HudRenderer(AssetLoader loader) {
        // TODO: load font + messageOverlay + gameOverImage
        this.gameFont       = null;
        this.messageOverlay = null;
        this.gameOverImage  = null;
    }

    /** Vẽ điểm số ở giữa trên cùng khi đang chơi. */
    public void renderMainScore(Graphics2D g, ScoreManager scoreManager) {
        // TODO:
        // - set font + color trắng
        // - text = scoreManager.getScore()
        // - căn giữa theo SCORE_CENTER_X, vẽ tại SCORE_TOP_Y
    }

    /** Vẽ màn hình game over: overlay + score + high score. */
    public void renderGameOverScreen(Graphics2D g, ScoreManager scoreManager) {
        // TODO:
        // - Vẽ messageOverlay giữa màn hình
        // - Vẽ "Score: X" ở SCORE_TOP_Y
        // - Vẽ "High Score: Y" ở SCORE_BOTTOM_Y
    }
}
