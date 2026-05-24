package game.entities;

import game.core.Collidable;
import game.core.Updatable;
import game.rendering.Renderable;
import game.utils.GameConstants;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Icon Bullet xuất hiện giữa khe ống, di chuyển theo tốc độ pipe.
 * Khi bird ăn → collect(), sẽ bị xoá khỏi list ở manager.
 */
public class BulletPowerUp implements Updatable, Renderable, Collidable {

    private int x;
    private final int y;
    private boolean collected;
    private final BufferedImage icon;
    private int rainbowTick = 0;

    private static final Color[] RAINBOW = {
        new Color(255, 127, 0,   200),
        new Color(255, 200, 0,   200),
        new Color(255, 255, 0,   200),
        new Color(200, 255, 0,   200),
        new Color(100, 255, 0,   200),
    };

    public BulletPowerUp(BufferedImage icon, int x, int y) {
        this.icon = icon;
        this.x    = x;
        this.y    = y;
        this.collected = false;
    }

    @Override
    public void update() {
        x -= GameConstants.PIPE_SPEED;
        ++rainbowTick;
    }

    @Override
    public void render(Graphics2D g) {
        if (collected) return;

        int w = icon.getWidth();
        int h = icon.getHeight();

        int drawX = x - w / 2;
        int drawY = y - h / 2;

        // Halo rainbow phía sau
        Color halo = RAINBOW[(rainbowTick / 4) % RAINBOW.length];
        int haloSize = 18;
        g.setColor(halo);
        g.fillOval(drawX - haloSize / 2, drawY - haloSize / 2,
                w + haloSize, h + haloSize);

        g.drawImage(icon, drawX, drawY, null);
    }

    @Override
    public Rectangle getBounds() {
        int w = icon.getWidth();
        int h = icon.getHeight();
        return new Rectangle( x - w / 2, y - h / 2, w, h);
    }

    @Override
    public boolean collidesWith(Collidable other) {
        return getBounds().intersects(other.getBounds());
    }

    public void collect() { collected = true; }
    public boolean isCollected() { return collected; }
    
    public boolean isOffScreen() {
        return x < -icon.getWidth();
    }
}
