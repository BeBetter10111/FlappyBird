package game.entities;

import game.core.*;
import game.rendering.BirdRenderer;
import game.rendering.Renderable;
import game.utils.AssetPaths;
import game.utils.GameConstants;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Chim người chơi.
 * Chỉ lo physics + animation + render (SRP).
 * Dash logic delegate hoàn toàn cho DashController.
 */
public class Bird implements Updatable, Renderable, Collidable, Resettable {
    private final DashController dashController;
    private final BirdRenderer renderer;
    private BulletManager bulletManager;
    public int x,y, width, height;
    private BufferedImage[] image; // mảng lưu 3 frame ảnh của chim
    private int frameIndex; //Frame ảnh hiện tại
    private double velocityY; // vận tốc theo trục Y
    private long lastFlapTime; // lưu mốc thời gian
    private long lastFireTime; // lưu mốc thời gian bắn đạn tự động
    private static final int FIRE_COOLDOWN = 500; // ms giữa các lần bắn tự động
    private int bulletCount = 0; // số lượng bullet có sẵn

    public Bird(AssetLoader loader, DashController dashController) {
        this.dashController = dashController;
        this.renderer = new BirdRenderer(dashController);
        this.width = 48;
        this.height = 24;
        // cap phat mang chua 3 khung anh, goi bo tai tai nguyen chua 3 anh cua chim
        this.image = new BufferedImage[3];
        this.image[0] = SpriteCleaner.removeLightBackground(loader.loadImage(AssetPaths.BIRD_DOWN));
        this.image[1] = SpriteCleaner.removeLightBackground(loader.loadImage(AssetPaths.BIRD_MID));
        this.image[2] = SpriteCleaner.removeLightBackground(loader.loadImage(AssetPaths.BIRD_UP));
        // dinh vi vi tri ban dau cua chim
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
        //Mỗi tick rơi thêm GRAVITY
        velocityY += GameConstants.GRAVITY; // trong luc keo chim xuong bang cach cong don vao van toc roi
        y += (int) velocityY; // cap nhat vi tri y cua chim dua vao van toc hien tai
        /*
        Khi velocityY càng tăng mỗi tick thì cái vận tốc càng nhanh -> cập nhật vị trí ở cột Y theo cái vận tốc hiện tại
        VD: velocity = 5.00 -> y += 5; ( dịch chuyển xuống 5 px)
         */
        long now = System.currentTimeMillis(); // lay thoi gian hien tai
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
        bulletCount += 3; // mỗi lần nhặt được 3 viên đạn
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
