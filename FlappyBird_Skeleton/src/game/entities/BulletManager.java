package game.entities;

import game.core.AssetLoader;
import game.core.Resettable;
import game.core.Updatable;
import game.rendering.Renderable;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Quản lý danh sách Bullet.
 * - Nhận lệnh fire() từ GameLoop / Bird khi người chơi bắn
 * - Kiểm tra bullet vs monster (có thể tách ra CollisionDetector)
 * - Xoá bullet ra ngoài màn hình HOẶC đã trúng
 */
public class BulletManager implements Updatable, Renderable, Resettable {

    private final List<Bullet> bullets = new ArrayList<>();
    private final BufferedImage sprite;

    public BulletManager(AssetLoader loader) {
        this.sprite = loader.loadImage(AssetPaths.BULLET);
    }

    /** Bird hoặc GameLoop gọi để bắn 1 viên đạn từ vị trí (x, y). */
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

    /** Check collision với danh sách monster, gây damage và deactivate bullet. */
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
