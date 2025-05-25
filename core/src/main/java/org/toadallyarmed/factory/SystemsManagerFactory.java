package org.toadallyarmed.factory;

import org.toadallyarmed.gameplay.GlobalGameState;
import org.toadallyarmed.system.*;

public class SystemsManagerFactory {

    public static SystemsManager getSystemsManagerForGameplay(GlobalGameState gameState) {
        return new SystemsManager.Builder()
            .tickRate(gameState.getGameConfig().systemManagerTickRate())
            .addThrottledSystem(
                gameState.getGameConfig().globalIncomeSystemTickRate(),
                new GlobalIncomeSystem(
                    gameState.getWallet(),
                    gameState.getGameConfig().globalIncomeDelta())
            )
            .addThrottledSystem(
                gameState.getGameConfig().healthSystemTickRate(),
                new HealthSystem()
            )
            .addThrottledSystem(
                gameState.getGameConfig().collisionSystemTickRate(),
                new CollisionSystem(gameState)
            )
            .addThrottledSystem(
                gameState.getGameConfig().physicsSystemTickRate(),
                new PhysicsSystem()
            )
            .addThrottledSystem(
                gameState.getGameConfig().actionSystemTickRate(),
                new ActionSystem(gameState)
            )
            .addThrottledSystem(
                gameState.getGameConfig().enemySpawnerSystemTickRate(),
                new EnemySpawnerSystem(
                    gameState.getEnemyFactory(),
                    gameState.getGameConfig()
                )
            )
            .build(gameState.getEntities());
    }
}
