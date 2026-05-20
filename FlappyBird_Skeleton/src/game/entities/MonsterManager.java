package game.entities;

import game.core.AssetLoader;
import game.core.Resettable;
import game.core.Updatable;
import game.rendering.Renderable;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Quản lý danh sách Monster.
 * - Spawn theo thời gian (hoặc theo điểm)
 * - Xoá monster ra ngoài màn hình HOẶC đã chết
 *
 * TODO: tự quyết định cơ chế spawn.
 */
public class MonsterManager implements Updatable, Renderable, Resettable {

    private final List<Monster> monsters = new ArrayList<>();
    private final BufferedImage sprite;
    private final Random random = new Random();
    private long lastSpawnTime;

    private static final long SPAWN_INTERVAL_MS = 3000; // tuỳ chỉnh

    public MonsterManager(AssetLoader loader) {
        // TODO: load sprite monster (thêm vào AssetPaths nếu chưa có)
        this.sprite = null;
        this.lastSpawnTime = System.currentTimeMillis();
    }

    @Override
    public void update() {
        // TODO:
        // 1) Nếu now - lastSpawnTime >= SPAWN_INTERVAL_MS:
        //    - Tạo monster mới (x = spawn ngoài màn hình bên phải, y random)
        //    - add vào list, update lastSpawnTime
        // 2) Update từng monster
        // 3) Xoá monster nào !alive hoặc isOffScreen()
    }

    @Override
    public void render(Graphics2D g) {
        // TODO: loop và render
    }

    @Override
    public void reset() {
        // TODO: clear + reset lastSpawnTime
    }

    public List<Monster> getMonsters() { return monsters; }
}
