package game.entities;

import game.core.AssetLoader;
import game.core.Collidable;
import game.core.PowerUpCollisionListener;
import game.core.Resettable;
import game.core.SpriteCleaner;
import game.core.Updatable;
import game.rendering.Renderable;
import game.utils.AssetPaths;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Quản lý DashPowerUp và BulletPowerUp:
 * - Spawn 40% DashPowerUp hoặc 30% BulletPowerUp mỗi lần PipeManager báo có cặp ống mới
 * - Tự kiểm tra collision với bird (DIP — không gọi thẳng Bird)
 * - Fire listener khi va chạm
 */
public class PowerUpManager implements Updatable, Renderable, Resettable {

    private final List<DashPowerUp> dashPowerUps = new ArrayList<>();
    private final List<BulletPowerUp> bulletPowerUps = new ArrayList<>();
    private final BufferedImage     dashIcon;
    private final BufferedImage     bulletIcon;
    private final Random            random   = new Random();

    private static final double DASH_SPAWN_CHANCE = 0.40;
    private static final double BULLET_SPAWN_CHANCE = 0.30;

    // DIP — phụ thuộc abstraction
    private PowerUpCollisionListener collisionListener;
    private Collidable               birdCollidable;

    public PowerUpManager(AssetLoader loader) {
        this.dashIcon = loader.loadImage(AssetPaths.DASH_ICON);
        this.bulletIcon = SpriteCleaner.removeLightBackground(loader.loadImage(AssetPaths.BULLET));
    }

    /** GameLoop gọi để inject bird + listener (DIP). */
    public void setCollisionTarget(Collidable bird, PowerUpCollisionListener listener) {
        this.birdCollidable    = bird;
        this.collisionListener = listener;
    }

    /** PipeManager gọi mỗi khi spawn cặp ống mới. */
    public void onPipePairSpawned(int spawnX, int gapCenterY) {
        //Kiểm
        if (birdCollidable instanceof Bird && ((Bird) birdCollidable).isDashing()) {
            return;
        }

        double rand = random.nextDouble();
        if (rand < DASH_SPAWN_CHANCE) {
            dashPowerUps.add(new DashPowerUp(dashIcon, spawnX, gapCenterY));
        } else if (rand < DASH_SPAWN_CHANCE + BULLET_SPAWN_CHANCE) {
            bulletPowerUps.add(new BulletPowerUp(bulletIcon, spawnX, gapCenterY));
        }
    }

    @Override
    public void update() {
        if (birdCollidable == null || collisionListener == null) {
            return;
        }

        // Update dash powerups
        for (DashPowerUp pu : dashPowerUps) {
            pu.update();
            if (!pu.isCollected() && birdCollidable.collidesWith(pu)) {
                pu.collect();
                collisionListener.onDashCollected();
            }
        }
        dashPowerUps.removeIf(pu -> pu.isOffScreen() || pu.isCollected());

        // Update bullet powerups
        for (BulletPowerUp pu : bulletPowerUps) {
            pu.update();
            if (!pu.isCollected() && birdCollidable.collidesWith(pu)) {
                pu.collect();
                if (birdCollidable instanceof Bird) {
                    ((Bird) birdCollidable).collectBullet();
                }
            }
        }
        bulletPowerUps.removeIf(pu -> pu.isOffScreen() || pu.isCollected());
    }

    @Override
    public void render(Graphics2D g) {
        for (DashPowerUp pu : dashPowerUps) {
            pu.render(g);
        }
        for (BulletPowerUp pu : bulletPowerUps) {
            pu.render(g);
        }
    }

    @Override
    public void reset() {
        dashPowerUps.clear();
        bulletPowerUps.clear();
    }
}
