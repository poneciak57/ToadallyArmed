package org.toadallyarmed.exception;

public class EntityComponentMissingException extends Exception {
    public EntityComponentMissingException(Class<?> clazz) {
        super(String.format("Entity misses component extending: %s", clazz.getName()));
    }
}
