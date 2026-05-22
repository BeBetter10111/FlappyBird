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
 * Icon Dash xuất hiện giữa khe ống, di chuyển theo tốc độ pipe.
 * Khi bird ăn → collect(), sẽ bị xoá khỏi list ở manager.
 */
public class DashPowerUp implements Updatable, Renderable, Collidable {

    private int x;
    private final int y;
    private boolean collected;
    private final BufferedImage icon;
    private int rainbowTick = 0;

    // Halo cycle rainbow xung quanh icon
    private static final Color[] RAINBOW = {
        new Color(255, 0,   0,   200),
        new Color(255, 127, 0,   200),
        new Color(255, 255, 0,   200),
        new Color(0,   200, 0,   200),
        new Color(0,   0,   255, 200),
        new Color(75,  0,   130, 200),
        new Color(148, 0,   211, 200),
    };

    public DashPowerUp(BufferedImage icon, int x, int y) {
        this.icon = icon;
        this.x    = x;
        this.y    = y;
        this.collected = false;
    }

    @Override
    public void update() {
        // TODO: x -= PIPE_SPEED, rainbowTick++
    }

    @Override
    public void render(Graphics2D g) {
        // TODO:
        // 1) Nếu collected → return
        // 2) Tính drawX, drawY (center icon)
        // 3) Vẽ halo rainbow (g.fillOval với màu RAINBOW[rainbowTick/4 % 7])
        // 4) Vẽ icon đè lên
    }

    @Override
    public Rectangle getBounds() {
        // TODO: rect bao quanh icon
        return new Rectangle();
    }

    @Override
    public boolean collidesWith(Collidable other) {
        return getBounds().intersects(other.getBounds());
    }

    public boolean isCollected() { return collected; }
    public void    collect()     { collected = true; }
    public boolean isOffScreen() {
        // TODO: x < -icon.getWidth()
        return false;
    }
    public int getX() { return x; }
}
