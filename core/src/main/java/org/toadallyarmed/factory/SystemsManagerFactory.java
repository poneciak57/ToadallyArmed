package org.toadallyarmed.factory;

import org.toadallyarmed.gameplay.GlobalGameState;
import org.toadallyarmed.system.GlobalIncomeSystem;
import org.toadallyarmed.system.HealthSystem;
import org.toadallyarmed.system.SystemsManager;

public class SystemsManagerFactory {

    public SystemsManager getSystemsManagerForGameplay(GlobalGameState gameState) {
        return new SystemsManager.Builder()
            .tickRate(gameState.getGameConfig().SystemManagerTickRate())
            .addThrottledSystem(
                gameState.getGameConfig().GlobalIncomeTickRate(),
                new GlobalIncomeSystem(
                    gameState.getWallet(),
                    gameState.getGameConfig().GlobalIncomeDelta())
            )
            .addThrottledSystem(
                gameState.getGameConfig().HealthSystemTickRate(),
                new HealthSystem()
            )
            .build(gameState.getEntities());
    }
}
