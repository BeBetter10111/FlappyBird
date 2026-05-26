package game.entities;

import game.core.AssetLoader;
import game.core.Updatable;
import game.rendering.Renderable;
import game.utils.AssetPaths;
import game.utils.GameConstants;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Floor implements Updatable, Renderable {

    private final BufferedImage image;
    private int xPosition;

    public Floor(AssetLoader loader) {
        this.image = loader.loadScaledImage(AssetPaths.FLOOR, 2);
        this.xPosition = 0;
    }

    @Override
    public void update() {
        xPosition -= 1;
        if (xPosition <= -GameConstants.FLOOR_WIDTH) {
            xPosition = 0;
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(image, xPosition, GameConstants.FLOOR_Y, null);
        g.drawImage(image, xPosition + GameConstants.FLOOR_WIDTH, GameConstants.FLOOR_Y, null);
    }
}
