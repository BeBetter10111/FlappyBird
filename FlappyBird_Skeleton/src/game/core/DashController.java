package game.core;

import java.awt.Color;
import java.util.List;

/**
 * Quản lý vòng đời Dash (SRP — tách logic dash ra khỏi Bird).
 * Dùng Strategy Pattern: list phases có thể thêm/đổi mà không sửa class này.
 */
public class DashController {

    // ── Strategy chain ────────────────────────────────────────────────────────
    private final List<DashSpeedStrategy> phases = List.of(
        new DashStrategies.RushStrategy(),
        new DashStrategies.SlowStrategy(),
        new DashStrategies.FreezeStrategy()
    );

    private int phaseIndex = -1;   // -1 = inactive
    private int phaseTick  = 0;

    // ── Rainbow flicker (RUSH + SLOW) ─────────────────────────────────────────
    private static final Color[] RAINBOW = {
        new Color(255, 0,   0),
        new Color(255, 127, 0),
        new Color(255, 255, 0),
        new Color(0,   200, 0),
        new Color(0,   120, 255),
        new Color(75,  0,   130),
        new Color(148, 0,   211),
    };
    private static final int RAINBOW_INTERVAL = 4;
    private int rainbowIndex = 0;
    private int rainbowTick  = 0;

    // ── Freeze white flicker ──────────────────────────────────────────────────
    private static final int FREEZE_FLICKER_INTERVAL = 10;
    private int freezeFlickerTick = 0;

    /** Bird gọi khi ăn DashPowerUp. */
    public void activate() {
        // TODO: set phaseIndex = 0, reset phaseTick, rainbow*, freeze*
    }

    public void reset() {
        // TODO: set phaseIndex = -1, phaseTick = 0
    }

    public void update() {
        // TODO:
        // 1) Nếu không active (phaseIndex < 0) thì return
        // 2) phaseTick++
        // 3) Nếu đang freeze phase → tick freeze flicker, ngược lại → tick rainbow
        // 4) Nếu phaseTick >= phase hiện tại.getDurationTicks() → chuyển phase tiếp
        //    Nếu hết phase → set phaseIndex = -1
    }

    public boolean isActive()      { /* TODO */ return false; }
    public boolean isInvincible()  { /* TODO: isActive() && phases.get(phaseIndex).isInvincible() */ return false; }
    public boolean isFreezePhase() { /* TODO: phaseIndex == phases.size() - 1 */ return false; }

    public float getSpeedMultiplier() {
        // TODO: nếu !isActive() → return 1.0f
        //       ngược lại → return phases.get(phaseIndex).getSpeedMultiplier(phaseTick)
        return 1.0f;
    }

    /** Trả về màu rainbow hiện tại, hoặc null nếu không trong phase rainbow. */
    public Color getRainbowTint() {
        // TODO: nếu !isActive() hoặc isFreezePhase() → return null
        //       ngược lại → return RAINBOW[rainbowIndex]
        return null;
    }

    /** true khi freeze phase đang ở nhịp trắng (để Bird vẽ nhấp nháy). */
    public boolean isWhiteFlicker() {
        // TODO: chỉ true khi isFreezePhase() và (freezeFlickerTick / INTERVAL) % 2 == 1
        return false;
    }
}
