package org.toadallyarmed.util;

public class Throttler {
    private final float interval;
    private float accumulatedTime;
    private float lastTime = 0f;

    public Throttler(float tickRate) {
        this.interval = 1f / tickRate;
        this.accumulatedTime = interval;
    }

    public void runPeriodically(Runnable runnable) {
        final float currentNanoTime = System.nanoTime();
        float deltaTime = (currentNanoTime - lastTime) / 1_000_000_000f;
        this.lastTime = currentNanoTime;
        accumulatedTime += deltaTime;
        if (accumulatedTime >= interval) {
            runnable.run();
            accumulatedTime = 0.f;
        }
    }
}
