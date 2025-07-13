package org.toadallyarmed.base.physics.collision;

import org.toadallyarmed.base.entity.EntityType;

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
