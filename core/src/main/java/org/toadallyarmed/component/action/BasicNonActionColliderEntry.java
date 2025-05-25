package org.toadallyarmed.component.action;

import org.toadallyarmed.component.action.payload.BasicCollisionActionPayload;
import org.toadallyarmed.component.interfaces.ColliderActionEntry;
import org.toadallyarmed.component.interfaces.ColliderType;
import org.toadallyarmed.entity.EntityType;
import org.toadallyarmed.util.collision.ConvexShape;

public class BasicNonActionColliderEntry implements ColliderActionEntry {
    private final ConvexShape shape;
    private final ColliderType type;

    public BasicNonActionColliderEntry(ConvexShape shape, ColliderType type) {
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
