package com.github.joelvaningen.timetools.time;

public class MinecraftTime {
    private static final double TICKS_PER_MINUTE = 1000. / 60.;

    private final long ticks;

    public MinecraftTime(long ticks) {
        this.ticks = ticks;
    }

    public long getTicks() {
        return ticks;
    }

    public static MinecraftTime fromComponents(int hours, int minutes) {
        long ticks = (long) ((hours - 6) % 24) * 1_000L + (long) (minutes * TICKS_PER_MINUTE);
        return new MinecraftTime(ticks);
    }

    public String toString() {
        int hours = (int) ((ticks / 1_000) + 6) % 24;
        int minutes = (int) Math.round(((double) (ticks % 1_000)) / TICKS_PER_MINUTE);

        return String.format("%02d:%02d", hours, minutes);
    }
}