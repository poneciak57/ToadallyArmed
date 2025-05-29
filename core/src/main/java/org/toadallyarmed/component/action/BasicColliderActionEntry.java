package org.toadallyarmed.component.action;

import org.toadallyarmed.component.action.payload.BasicCollisionActionPayload;
import org.toadallyarmed.component.interfaces.ColliderType;
import org.toadallyarmed.entity.EntityType;
import org.toadallyarmed.util.action.Action;
import org.toadallyarmed.util.action.CollisionActionFilter;
import org.toadallyarmed.util.collision.ConvexShape;
import org.toadallyarmed.component.interfaces.ColliderActionEntry;

public class BasicColliderActionEntry implements ColliderActionEntry {
    private final ConvexShape shape;
    private final Action<?, BasicCollisionActionPayload> action;
    private final ColliderType colliderType;
    private final CollisionActionFilter collisionActionFilter;


    public BasicColliderActionEntry(
        ConvexShape shape,
        Action<?, BasicCollisionActionPayload> action,
        ColliderType colliderType,
        CollisionActionFilter collisionActionFilter
    ) {
        this.shape = shape;
        this.action = action;
        this.colliderType = colliderType;
        this.collisionActionFilter = collisionActionFilter;
    }

    @Override
    public ConvexShape getShape() {
        return shape;
    }

    @Override
    public ColliderType getColliderType() {
        return colliderType;
    }

    @Override
    public boolean filter(EntityType otherType, ColliderType otherColliderType) {
        return collisionActionFilter.filter(otherType, otherColliderType);
    }

    @Override
    public void run(float currentNano, BasicCollisionActionPayload payload) {
        action.extract_run(payload);
    }
}
