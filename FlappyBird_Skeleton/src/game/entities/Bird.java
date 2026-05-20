package game.entities;

import game.core.AssetLoader;
import game.core.Collidable;
import game.core.DashController;
import game.core.Resettable;
import game.core.Updatable;
import game.rendering.Renderable;
import game.utils.AssetPaths;
import game.utils.GameConstants;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Chim người chơi.
 * Chỉ lo physics + animation + render (SRP).
 * Dash logic delegate hoàn toàn cho DashController.
 */
public class Bird implements Updatable, Renderable, Collidable, Resettable {

    // TODO: 3 frame sprite (down, mid, up), frameIndex, lastFlapTime
    // TODO: physics (velocityY, x, y)

    private final DashController dashController;

    public Bird(AssetLoader loader, DashController dashController) {
        this.dashController = dashController;
        // TODO: load 3 frame từ AssetPaths.BIRD_DOWN, BIRD_MID, BIRD_UP
        // TODO: init x = BIRD_START_X, y = BIRD_START_Y, velocityY = 0
    }

    @Override
    public void update() {
        // TODO:
        // 1) velocityY += GRAVITY; y += velocityY
        // 2) Animation: nếu now - lastFlapTime >= BIRD_FLAP_INTERVAL → đổi frame
        // 3) dashController.update()
    }

    @Override
    public void render(Graphics2D g) {
        // TODO:
        // 1) Tính drawX, drawY (center sprite)
        // 2) Xoay theo velocityY (góc = -velocityY * 3 độ)
        // 3) Nếu dashController.getRainbowTint() != null → vẽ rainbow tint
        //    Nếu dashController.isWhiteFlicker() → vẽ trắng nhấp nháy
        //    Còn lại → vẽ bình thường
    }

    @Override
    public Rectangle getBounds() {
        // TODO: trả về Rectangle bao quanh frame hiện tại
        return new Rectangle();
    }

    @Override
    public boolean collidesWith(Collidable other) {
        // TODO: return getBounds().intersects(other.getBounds())
        return false;
    }

    @Override
    public void reset() {
        // TODO: reset x, y, velocityY, frameIndex, dashController.reset()
    }

    public void flap() {
        // TODO: velocityY = FLAP_STRENGTH
    }

    public void activateDash() { dashController.activate(); }

    public boolean isDashing()    { return dashController.isActive(); }
    public boolean isInvincible() { return dashController.isInvincible(); }
    public float   getDashSpeedMultiplier() { return dashController.getSpeedMultiplier(); }

    public boolean isOutOfBounds() {
        // TODO: y <= BIRD_TOP_BOUND || y >= FLOOR_Y
        return false;
    }

    public int getX() { /* TODO */ return 0; }
    public int getY() { /* TODO */ return 0; }
}
