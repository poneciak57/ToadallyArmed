package org.toadallyarmed.system;

import org.toadallyarmed.component.interfaces.ColliderComponent;
import org.toadallyarmed.component.interfaces.CollisionActionComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.util.collision.ConvexShape;
import org.toadallyarmed.util.collision.GJK;

import java.util.concurrent.ConcurrentLinkedQueue;

public class CollisionSystem implements System{
    @Override
    public void tick(float deltaTime, ConcurrentLinkedQueue<Entity> entities) {
        for (Entity entity : entities) {
            CollisionActionComponent actionComponent = entity.get(CollisionActionComponent.class).orElse(null);
            ColliderComponent colliderComponent = entity.get(ColliderComponent.class).orElse(null);
            TransformComponent transformComponent = entity.get(TransformComponent.class).orElse(null);
            if (colliderComponent == null || transformComponent == null || actionComponent == null) continue;
            ConvexShape convex = colliderComponent.getConvexShape(transformComponent.getPosition());
            for (Entity otherEntity : entities) {
                ColliderComponent otherColliderComponent = otherEntity.get(ColliderComponent.class).orElse(null);
                TransformComponent otherTransformComponent = otherEntity.get(TransformComponent.class).orElse(null);
                if (otherColliderComponent == null || otherTransformComponent == null) continue;
                ConvexShape otherConvex = otherColliderComponent.getConvexShape(otherTransformComponent.getPosition());
                if (GJK.intersects(convex, otherConvex))
                    actionComponent.collide(deltaTime, otherEntity);
            }
        }
    }
}
