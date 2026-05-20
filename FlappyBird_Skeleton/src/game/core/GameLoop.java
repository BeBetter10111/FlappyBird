package game.core;

import game.audio.SoundPlayer;
import game.entities.Bird;
import game.entities.Floor;
import game.entities.PipeManager;
import game.entities.PowerUpManager;
// TODO: import MonsterManager, BulletManager khi đã code xong
import game.rendering.BackgroundRenderer;
import game.rendering.HudRenderer;
import game.utils.GameConstants;
import game.utils.GameState;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

/**
 * Vòng lặp game chính.
 * Nhận tất cả dependencies qua constructor (DIP) — không tự new gì.
 * Dùng GameLoopBuilder để tạo instance.
 *
 * Implement PowerUpCollisionListener để react khi bird ăn DashPowerUp.
 */
public class GameLoop extends Canvas implements Runnable, PowerUpCollisionListener {

    private Thread    gameThread;
    private boolean   running;
    private GameState gameState;

    private final SoundPlayer        soundPlayer;
    private final Bird               bird;
    private final PipeManager        pipeManager;
    private final PowerUpManager     powerUpManager;
    private final Floor              floor;
    private final ScoreManager       scoreManager;
    private final CollisionDetector  collisionDetector;
    private final BackgroundRenderer backgroundRenderer;
    private final HudRenderer        hudRenderer;
    // TODO: thêm MonsterManager, BulletManager

    private int scoreSoundCountdown;
    private static final int SCORE_SOUND_INTERVAL = 100;

    /** Package-private — chỉ gọi qua GameLoopBuilder */
    GameLoop(SoundPlayer soundPlayer, Bird bird,
             PipeManager pipeManager, PowerUpManager powerUpManager,
             Floor floor, ScoreManager scoreManager,
             CollisionDetector collisionDetector,
             BackgroundRenderer backgroundRenderer, HudRenderer hudRenderer) {

        this.soundPlayer        = soundPlayer;
        this.bird               = bird;
        this.pipeManager        = pipeManager;
        this.powerUpManager     = powerUpManager;
        this.floor              = floor;
        this.scoreManager       = scoreManager;
        this.collisionDetector  = collisionDetector;
        this.backgroundRenderer = backgroundRenderer;
        this.hudRenderer        = hudRenderer;

        // Wire connectors
        powerUpManager.setCollisionTarget(bird, this);
        pipeManager.setPowerUpManager(powerUpManager);

        gameState           = GameState.GAME_OVER;
        scoreSoundCountdown = SCORE_SOUND_INTERVAL;

        setPreferredSize(new Dimension(GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT));
        addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) { handleKeyPress(e.getKeyCode()); }
        });
        setFocusable(true);
    }

    @Override
    public void onDashCollected() {
        // TODO: bird.activateDash() + soundPlayer.playSwoosh()
    }

    private void handleKeyPress(int keyCode) {
        // TODO:
        // Nếu phím SPACE:
        //   - Đang MAIN_GAME → bird.flap() + soundPlayer.playFlap()
        //   - Đang GAME_OVER → startNewGame()
    }

    private void startNewGame() {
        // TODO: reset tất cả: bird, pipeManager, powerUpManager,
        // scoreManager, backgroundRenderer
        // scoreSoundCountdown = SCORE_SOUND_INTERVAL
        // gameState = MAIN_GAME
    }

    public void start() {
        running    = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void stop() {
        running = false;
        try { gameThread.join(); }
        catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    @Override
    public void run() {
        long   lastTime  = System.nanoTime();
        double nsPerTick = 1_000_000_000.0 / GameConstants.FPS;
        double delta     = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            while (delta >= 1) { update(); delta--; }
            render();
        }
    }

    private void update() {
        // TODO:
        // 1) floor.update(), backgroundRenderer.update() — luôn chạy
        // 2) Nếu gameState != MAIN_GAME → return
        // 3) bird.update()
        // 4) pipeManager.setSpeedMultiplier(bird.getDashSpeedMultiplier())
        // 5) pipeManager.update(), powerUpManager.update()
        // 6) scoreManager.increment(), countdown để phát sound point
        // 7) Check collision: nếu !bird.isInvincible() & có va chạm pipe → game over
        //    Nếu invincible nhưng out of bounds → vẫn game over
    }

    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) { createBufferStrategy(3); return; }

        Graphics2D g = (Graphics2D) bs.getDrawGraphics();

        // TODO:
        // - backgroundRenderer.render(g)
        // - pipeManager, powerUpManager, bird, floor render
        // - Nếu MAIN_GAME → hudRenderer.renderMainScore; ngược lại → renderGameOverScreen

        g.dispose();
        bs.show();
    }
}
