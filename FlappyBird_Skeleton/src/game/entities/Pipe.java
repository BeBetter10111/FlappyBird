package game.entities;

import game.core.Collidable;
import game.core.Updatable;
import game.rendering.Renderable;
import game.utils.GameConstants;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * 1 ống xanh — ống trên hoặc ống dưới.
 * Hỗ trợ speed multiplier để compatibly với Dash phase.
 */
public class Pipe implements Updatable, Renderable, Collidable {

    private float xf;                       // float để di chuyển mượt khi speed != 1
    private final int y;
    private final boolean isTop;
    private final BufferedImage image;
    private final BufferedImage flippedImage;

    public Pipe(BufferedImage image, int x, int y, boolean isTop) {
        this.image = image;
        this.xf = x;
        this.y = y;
        this.isTop = isTop;
        this.flippedImage = flipVertically(image);
    }

    private BufferedImage flipVertically(BufferedImage src) {
        // TODO: tạo BufferedImage mới, vẽ ngược chiều dọc bằng drawImage
        return src;
    }

    @Override
    public void update() {
        // TODO: gọi update(1.0f)
    }

    /** PipeManager truyền speedMultiplier xuống mỗi tick (dash sẽ làm nhanh/chậm). */
    public void update(float speedMultiplier) {
        // TODO: xf -= PIPE_SPEED * speedMultiplier
    }

    @Override
    public void render(Graphics2D g) {
        // TODO:
        // - Nếu isTop → vẽ flippedImage tại (x - w/2, y - h)
        // - Ngược lại → vẽ image tại (x - w/2, y)
    }

    @Override
    public Rectangle getBounds() {
        // TODO: tính rect bao quanh tuỳ isTop
        return new Rectangle();
    }

    @Override
    public boolean collidesWith(Collidable other) {
        return getBounds().intersects(other.getBounds());
    }

    public boolean isOffScreen() {
        // TODO: xf < -image.getWidth()
        return false;
    }

    public int getX() { return (int) xf; }
}
