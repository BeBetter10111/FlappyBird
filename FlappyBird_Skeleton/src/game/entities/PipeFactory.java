package game.entities;

import game.core.AssetLoader;
import game.utils.AssetPaths;
import game.utils.GameConstants;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

public class PipeFactory {

    private final BufferedImage pipeImage;
    private final Random        random = new Random();
    private int lastGapCenterY = 0;

    public PipeFactory(AssetLoader loader) {
        this.pipeImage = loader.loadScaledImage(AssetPaths.PIPE, 2);
    }

    public List<Pipe> createPipePair() {
        int range = GameConstants.PIPE_MAX_GAP_Y - GameConstants.PIPE_MIN_GAP_Y;
        int gapTopY = GameConstants.PIPE_MIN_GAP_Y + random.nextInt(range+1);
        int gapBottomY = gapTopY + GameConstants.PIPE_GAP;
        lastGapCenterY = gapTopY +  GameConstants.PIPE_GAP/2;

        return List.of(
                new Pipe(pipeImage, GameConstants.PIPE_SPAWN_X, gapTopY, true),
                new Pipe(pipeImage, GameConstants.PIPE_SPAWN_X, gapBottomY, false)
        );
    }

    public int getLastGapCenterY() { return lastGapCenterY; }
}

