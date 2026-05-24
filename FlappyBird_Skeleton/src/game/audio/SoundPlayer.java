package game.audio;

import game.core.AssetLoader;

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
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }
}
