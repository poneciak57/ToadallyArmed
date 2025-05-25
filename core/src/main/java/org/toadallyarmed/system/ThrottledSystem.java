package org.toadallyarmed.system;

import org.toadallyarmed.entity.Entity;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/// System which tick method is throttled
public class ThrottledSystem implements System {
    private final System system;
    private final float interval;
    private float accumulatedTime;
    private final AtomicBoolean running = new AtomicBoolean(false);

    public ThrottledSystem(float tickRate, System system) {
        this.interval = 1f / tickRate;
        this.system = system;
        this.accumulatedTime = interval;
    }

    @Override
    public void tick(float deltaTime, ConcurrentLinkedQueue<Entity> entities) {
        if (running.get()) return;
        accumulatedTime += deltaTime;
        if (accumulatedTime >= interval) {
            final float finalAccumulatedTime = accumulatedTime;
            running.set(true);
            accumulatedTime %= interval;
            system.tick(finalAccumulatedTime, entities);
            running.set(false);
        }
    }
}
