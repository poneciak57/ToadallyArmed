package org.toadallyarmed.component.action.payload;

import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.gameplay.GlobalGameState;

public record BasicCollisionActionPayload (
    GlobalGameState gameState,
    Entity entity,
    Entity other
)
{ }
