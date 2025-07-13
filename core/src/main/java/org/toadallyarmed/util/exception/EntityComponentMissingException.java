package org.toadallyarmed.util.exception;

import org.toadallyarmed.util.log.Logger;

public class EntityComponentMissingException extends Exception {
    public EntityComponentMissingException(Class<?> clazz) {
        super(String.format("Entity misses component extending: %s", clazz.getName()));
        Logger.error("Entity misses component extending: " + clazz.getName());
    }
}
