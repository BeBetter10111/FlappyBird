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
    private final DashController dashController;
    public int x,y, width, height;
    private BufferedImage[] image; // mảng lưu 3 frame ảnh của chim
    private int frameIndex; //Frame ảnh hiện tại
    private double velocityY; // vận tốc theo trục Y
    private long lastFlapTime; // lưu mốc thời gian
    public Bird(int x, int y, int width, int height, DashController dashController) {
        this.x = x;
        this.y = y;
        this.dashController = dashController;
        this.width = 48;
        this.height = 24;
        this.frameIndex = 0;
        this.velocityY = 0;
        this.lastFlapTime = System.currentTimeMillis();
    }
    public Bird(AssetLoader loader, DashController dashController) {
        this.dashController = dashController;
        this.width = 48;
        this.height = 24;
        // cap phat mang chua 3 khung anh, goi bo tai tai nguyen chua 3 anh cua chim
        this.image = new BufferedImage[3];
        this.image[0] = loader.loadImage(AssetPaths.BIRD_DOWN);
        this.image[1] = loader.loadImage(AssetPaths.BIRD_MID);
        this.image[2] = loader.loadImage(AssetPaths.BIRD_UP);
        // dinh vi vi tri ban dau cua chim
        this.x = GameConstants.BIRD_START_X;
        this.y = GameConstants.BIRD_START_Y;
        this.frameIndex = 0;
        this.velocityY = 0;
        this.lastFlapTime = System.currentTimeMillis();
    }
    @Override
    public void update() {
        velocityY += GameConstants.GRAVITY; // trong luc keo chim xuong bang cach cong don vao van toc roi
        y += (int) velocityY; // cap nhat vi tri y cua chim dua vao van toc hien tai
        long now = System.currentTimeMillis(); // lay thoi gian hien tai
        if (now - lastFlapTime >= GameConstants.BIRD_FLAP_INTERVAL) {
            if (image != null && image.length > 0) {
                frameIndex = (frameIndex + 1) % image.length;
            }
            lastFlapTime = now;
        }
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
        return new Rectangle( x, y, width, height);
    }

    @Override
    public boolean collidesWith(Collidable other) {

        return this.getBounds().intersects(other.getBounds());
    }
    public boolean IsOutOfBounds(){
        return y <=  GameConstants.BIRD_TOP_BOUND || y >= GameConstants.FLOOR_Y;
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
