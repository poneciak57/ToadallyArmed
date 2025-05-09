package org.toadallyarmed.gameplay;

import org.toadallyarmed.component.WalletComponent;

public class GlobalGameState {
    private final WalletComponent wallet;
    private final GameConfig gameConfig;

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
}
