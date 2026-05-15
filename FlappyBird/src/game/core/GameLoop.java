package game.core;

import game.audio.SoundPlayer;
import game.entities.Bird;
import game.entities.DashPowerUp;
import game.entities.Floor;
import game.entities.PipeFactory;
import game.entities.PipeManager;
import game.entities.PowerUpManager;
import game.rendering.BackgroundRenderer;
import game.rendering.HudRenderer;
import game.utils.GameConstants;
import game.utils.GameState;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

public class GameLoop extends Canvas implements Runnable {

    private Thread    gameThread;
    private boolean   running;
    private GameState gameState;

    private final AssetLoader       assetLoader;
    private final SoundPlayer       soundPlayer;
    private final Bird              bird;
    private final PipeManager       pipeManager;
    private final PowerUpManager    powerUpManager;
    private final Floor             floor;
    private final ScoreManager      scoreManager;
    private final CollisionDetector collisionDetector;
    private final BackgroundRenderer backgroundRenderer;
    private final HudRenderer        hudRenderer;

    private int scoreSoundCountdown;
    private static final int SCORE_SOUND_INTERVAL = 100;

    public GameLoop() {
        assetLoader  = new AssetLoader();
        soundPlayer  = new SoundPlayer(assetLoader);
        bird         = new Bird(assetLoader);

        PipeFactory pipeFactory = new PipeFactory(assetLoader);
        pipeManager    = new PipeManager(pipeFactory);
        powerUpManager = new PowerUpManager(assetLoader);

        // Wire PipeManager → PowerUpManager
        pipeManager.setPowerUpManager(powerUpManager);

        floor             = new Floor(assetLoader);
        scoreManager      = new ScoreManager();
        collisionDetector = new CollisionDetector();
        backgroundRenderer = new BackgroundRenderer(assetLoader);
        hudRenderer        = new HudRenderer(assetLoader);

        gameState           = GameState.GAME_OVER;
        scoreSoundCountdown = SCORE_SOUND_INTERVAL;

        setPreferredSize(new Dimension(GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT));
        addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) { handleKeyPress(e.getKeyCode()); }
        });
        setFocusable(true);
    }

    // ─────────────────────────────────────────────────────────────────────────
    private void handleKeyPress(int keyCode) {
        if (keyCode == KeyEvent.VK_SPACE) {
            if (gameState == GameState.MAIN_GAME) {
                bird.flap();
                soundPlayer.playFlap();
            } else {
                startNewGame();
            }
        }
    }

    private void startNewGame() {
        bird.reset();
        pipeManager.reset();
        powerUpManager.reset();
        scoreManager.reset();
        backgroundRenderer.reset();
        scoreSoundCountdown = SCORE_SOUND_INTERVAL;
        gameState = GameState.MAIN_GAME;
    }

    // ─────────────────────────────────────────────────────────────────────────
    public void start() {
        running    = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void stop() {
        running = false;
        try { gameThread.join(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
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

    // ─────────────────────────────────────────────────────────────────────────
    private void update() {
        floor.update();
        backgroundRenderer.update();

        if (gameState != GameState.MAIN_GAME) return;

        bird.update();

        // Apply dash speed to pipes & power-ups
        float speedMult = bird.getDashSpeedMultiplier();
        pipeManager.setSpeedMultiplier(speedMult);

        pipeManager.update();
        powerUpManager.update();

        scoreManager.increment();
        scoreSoundCountdown--;
        if (scoreSoundCountdown <= 0) {
            soundPlayer.playPoint();
            scoreSoundCountdown = SCORE_SOUND_INTERVAL;
        }

        // ── Check dash power-up collection ────────────────────────────────────
        for (DashPowerUp pu : powerUpManager.getPowerUps()) {
            if (!pu.isCollected() && bird.collidesWith(pu)) {
                pu.collect();
                bird.activateDash();
                soundPlayer.playSwoosh();
            }
        }

        // ── Collision with pipes (skip if bird is dashing / invincible) ───────
        if (!bird.isDashing()) {
            if (collisionDetector.hasCollision(bird, pipeManager.getPipes())) {
                soundPlayer.playHit();
                scoreManager.updateHighScore();
                gameState = GameState.GAME_OVER;
            }
        } else {
            // Still die if bird goes out of vertical bounds during dash
            if (bird.isOutOfBounds()) {
                soundPlayer.playHit();
                scoreManager.updateHighScore();
                gameState = GameState.GAME_OVER;
            }
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) { createBufferStrategy(3); return; }

        Graphics2D g = (Graphics2D) bs.getDrawGraphics();

        backgroundRenderer.render(g);

        pipeManager.render(g);
        powerUpManager.render(g);
        bird.render(g);
        floor.render(g);

        if (gameState == GameState.MAIN_GAME) {
            hudRenderer.renderMainScore(g, scoreManager);
        } else {
            hudRenderer.renderGameOverScreen(g, scoreManager);
        }

        g.dispose();
        bs.show();
    }
}