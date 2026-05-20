package game.entities;

import game.core.Collidable;
import game.core.Updatable;
import game.rendering.Renderable;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Đạn — di chuyển từ trái sang phải (player bắn).
 * Trúng monster → monster.takeDamage(), bullet → isHit = true → bị xoá.
 *
 * TODO: bàn:
 * - Bird tự bắn (auto-fire) hay người chơi bấm phím để bắn?
 * - Bullet đi thẳng hay có gravity?
 */
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
        // TODO: x += BULLET_SPEED (thêm vào GameConstants)
    }

    @Override
    public void render(Graphics2D g) {
        // TODO: nếu active → vẽ sprite
    }

    @Override
    public Rectangle getBounds() {
        // TODO
        return new Rectangle();
    }

    @Override
    public boolean collidesWith(Collidable other) {
        return getBounds().intersects(other.getBounds());
    }

    public void deactivate() { active = false; }
    public boolean isActive() { return active; }
    public boolean isOffScreen() {
        // TODO: x > SCREEN_WIDTH
        return false;
    }
}
