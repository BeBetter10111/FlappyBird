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

public class MonsterManager implements Updatable, Renderable, Resettable {

    private final List<Monster> monsters = new ArrayList<>();
    private final BufferedImage sprite;
    private final Random random = new Random();
    private long lastSpawnTime;

    private static final long SPAWN_INTERVAL_MS = 3000; 

    public MonsterManager(AssetLoader loader) {
        this.sprite = SpriteCleaner.removeLightBackground(loader.loadScaledImage(AssetPaths.MONSTER, 2));
        this.lastSpawnTime = System.currentTimeMillis();
    }

    @Override
    public void update() {
        if (System.currentTimeMillis() - lastSpawnTime >= SPAWN_INTERVAL_MS) {
            int x = 800; 
            int y = random.nextInt(400); 
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
