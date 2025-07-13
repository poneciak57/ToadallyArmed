package org.toadallyarmed.scene.gameplay;

import org.toadallyarmed.base.action.ActionSystem;
import org.toadallyarmed.base.system.GarbageCollectorSystem;
import org.toadallyarmed.base.physics.collision.CollisionSystem;
import org.toadallyarmed.base.physics.PhysicsSystem;
import org.toadallyarmed.base.system.SystemsManager;

public class SystemsManagerFactory {

    public static SystemsManager getSystemsManagerForGameplay(GlobalGameState gameState) {
        return new SystemsManager.Builder()
            .tickRate(gameState.getGameConfig().SystemManagerTickRate())
            .addThrottledSystem(
                gameState.getGameConfig().GlobalIncomeSystemTickRate(),
                new GlobalIncomeSystem(
                    gameState.getWallet(),
                    gameState.getGameConfig().GlobalIncomeDelta())
            )
            .addThrottledSystem(
                gameState.getGameConfig().GarbageCollectorSystemTickRate(),
                new GarbageCollectorSystem()
            )
            .addThrottledSystem(
                gameState.getGameConfig().CollisionSystemTickRate(),
                new CollisionSystem(gameState)
            )
            .addThrottledSystem(
                gameState.getGameConfig().PhysicsSystemTickRate(),
                new PhysicsSystem()
            )
            .addThrottledSystem(
                gameState.getGameConfig().ActionSystemTickRate(),
                new ActionSystem(gameState)
            )
            .addThrottledSystem(
                gameState.getGameConfig().EnemySpawnerSystemTickRate(),
                new EnemySpawnerSystem(
                    gameState.getEnemyFactory(),
                    gameState.getGameConfig()
                )
            )
            .build(gameState.getEntities());
    }
}
