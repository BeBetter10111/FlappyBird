package game.entities;

import game.core.Collidable;
import game.core.Updatable;
import game.rendering.Renderable;
import game.utils.GameConstants;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Pipe implements Updatable, Renderable, Collidable {

    private float xf;          // float x for smooth sub-pixel movement
    private final int y;
    private final boolean isTop;
    private final BufferedImage image;
    private final BufferedImage flippedImage;

    public Pipe(BufferedImage image, int x, int y, boolean isTop) {
        this.image        = image;
        this.xf           = x;
        this.y            = y;
        this.isTop        = isTop;
        this.flippedImage = flipVertically(image);
    }

    private BufferedImage flipVertically(BufferedImage src) {
        int w = src.getWidth(), h = src.getHeight();
        BufferedImage flipped = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = flipped.createGraphics();
        g.drawImage(src, 0, 0, w, h, 0, h, w, 0, null);
        g.dispose();
        return flipped;
    }

    // normal update (speed × 1)
    @Override
    public void update() {
        update(1.0f);
    }

    // speed-aware update called by PipeManager
    public void update(float speedMultiplier) {
        xf -= GameConstants.PIPE_SPEED * speedMultiplier;
    }

    @Override
    public void render(Graphics2D g) {
        int x = (int) xf;
        int w = image.getWidth();
        int h = image.getHeight();
        if (isTop) {
            g.drawImage(flippedImage, x - w / 2, y - h, null);
        } else {
            g.drawImage(image,        x - w / 2, y,     null);
        }
    }

    @Override
    public Rectangle getBounds() {
        int x = (int) xf;
        int w = image.getWidth();
        int h = image.getHeight();
        if (isTop) {
            return new Rectangle(x - w / 2, y - h, w, h);
        } else {
            return new Rectangle(x - w / 2, y,     w, h);
        }
    }

    @Override
    public boolean collidesWith(Collidable other) {
        return getBounds().intersects(other.getBounds());
    }

    public boolean isOffScreen() { return xf < -image.getWidth(); }
    public int     getX()        { return (int) xf; }
}