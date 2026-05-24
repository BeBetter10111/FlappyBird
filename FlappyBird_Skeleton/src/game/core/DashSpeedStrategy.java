package game.core;

/**
 * Strategy Pattern — mỗi phase Dash có cách tính speed riêng.
 * Thêm phase mới = thêm class implement interface này, không sửa code cũ.
 */
public interface DashSpeedStrategy {
    float getSpeedMultiplier(int tick);
    int   getDurationTicks();
    boolean isInvincible();
}
