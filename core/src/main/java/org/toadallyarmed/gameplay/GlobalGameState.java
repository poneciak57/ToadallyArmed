package org.toadallyarmed.gameplay;

import org.toadallyarmed.component.WalletComponent;
import org.toadallyarmed.config.GameConfig;
import org.toadallyarmed.entity.Entity;

import java.util.concurrent.ConcurrentLinkedQueue;

public class GlobalGameState {
    private final WalletComponent wallet;
    private final GameConfig gameConfig;
    private final ConcurrentLinkedQueue<Entity> entities = new ConcurrentLinkedQueue<>();

    public GlobalGameState(WalletComponent wallet, GameConfig gameConfig) {
        this.wallet = wallet;
        this.gameConfig = gameConfig;
    }

    public WalletComponent getWallet() {
        return wallet;
    }

    public GameConfig getGameConfig() {
        return gameConfig;
    }

    public ConcurrentLinkedQueue<Entity> getEntities() {
        return entities;
    }
}
