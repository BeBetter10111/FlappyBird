package game.core;

import java.awt.Rectangle;

/**
 * Interface cho mọi đối tượng có thể va chạm.
 */
public interface Collidable {
    Rectangle getBounds();
    boolean collidesWith(Collidable other);
}
