package game.core;

import java.awt.Rectangle;

public interface Collidable {
    Rectangle getBounds();
    boolean collidesWith(Collidable other);
}
