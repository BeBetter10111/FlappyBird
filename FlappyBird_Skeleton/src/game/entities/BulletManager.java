package game.entities;

import game.core.AssetLoader;
import game.core.Resettable;
import game.core.SpriteCleaner;
import game.core.Updatable;
import game.rendering.Renderable;
import game.utils.AssetPaths;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class BulletManager implements Updatable, Renderable, Resettable {

    private final List<Bullet> bullets = new ArrayList<>();
    private final BufferedImage sprite;

    public BulletManager(AssetLoader loader) {
        this.sprite = SpriteCleaner.removeLightBackground(loader.loadImage(AssetPaths.BULLET));
    }

    public void fire(int x, int y) {
        bullets.add(new Bullet(sprite, x, y));
    }

    @Override
    public void update() {
        for (Bullet bullet : bullets) {
            bullet.update();
        }
        bullets.removeIf(bullet -> !bullet.isActive() || bullet.isOffScreen());
    }

    public void checkCollisionWith(List<Monster> monsters) {
        for (Bullet bullet : bullets) {
            if (bullet.isActive()) {
                for (Monster monster : monsters) {
                    if (monster.isAlive() && bullet.collidesWith(monster)) {
                        monster.takeDamage(1);
                        bullet.deactivate();
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        for (Bullet bullet : bullets) {
            bullet.render(g);
        }
    }

    @Override
    public void reset() {
        bullets.clear();
    }
}
