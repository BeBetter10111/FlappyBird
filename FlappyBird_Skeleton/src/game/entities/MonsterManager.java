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
import java.util.Random;

/**
 * Quản lý danh sách Monster.
 * - Spawn theo thời gian (hoặc theo điểm)
 * - Xoá monster ra ngoài màn hình HOẶC đã chết
 *
 */
public class MonsterManager implements Updatable, Renderable, Resettable {

    private final List<Monster> monsters = new ArrayList<>();
    private final BufferedImage sprite;
    private final Random random = new Random();
    private long lastSpawnTime;

    private static final long SPAWN_INTERVAL_MS = 3000; 

    public MonsterManager(AssetLoader loader) {
        //Fixed: load sprite monster (thêm vào AssetPaths)
        this.sprite = SpriteCleaner.removeLightBackground(loader.loadScaledImage(AssetPaths.MONSTER, 2));
        this.lastSpawnTime = System.currentTimeMillis();
    }

    @Override
    public void update() {
        if (System.currentTimeMillis() - lastSpawnTime >= SPAWN_INTERVAL_MS) {
            int x = 800; // spawn ngoài màn hình
            int y = random.nextInt(400); // tuỳ chỉnh giới hạn y
            int hp = 1;
            monsters.add(new Monster(sprite, x, y, hp));
            lastSpawnTime = System.currentTimeMillis();
            
        }

        for (Monster monster : monsters) {
            monster.update();
        }

        monsters.removeIf(monster -> !monster.isAlive() || monster.isOffScreen());      
    }

    @Override
    public void render(Graphics2D g) {
        for (Monster monster : monsters) {
            monster.render(g);
        }
    }

    @Override
    public void reset() {

        monsters.clear();
        lastSpawnTime = System.currentTimeMillis();
    }

    public List<Monster> getMonsters() { return monsters; }
}
