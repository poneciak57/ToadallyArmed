package org.toadallyarmed.scene.gameplay;

import org.toadallyarmed.base.system.System;
import org.toadallyarmed.base.entity.Entity;
import org.toadallyarmed.util.log.Logger;

import java.util.Collection;

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
