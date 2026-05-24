package game.core;

/**
 * Strategy Pattern — 3 phase Dash, mỗi cái 1 class độc lập.
 * Thêm phase mới = thêm 1 inner class implement DashSpeedStrategy.
 */
public final class DashStrategies {

    private DashStrategies() {}

    //Phase 1 — RUSH
    public static class RushStrategy implements DashSpeedStrategy {
        private static final float SPEED = 3.0f;
        private static final int   TICKS = 90;

        @Override
        public float getSpeedMultiplier(int tick) {
            return SPEED;
        }

        @Override public int     getDurationTicks() { return TICKS; }
        @Override public boolean isInvincible()     { return true; }
    }

    //Phase 2 — SLOW
    public static class SlowStrategy implements DashSpeedStrategy {
        private static final float FROM  = 3.0f;
        private static final float TO    = 1.0f;
        private static final int   TICKS = 50;

        @Override
        public float getSpeedMultiplier(int tick) {

            float t = Math.min(1.0f, (float) tick / TICKS);
            return FROM + t * (TO - FROM);        }

        @Override public int     getDurationTicks() { return TICKS; }
        @Override public boolean isInvincible()     { return true; }
    }

    //Phase 3 — FREEZE
    public static class FreezeStrategy implements DashSpeedStrategy {
        private static final float SPEED = 1.0f;
        private static final int   TICKS = 50;

        @Override
        public float getSpeedMultiplier(int tick) {
            return SPEED;
        }

        @Override public int     getDurationTicks() { return TICKS; }
        @Override public boolean isInvincible()     { return true; }
    }
}
