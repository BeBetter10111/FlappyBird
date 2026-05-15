package game.audio;

import game.core.AssetLoader;
import game.utils.AssetPaths;

import javax.sound.sampled.Clip;

public class SoundPlayer {
    private final Clip flapSound;
    private final Clip hitSound;
    private final Clip pointSound;
    private final Clip swooshSound;
    private final Clip dieSound;

    public SoundPlayer(AssetLoader loader) {
        flapSound = loader.loadSound(AssetPaths.SOUND_FLAP);
        hitSound = loader.loadSound(AssetPaths.SOUND_HIT);
        pointSound = loader.loadSound(AssetPaths.SOUND_POINT);
        swooshSound = loader.loadSound(AssetPaths.SOUND_SWOOSH);
        dieSound = loader.loadSound(AssetPaths.SOUND_DIE);
    }

    public void playFlap() {
        playClip(flapSound);
    }

    public void playHit() {
        playClip(hitSound);
    }

    public void playPoint() {
        playClip(pointSound);
    }

    public void playSwoosh() {
        playClip(swooshSound);
    }

    public void playDie() {
        playClip(dieSound);
    }

    private void playClip(Clip clip) {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }
}
