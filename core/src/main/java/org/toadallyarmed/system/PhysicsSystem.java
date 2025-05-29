package org.toadallyarmed.system;

import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.util.logger.Logger;

import java.util.concurrent.ConcurrentLinkedQueue;

public class PhysicsSystem implements System {
    @Override
    public void tick(float deltaTime, ConcurrentLinkedQueue<Entity> entities) {
        Logger.trace("PhysicsSystem: tick");
        float currentNanoTime = java.lang.System.nanoTime();
        for (Entity entity : entities) {
            if (entity.markedForRemoval()) continue;
            TransformComponent transformComponent = entity.get(TransformComponent.class).orElse(null);
            if (transformComponent == null) continue;
            transformComponent.setPosition(transformComponent.getAdvancedPosition(currentNanoTime), currentNanoTime);
        }
    }
}
