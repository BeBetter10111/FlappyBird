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
        // TODO: load sprite bullet (thêm vào AssetPaths)
        this.sprite = null;
    }

    /** Bird hoặc GameLoop gọi để bắn 1 viên đạn từ vị trí (x, y). */
    public void fire(int x, int y) {
        // TODO: bullets.add(new Bullet(sprite, x, y));
    }

    @Override
    public void update() {
        // TODO:
        // 1) Update từng bullet
        // 2) Xoá bullet nào !isActive() hoặc isOffScreen()
    }

    /** Check collision với danh sách monster, gây damage và deactivate bullet. */
    public void checkCollisionWith(List<Monster> monsters) {
        // TODO:
        // Loop bullets — với mỗi bullet active:
        //   Loop monsters — nếu alive và collidesWith bullet:
        //     monster.takeDamage(1)
        //     bullet.deactivate()
        //     break (bullet chỉ trúng 1 monster)
    }

    @Override
    public void render(Graphics2D g) {
        // TODO
    }

    @Override
    public void reset() {
        // TODO: clear list
    }
}
