package game.rendering;

import game.core.AssetLoader;
import game.utils.AssetPaths;
import game.utils.GameConstants;
import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Vẽ nền + cross-fade ngày/đêm theo chu kỳ.
 *
 * State machine: DAY → FADING_TO_NIGHT → NIGHT → FADING_TO_DAY → DAY → ...
 *
 * update() phải được GameLoop gọi mỗi tick.
 */
public class BackgroundRenderer implements Renderable {

    private final BufferedImage bgDay;
    private final BufferedImage bgNight;

    private static final int HOLD_TICKS = GameConstants.FPS * GameConstants.BG_CYCLE_SECONDS;
    private static final int FADE_TICKS = GameConstants.FPS * GameConstants.BG_FADE_SECONDS;

    private enum Phase {
        DAY, FADING_TO_NIGHT, NIGHT, FADING_TO_DAY
    }

    private Phase phase = Phase.DAY;
    private int ticker = 0;
    private float alpha = 0f; // 0 = full day, 1 = full night

    public BackgroundRenderer(AssetLoader loader) {
        this.bgDay = loader.loadScaledImage(AssetPaths.BACKGROUND_DAY, 2);
        this.bgNight = loader.loadScaledImage(AssetPaths.BACKGROUND_NIGHT, 2);
    }

    public void update() {
        ticker++;
        switch (phase) {
            case Phase.DAY -> {
                alpha = 0f;
                if (ticker >= HOLD_TICKS) {
                    phase = Phase.FADING_TO_NIGHT;
                    ticker = 0;
                }
            }
            case Phase.FADING_TO_NIGHT -> {
                alpha = (float) ticker / FADE_TICKS;
                if (ticker >= FADE_TICKS) {
                    phase = Phase.NIGHT;
                    ticker = 0;
                }
            }
            case Phase.NIGHT -> {
                alpha = 1f;
                if (ticker >= HOLD_TICKS) {
                    phase = Phase.FADING_TO_DAY;
                    ticker = 0;
                }
            }
            case Phase.FADING_TO_DAY -> {
                alpha = 1f - (float) ticker / FADE_TICKS;
                if (ticker >= FADE_TICKS) {
                    phase = Phase.DAY;
                    ticker = 0;
                }
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(bgDay, 0, 0, null);
        if (alpha > 0f) {
            Composite originalComposite = g.getComposite();
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g.drawImage(bgNight, 0, 0, null);
            g.setComposite(originalComposite);
        }
    }

    public void reset() {
        phase = Phase.DAY;
        ticker = 0;
        alpha = 0f;
    }

    public boolean isNight() {
        return phase == Phase.NIGHT || phase == Phase.FADING_TO_NIGHT;
    }
}
