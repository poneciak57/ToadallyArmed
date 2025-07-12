package org.toadallyarmed.system;

import org.toadallyarmed.entity.Entity;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GarbageCollectorSystem implements System {
    @Override
    public void tick(float deltaTime, Collection<Entity> entities) {
        entities.removeIf(Entity::isMarkedForRemoval);
    }
}
