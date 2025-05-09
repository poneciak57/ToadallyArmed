package org.toadallyarmed.system;

import org.toadallyarmed.component.HealthComponent;
import org.toadallyarmed.entity.Entity;

import java.util.concurrent.ConcurrentLinkedQueue;

/// HealthSystem is responsible for deleting entities which health dropped below 0
public class HealthSystem implements System {
    @Override
    public void tick(float __, ConcurrentLinkedQueue<Entity> entities) {
        entities.removeIf((e) -> {
            var h = e.get(HealthComponent.class);
            if (h.isEmpty()) return false;
            final var health = h.get();
            return health.access().get() <= 0;
        });
    }
}
