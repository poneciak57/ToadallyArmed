package org.toadallyarmed.component.action;

import org.toadallyarmed.component.HealthComponent;
import org.toadallyarmed.component.action.payload.BasicCollisionActionPayload;
import org.toadallyarmed.entity.Entity;
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
