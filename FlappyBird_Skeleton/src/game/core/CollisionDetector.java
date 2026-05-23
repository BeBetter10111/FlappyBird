package game.core;

import game.entities.Bird;
import game.entities.Pipe;
import game.entities.Monster;

import java.util.List;

public class CollisionDetector {

    /**
     * @return true nếu bird ra ngoài màn hình HOẶC va chạm 1 pipe.
     */

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

    public boolean checkCollisionWithMonsters(Bird bird, List<Monster> monsters) {
        for (Monster monster : monsters) {
            if (monster.isAlive() && bird.collidesWith(monster)) {
                return true;
            }
        }
        return false;
    }
}
