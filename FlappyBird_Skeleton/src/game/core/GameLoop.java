package game.core;

import game.audio.SoundPlayer;
import game.entities.Bird;
import game.entities.Floor;
import game.entities.PipeManager;
import game.entities.Pipe;
import game.entities.PowerUpManager;
import game.entities.MonsterManager;
import game.entities.BulletManager;
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
    private final MonsterManager     monsterManager;
    private final BulletManager      bulletManager;

    private int scoreSoundCountdown;
    private static final int SCORE_SOUND_INTERVAL = 100;

    /** Package-private — chỉ gọi qua GameLoopBuilder */
    GameLoop(SoundPlayer soundPlayer, Bird bird,
             PipeManager pipeManager, PowerUpManager powerUpManager,
             Floor floor, ScoreManager scoreManager,
             CollisionDetector collisionDetector,
             BackgroundRenderer backgroundRenderer, HudRenderer hudRenderer, 
             MonsterManager monsterManager, BulletManager bulletManager) {

        this.soundPlayer        = soundPlayer;
        this.bird               = bird;
        this.pipeManager        = pipeManager;
        this.powerUpManager     = powerUpManager;
        this.floor              = floor;
        this.scoreManager       = scoreManager;
        this.collisionDetector  = collisionDetector;
        this.backgroundRenderer = backgroundRenderer;
        this.hudRenderer        = hudRenderer;
        this.monsterManager     = monsterManager;
        this.bulletManager      = bulletManager;

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
        bird.activateDash();
        soundPlayer.playSwoosh();
    }

    private void handleKeyPress(int keyCode) {
        if (keyCode == KeyEvent.VK_SPACE) {
            if (gameState == GameState.MAIN_GAME) {
                bird.flap();
                soundPlayer.playFlap();
            } else if (gameState == GameState.GAME_OVER) {
                startNewGame();
            }
        }

    }

    private void startNewGame() {
        bird.reset();
        pipeManager.reset();
        powerUpManager.reset();
        monsterManager.reset();
        bulletManager.reset();
        scoreManager.reset();
        backgroundRenderer.reset();
        scoreSoundCountdown = SCORE_SOUND_INTERVAL;
        gameState = GameState.MAIN_GAME;
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
        floor.update();
        backgroundRenderer.update();
        if (gameState != GameState.MAIN_GAME) {
            return;
        }
        bird.update();
        pipeManager.setSpeedMultiplier(bird.getDashSpeedMultiplier());
        pipeManager.update();
        powerUpManager.update();

        monsterManager.update();
        bulletManager.update();
        bulletManager.checkCollisionWith(monsterManager.getMonsters());

        for(Pipe p : pipeManager.getPipes()){
            if(!p.isSocred() && p.isTopPipe() && p.getX() < bird.getX()){
                p.markScored(); // đánh dấu pipe này đã tính frame sau không tính lại
                scoreManager.addPoint();
                soundPlayer.playPoint();
            }
        }

        if(!bird.isInvincible()){
            if(collisionDetector.hasCollision(bird, pipeManager.getPipes())){
                gameState = GameState.GAME_OVER;
                soundPlayer.playHit();
                scoreManager.updateHighScore();
                return;
            }
            if(collisionDetector.checkCollisionWithMonsters(bird, monsterManager.getMonsters())){
                gameState = GameState.GAME_OVER;
                soundPlayer.playHit();
                scoreManager.updateHighScore();
                return;
            }
        } else if(bird.isOutOfBounds()){
            gameState = GameState.GAME_OVER;
            soundPlayer.playHit();
            scoreManager.updateHighScore();
        }

    }

    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) { createBufferStrategy(3); return; }

        Graphics2D g = (Graphics2D) bs.getDrawGraphics();

        backgroundRenderer.render(g);
        pipeManager.render(g);
        powerUpManager.render(g);
        monsterManager.render(g);
        bulletManager.render(g);
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
