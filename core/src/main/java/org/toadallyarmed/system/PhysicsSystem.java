package org.toadallyarmed.system;

import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.util.logger.Logger;

import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PhysicsSystem implements System {
    @Override
    public void tick(float deltaTime, ConcurrentLinkedQueue<Entity> entities) {
        Logger.trace("PhysicsSystem: tick");
        float currentNanoTime = java.lang.System.nanoTime();
        for (Entity entity : entities) {
            if (entity.isMarkedForRemoval()) continue;
            Optional<TransformComponent> transformComponentOpt = entity.get(TransformComponent.class);
            if (transformComponentOpt.isEmpty()) continue;
            TransformComponent transformComponent = transformComponentOpt.get();
            transformComponent.setPosition(transformComponent.getAdvancedPosition(currentNanoTime), currentNanoTime);
        }
    }
}
