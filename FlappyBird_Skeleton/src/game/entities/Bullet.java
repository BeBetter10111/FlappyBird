package game.entities;

import game.core.Collidable;
import game.utils.AssetPaths;
import game.core.Updatable;
import game.rendering.Renderable;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Bullet implements Updatable, Renderable, Collidable {
    private int x;
    private int y;
    private boolean active;
    private final BufferedImage sprite;

    public Bullet(BufferedImage sprite, int x, int y) {
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.active = true;
    }

    @Override
    public void update() {
        move();
    }

    /** Logic di chuyển — tách method để dễ override sau này. */
    public void move() {
        x += 5;
    }

    @Override
    public void render(Graphics2D g) {
        if (active) {
            g.drawImage(sprite, x, y, null);
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, sprite.getWidth(), sprite.getHeight());
    }

    @Override
    public boolean collidesWith(Collidable other) {
        return getBounds().intersects(other.getBounds());
    }

    public void deactivate() { active = false; }
    public boolean isActive() { return active; }
    public boolean isOffScreen() {
        if (x > 800) {
            return true;
        }
        return false;
    }
}
