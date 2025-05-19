package org.toadallyarmed.system;

import org.toadallyarmed.component.ColliderComponent;
import org.toadallyarmed.component.action.payload.BasicCollisionActionPayload;
import org.toadallyarmed.component.interfaces.ColliderActionEntry;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.gameplay.GlobalGameState;
import org.toadallyarmed.util.collision.GJK;
import org.toadallyarmed.util.logger.Logger;

import java.util.List;
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
//        float currentNanoTime = java.lang.System.nanoTime();
        for (Entity entity : entities) {

            Optional<List<ColliderActionEntry>> entries = getEntries(entity);
            if (entries.isEmpty())
                continue;
            for (Entity otherEntity : entities) {
                if (entity.equals(otherEntity)) continue;
                Optional<List<ColliderActionEntry>> otherEntries = getEntries(otherEntity);
                if (otherEntries.isEmpty())
                    continue;
                BasicCollisionActionPayload payload = new BasicCollisionActionPayload(
                   this.gameState,
                   entity,
                   otherEntity
                );
                for (ColliderActionEntry entry : entries.get()) {
                    for (ColliderActionEntry otherEntry : otherEntries.get()) {
                        if (!entry.filter(otherEntity.type(), otherEntry.getColliderType())) continue;
                        if (GJK.intersects(entry.getShape(), otherEntry.getShape()))
                            entry.run(deltaTime, payload);
                    }
                }
            }
        }
    }

    private static Optional<List<ColliderActionEntry>> getEntries(Entity entity) {
        Optional<ColliderComponent> colliderComponent = entity.get(ColliderComponent.class);
        Optional<TransformComponent> transformComponent = entity.get(TransformComponent.class);
        if (colliderComponent.isEmpty() || transformComponent.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(colliderComponent.get().getEntries());
    }
}
