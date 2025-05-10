package org.toadallyarmed.system;

import org.toadallyarmed.component.interfaces.RenderableComponent;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.util.rendering.Renderer;

import java.time.Instant;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RenderingSystem implements System {
    final private Renderer renderer;

    public RenderingSystem(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void tick(float deltaTime, ConcurrentLinkedQueue<Entity> entities) {
        float currentTimestamp = Instant.now().toEpochMilli();
        for (Entity entity : entities) {
            var renderableOptional = entity.get(RenderableComponent.class);
            if (renderableOptional.isEmpty())
                continue;
            var renderable = renderableOptional.get();
            renderable.render(renderer, deltaTime, currentTimestamp);
        }
    }
}
