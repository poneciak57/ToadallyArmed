package org.toadallyarmed.component.interfaces;

import org.toadallyarmed.component.action.payload.BasicCollisionActionPayload;
import org.toadallyarmed.util.collision.ConvexShape;

public interface ColliderActionEntry {
    ConvexShape getShape();
    void run(float deltaTime, BasicCollisionActionPayload payload);
}
