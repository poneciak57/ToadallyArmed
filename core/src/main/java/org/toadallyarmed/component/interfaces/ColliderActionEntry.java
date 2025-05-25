package org.toadallyarmed.component.interfaces;

import org.toadallyarmed.component.action.payload.BasicCollisionActionPayload;
import org.toadallyarmed.entity.EntityType;
import org.toadallyarmed.util.collision.ConvexShape;

public interface ColliderActionEntry {
    /// Returns convex shape for GJK intersection checking
    ConvexShape getShape();

    /// Returns collider type for filtering
    ColliderType getColliderType();

    /// Checks if current collider action should interact with other collider
    /// Its main purpose is to decrease number of redundant collision checks because GJK is not constant time magic
    ///
    /// @param otherType type of colliding entity
    /// @param otherColliderType type of collider of colliding entity
    ///
    /// @return true if run should be run on this entry
    boolean filter(EntityType otherType, ColliderType otherColliderType);

    /// Should run this entry action
    void run(float currentNano, BasicCollisionActionPayload payload);
}
