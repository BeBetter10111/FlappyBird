package game.core;

import game.audio.SoundPlayer;
import game.entities.Bird;
import game.entities.Floor;
import game.entities.PipeFactory;
import game.entities.PipeManager;
import game.entities.PowerUpManager;
import game.rendering.BackgroundRenderer;
import game.rendering.HudRenderer;
import game.entities.MonsterManager;
import game.entities.BulletManager;


/**
 * Builder Pattern — tách việc khởi tạo dependencies ra khỏi GameLoop.
 */
public class GameLoopBuilder {

    private AssetLoader        assetLoader;
    private SoundPlayer        soundPlayer;
    private DashController     dashController;
    private Bird               bird;
    private PipeFactory        pipeFactory;
    private PipeManager        pipeManager;
    private PowerUpManager     powerUpManager;
    private Floor              floor;
    private ScoreManager       scoreManager;
    private CollisionDetector  collisionDetector;
    private BackgroundRenderer backgroundRenderer;
    private HudRenderer        hudRenderer;
    private MonsterManager     monsterManager;
    private BulletManager      bulletManager;

    public GameLoopBuilder assetLoader(AssetLoader v)        { this.assetLoader = v; return this; }
    public GameLoopBuilder bird(Bird v)                      { this.bird = v; return this; }
    public GameLoopBuilder soundPlayer(SoundPlayer v)        { this.soundPlayer = v; return this; } 
    public GameLoopBuilder dashController(DashController v) { this.dashController = v; return this; }
    public GameLoopBuilder pipeFactory(PipeFactory v)       { this.pipeFactory = v; return this; }
    public GameLoopBuilder pipeManager(PipeManager v)       { this.pipeManager = v; return this; }
    public GameLoopBuilder powerUpManager(PowerUpManager v) { this.powerUpManager = v; return this; }
    public GameLoopBuilder floor(Floor v)                    { this.floor = v; return this; }
    public GameLoopBuilder scoreManager(ScoreManager v)     { this.scoreManager = v; return this; }
    public GameLoopBuilder collisionDetector(CollisionDetector v) { this.collisionDetector = v; return this; }
    public GameLoopBuilder backgroundRenderer(BackgroundRenderer v) { this.backgroundRenderer = v; return this; }
    public GameLoopBuilder hudRenderer(HudRenderer v)       { this.hudRenderer = v; return this; }
    public GameLoopBuilder monsterManager(MonsterManager v) { this.monsterManager = v; return this; }      
    public GameLoopBuilder bulletManager(BulletManager v)   { this.bulletManager = v; return this; }  

    public GameLoop build() {
        if (assetLoader == null) assetLoader = new AssetLoader();
        if (soundPlayer == null) soundPlayer = new SoundPlayer(assetLoader);
        if (dashController == null) dashController = new DashController();
        if (bird == null) bird = new Bird(assetLoader, dashController);
        if (pipeFactory == null) pipeFactory = new PipeFactory(assetLoader);
        if (pipeManager == null) pipeManager = new PipeManager(pipeFactory);
        if (powerUpManager == null) powerUpManager = new PowerUpManager(assetLoader);
        if (floor == null) floor = new Floor(assetLoader);
        if (scoreManager == null) scoreManager = new ScoreManager();
        if (collisionDetector == null) collisionDetector = new CollisionDetector();
        if (backgroundRenderer == null) backgroundRenderer = new BackgroundRenderer(assetLoader);
        if (hudRenderer == null) hudRenderer = new HudRenderer(assetLoader);
        if (monsterManager == null) monsterManager = new MonsterManager(assetLoader);
        if (bulletManager == null) bulletManager = new BulletManager(assetLoader);
        

        return new GameLoop(soundPlayer, bird, pipeManager,
            powerUpManager, floor, scoreManager, collisionDetector,
            backgroundRenderer, hudRenderer, monsterManager, bulletManager);
    }
}
