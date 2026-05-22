package game.entities;

import game.core.Resettable;
import game.core.Updatable;
import game.rendering.Renderable;
import game.utils.GameConstants;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Danh sách Pipe — spawn mỗi 1.2 giây, xoá pipe ra ngoài màn hình.
 * Báo cho PowerUpManager mỗi lần spawn cặp ống mới.
 */
public class PipeManager implements Updatable, Renderable, Resettable {

    private final List<Pipe> pipes = new ArrayList<>();
    private final PipeFactory factory;
    private long lastSpawnTime;
    private static final long SPAWN_INTERVAL_MS = 1200;

    private PowerUpManager powerUpManager = null;   // optional listener
    private float speedMultiplier = 1.0f;           // GameLoop set khi dash

    public PipeManager(PipeFactory factory) {
        this.factory = factory;
        this.lastSpawnTime = System.currentTimeMillis();
    }

    public void setPowerUpManager(PowerUpManager pum) { this.powerUpManager = pum; }
    public void setSpeedMultiplier(float m)           { this.speedMultiplier = m; }

    @Override
    public void update() {
        long now = System.currentTimeMillis();
        if (now - lastSpawnTime > SPAWN_INTERVAL_MS) {
            List<Pipe> pair = factory.createPipePair();
            pipes.addAll(pair);
            if (powerUpManager != null) {
                powerUpManager.onPipePairSpawned(
                        GameConstants.PIPE_SPAWN_X, factory.getLastGapCenterY()
                );
            }
            lastSpawnTime = now;
        }
        pipes.forEach(p -> p.update(speedMultiplier));
        pipes.removeIf(Pipe::isOffScreen);
    }

    @Override
    public void render(Graphics2D g) {
        pipes.forEach(p -> p.render(g));
    }

    @Override
    public void reset() {

        pipes.clear();
        lastSpawnTime = System.currentTimeMillis();
        speedMultiplier = 1.0f;
    }

    public List<Pipe> getPipes() { return pipes; }
}
