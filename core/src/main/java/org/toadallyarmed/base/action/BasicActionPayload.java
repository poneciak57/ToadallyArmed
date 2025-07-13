package org.toadallyarmed.base.action;

import org.toadallyarmed.base.entity.Entity;
import org.toadallyarmed.scene.gameplay.GlobalGameState;

public record BasicActionPayload(
    GlobalGameState gameState,
    Entity entity // entity on which action was invoked
) { }
