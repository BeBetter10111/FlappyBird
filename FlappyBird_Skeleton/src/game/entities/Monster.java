package game.entities;

import game.core.Collidable;
import game.core.Updatable;
import game.rendering.Renderable;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Quái — có HP, di chuyển từ phải sang trái như pipe.
 * Bullet trúng → takeDamage(1). HP <= 0 → chết.
 *
 * TODO: bàn với nhóm:
 * - Quái xuất hiện ngẫu nhiên hay theo điểm số?
 * - Quái di chuyển theo y (lên xuống) hay đứng yên?
 * - Quái va chạm chim → game over hay chỉ trừ shield?
 */
public class Monster implements Updatable, Renderable, Collidable {

    private int x;
    private int y;
    private int hp;
    private boolean alive;
    private final BufferedImage sprite;

    public Monster(BufferedImage sprite, int x, int y, int hp) {
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.alive = true;
    }

    @Override
    public void update() {
        // TODO: x -= MONSTER_SPEED (thêm vào GameConstants)
        //Monter di chuyển theo trục x
        x -= 2; 
    }

    @Override
    public void render(Graphics2D g) {
        // TODO: nếu alive → vẽ sprite tại (x, y)
        if (alive) {
            g.drawImage(sprite, x, y, null);
        } else {
            // TODO (option): vẽ hiệu ứng chết (ví dụ: sprite nổ) thay vì xóa hẳn
            g.setColor(java.awt.Color.RED);
            g.fillRect(x, y, sprite.getWidth(), sprite.getHeight());
        }
    }

    @Override
    public Rectangle getBounds() {
        // TODO: rect bao quanh sprite
        if (alive) {
            return new Rectangle(x, y, sprite.getWidth(), sprite.getHeight());
        }
        return new Rectangle(0, 0, 0, 0); 
    }

    @Override
    public boolean collidesWith(Collidable other) {
        return getBounds().intersects(other.getBounds());
    }

    public void takeDamage(int dmg) {
        // TODO: hp -= dmg; nếu hp <= 0 → alive = false
        hp -= dmg;
        if (hp <= 0) {
            alive = false;
        }
    }

    public boolean isAlive()     { return alive; }
    public boolean isOffScreen() {
        // TODO: x < -sprite.getWidth()
        if (x < -sprite.getWidth()) {
            return true;
        }
        return false;
    }

    public int getHp() { return hp; }
    public int getX()  { return x; }
    public int getY()  { return y; }
}
