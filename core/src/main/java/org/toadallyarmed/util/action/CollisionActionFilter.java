package org.toadallyarmed.util.action;

import org.toadallyarmed.component.interfaces.ColliderType;
import org.toadallyarmed.entity.EntityType;

public interface CollisionActionFilter {
    /// Checks if current collider action should interact with other collider
    /// @param otherType type of colliding entity
    /// @param otherColliderType type of collider of colliding entity
    ///
    /// @return true if action should be run on this entity
    boolean filter(EntityType otherType, ColliderType otherColliderType);
}
