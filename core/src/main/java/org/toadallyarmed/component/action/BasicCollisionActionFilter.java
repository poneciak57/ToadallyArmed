package org.toadallyarmed.component.action;

import org.toadallyarmed.component.interfaces.ColliderType;
import org.toadallyarmed.entity.EntityType;
import org.toadallyarmed.util.action.CollisionActionFilter;

public class BasicCollisionActionFilter implements CollisionActionFilter {
    private final EntityType entityType;
    private final ColliderType colliderType;

    public BasicCollisionActionFilter(EntityType entityType, ColliderType colliderType) {
        this.entityType = entityType;
        this.colliderType = colliderType;
    }

    @Override
    public boolean filter(EntityType otherType, ColliderType otherColliderType) {
        return entityType.equals(otherType) && colliderType.equals(otherColliderType);
    }
}
