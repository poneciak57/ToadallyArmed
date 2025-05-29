package org.toadallyarmed.component.action;

import org.toadallyarmed.component.action.payload.BasicCollisionActionPayload;
import org.toadallyarmed.component.action.payload.BulletCollisionActionPayload;
import org.toadallyarmed.util.action.Action;
import org.toadallyarmed.util.action.PayloadExtractor;

public class BulletCollisionAction implements Action<BulletCollisionActionPayload, BasicCollisionActionPayload> {
    int damage;
    Runnable markForRemovalItself;

    public BulletCollisionAction(final int damage, final Runnable markForRemovalItselfRunnable) {
        this.damage = damage;
        this.markForRemovalItself = markForRemovalItselfRunnable;
    }

    @Override
    public void run(BulletCollisionActionPayload payload) {
        payload.otherEntityHealthComponent().removeHealth(damage);
        markForRemovalItself.run();
    }

    @Override
    public PayloadExtractor<BulletCollisionActionPayload, BasicCollisionActionPayload> extractor() {
        return BulletCollisionActionPayload.EXTRACTOR;
    }
}
