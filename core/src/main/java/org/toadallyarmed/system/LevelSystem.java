package org.toadallyarmed.system;

import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.system.level.BasicLevelActionPayload;
import org.toadallyarmed.system.level.Level;
import org.toadallyarmed.util.logger.Logger;

import java.util.concurrent.ConcurrentLinkedQueue;

public class LevelSystem implements System {
    private final Level level;

    public LevelSystem(Level level) {
        this.level = level;
    }

    @Override
    public void tick(float deltaTime, ConcurrentLinkedQueue<Entity> entities) // should not throw an exception
    {
        BasicLevelActionPayload levelActionPayload = new BasicLevelActionPayload();
        level.getActiveAction().ifPresentOrElse(
            levelAction -> levelAction.extract_run(levelActionPayload),
            () -> Logger.info("Level is already completed")
        );
    }
}
