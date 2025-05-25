package org.toadallyarmed.component.action.payload;

import com.badlogic.gdx.math.Vector2;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.util.action.PayloadExtractor;

import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

public record OnCollisionShootActionPayload(
    Vector2 pos,
    ConcurrentLinkedQueue<Entity> entities
) {
    public static final PayloadExtractor<OnCollisionShootActionPayload, BasicCollisionActionPayload> EXTRACTOR =
        basicCollisionActionPayload -> {
            var pos = basicCollisionActionPayload.entity().get(TransformComponent.class);
            return pos.map(transformComponent -> new OnCollisionShootActionPayload(
                transformComponent.getAdvancedPosition(basicCollisionActionPayload.currentNanoTime()),
                basicCollisionActionPayload.gameState().getEntities()
            ));
        };
}
