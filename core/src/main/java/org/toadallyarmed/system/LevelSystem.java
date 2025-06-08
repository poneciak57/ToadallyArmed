package org.toadallyarmed.system;

import org.toadallyarmed.config.GameConfig;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.factory.HedgehogFactory;
import org.toadallyarmed.system.level.BasicLevelActionPayload;
import org.toadallyarmed.system.level.Level;
import org.toadallyarmed.util.logger.Logger;

import java.util.concurrent.ConcurrentLinkedQueue;

public class LevelSystem implements System {
    private final Level level;
    private final GameConfig gameConfig;
    private final HedgehogFactory  hedgehogFactory;

    public LevelSystem(Level level, GameConfig gameConfig, HedgehogFactory hedgehogFactory) {
        this.level = level;
        this.gameConfig = gameConfig;
        this.hedgehogFactory = hedgehogFactory;
    }

    @Override
    public void tick(float deltaTime, ConcurrentLinkedQueue<Entity> entities) // should not throw an exception
    {
        BasicLevelActionPayload levelActionPayload
            = new BasicLevelActionPayload(gameConfig, entities, hedgehogFactory);
        level.getActiveAction().ifPresentOrElse(
            levelAction -> levelAction.extract_run(levelActionPayload),
            () -> Logger.info("Level is already completed")
        );
    }
}
