package org.toadallyarmed.system;

import org.toadallyarmed.entity.Entity;

import java.util.concurrent.ConcurrentLinkedQueue;

public class GarbageCollectorSystem implements System {
    @Override
    public void tick(float deltaTime, ConcurrentLinkedQueue<Entity> entities) {
        entities.removeIf(Entity::isMarkedForRemoval);
    }
}
