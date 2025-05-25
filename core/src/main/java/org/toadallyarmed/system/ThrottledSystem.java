package org.toadallyarmed.system;

import org.toadallyarmed.entity.Entity;

import java.util.concurrent.ConcurrentLinkedQueue;

/// System which tick method is throttled
public class ThrottledSystem implements System {
    private final System system;
    private final float interval;
    private float accumulatedTime;

    public ThrottledSystem(float tickRate, System system) {
        this.interval = 1f / tickRate;
        this.system = system;
        this.accumulatedTime = interval;
    }

    @Override
    public void tick(float deltaTime, ConcurrentLinkedQueue<Entity> entities) {
        accumulatedTime += deltaTime;
        if (accumulatedTime >= interval) {
            final float finalAccumulatedTime = accumulatedTime;
            accumulatedTime %= interval;
            system.tick(finalAccumulatedTime, entities);
        }
    }
}
