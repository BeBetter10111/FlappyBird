package game.entities;

import game.core.AssetLoader;
import game.utils.AssetPaths;
import game.utils.GameConstants;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

/**
 * Sinh 1 cặp Pipe (trên + dưới) với gap random.
 * Lưu gapCenterY để PowerUpManager biết đặt icon Dash ở đâu.
 */
public class PipeFactory {

    private final BufferedImage pipeImage;
    private final Random        random = new Random();
    private int lastGapCenterY = 0;

    public PipeFactory(AssetLoader loader) {
        // TODO: load image từ AssetPaths.PIPE (scale 2)
        this.pipeImage = null;
    }

    public List<Pipe> createPipePair() {
        // TODO:
        // 1) gapTopY = random trong khoảng [PIPE_MIN_GAP_Y, PIPE_MAX_GAP_Y]
        // 2) bottomPipeY = gapTopY + PIPE_GAP
        // 3) lastGapCenterY = gapTopY + PIPE_GAP / 2
        // 4) Return List chứa 2 Pipe:
        //    - new Pipe(pipeImage, PIPE_SPAWN_X, gapTopY, true)
        //    - new Pipe(pipeImage, PIPE_SPAWN_X, bottomPipeY, false)
        return null;
    }

    public int getLastGapCenterY() { return lastGapCenterY; }
}
