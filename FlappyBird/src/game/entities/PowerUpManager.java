package game.entities;

import game.core.AssetLoader;
import game.core.Resettable;
import game.core.Updatable;
import game.rendering.Renderable;
import game.utils.AssetPaths;
import game.utils.GameConstants;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class PowerUpManager implements Updatable, Renderable, Resettable {

    private final List<DashPowerUp> powerUps = new ArrayList<>();
    private final BufferedImage dashIcon;
    private final Random random = new Random();

    /** Probability (0.0–1.0) that a pipe pair spawns a dash icon */
    private static final double SPAWN_CHANCE = 0.40;

    public PowerUpManager(AssetLoader loader) {
        dashIcon = loader.loadScaledImage(AssetPaths.DASH_ICON, 2);
    }

    /**
     * Called by PipeManager each time a new pipe pair is spawned.
     * gapCenterY = vertical centre of the gap between the two pipes.
     * spawnX     = same x used by the pipe pair.
     */
    public void onPipePairSpawned(int spawnX, int gapCenterY) {
        if (random.nextDouble() < SPAWN_CHANCE) {
            powerUps.add(new DashPowerUp(dashIcon, spawnX, gapCenterY));
        }
    }

    @Override
    public void update() {
        for (DashPowerUp pu : powerUps) {
            pu.update();
        }
        Iterator<DashPowerUp> it = powerUps.iterator();
        while (it.hasNext()) {
            DashPowerUp pu = it.next();
            if (pu.isOffScreen() || pu.isCollected()) {
                it.remove();
            }
        }
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

    public List<DashPowerUp> getPowerUps() {
        return powerUps;
    }
}