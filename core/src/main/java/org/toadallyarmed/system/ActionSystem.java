package org.toadallyarmed.system;

import org.toadallyarmed.component.action.payload.BasicActionPayload;
import org.toadallyarmed.component.interfaces.ActionComponent;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.gameplay.GlobalGameState;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ActionSystem implements System {

    private final GlobalGameState gameState;
    public ActionSystem(GlobalGameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void tick(float deltaTime, ConcurrentLinkedQueue<Entity> entities) {
        for (Entity entity : entities) {
            BasicActionPayload basicActionPayload = new BasicActionPayload(gameState, entity);
            entity.get(ActionComponent.class)
                .ifPresent(component -> component.run(deltaTime, basicActionPayload));
        }
    }
}
