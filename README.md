## Flappy Bird - Java OOP Project

A feature-rich implementation of the classic Flappy Bird game in Java, showcasing professional Object-Oriented Programming principles, SOLID design patterns, and enterprise-level software architecture.

![Java](https://img.shields.io/badge/Java-11+-orange)
![License](https://img.shields.io/badge/license-MIT-blue)
![OOP](https://img.shields.io/badge/OOP-SOLID%20Principles-green)
![Design Patterns](https://img.shields.io/badge/Design%20Patterns-7%20Patterns-purple)

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Requirements](#-requirements)
- [Installation](#-installation)
- [How to Play](#-how-to-play)
- [Game Architecture](#-game-architecture)
- [SOLID Principles](#-solid-principles)
- [Design Patterns](#-design-patterns)
- [Project Structure](#-project-structure)
- [Contributing](#-contributing)

---

## 🎮 Overview

Flappy Bird is a classic arcade-style game recreated in Java with a focus on demonstrating advanced OOP concepts and design patterns. This implementation goes beyond a simple game by providing a well-architected, maintainable, and extensible codebase.

The project serves as an educational resource for:
- Object-Oriented Programming principles
- SOLID design principles
- Enterprise design patterns
- Game development architecture
- Software engineering best practices

---

## ✨ Features

- **Classic Gameplay**: Navigate the bird through obstacles and earn points
- **Power-ups System**: 
  - 🚀 **Dash Power-up**: Sprint through obstacles
  - 🔫 **Bullet Power-up**: Shoot through pipes
- **Obstacle Management**: Dynamic pipe generation with varied gaps
- **Monster Enemies**: Additional challenge with intelligent opponent behavior
- **Sound Effects**: Audio feedback for gameplay events
- **Score Tracking**: Real-time score display and management
- **Game State Management**: Pause, resume, and reset functionality

---

## 📦 Requirements

- **Java**: 11 or higher
- **Platform**: Windows, macOS, or Linux
- **IDE** (optional): IntelliJ IDEA, Eclipse, or any Java IDE
- **RAM**: 512 MB minimum

---

## 🚀 Installation

### Option 1: IntelliJ IDEA (Recommended)

1. **Open the Project**
   - Launch IntelliJ IDEA
   - Select "Open" and navigate to the `FlappyBird` folder

2. **Configure Project Structure**
   - Right-click on the `assets` folder → Mark Directory As → Resources Root
   - Right-click on the `src` folder → Mark Directory As → Sources Root

3. **Run the Game**
   - Locate `src/game/Main.java`
   - Right-click and select "Run 'Main.main()'"

### Option 2: Command Line

**Windows:**
```bash
javac -d out -sourcepath src src/game/Main.java
java -cp "out;assets" game.Main
```

**macOS/Linux:**
```bash
javac -d out -sourcepath src src/game/Main.java
java -cp "out:assets" game.Main
```

### Option 3: Build Script (if available)

```bash
./build.sh        # macOS/Linux
build.bat         # Windows
```

---

## How to Play

| Action | Control | Effect |
|--------|---------|--------|
| **Flap** | SPACE or CLICK | Make the bird jump |
| **Pause** | P | Pause/Resume game |
| **Reset** | R | Start a new game |
| **Dash** | D (if available) | Activate dash power-up (sprint mode) |
| **Shoot** | S (if available) | Fire bullets (if bullet power-up active) |

**Objective:**
- Navigate the bird through the gaps in the pipes
- Avoid collisions with pipes, ground, and monsters
- Collect power-ups to gain advantages
- Achieve the highest score possible

**Scoring:**
- +1 point for each pipe successfully passed
- Bonus points when collecting power-ups

---

## Game Architecture

The game is built using a layered architecture with clear separation of concerns:

```
┌─────────────────────────────────────┐
│      Game Loop (Orchestrator)       │
├─────────────────────────────────────┤
│  Core Logic Layer                   │
│  ├─ Collision Detection             │
│  ├─ State Management                │
│  └─ Asset Loading                   │
├─────────────────────────────────────┤
│  Entity Layer                       │
│  ├─ Bird (Player)                   │
│  ├─ Pipes (Obstacles)               │
│  ├─ Monsters (Enemies)              │
│  └─ Power-ups                       │
├─────────────────────────────────────┤
│  Rendering Layer                    │
│  └─ Graphics Rendering              │
├─────────────────────────────────────┤
│  Audio Layer                        │
│  └─ Sound Effects                   │
└─────────────────────────────────────┘
```

### Core Components

| Component | Responsibility |
|-----------|-----------------|
| **GameLoop** | Main game loop orchestration; FPS control |
| **CollisionDetector** | Detects collisions between entities |
| **AssetLoader** | Loads sprites, sounds, and resources |
| **ScoreManager** | Tracks and updates game score |
| **PowerUpManager** | Manages power-up spawning and effects |
| **GameState** | Maintains current game state |

---

## SOLID Principles

### **S** — Single Responsibility Principle
Each class has a single, well-defined responsibility:
- `Bird.java`: Handles bird physics and state only
- `DashController.java`: Manages dash mechanics exclusively
- `Manager` classes: Manage collections of their entities
- `GameLoop.java`: Orchestrates game flow

✅ **Benefit**: Easy to test, maintain, and modify individual components

### **O** — Open/Closed Principle
Open for extension, closed for modification:
- Adding new **Dash strategies**: Create a new class implementing `DashSpeedStrategy`
- Adding new **entities**: Implement `Renderable`, `Updatable`, `Collidable` interfaces
- No need to modify existing classes when extending functionality

✅ **Benefit**: Extensible without breaking existing code

### **L** — Liskov Substitution Principle
Subtypes must be substitutable for their base types:
- All `Renderable` implementations can be used interchangeably
- Any `PowerUp` subclass works with `PowerUpManager`
- All game entities follow consistent update/render contracts

✅ **Benefit**: Polymorphic behavior; easy to add new entity types

### **I** — Interface Segregation Principle
Clients depend only on interfaces they use:
- `Renderable`: Drawing contracts only
- `Updatable`: Update logic only
- `Collidable`: Collision detection only
- `Resettable`: Game reset functionality only

✅ **Benefit**: Classes implement only what they need; cleaner dependencies

### **D** — Dependency Inversion Principle
Depend on abstractions, not concretions:
- `GameLoop` receives dependencies via constructor
- `PowerUpManager` uses `PowerUpCollisionListener` interface
- No hardcoded object creation inside classes

✅ **Benefit**: Loose coupling; easy testing and dependency injection

---

## Design Patterns

### 1. **Builder Pattern**
Simplifies complex object construction:
```java
GameLoop gameLoop = new GameLoopBuilder()
    .withBird(new Bird(...))
    .withPipeManager(new PipeManager(...))
    .build();
```
✅ Readable, fluent API for object creation

### 2. **Strategy Pattern**
Encapsulates dash speed algorithms:
```java
DashSpeedStrategy strategy = new RushStrategy(); // or SlowStrategy, FreezeStrategy
dashController.setStrategy(strategy);
```
✅ Runtime algorithm switching; easy to add new strategies

### 3. **Observer/Listener Pattern**
Decouples collision detection from response:
```java
powerUpManager.addCollisionListener(new PowerUpCollisionListener() {
    public void onCollision(PowerUp powerUp) { /* handle */ }
});
```
✅ Loose coupling between components

### 4. **Factory Pattern**
Encapsulates entity creation:
```java
Pipe pipe = PipeFactory.createPipe(x, y, gapSize);
```
✅ Centralized object creation logic

### 5. **Manager/Pool Pattern**
Manages collections of related objects:
- `PipeManager`: Manages all pipes
- `MonsterManager`: Manages all monsters
- `PowerUpManager`: Manages all power-ups

✅ Centralized lifecycle management

### 6. **State Pattern**
Manages game states (Running, Paused, GameOver):
```java
GameState state = GameState.RUNNING;
// State-specific logic
```
✅ Clear state transitions

### 7. **Adapter Pattern**
Adapts entities to rendering/collision interfaces:
```java
Bird implements Renderable, Updatable, Collidable, Resettable
```
✅ Multiple interface contracts; flexible component composition

---

## Project Structure

```
FlappyBird/
├── src/game/
│   ├── Main.java                    # Entry point
│   ├── audio/
│   │   └── SoundPlayer.java         # Audio management
│   ├── core/                        # Core game logic
│   │   ├── GameLoop.java            # Main game loop
│   │   ├── GameLoopBuilder.java     # Builder pattern
│   │   ├── CollisionDetector.java   # Collision detection
│   │   ├── AssetLoader.java         # Resource loading
│   │   ├── ScoreManager.java        # Score tracking
│   │   └── *.java                   # Other core components
│   ├── entities/                    # Game entities
│   │   ├── Bird.java                # Player character
│   │   ├── Pipe.java                # Obstacles
│   │   ├── Monster.java             # Enemy
│   │   ├── Bullet.java              # Projectile
│   │   ├── *Manager.java            # Entity managers
│   │   └── *PowerUp.java            # Power-up types
│   ├── rendering/                   # Rendering layer
│   │   ├── BirdRenderer.java
│   │   ├── BackgroundRenderer.java
│   │   └── HudRenderer.java
│   └── utils/
│       ├── GameConstants.java       # Game configuration
│       ├── AssetPaths.java          # Asset locations
│       └── GameState.java           # State enumerations
├── assets/                          # Game resources
│   ├── sprites/                     # Image assets
│   └── sounds/                      # Audio files
├── README.md                        # This file
└── FlappyBird_Java_OOP.iml          # IntelliJ project file
```

---

## Contributing

Contributions are welcome! To contribute:

1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/AmazingFeature`)
3. **Commit** your changes (`git commit -m 'Add AmazingFeature'`)
4. **Push** to the branch (`git push origin feature/AmazingFeature`)
5. **Open** a Pull Request

### Areas for Contribution
- Additional game features
- New power-ups and dash strategies
- Performance optimization
- Unit tests and integration tests
- Documentation improvements
- Graphics and sound assets

---

## License

This project is licensed under the **MIT License** — see the LICENSE file for details.

---

## Credits

Created as an educational project demonstrating professional Java development practices, OOP principles, and game architecture design.

**Inspired by**: The original Flappy Bird game by Dong Nguyen

---

## FAQ

**Q: Can I modify and redistribute this game?**
A: Yes, as long as you follow the MIT License terms and provide attribution.

**Q: How can I add new features?**
A: The architecture supports easy extension through interfaces and design patterns. Follow the existing patterns for consistency.

**Q: Is this game optimized for performance?**
A: The game is designed for educational purposes with a focus on architecture. Performance can be optimized further based on requirements.

---

## Support

For questions or issues, please check the documentation or create an issue in the repository.

