package org.toadallyarmed.gameplay;

import org.toadallyarmed.component.WalletComponent;

public class GlobalGameState {
    private final WalletComponent wallet;

    public GlobalGameState(WalletComponent wallet) {
        this.wallet = wallet;
    }

    public WalletComponent getWallet() {
        return wallet;
    }
}
