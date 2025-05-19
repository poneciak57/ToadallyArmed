package org.toadallyarmed.component.action;

import org.toadallyarmed.component.action.payload.BasicCollisionActionPayload;
import org.toadallyarmed.util.action.Action;
import org.toadallyarmed.util.collision.ConvexShape;

public class BasicColliderActionEntry implements org.toadallyarmed.component.interfaces.ColliderActionEntry {
    private final ConvexShape shape;
    private final Action<?, BasicCollisionActionPayload> action;

    public BasicColliderActionEntry(ConvexShape shape, Action<?, BasicCollisionActionPayload> action) {
        this.shape = shape;
        this.action = action;
    }

    @Override
    public ConvexShape getShape() {
        return shape;
    }

    @Override
    public void run(float deltaTime, BasicCollisionActionPayload payload) {
        action.extract_run(payload);
    }
}
