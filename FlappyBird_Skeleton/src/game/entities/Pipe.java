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
    private boolean scored = false;

    public Pipe(BufferedImage image, int x, int y, boolean isTop) {
        this.image = image;
        this.xf = x;
        this.y = y;
        this.isTop = isTop;
        this.flippedImage = flipVertically(image);
    }

    private BufferedImage flipVertically(BufferedImage src) {
        int w =  src.getWidth();
        int h =  src.getHeight();
        BufferedImage flipped = new BufferedImage(w, h, src.getType());
        Graphics2D g2d = flipped.createGraphics();
        g2d.drawImage(src, 0, 0, w, h,
                           0, h, w, 0,
                       null);
        g2d.dispose();
        return flipped;
    }

    @Override
    public void update() {
        update (1.0f);
    }

    /** PipeManager truyền speedMultiplier xuống mỗi tick (dash sẽ làm nhanh/chậm). */
    public void update(float speedMultiplier) {
         xf -= GameConstants.PIPE_SPEED * speedMultiplier;
    }

    @Override
    public void render(Graphics2D g) {
        int  w = image.getWidth();
        int h = image.getHeight();
        int drawX = (int) xf - w/2;
        if (isTop) {
            g.drawImage(flippedImage, drawX, y-h, null);
        } else {
            g.drawImage(image, drawX, y, null);
        }
    }

    @Override
    public Rectangle getBounds() {
        int w = image.getWidth();
        int h = image.getHeight();
        int drawX = (int) xf - w / 2;
        if (isTop) {
            return new Rectangle(drawX, y - h, w, h);
        } else {
            return new Rectangle(drawX, y, w, h);
        }
    }
    @Override
    public boolean collidesWith(Collidable other) {
        return getBounds().intersects(other.getBounds());
    }

    public boolean isOffScreen() {
        return xf < -image.getWidth();
    }

    public int getX() { return (int) xf; }
    public boolean isSocred(){ return scored;}
    public void markScored(){ scored = true;}
    public boolean isTopPipe(){ return isTop;}
}
