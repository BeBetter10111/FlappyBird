package game.core;

import game.entities.Bird;
import game.entities.Pipe;

import java.util.List;

public class CollisionDetector {
    public boolean hasCollision(Bird bird, List<Pipe> pipes) {
        if (bird.isOutOfBounds()) {
            return true;
        }
        for (Pipe pipe : pipes) {
            if (bird.collidesWith(pipe)) {
                return true;
            }
        }
        return false;
    }
}
