package game.audio;

import game.core.AssetLoader;
import game.utils.AssetPaths;

import javax.sound.sampled.Clip;

/**
 * Phát các sound effect.
 * Load 1 lần khi khởi tạo, reuse Clip cho nhiều lần phát.
 */
public class SoundPlayer {

    private final Clip flapSound;
    private final Clip hitSound;
    private final Clip pointSound;
    private final Clip swooshSound;
    private final Clip dieSound;

    public SoundPlayer(AssetLoader loader) {
        // TODO: load 5 clip qua loader.loadSound(AssetPaths.SOUND_*)
        this.flapSound   = null;
        this.hitSound    = null;
        this.pointSound  = null;
        this.swooshSound = null;
        this.dieSound    = null;
    }

    public void playFlap()   { playClip(flapSound); }
    public void playHit()    { playClip(hitSound); }
    public void playPoint()  { playClip(pointSound); }
    public void playSwoosh() { playClip(swooshSound); }
    public void playDie()    { playClip(dieSound); }

    private void playClip(Clip clip) {
        // TODO:
        // - Nếu clip != null:
        //   - setFramePosition(0)
        //   - start()
    }
}
