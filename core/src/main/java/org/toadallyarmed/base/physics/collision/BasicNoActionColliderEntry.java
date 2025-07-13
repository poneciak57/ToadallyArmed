package org.toadallyarmed.base.physics.collision;

import org.toadallyarmed.base.action.BasicCollisionActionPayload;
import org.toadallyarmed.base.entity.EntityType;
import org.toadallyarmed.util.collision.ConvexShape;

public class BasicNoActionColliderEntry implements ColliderActionEntry {
    private final ConvexShape shape;
    private final ColliderType type;

    public BasicNoActionColliderEntry(ConvexShape shape, ColliderType type) {
        this.shape = shape;
        this.type = type;
    }

    @Override
    public ConvexShape getShape() {
        return this.shape;
    }

    @Override
    public ColliderType getColliderType() {
        return this.type;
    }

    @Override
    public boolean filter(EntityType otherType, ColliderType otherColliderType) {
        return false;
    }

    @Override
    public void run(float deltaTime, BasicCollisionActionPayload payload) {
        return;
    }
}
