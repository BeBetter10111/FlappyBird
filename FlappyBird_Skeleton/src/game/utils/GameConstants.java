package game.utils;

/**
 * Hằng số toàn game — chỉnh ở đây để tune gameplay.
 */
public final class GameConstants {
    public static final int   SCREEN_WIDTH        = 432;
    public static final int   SCREEN_HEIGHT       = 768;
    public static final int   FPS                 = 120;
    public static final int   FLOOR_Y             = 650;
    public static final int   FLOOR_WIDTH         = 432;

    public static final float GRAVITY             = 0.25f;
    public static final float FLAP_STRENGTH       = -6.5f;
    public static final int   PIPE_SPEED          = 5;
    public static final int   PIPE_SPAWN_INTERVAL = 1200;
    public static final int   BIRD_FLAP_INTERVAL  = 200;

    public static final int   BIRD_START_X        = 100;
    public static final int   BIRD_START_Y        = 384;
    public static final int   BIRD_TOP_BOUND      = -75;

    public static final int   PIPE_SPAWN_X        = 500;
    public static final int   PIPE_GAP            = 170;
    public static final int   PIPE_MIN_GAP_Y      = 140;
    public static final int   PIPE_MAX_GAP_Y      = 400;

    public static final int   SCORE_CENTER_X      = 216;
    public static final int   SCORE_TOP_Y         = 100;
    public static final int   SCORE_BOTTOM_Y      = 630;
    public static final int   FONT_SIZE           = 40;

    // Background day/night
    public static final int   BG_CYCLE_SECONDS    = 15;
    public static final int   BG_FADE_SECONDS     = 3;

    public static final int MONSTER_HP = 1;
    public static final int MONSTER_SPEED = 7;
    public static final int MONSTER_SPAWN_INTERVAL = 1200;

    public static final int BULLET_SPEED        = 12;
    public static final int BULLET_COOLDOWN = 500;

    private GameConstants() {}
}
