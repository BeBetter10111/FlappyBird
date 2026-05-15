package game.rendering;

import game.core.AssetLoader;
import game.core.ScoreManager;
import game.utils.AssetPaths;
import game.utils.GameConstants;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HudRenderer {
    private final Font gameFont;
    private final BufferedImage messageOverlay;
    private final BufferedImage gameOverImage;

    public HudRenderer(AssetLoader loader) {
        gameFont = loader.loadFont(AssetPaths.FONT, GameConstants.FONT_SIZE);
        messageOverlay = loader.loadScaledImage(AssetPaths.MESSAGE, 2);
        gameOverImage = loader.loadImage(AssetPaths.GAME_OVER);
    }

    public void renderMainScore(Graphics2D g, ScoreManager scoreManager) {
        g.setFont(gameFont);
        g.setColor(Color.WHITE);
        String text = String.valueOf(scoreManager.getScore());
        FontMetrics fm = g.getFontMetrics();
        int textX = GameConstants.SCORE_CENTER_X - fm.stringWidth(text) / 2;
        g.drawString(text, textX, GameConstants.SCORE_TOP_Y);
    }

    public void renderGameOverScreen(Graphics2D g, ScoreManager scoreManager) {
        int imgX = GameConstants.SCORE_CENTER_X - messageOverlay.getWidth() / 2;
        int imgY = GameConstants.SCREEN_HEIGHT / 2 - messageOverlay.getHeight() / 2;
        g.drawImage(messageOverlay, imgX, imgY, null);

        g.setFont(gameFont);
        g.setColor(Color.WHITE);

        String score = "Score: " + scoreManager.getScore();
        FontMetrics fm = g.getFontMetrics();
        g.drawString(score, GameConstants.SCORE_CENTER_X - fm.stringWidth(score) / 2, GameConstants.SCORE_TOP_Y);

        String high = "High Score: " + scoreManager.getHighScore();
        g.drawString(high, GameConstants.SCORE_CENTER_X - fm.stringWidth(high) / 2, GameConstants.SCORE_BOTTOM_Y);
    }
}
