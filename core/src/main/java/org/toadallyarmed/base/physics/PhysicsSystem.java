package org.toadallyarmed.base.physics;

import org.toadallyarmed.base.transform.TransformComponent;
import org.toadallyarmed.base.entity.Entity;
import org.toadallyarmed.base.entity.EntityType;
import org.toadallyarmed.base.system.System;
import org.toadallyarmed.util.log.Logger;

import java.util.Collection;
import java.util.Optional;

public class PhysicsSystem implements System {
    @Override
    public void tick(float deltaTime, Collection<Entity> entities) {
        Logger.trace("PhysicsSystem: tick");
        float currentNanoTime = java.lang.System.nanoTime();
        for (Entity entity : entities) {
            if (entity.isMarkedForRemoval()) continue;
            Optional<TransformComponent> transformComponentOpt = entity.get(TransformComponent.class);
            if (transformComponentOpt.isEmpty()) continue;
            TransformComponent transformComponent = transformComponentOpt.get();
            transformComponent.setPosition(transformComponent.getAdvancedPosition(currentNanoTime), currentNanoTime);
            if (transformComponent.getAdvancedPosition(currentNanoTime).x<-0.5f) entities.add(new Entity(EntityType.LOSING));
        }
    }
}
