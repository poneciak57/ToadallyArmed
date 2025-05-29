package org.toadallyarmed.component.action.payload;

import org.toadallyarmed.component.HealthComponent;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.util.action.PayloadExtractor;

import java.util.Optional;

public record BulletCollisionActionPayload (
    HealthComponent otherEntityHealthComponent
) {
    public static final PayloadExtractor<BulletCollisionActionPayload, BasicCollisionActionPayload> EXTRACTOR = basicCollisionActionPayload -> {
        Entity other = basicCollisionActionPayload.other();
        var healthComponentOpt = other.get(HealthComponent.class);
        if (healthComponentOpt.isEmpty())
            return Optional.empty();
        else
            return Optional.of(new BulletCollisionActionPayload(healthComponentOpt.get()));
    };
}
