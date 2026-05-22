package game.rendering;

import java.awt.Graphics2D;

/**
 * Interface cho mọi đối tượng vẽ được lên màn hình.
 */
public interface Renderable {
    void render(Graphics2D g);
}
