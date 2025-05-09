package org.toadallyarmed.exception;

import org.toadallyarmed.util.logger.Logger;

public class EntityComponentMissingException extends Exception {
    public EntityComponentMissingException(Class<?> clazz) {
        super(String.format("Entity misses component extending: %s", clazz.getName()));
        Logger.error("Entity misses component extending: " + clazz.getName());
    }
}
