package org.toadallyarmed.component.action.payload;

import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.gameplay.GlobalGameState;

public record BasicActionPayload(
    GlobalGameState gameState,
    Entity entity // entity on which action was invoked
) { }
