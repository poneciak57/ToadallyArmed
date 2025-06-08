package org.toadallyarmed.system.level;

import org.toadallyarmed.config.GameConfig;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.factory.HedgehogFactory;
import org.toadallyarmed.util.action.PayloadExtractor;

import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

public record EnemyWaveLevelActionPayload(
    GameConfig config,
    ConcurrentLinkedQueue<Entity> entities,
    HedgehogFactory hedgehogFactory
)
{
    public static final
    PayloadExtractor<EnemyWaveLevelActionPayload, BasicLevelActionPayload> EXTRACTOR
        = rawPayload -> {
        return Optional.of(new EnemyWaveLevelActionPayload(
            rawPayload.config(),
            rawPayload.entities(),
            rawPayload.hedgehogFactory()
        ));
    };
}
