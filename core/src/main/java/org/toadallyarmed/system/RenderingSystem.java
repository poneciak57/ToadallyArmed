package org.toadallyarmed.system;

import org.toadallyarmed.component.interfaces.RenderableComponent;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.util.logger.Logger;
import org.toadallyarmed.util.rendering.Renderer;

import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RenderingSystem implements System {
    final private Renderer renderer;

    public RenderingSystem(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void tick(float deltaTime, ConcurrentLinkedQueue<Entity> entities) {
        Logger.trace("RenderingSystem: tick");
        float currentNanoTime = java.lang.System.nanoTime();
        entities.stream()
            .filter(Entity::isActive)
            .map(entity -> entity.get(RenderableComponent.class))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .forEach(renderable -> renderable.render(renderer, deltaTime, currentNanoTime));
    }
}
