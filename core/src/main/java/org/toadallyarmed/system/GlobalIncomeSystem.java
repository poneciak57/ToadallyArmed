package org.toadallyarmed.system;

import org.toadallyarmed.component.WalletComponent;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.util.logger.Logger;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GlobalIncomeSystem implements System {
    private final WalletComponent globalWallet;
    private final int globalIncomeDelta;

    public GlobalIncomeSystem(WalletComponent globalWallet, int globalIncomeDelta) {
        this.globalWallet = globalWallet;
        this.globalIncomeDelta = globalIncomeDelta;
    }

    @Override
    public void tick(float deltaTime, Collection<Entity> entities) {
        Logger.trace("GlobalIncomeSystem: tick");
        globalWallet.increase(globalIncomeDelta);
    }
}
