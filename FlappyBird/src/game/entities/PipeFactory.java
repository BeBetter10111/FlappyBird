package game.entities;

import game.core.AssetLoader;
import game.utils.AssetPaths;
import game.utils.GameConstants;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PipeFactory {

    private final BufferedImage pipeImage;
    private final Random random;

    private int lastGapCenterY = 0;   // remembered after each createPipePair()

    public PipeFactory(AssetLoader loader) {
        pipeImage = loader.loadScaledImage(AssetPaths.PIPE, 2);
        random    = new Random();
    }

    public List<Pipe> createPipePair() {
        int gapTopY = random.nextInt(
                GameConstants.PIPE_MAX_GAP_Y - GameConstants.PIPE_MIN_GAP_Y + 1
        ) + GameConstants.PIPE_MIN_GAP_Y;

        int bottomPipeY = gapTopY + GameConstants.PIPE_GAP;

        // Centre of the gap (used by PowerUpManager for icon placement)
        lastGapCenterY = gapTopY + GameConstants.PIPE_GAP / 2;

        List<Pipe> pair = new ArrayList<>();
        pair.add(new Pipe(pipeImage, GameConstants.PIPE_SPAWN_X, gapTopY,     true));
        pair.add(new Pipe(pipeImage, GameConstants.PIPE_SPAWN_X, bottomPipeY, false));
        return pair;
    }

    /** Y coordinate of the centre of the gap in the most recently created pair. */
    public int getLastGapCenterY() { return lastGapCenterY; }
}