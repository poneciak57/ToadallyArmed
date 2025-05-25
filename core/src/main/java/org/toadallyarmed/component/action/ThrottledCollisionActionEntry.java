package org.toadallyarmed.component.action;

import org.toadallyarmed.component.action.payload.BasicCollisionActionPayload;
import org.toadallyarmed.component.interfaces.ColliderType;
import org.toadallyarmed.entity.EntityType;
import org.toadallyarmed.util.collision.ConvexShape;
import org.toadallyarmed.util.logger.Logger;

public class ThrottledCollisionActionEntry implements org.toadallyarmed.component.interfaces.ColliderActionEntry {
    private final float interval;
    private float accumulator = 0.f;
    private final BasicColliderActionEntry entry;

    public ThrottledCollisionActionEntry(float tickRate, BasicColliderActionEntry entry) {
        this.interval = 1f / tickRate;
        this.entry = entry;
    }

    @Override
    public ConvexShape getShape() {
        return this.entry.getShape();
    }

    @Override
    public ColliderType getColliderType() {
        return this.entry.getColliderType();
    }

    @Override
    public boolean filter(EntityType otherType, ColliderType otherColliderType) {
        return this.entry.filter(otherType, otherColliderType);
    }

    @Override
    public void run(float deltaTime, BasicCollisionActionPayload payload) {
        this.accumulator += deltaTime;
        if (this.accumulator >= interval) {
            this.entry.run(deltaTime, payload);
            this.accumulator %= interval;
        }
    }
}
