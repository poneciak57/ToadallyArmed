package org.toadallyarmed.entity.passive.bullet;

import org.toadallyarmed.base.health.HealthComponent;
import org.toadallyarmed.base.action.BasicCollisionActionPayload;
import org.toadallyarmed.base.entity.Entity;
import org.toadallyarmed.util.action.ActionAdapter;
import org.toadallyarmed.util.action.PayloadExtractor;

public class BulletCollisionActionPerformer extends ActionAdapter<BulletCollisionActionPayload, BasicCollisionActionPayload> {
    private static final PayloadExtractor<BulletCollisionActionPayload, BasicCollisionActionPayload>
        EXTRACTOR = basicCollisionActionPayload -> {
        Entity other = basicCollisionActionPayload.other();
        var health = other.get(HealthComponent.class);
        return health.map(BulletCollisionActionPayload::new);
    };

    public BulletCollisionActionPerformer(int damage, Runnable markForRemovalSelf) {
        super(new BulletCollisionActionImpl(damage, markForRemovalSelf), EXTRACTOR);
    }
}
