package org.toadallyarmed.entity;

import org.toadallyarmed.component.interfaces.BaseComponentsRegistry;
import org.toadallyarmed.component.interfaces.Component;
import org.toadallyarmed.exception.NotBaseComponentException;
import org.toadallyarmed.util.logger.Logger;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class Entity {
    private final EntityType type;
    private volatile boolean markedForRemoval = false;
    private final ConcurrentHashMap<Class<? extends Component>, Component> components =
        new ConcurrentHashMap<>();
    private Runnable onRemovalAction;

    public Entity(final EntityType type) {
        this.type = type;
    }

    public EntityType type() {
        assertNotMarkedForRemoval();
        return type;
    }

    public void setOnRemoveAction(final Runnable action) {
        this.onRemovalAction = action;
    }

    /// Marks entity for removal, but does
    /// not perform the removal operation itself.
    /// It should be done externally.
    public void markForRemoval() {
        markedForRemoval = true;
        onRemovalAction.run();
    }

    public Runnable getMarkForRemovalRunnable() {
        return this::markForRemoval;
    }

    /// Entity marked for removal should not be used
    /// and should be removed when viable.
    public boolean isMarkedForRemoval() {
        return markedForRemoval;
    }

    /// Such entities can be used safely.
    public boolean isActive() {
        return !isMarkedForRemoval();
    }

    public void assertNotMarkedForRemoval() {
        if (isMarkedForRemoval()) {
            Logger.error("Illegal usage of marked-for-removal entity!");
        }
    }

    /// Returns component that extends one of BASE_COMPONENTS
    public <T extends Component> Optional<T> get(Class<T> clazz) {
        assertNotMarkedForRemoval();
        return Optional.ofNullable(clazz.cast(components.get(clazz)));
    }

    /// Puts key and value
    /// Checks if key is in BASE_COMPONENTS
    public <T1 extends Component, T2 extends T1> Entity put(Class<T1> clazz, T2 component)
        throws NotBaseComponentException {
        assertNotMarkedForRemoval();
        if (!BaseComponentsRegistry.BASE_COMPONENTS.contains(clazz))
            throw new NotBaseComponentException(clazz.getName());
        components.put(clazz, component);
        return this;
    }
}
