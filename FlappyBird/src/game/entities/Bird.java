package game.entities;

import game.core.AssetLoader;
import game.core.Collidable;
import game.core.Resettable;
import game.core.Updatable;
import game.rendering.Renderable;
import game.utils.AssetPaths;
import game.utils.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bird implements Updatable, Renderable, Collidable, Resettable {

    // ── sprites ──────────────────────────────────────────────────────────────
    private final BufferedImage[] frames;
    private int frameIndex;
    private long lastFlapTime;

    // ── physics ───────────────────────────────────────────────────────────────
    private float velocityY;
    private int x;
    private int y;

    // ── dash state ────────────────────────────────────────────────────────────
    private boolean dashing      = false;
    private int     dashTick     = 0;

    /**
     * Phase 1 – RUSH  : pipe speed is multiplied × DASH_SPEED_MULT for DASH_RUSH_TICKS ticks.
     * Phase 2 – SLOW  : speed lerps back to normal over DASH_SLOW_TICKS ticks.
     * After that        : normal gameplay resumes.
     */
    private static final int   DASH_RUSH_TICKS   = 90;   // ~0.75 s at 120 fps
    private static final int   DASH_SLOW_TICKS   = 60;   // ~0.5 s deceleration
    private static final int   DASH_TOTAL_TICKS  = DASH_RUSH_TICKS + DASH_SLOW_TICKS;
    private static final float DASH_SPEED_MULT   = 4.0f;

    // ── rainbow flicker ───────────────────────────────────────────────────────
    private static final Color[] RAINBOW = {
            new Color(255, 0,   0),
            new Color(255, 127, 0),
            new Color(255, 255, 0),
            new Color(0,   200, 0),
            new Color(0,   120, 255),
            new Color(75,  0,   130),
            new Color(148, 0,   211),
    };
    private int rainbowIndex = 0;
    private int rainbowTick  = 0;
    private static final int RAINBOW_INTERVAL = 4; // ticks per colour

    public Bird(AssetLoader loader) {
        frames = new BufferedImage[]{
                loader.loadScaledImage(AssetPaths.BIRD_DOWN, 1),
                loader.loadScaledImage(AssetPaths.BIRD_MID, 1),
                loader.loadScaledImage(AssetPaths.BIRD_UP,  1)
        };
        frameIndex   = 0;
        velocityY    = 0;
        x            = GameConstants.BIRD_START_X;
        y            = GameConstants.BIRD_START_Y;
        lastFlapTime = System.currentTimeMillis();
    }

    // ─────────────────────────────────────────────────────────────────────────
    @Override
    public void update() {
        // normal physics
        velocityY += GameConstants.GRAVITY;
        y += (int) velocityY;

        // wing animation
        long now = System.currentTimeMillis();
        if (now - lastFlapTime >= GameConstants.BIRD_FLAP_INTERVAL) {
            frameIndex   = (frameIndex + 1) % frames.length;
            lastFlapTime = now;
        }

        // dash countdown
        if (dashing) {
            dashTick++;
            if (dashTick >= DASH_TOTAL_TICKS) {
                dashing = false;
                dashTick = 0;
            }

            // rainbow cycle
            rainbowTick++;
            if (rainbowTick >= RAINBOW_INTERVAL) {
                rainbowTick  = 0;
                rainbowIndex = (rainbowIndex + 1) % RAINBOW.length;
            }
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    @Override
    public void render(Graphics2D g) {
        BufferedImage frame = frames[frameIndex];
        int drawX = x - frame.getWidth()  / 2;
        int drawY = y - frame.getHeight() / 2;

        double angle = Math.toRadians(-velocityY * 3);
        AffineTransform old = g.getTransform();
        g.rotate(angle, x, y);

        if (dashing) {
            // --- rainbow tinted sprite ---
            Color tint = RAINBOW[rainbowIndex];

            // 1. draw normal sprite first
            g.drawImage(frame, drawX, drawY, null);

            // 2. overlay a semi-transparent rainbow colour (multiply-like effect)
            Composite prevComp = g.getComposite();
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.45f));
            g.setColor(tint);
            g.fillRect(drawX, drawY, frame.getWidth(), frame.getHeight());
            g.setComposite(prevComp);

            // 3. draw sprite again on top so detail shows through
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
            g.drawImage(frame, drawX, drawY, null);
            g.setComposite(prevComp);

        } else {
            g.drawImage(frame, drawX, drawY, null);
        }

        g.setTransform(old);
    }

    // ─────────────────────────────────────────────────────────────────────────
    @Override
    public Rectangle getBounds() {
        BufferedImage frame = frames[frameIndex];
        return new Rectangle(
                x - frame.getWidth()  / 2,
                y - frame.getHeight() / 2,
                frame.getWidth(),
                frame.getHeight()
        );
    }

    @Override
    public boolean collidesWith(Collidable other) {
        return getBounds().intersects(other.getBounds());
    }

    @Override
    public void reset() {
        x            = GameConstants.BIRD_START_X;
        y            = GameConstants.BIRD_START_Y;
        velocityY    = 0;
        frameIndex   = 0;
        dashing      = false;
        dashTick     = 0;
        rainbowIndex = 0;
        rainbowTick  = 0;
    }

    // ─────────────────────────────────────────────────────────────────────────
    public void flap() {
        velocityY = GameConstants.FLAP_STRENGTH;
    }

    public boolean isOutOfBounds() {
        return y <= GameConstants.BIRD_TOP_BOUND || y >= GameConstants.FLOOR_Y;
    }

    public int getY() { return y; }
    public int getX() { return x; }

    // ── dash API ──────────────────────────────────────────────────────────────
    public void activateDash() {
        dashing      = true;
        dashTick     = 0;
        rainbowIndex = 0;
        rainbowTick  = 0;
    }

    public boolean isDashing() { return dashing; }

    /**
     * Returns the current pipe-speed multiplier based on dash phase.
     * GameLoop multiplies PIPE_SPEED by this value every tick.
     */
    public float getDashSpeedMultiplier() {
        if (!dashing) return 1.0f;

        if (dashTick < DASH_RUSH_TICKS) {
            // Phase 1 — full rush speed
            return DASH_SPEED_MULT;
        } else {
            // Phase 2 — lerp from DASH_SPEED_MULT back to 1.0
            float t = (float)(dashTick - DASH_RUSH_TICKS) / DASH_SLOW_TICKS;
            return DASH_SPEED_MULT + t * (1.0f - DASH_SPEED_MULT); // lerp
        }
    }
}