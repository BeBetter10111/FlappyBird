package game.core;

/**
 * Observer/Listener — PowerUpManager báo cho GameLoop biết bird vừa ăn powerup.
 * DIP: GameLoop không cần biết DashPowerUp cụ thể.
 */
public interface PowerUpCollisionListener {
    void onDashCollected();
}
