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
        this.gameFont       = loader.loadFont(AssetPaths.FONT, GameConstants.FONT_SIZE);
        this.messageOverlay = loader.loadImage(AssetPaths.MESSAGE);
        this.gameOverImage  = loader.loadImage(AssetPaths.GAME_OVER);
    }

    /** Vẽ điểm số ở giữa trên cùng khi đang chơi. */
    public void renderMainScore(Graphics2D g, ScoreManager scoreManager) {
        g.setFont(gameFont);
        g.setColor(Color.WHITE);
        String text = String.valueOf(scoreManager.getScore());
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int x = GameConstants.SCREEN_WIDTH / 2 - textWidth / 2;
        int y = GameConstants.SCORE_TOP_Y;
        g.drawString(text, x, y);
    }

    /** Vẽ màn hình game over: overlay + score + high score. */
    public void renderGameOverScreen(Graphics2D g, ScoreManager scoreManager) {
        int overlayX = (GameConstants.SCREEN_WIDTH - messageOverlay.getWidth()) / 2;
        int overlayY = (GameConstants.SCREEN_HEIGHT - messageOverlay.getHeight()) / 2;
        g.drawImage(messageOverlay, overlayX, overlayY, null); 
    }
}
