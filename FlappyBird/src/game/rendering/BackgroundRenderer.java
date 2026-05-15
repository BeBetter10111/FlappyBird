package game.rendering;

import game.core.AssetLoader;
import game.utils.AssetPaths;
import game.utils.GameConstants;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Renders a day/night background that cross-fades every CYCLE_SECONDS seconds.
 *
 * States:
 *   DAY   → FADING_TO_NIGHT → NIGHT → FADING_TO_DAY → DAY → …
 *
 * Call update() once per game tick (from GameLoop).
 * Call render(g) to draw.
 */
public class BackgroundRenderer implements Renderable {

    // ── assets ────────────────────────────────────────────────────────────────
    private final BufferedImage bgDay;
    private final BufferedImage bgNight;

    // ── timing ────────────────────────────────────────────────────────────────
    /** How long each day/night phase lasts before fading starts (ticks). */
    private static final int HOLD_TICKS = GameConstants.FPS * GameConstants.BG_CYCLE_SECONDS;

    /** How many ticks the cross-fade transition takes. */
    private static final int FADE_TICKS = GameConstants.FPS * GameConstants.BG_FADE_SECONDS;

    // ── state ─────────────────────────────────────────────────────────────────
    private enum Phase { DAY, FADING_TO_NIGHT, NIGHT, FADING_TO_DAY }

    private Phase phase    = Phase.DAY;
    private int   ticker   = 0;   // ticks spent in current phase
    private float alpha    = 0f;  // 0 = fully day, 1 = fully night

    // ─────────────────────────────────────────────────────────────────────────
    public BackgroundRenderer(AssetLoader loader) {
        bgDay   = loader.loadScaledImage(AssetPaths.BACKGROUND_DAY,   2);
        bgNight = loader.loadScaledImage(AssetPaths.BACKGROUND_NIGHT, 2);
    }

    // ─────────────────────────────────────────────────────────────────────────
    /** Must be called every game tick. */
    public void update() {
        ticker++;

        switch (phase) {
            case DAY:
                alpha = 0f;
                if (ticker >= HOLD_TICKS) { phase = Phase.FADING_TO_NIGHT; ticker = 0; }
                break;

            case FADING_TO_NIGHT:
                alpha = (float) ticker / FADE_TICKS;
                if (ticker >= FADE_TICKS) { alpha = 1f; phase = Phase.NIGHT; ticker = 0; }
                break;

            case NIGHT:
                alpha = 1f;
                if (ticker >= HOLD_TICKS) { phase = Phase.FADING_TO_DAY; ticker = 0; }
                break;

            case FADING_TO_DAY:
                alpha = 1f - (float) ticker / FADE_TICKS;
                if (ticker >= FADE_TICKS) { alpha = 0f; phase = Phase.DAY; ticker = 0; }
                break;
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    @Override
    public void render(Graphics2D g) {
        // Always draw day background first
        g.drawImage(bgDay, 0, 0, null);

        // Overlay night background with alpha = current blend amount
        if (alpha > 0f) {
            Composite prev = g.getComposite();
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g.drawImage(bgNight, 0, 0, null);
            g.setComposite(prev);
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    /** Reset to daytime (called on new game). */
    public void reset() {
        phase  = Phase.DAY;
        ticker = 0;
        alpha  = 0f;
    }

    public boolean isNight() { return phase == Phase.NIGHT || phase == Phase.FADING_TO_NIGHT; }
}