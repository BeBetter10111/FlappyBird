package game.core;

/**
 * Strategy Pattern — 3 phase Dash, mỗi cái 1 class độc lập.
 * Thêm phase mới = thêm 1 inner class implement DashSpeedStrategy.
 */
public final class DashStrategies {

    private DashStrategies() {}

    /**
     * Phase 1 — RUSH: lướt nhanh x4 trong 90 tick (~0.75s).
     */
    public static class RushStrategy implements DashSpeedStrategy {
        private static final float SPEED = 4.0f;
        private static final int   TICKS = 90;

        @Override
        public float getSpeedMultiplier(int tick) {

            return SPEED;
        }

        @Override public int     getDurationTicks() { return TICKS; }
        @Override public boolean isInvincible()     { return true; }
    }

    /**
     * Phase 2 — SLOW: speed lerp từ 4.0 về 1.0 trong 60 tick (~0.5s).
     */
    public static class SlowStrategy implements DashSpeedStrategy {
        private static final float FROM  = 4.0f;
        private static final float TO    = 1.0f;
        private static final int   TICKS = 60;

        @Override
        public float getSpeedMultiplier(int tick) {

            float t = Math.min(1.0f, (float) tick / TICKS);
            return FROM + t * (TO - FROM);        }

        @Override public int     getDurationTicks() { return TICKS; }
        @Override public boolean isInvincible()     { return true; }
    }

    /**
     * Phase 3 — FREEZE: slowmo gần đứng yên 180 tick (~1.5s) — cảnh báo sắp hết.
     */
    public static class FreezeStrategy implements DashSpeedStrategy {
        private static final float SPEED = 0.08f;
        private static final int   TICKS = 180;

        @Override
        public float getSpeedMultiplier(int tick) {

            return SPEED;
        }

        @Override public int     getDurationTicks() { return TICKS; }
        @Override public boolean isInvincible()     { return true; }
    }
}
