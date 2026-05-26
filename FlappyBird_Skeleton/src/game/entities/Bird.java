package game.entities;

import game.core.*;
import game.rendering.BirdRenderer;
import game.rendering.Renderable;
import game.utils.AssetPaths;
import game.utils.GameConstants;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Bird implements Updatable, Renderable, Collidable, Resettable {
    private final DashController dashController;
    private final BirdRenderer renderer;
    private BulletManager bulletManager;
    public int x,y, width, height;
    private BufferedImage[] image; 
    private int frameIndex; 
    private double velocityY; 
    private long lastFlapTime; 
    private long lastFireTime; 
    private static final int FIRE_COOLDOWN = 500; 
    private int bulletCount = 0; 

    public Bird(AssetLoader loader, DashController dashController) {
        this.dashController = dashController;
        this.renderer = new BirdRenderer(dashController);
        this.width = 48;
        this.height = 24;
        this.image = new BufferedImage[3];
        this.image[0] = SpriteCleaner.removeLightBackground(loader.loadImage(AssetPaths.BIRD_DOWN));
        this.image[1] = SpriteCleaner.removeLightBackground(loader.loadImage(AssetPaths.BIRD_MID));
        this.image[2] = SpriteCleaner.removeLightBackground(loader.loadImage(AssetPaths.BIRD_UP));
        this.x = GameConstants.BIRD_START_X;
        this.y = GameConstants.BIRD_START_Y;
        this.frameIndex = 0;
        this.velocityY = 0;
        this.lastFlapTime = System.currentTimeMillis();
        this.lastFireTime = System.currentTimeMillis();
    }

    public void setBulletManager(BulletManager bulletManager) {
        this.bulletManager = bulletManager;
    }

    @Override
    public void update() {
        velocityY += GameConstants.GRAVITY;
        y += (int) velocityY; 
        long now = System.currentTimeMillis(); 
        if (now - lastFlapTime >= GameConstants.BIRD_FLAP_INTERVAL) {
            if (image != null && image.length > 0) {
                frameIndex = (frameIndex + 1) % image.length;
            }
            lastFlapTime = now;
        }
        
        // Tự động bắn đạn nếu có bullet
        if (bulletCount > 0 && now - lastFireTime >= FIRE_COOLDOWN && bulletManager != null) {
            bulletManager.fire(x + width / 2, y);
            bulletCount--;
            lastFireTime = now;
        }
        
        dashController.update();
    }

    @Override
    public void render(Graphics2D g) {
        renderer.render(g, image[frameIndex], x, y, velocityY);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle( x, y, width, height);
    }

    @Override
    public boolean collidesWith(Collidable other) {

        return this.getBounds().intersects(other.getBounds());
    }

    @Override
    public void reset() {
        x = GameConstants.BIRD_START_X;
        y = GameConstants.BIRD_START_Y;
        velocityY = 0;
        frameIndex = 0;
        bulletCount = 0;
        lastFlapTime = System.currentTimeMillis();
        lastFireTime = System.currentTimeMillis();
        dashController.reset();
    }

    public void flap() {
        velocityY = GameConstants.FLAP_STRENGTH;
    }

    public void collectBullet() {
        bulletCount += 3; 
    }

    public void activateDash() { dashController.activate(); }

    public boolean isDashing()    { return dashController.isActive(); }
    public boolean isInvincible() { return dashController.isInvincible(); }
    public float   getDashSpeedMultiplier() { return dashController.getSpeedMultiplier(); }

    public boolean isOutOfBounds(){
        return y <= GameConstants.BIRD_TOP_BOUND || y >= GameConstants.FLOOR_Y;
    }

    public int getX() { return x; }
    public int getY() { return y; }
}
