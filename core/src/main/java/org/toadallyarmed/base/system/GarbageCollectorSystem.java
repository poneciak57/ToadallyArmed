package org.toadallyarmed.base.system;

import org.toadallyarmed.base.entity.Entity;

import java.util.Collection;

public class GarbageCollectorSystem implements System {
    @Override
    public void tick(float deltaTime, Collection<Entity> entities) {
        entities.removeIf(Entity::isMarkedForRemoval);
    }
}
