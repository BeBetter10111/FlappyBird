package game.core;

import game.audio.SoundPlayer;
import game.entities.Bird;
import game.entities.Floor;
import game.entities.PipeFactory;
import game.entities.PipeManager;
import game.entities.PowerUpManager;
import game.rendering.BackgroundRenderer;
import game.rendering.HudRenderer;

/**
 * Builder Pattern — tách việc khởi tạo dependencies ra khỏi GameLoop.
 *
 * Cách dùng:
 *     GameLoop loop = new GameLoopBuilder().build();
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

    // Setter chaining — optional, để test có thể inject mock
    public GameLoopBuilder assetLoader(AssetLoader v)        { this.assetLoader = v; return this; }
    public GameLoopBuilder bird(Bird v)                      { this.bird = v; return this; }
    // TODO: thêm các setter khác tương tự nếu cần

    public GameLoop build() {
        // TODO: nếu field nào null thì gán default
        //  if (assetLoader == null) assetLoader = new AssetLoader();
        //  if (soundPlayer == null) soundPlayer = new SoundPlayer(assetLoader);
        //  if (dashController == null) dashController = new DashController();
        //  if (bird == null) bird = new Bird(assetLoader, dashController);
        //  ... (tương tự cho các dependency còn lại)

        // Cuối cùng: return new GameLoop(soundPlayer, bird, pipeManager,
        //     powerUpManager, floor, scoreManager, collisionDetector,
        //     backgroundRenderer, hudRenderer);

        return null;
    }
}
