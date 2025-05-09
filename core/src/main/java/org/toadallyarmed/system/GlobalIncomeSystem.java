package org.toadallyarmed.system;

import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.gameplay.GlobalGameState;

import java.util.concurrent.ConcurrentLinkedQueue;

public class GlobalIncomeSystem implements System {

    private final GlobalGameState gameState;

    GlobalIncomeSystem(GlobalGameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void tick(float deltaTime, ConcurrentLinkedQueue<Entity> entities) {
        gameState.getWallet().access().addAndGet(gameState.getGameConfig().GlobalIncomeDelta());
    }
}
