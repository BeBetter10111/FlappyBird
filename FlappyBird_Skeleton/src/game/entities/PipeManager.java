package game.entities;

import game.core.Resettable;
import game.core.Updatable;
import game.rendering.Renderable;
import game.utils.GameConstants;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Danh sách Pipe — spawn mỗi 1.2 giây, xoá pipe ra ngoài màn hình.
 * Báo cho PowerUpManager mỗi lần spawn cặp ống mới.
 */
public class PipeManager implements Updatable, Renderable, Resettable {

    private final List<Pipe> pipes = new ArrayList<>();
    private final PipeFactory factory;
    private PowerUpManager powerUpManager = null;   // optional listener
    private float speedMultiplier = 1.0f;           // GameLoop set khi dash

    private float spawnTicker = 0;
    private static final float SPAWN_INTERVAL_TICKS = 120;

    public PipeManager(PipeFactory factory) {
        this.factory = factory;
    }

    public void setPowerUpManager(PowerUpManager pum) { this.powerUpManager = pum; }
    public void setSpeedMultiplier(float m)           { this.speedMultiplier = m; }

    @Override
    public void update() {
        spawnTicker += speedMultiplier;

        if(spawnTicker >= SPAWN_INTERVAL_TICKS){
            List<Pipe> pairs = factory.createPipePair();
            pipes.addAll(pairs);

            if(powerUpManager != null){
                powerUpManager.onPipePairSpawned(
                        GameConstants.PIPE_SPAWN_X,
                        factory.getLastGapCenterY()
                );
            }
            spawnTicker = 0;
        }
        for(Pipe pipe : pipes){
            pipe.update(speedMultiplier);
        }

        Iterator<Pipe> it = pipes.iterator();
        while (it.hasNext()) {
            if (it.next().isOffScreen()) it.remove();
        }

    }

    @Override
    public void render(Graphics2D g) {
        pipes.forEach(p -> p.render(g));
    }

    @Override
    public void reset() {

        pipes.clear();
        spawnTicker = 0;
        speedMultiplier = 1.0f;
    }

    public List<Pipe> getPipes() { return pipes; }
}
