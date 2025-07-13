package org.toadallyarmed.base.render;

import org.toadallyarmed.base.transform.TransformComponent;
import org.toadallyarmed.base.entity.Entity;
import org.toadallyarmed.base.entity.EntityType;
import org.toadallyarmed.base.system.System;
import org.toadallyarmed.util.log.Logger;
import org.toadallyarmed.util.render.Renderer;

import java.util.Collection;
import java.util.Optional;

public class RenderingSystem implements System {
    final private Renderer renderer;

    public RenderingSystem(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void tick(float deltaTime, Collection<Entity> entities) {
        Logger.trace("RenderingSystem: tick");
        float currentNanoTime = java.lang.System.nanoTime();
        entities.stream()
            .filter(Entity::isActive)
            .sorted((a, b) -> {
                final EntityType typeA = a.type();
                final EntityType typeB = b.type();
                final int typesComparison = Integer.compare(typeA.renderOrder(), typeB.renderOrder());
                if (typesComparison != 0) return typesComparison;

                final var transformAOpt = a.get(TransformComponent.class);
                final var transformBOpt = b.get(TransformComponent.class);
                if (transformAOpt.isEmpty() && transformBOpt.isEmpty()) return 0;
                if (transformAOpt.isEmpty()) return 1;
                if (transformBOpt.isEmpty()) return -1;

                final TransformComponent transformA = transformAOpt.get();
                final TransformComponent transformB = transformBOpt.get();

                return Float.compare(
                    transformB.getAdvancedPosition(currentNanoTime).y,
                    transformA.getAdvancedPosition(currentNanoTime).y);
            })
            .map(entity -> entity.get(RenderableComponent.class))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .forEach(renderable -> renderable.render(renderer, deltaTime, currentNanoTime));
    }
}
