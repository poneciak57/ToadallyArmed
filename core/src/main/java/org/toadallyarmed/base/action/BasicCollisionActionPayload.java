package org.toadallyarmed.base.action;

import org.toadallyarmed.base.entity.Entity;
import org.toadallyarmed.scene.gameplay.GlobalGameState;

public record BasicCollisionActionPayload (
    GlobalGameState gameState,
    Entity entity,
    Entity other,
    float currentNanoTime
)
{ }
