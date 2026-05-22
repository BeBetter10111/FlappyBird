package game.core;

import game.entities.Bird;
import game.entities.Pipe;

import java.util.List;

/**
 * Kiểm tra bird có va chạm pipe nào không.
 * Có thể mở rộng cho Monster, Bullet.
 */
public class CollisionDetector {

    /**
     * @return true nếu bird ra ngoài màn hình HOẶC va chạm 1 pipe.
     */
    public boolean hasCollision(Bird bird, List<Pipe> pipes) {
        // TODO:
        // 1) Nếu bird.isOutOfBounds() → return true
        if (bird.isOutOfBounds()) {
            return true;
        }
        // 2) Loop pipes, nếu bird.collidesWith(pipe) → return true
        for (Pipe pipe : pipes) {
            if (bird.collidesWith(pipe)) {
                return true;
            }
        }
        // 3) return false
        return false;
    }
}
