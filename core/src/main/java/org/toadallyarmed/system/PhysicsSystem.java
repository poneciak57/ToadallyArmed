package org.toadallyarmed.system;

import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.entity.Entity;

import java.time.Instant;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PhysicsSystem implements System {
    @Override
    public void tick(float deltaTime, ConcurrentLinkedQueue<Entity> entities) {
        float currentTimestamp = Instant.now().toEpochMilli();
        for (Entity entity : entities) {
            TransformComponent transformComponent = entity.get(TransformComponent.class).orElse(null);
            if (transformComponent == null) continue;
            transformComponent.setPosition(transformComponent.getAdvancedPosition(currentTimestamp), currentTimestamp);
        }
    }
}
