package org.toadallyarmed.component.action.payload;

import com.badlogic.gdx.math.Vector2;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.util.action.PayloadExtractor;

import java.util.concurrent.ConcurrentLinkedQueue;

public record FrogAttackCollisionActionPayload(
    Vector2 pos,
    ConcurrentLinkedQueue<Entity> entities
) {
    public static final PayloadExtractor<FrogAttackCollisionActionPayload, BasicCollisionActionPayload> EXTRACTOR =
        basicCollisionActionPayload -> {
            var pos = basicCollisionActionPayload.entity().get(TransformComponent.class);
            return pos.map(transformComponent -> new FrogAttackCollisionActionPayload(
                transformComponent.getAdvancedPosition(basicCollisionActionPayload.currentNanoTime()),
                basicCollisionActionPayload.gameState().getEntities()
            ));
        };
}
