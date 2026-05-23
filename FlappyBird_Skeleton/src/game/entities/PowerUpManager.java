package game.entities;

import game.core.AssetLoader;
import game.core.Collidable;
import game.core.PowerUpCollisionListener;
import game.core.Resettable;
import game.core.Updatable;
import game.rendering.Renderable;
import game.utils.AssetPaths;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Quản lý DashPowerUp:
 * - Spawn 40% xác suất mỗi lần PipeManager báo có cặp ống mới
 * - Tự kiểm tra collision với bird (DIP — không gọi thẳng Bird)
 * - Fire listener khi va chạm
 */
public class PowerUpManager implements Updatable, Renderable, Resettable {

    private final List<DashPowerUp> powerUps = new ArrayList<>();
    private final BufferedImage     dashIcon;
    private final Random            random   = new Random();

    private static final double SPAWN_CHANCE = 0.40;

    // DIP — phụ thuộc abstraction
    private PowerUpCollisionListener collisionListener;
    private Collidable               birdCollidable;

    public PowerUpManager(AssetLoader loader) {
        this.dashIcon = loader.loadImage(AssetPaths.DASH_ICON);
    }

    /** GameLoop gọi để inject bird + listener (DIP). */
    public void setCollisionTarget(Collidable bird, PowerUpCollisionListener listener) {
        this.birdCollidable    = bird;
        this.collisionListener = listener;
    }

    /** PipeManager gọi mỗi khi spawn cặp ống mới. */
    public void onPipePairSpawned(int spawnX, int gapCenterY) {
        if (random.nextDouble() < SPAWN_CHANCE) {
            powerUps.add(new DashPowerUp(dashIcon, spawnX, gapCenterY));
        }
    }

    @Override
    public void update() {
        for (DashPowerUp pu : powerUps) {
            pu.update();
            if (!pu.isCollected() && birdCollidable.collidesWith(pu)) {
                pu.collect();
                collisionListener.onDashCollected();
            }
        }
        powerUps.removeIf(pu -> pu.isOffScreen() || pu.isCollected());
    }

    @Override
    public void render(Graphics2D g) {
        for (DashPowerUp pu : powerUps) {
            pu.render(g);
        }
    }

    @Override
    public void reset() {
        powerUps.clear();
    }
}
