package org.toadallyarmed.factory;

import org.toadallyarmed.gameplay.GlobalGameState;
import org.toadallyarmed.system.*;
import org.toadallyarmed.system.level.EmptyLevelAction;
import org.toadallyarmed.system.level.EnemyWaveLevelAction;
import org.toadallyarmed.system.level.Level;

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
            // .addThrottledSystem(
            //     gameState.getGameConfig().EnemySpawnerSystemTickRate(),
            //     new EnemySpawnerSystem(
            //         gameState.getEnemyFactory(),
            //         gameState.getGameConfig()
            //     )
            // )
            .addSystem(new LevelSystem(
                gameState.getGameConfig().level(),
                gameState.getGameConfig(),
                gameState.getEnemyFactory()
            ))
            .build(gameState.getEntities());
    }
}
