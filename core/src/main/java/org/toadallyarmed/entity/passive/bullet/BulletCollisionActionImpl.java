package org.toadallyarmed.entity.passive.bullet;

import org.toadallyarmed.base.health.HealthComponent;
import org.toadallyarmed.util.action.Action;

record BulletCollisionActionPayload(
    HealthComponent otherEntityHealthComponent
) { }

class BulletCollisionActionImpl implements Action<BulletCollisionActionPayload> {
    int damage;
    Runnable markForRemovalSelf;

    public BulletCollisionActionImpl(int damage, Runnable markForRemovalSelf) {
        this.damage = damage;
        this.markForRemovalSelf = markForRemovalSelf;
    }

    @Override
    public void run(BulletCollisionActionPayload payload) {
        payload.otherEntityHealthComponent().removeHealth(damage);
        markForRemovalSelf.run();
    }
}
