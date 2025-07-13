package org.toadallyarmed.base.action;

import org.toadallyarmed.base.entity.Entity;
import org.toadallyarmed.scene.gameplay.GlobalGameState;
import org.toadallyarmed.base.system.System;

import java.util.Collection;

public class ActionSystem implements System {

    private final GlobalGameState gameState;
    public ActionSystem(GlobalGameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void tick(float deltaTime, Collection<Entity> entities) {
        float currentNano = java.lang.System.nanoTime();
        for (Entity entity : entities) {
            if (entity.isMarkedForRemoval()) continue;
            BasicActionPayload basicActionPayload = new BasicActionPayload(gameState, entity);
            entity.get(ActionComponent.class)
                .ifPresent(component -> component.run(currentNano, basicActionPayload));
        }
    }
}
