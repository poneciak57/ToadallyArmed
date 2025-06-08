package org.toadallyarmed.system.level;

import org.toadallyarmed.config.GameConfig;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.factory.HedgehogFactory;

import java.util.concurrent.ConcurrentLinkedQueue;

public record BasicLevelActionPayload(
    GameConfig config,
    ConcurrentLinkedQueue<Entity> entities,
    HedgehogFactory hedgehogFactory
)
{  }
