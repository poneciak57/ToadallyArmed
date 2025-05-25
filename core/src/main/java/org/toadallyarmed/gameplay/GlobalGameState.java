package org.toadallyarmed.gameplay;

import org.toadallyarmed.component.WalletComponent;
import org.toadallyarmed.config.GameConfig;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.factory.HedgehogFactory;

import java.util.concurrent.ConcurrentLinkedQueue;

public class GlobalGameState {
    private final WalletComponent wallet;
    private final HedgehogFactory enemyFactory;
    private final GameConfig gameConfig;
    private final ConcurrentLinkedQueue<Entity> entities = new ConcurrentLinkedQueue<>();

    public GlobalGameState(WalletComponent wallet, GameConfig gameConfig, HedgehogFactory enemyFactory) {
        this.wallet = wallet;
        this.gameConfig = gameConfig;
        this.enemyFactory = enemyFactory;
    }

    public WalletComponent getWallet() {
        return wallet;
    }
    public HedgehogFactory getEnemyFactory() {return enemyFactory;}

    public GameConfig getGameConfig() {
        return gameConfig;
    }

    public ConcurrentLinkedQueue<Entity> getEntities() {
        return entities;
    }
}
