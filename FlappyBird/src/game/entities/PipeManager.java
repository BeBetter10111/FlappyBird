package game.entities;

import game.core.Resettable;
import game.core.Updatable;
import game.rendering.Renderable;
import game.utils.GameConstants;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PipeManager implements Updatable, Renderable, Resettable {

    private final List<Pipe> pipes;
    private final PipeFactory factory;
    private long lastSpawnTime;
    private static final long SPAWN_INTERVAL_MS = 1200;

    // optional listener notified when a new pair spawns
    private PowerUpManager powerUpManager = null;

    public PipeManager(PipeFactory factory) {
        this.factory       = factory;
        this.pipes         = new ArrayList<>();
        this.lastSpawnTime = System.currentTimeMillis();
    }

    /** Wire up the PowerUpManager so it can react to new pipe pairs. */
    public void setPowerUpManager(PowerUpManager pum) {
        this.powerUpManager = pum;
    }

    // ── speed multiplier (set each tick by GameLoop during dash) ──────────────
    private float speedMultiplier = 1.0f;

    public void setSpeedMultiplier(float m) { this.speedMultiplier = m; }

    // ─────────────────────────────────────────────────────────────────────────
    @Override
    public void update() {
        long now = System.currentTimeMillis();
        if (now - lastSpawnTime >= SPAWN_INTERVAL_MS) {
            List<Pipe> pair = factory.createPipePair();
            pipes.addAll(pair);

            // notify PowerUpManager with the gap centre
            if (powerUpManager != null) {
                int gapCenterY = factory.getLastGapCenterY();
                powerUpManager.onPipePairSpawned(GameConstants.PIPE_SPAWN_X, gapCenterY);
            }

            lastSpawnTime = now;
        }

        for (Pipe pipe : pipes) {
            pipe.update(speedMultiplier);
        }

        Iterator<Pipe> it = pipes.iterator();
        while (it.hasNext()) {
            if (it.next().isOffScreen()) it.remove();
        }
    }

    @Override
    public void render(Graphics2D g) {
        for (Pipe pipe : pipes) pipe.render(g);
    }

    @Override
    public void reset() {
        pipes.clear();
        lastSpawnTime  = System.currentTimeMillis();
        speedMultiplier = 1.0f;
    }

    public List<Pipe> getPipes() { return pipes; }
}