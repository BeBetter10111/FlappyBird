Flappy Bird Java OOP — Skeleton
================================

Cấu trúc folder
---------------
src/game/
├── Main.java                          [entry point]
│
├── core/
│   ├── Renderable                     KHÔNG ở đây — ở game/rendering/
│   ├── Updatable.java                 [interface]
│   ├── Collidable.java                [interface]
│   ├── Resettable.java                [interface]
│   ├── DashSpeedStrategy.java         [interface — Strategy]
│   ├── PowerUpCollisionListener.java  [interface]
│   ├── AssetLoader.java               [load PNG, WAV, TTF]
│   ├── CollisionDetector.java         [bird vs pipes]
│   ├── ScoreManager.java              [đếm điểm + high score]
│   ├── DashStrategies.java            [3 phase Rush/Slow/Freeze]
│   ├── DashController.java            [vòng đời dash, dùng Strategy]
│   ├── GameLoop.java                  [main loop]
│   └── GameLoopBuilder.java           [Builder pattern]
│
├── entities/
│   ├── Bird.java                      [chim người chơi]
│   ├── Pipe.java                      [1 ống]
│   ├── PipeFactory.java               [sinh cặp ống]
│   ├── PipeManager.java               [danh sách Pipe]
│   ├── Floor.java                     [sàn cuộn]
│   ├── DashPowerUp.java               [icon Dash]
│   ├── PowerUpManager.java            [danh sách DashPowerUp]
│   ├── Monster.java                   [quái có HP]
│   ├── MonsterManager.java            [danh sách Monster]
│   ├── Bullet.java                    [đạn người chơi]
│   └── BulletManager.java             [danh sách Bullet]
│
├── rendering/
│   ├── Renderable.java                [interface]
│   ├── BackgroundRenderer.java        [day/night cycle]
│   └── HudRenderer.java               [score, game over UI]
│
├── audio/
│   └── SoundPlayer.java
│
└── utils/
    ├── AssetPaths.java                [đường dẫn assets]
    ├── GameConstants.java             [hằng số game]
    └── GameState.java                 [enum]

assets/
├── (copy hết file .png .wav .TTF từ project gốc vào đây)
├── Dash.png
└── background-day.png                 [mới]


SOLID + Design Patterns đã áp dụng
-----------------------------------
S — Single Responsibility:
    Mỗi class 1 việc. Bird chỉ lo physics+render, DashController lo dash logic,
    Manager lo collection, GameLoop lo orchestration.

O — Open/Closed:
    Thêm phase Dash mới = thêm class implement DashSpeedStrategy, không sửa Bird/DashController.
    Thêm entity mới = implement Renderable/Updatable/..., không sửa GameLoop.

L — Liskov Substitution:
    Mọi Renderable có thể substitute nhau qua interface.

I — Interface Segregation:
    Interface nhỏ: Renderable, Updatable, Collidable, Resettable — class chỉ implement cái cần.

D — Dependency Inversion:
    GameLoop nhận dependencies qua constructor (không tự new).
    PowerUpManager báo qua PowerUpCollisionListener — không gọi thẳng Bird.

Builder Pattern:
    GameLoopBuilder xây GameLoop với toàn bộ dependencies trong 1 dòng:
        new GameLoopBuilder().build();

Strategy Pattern:
    DashSpeedStrategy + 3 class RushStrategy / SlowStrategy / FreezeStrategy.
    DashController giữ List<DashSpeedStrategy>, chuyển phase tự động.


Thứ tự code (đề xuất)
----------------------
GIAI ĐOẠN 1 — Cả 2 cùng làm cho thống nhất:
  □ utils/AssetPaths.java
  □ utils/GameConstants.java
  □ utils/GameState.java
  □ rendering/Renderable.java
  □ core/Updatable.java
  □ core/Collidable.java
  □ core/Resettable.java
  □ core/DashSpeedStrategy.java
  □ core/PowerUpCollisionListener.java
  □ core/AssetLoader.java

GIAI ĐOẠN 2 — Code song song:

Đứa A (Game world):
  □ entities/Pipe.java
  □ entities/PipeFactory.java
  □ entities/PipeManager.java
  □ entities/Floor.java
  □ entities/DashPowerUp.java
  □ entities/PowerUpManager.java
  □ entities/Bird.java
  □ core/DashStrategies.java
  □ core/DashController.java
  □ core/CollisionDetector.java

Đứa B (Systems & polish):
  □ entities/Monster.java
  □ entities/MonsterManager.java
  □ entities/Bullet.java
  □ entities/BulletManager.java
  □ rendering/BackgroundRenderer.java
  □ rendering/HudRenderer.java
  □ audio/SoundPlayer.java
  □ core/ScoreManager.java

GIAI ĐOẠN 3 — Cả 2 cùng ráp:
  □ core/GameLoop.java
  □ core/GameLoopBuilder.java
  □ Main.java
  □ Test + fix bug integration


Build & Run
-----------
Java 11+. IntelliJ:
  1. Mở folder này như project
  2. Right-click "assets" → Mark Directory As → Resources Root
  3. Right-click "src" → Mark Directory As → Sources Root
  4. Run game.Main

Command line (từ project root):
  javac -d out -sourcepath src src/game/Main.java
  java -cp "out;assets" game.Main      (Windows)
  java -cp "out:assets" game.Main      (macOS/Linux)


Controls
--------
SPACE — Flap / Start lại sau game over
TODO: thêm phím bắn đạn (vd phím Z hoặc F) → BulletManager.fire(bird.getX(), bird.getY())


Mẹo debug
---------
- Lỗi "Asset not found": chưa Mark Directory As → Resources Root cho thư mục assets
- Cửa sổ trắng / không vẽ: thiếu BufferStrategy hoặc gameThread chưa start
- Chim rớt liền: chưa init y = BIRD_START_Y trong constructor Bird
- Pipe chạy quá nhanh: speedMultiplier chưa set lại về 1.0f sau khi dash
