package org.toadallyarmed.factory;

import org.toadallyarmed.gameplay.GlobalGameState;
import org.toadallyarmed.system.*;

public class SystemsManagerFactory {

    public SystemsManager getSystemsManagerForGameplay(GlobalGameState gameState) {
        return new SystemsManager.Builder()
            .tickRate(gameState.getGameConfig().SystemManagerTickRate())
            .addThrottledSystem(
                gameState.getGameConfig().GlobalIncomeSystemTickRate(),
                new GlobalIncomeSystem(
                    gameState.getWallet(),
                    gameState.getGameConfig().GlobalIncomeDelta())
            )
            .addThrottledSystem(
                gameState.getGameConfig().HealthSystemTickRate(),
                new HealthSystem()
            )
            .addThrottledSystem(
                gameState.getGameConfig().CollisionSystemTickRate(),
                new CollisionSystem()
            )
            .addThrottledSystem(
                gameState.getGameConfig().PhysicsSystemTickRate(),
                new PhysicsSystem()
            )
            .build(gameState.getEntities());
    }
}
