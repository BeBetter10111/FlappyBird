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
        // TODO:
        // 1) Nếu now - lastSpawnTime >= SPAWN_INTERVAL_MS:
        //    - Tạo cặp pipe mới qua factory, add vào list
        //    - Báo cho powerUpManager (nếu != null):
        //      powerUpManager.onPipePairSpawned(PIPE_SPAWN_X, factory.getLastGapCenterY())
        //    - Update lastSpawnTime
        // 2) Update từng pipe với speedMultiplier
        // 3) Xoá pipe nào isOffScreen() khỏi list
    }

    @Override
    public void render(Graphics2D g) {
        // TODO: loop và gọi pipe.render(g)
    }

    @Override
    public void reset() {
        // TODO: clear list, reset lastSpawnTime, speedMultiplier = 1.0f
    }

    public List<Pipe> getPipes() { return pipes; }
}
