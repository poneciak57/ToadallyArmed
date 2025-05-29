package org.toadallyarmed.system;

import org.toadallyarmed.component.interfaces.RenderableComponent;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.util.logger.Logger;
import org.toadallyarmed.util.rendering.Renderer;

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
        for (Entity entity : entities) {
            if (entity.markedForRemoval()) continue;
            var renderableOptional = entity.get(RenderableComponent.class);
            if (renderableOptional.isEmpty())
                continue;
            var renderable = renderableOptional.get();
            renderable.render(renderer, deltaTime, currentNanoTime);
        }
    }
}
