Flappy Bird - Java OOP Refactor
================================

SOLID Design Principles Applied
--------------------------------
S - Single Responsibility:
    Each class has one job: Bird (movement/animation), Floor (scrolling),
    PipeManager (pipe lifecycle), ScoreManager (scoring), SoundPlayer (audio),
    CollisionDetector (collision), HudRenderer (UI), BackgroundRenderer (BG).

O - Open/Closed:
    Renderable, Updatable, Collidable, Resettable interfaces allow extending
    behavior without modifying existing classes.

L - Liskov Substitution:
    All entities implementing Renderable/Updatable can be substituted freely.

I - Interface Segregation:
    Interfaces are small and focused: Renderable, Updatable, Collidable,
    Resettable are all separate rather than one large interface.

D - Dependency Inversion:
    GameLoop and systems depend on abstractions (interfaces), not concretions.
    AssetLoader is injected into components rather than hardcoded.

Project Structure
-----------------
src/
  game/
    Main.java
    core/     AssetLoader, GameLoop, CollisionDetector, ScoreManager, interfaces
    entities/ Bird, Floor, Pipe, PipeFactory, PipeManager
    rendering/ BackgroundRenderer, HudRenderer, Renderable
    audio/    SoundPlayer
    utils/    AssetPaths, GameConstants, GameState
assets/
  (all .png, .wav, .TTF files)

Build & Run
-----------
Requires Java 11+

IntelliJ IDEA (recommended):
  1. Open the FlappyBird_Java_OOP folder as a project
  2. The .idea/ config is included -- assets/ is pre-marked as a Resources Root
  3. If assets folder icon is not yellow: right-click "assets" folder
     -> Mark Directory As -> Resources Root
  4. Run game.Main

Command line (from project root):
  javac -d out -sourcepath src src/game/Main.java
  java -cp "out;assets" game.Main    (Windows)
  java -cp "out:assets" game.Main    (macOS/Linux)

Controls
--------
SPACE - Flap / Start new game after game over
