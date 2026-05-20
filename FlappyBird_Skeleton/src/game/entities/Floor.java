package game.entities;

import game.core.AssetLoader;
import game.core.Updatable;
import game.rendering.Renderable;
import game.utils.AssetPaths;
import game.utils.GameConstants;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Sàn cuộn vô tận — vẽ 2 ảnh nối tiếp, dịch trái mỗi tick,
 * khi ảnh đầu ra khỏi màn hình thì reset về 0.
 */
public class Floor implements Updatable, Renderable {

    private final BufferedImage image;
    private int xPosition;

    public Floor(AssetLoader loader) {
        // TODO: load image từ AssetPaths.FLOOR (scale 2)
        this.image = null;
        this.xPosition = 0;
    }

    @Override
    public void update() {
        // TODO: xPosition -= 1
        // TODO: nếu xPosition <= -FLOOR_WIDTH → xPosition = 0
    }

    @Override
    public void render(Graphics2D g) {
        // TODO: vẽ image tại 2 vị trí:
        // (xPosition, FLOOR_Y)
        // (xPosition + FLOOR_WIDTH, FLOOR_Y)
    }
}
