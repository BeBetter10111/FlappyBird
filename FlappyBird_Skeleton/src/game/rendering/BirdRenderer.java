package game.rendering;

import game.core.DashController;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class BirdRenderer {

    private final DashController dashController;

    public BirdRenderer(DashController dashController) {
        this.dashController = dashController;
    }

    public void render(Graphics2D g, BufferedImage frame, int x, int y, double velocityY) {
        int drawX = x - frame.getWidth()  / 2;
        int drawY = y - frame.getHeight() / 2;

        double angle = Math.toRadians(-velocityY * 3);
        AffineTransform old = g.getTransform();
        g.rotate(angle, x, y);

        Color rainbowTint = dashController.getRainbowTint();
        if (rainbowTint != null) {
            renderWithTint(g, frame, drawX, drawY, rainbowTint, 0.45f);
        } else if (dashController.isWhiteFlicker()) {
            renderWithTint(g, frame, drawX, drawY, Color.WHITE, 0.55f);
        } else {
            g.drawImage(frame, drawX, drawY, null);
        }

        g.setTransform(old);
    }

    /**
     * Vẽ sprite với 1 màu overlay (rainbow hoặc trắng).
     * Tách method riêng vì 2 mode (rainbow/white) cùng logic, khác màu.
     */
    private void renderWithTint(Graphics2D g, BufferedImage frame, int drawX, int drawY,
                                Color tint, float tintAlpha) {
        Composite prev = g.getComposite();

        // Layer 1: sprite gốc
        g.drawImage(frame, drawX, drawY, null);

        // Layer 2: tô màu overlay
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, tintAlpha));
        g.setColor(tint);
        g.fillRect(drawX, drawY, frame.getWidth(), frame.getHeight());

        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.55f));
        g.drawImage(frame, drawX, drawY, null);

        g.setComposite(prev);
    }
}