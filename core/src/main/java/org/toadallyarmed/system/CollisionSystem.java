package org.toadallyarmed.system;

import org.toadallyarmed.component.ColliderComponent;
import org.toadallyarmed.component.action.payload.BasicCollisionActionPayload;
import org.toadallyarmed.component.interfaces.ColliderActionEntry;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.gameplay.GlobalGameState;
import org.toadallyarmed.util.collision.GJK;
import org.toadallyarmed.util.logger.Logger;

import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CollisionSystem implements System {

    private final GlobalGameState gameState;

    public CollisionSystem(GlobalGameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void tick(float deltaTime, ConcurrentLinkedQueue<Entity> entities) {
        Logger.trace("CollisionSystem: tick");
        float currentNanoTime = java.lang.System.nanoTime();
        entities.stream()
            .filter(Entity::isActive)
            .forEach(entity -> {
            Optional<ColliderComponent> colliderComponent = entity.get(ColliderComponent.class);
            Optional<TransformComponent> transformComponent = entity.get(TransformComponent.class);
            if (colliderComponent.isEmpty() || transformComponent.isEmpty()) {
                return;
            }
            entities.stream()
                .filter(Entity::isActive)
                .forEach(otherEntity -> {
                    if (entity == otherEntity) return;
                    Optional<ColliderComponent> otherColliderComponent = otherEntity.get(ColliderComponent.class);
                    Optional<TransformComponent> otherTransformComponent = otherEntity.get(TransformComponent.class);
                    if (otherColliderComponent.isEmpty() || otherTransformComponent.isEmpty()) {
                        return;
                    }
                    BasicCollisionActionPayload payload = new BasicCollisionActionPayload(
                        this.gameState,
                        entity,
                        otherEntity,
                        currentNanoTime
                    );
                    for (ColliderActionEntry entry : colliderComponent.get().getEntries()) {
                        for (ColliderActionEntry otherEntry : otherColliderComponent.get().getEntries()) {
                            if (!entry.filter(otherEntity.type(), otherEntry.getColliderType())) continue;
                            boolean intersecting = GJK.intersects(
                                entry.getShape()
                                    .shiftedBy(
                                        transformComponent.get()
                                            .getAdvancedPosition(currentNanoTime)
                                    ),
                                otherEntry.getShape()
                                    .shiftedBy(
                                        otherTransformComponent.get()
                                            .getAdvancedPosition(currentNanoTime)
                                    )
                            );
                            if (intersecting) entry.run(currentNanoTime, payload);
                        }
                    }
                });
        });
    }
}
