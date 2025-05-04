package org.toadallyarmed.core.components;

public class EntityMissesComponentException extends Exception {
    public EntityMissesComponentException(String componentName) {
        super(String.format("Entity misses component: %s", componentName));
    }
}
