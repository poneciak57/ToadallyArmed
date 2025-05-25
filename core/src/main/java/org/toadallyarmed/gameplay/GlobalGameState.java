package org.toadallyarmed.gameplay;

import org.toadallyarmed.component.WalletComponent;
import org.toadallyarmed.config.GameConfig;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.factory.HedgehogFactory;

import java.util.concurrent.ConcurrentLinkedQueue;

public class GlobalGameState {
    private final WalletComponent wallet;
    private final GameConfig gameConfig;
    private final HedgehogFactory enemySpawner;
    private final ConcurrentLinkedQueue<Entity> entities = new ConcurrentLinkedQueue<>();

    public GlobalGameState(WalletComponent wallet, GameConfig gameConfig, HedgehogFactory enemySpawner) {
        this.wallet = wallet;
        this.gameConfig = gameConfig;
        this.enemySpawner=enemySpawner;
    }

    public WalletComponent getWallet() {
        return wallet;
    }

    public GameConfig getGameConfig() {
        return gameConfig;
    }

    public HedgehogFactory getEnemyFactory() {return enemySpawner;}

    public ConcurrentLinkedQueue<Entity> getEntities() {
        return entities;
    }
}
