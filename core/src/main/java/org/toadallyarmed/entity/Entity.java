package org.toadallyarmed.entity;

import org.toadallyarmed.component.interfaces.BaseComponentsRegistry;
import org.toadallyarmed.component.interfaces.Component;
import org.toadallyarmed.exception.NotBaseComponentException;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class Entity {
    private final EntityType type;
    private final ConcurrentHashMap<Class<? extends Component>, Component> components =
        new ConcurrentHashMap<>();

    public Entity(final EntityType type) {
        this.type = type;
    }

    /// Returns type of the entity
    public EntityType type() {
        return type;
    }

    /// Returns component that extends one of BASE_COMPONENTS
    public <T extends Component> Optional<T> get(Class<T> clazz) {
        return Optional.ofNullable(clazz.cast(components.get(clazz)));
    }

    /// Puts key and value
    /// Checks if key is in BASE_COMPONENTS
    public <T1 extends Component, T2 extends T1> Entity put(Class<T1> clazz, T2 component) throws NotBaseComponentException {
        if (!BaseComponentsRegistry.BASE_COMPONENTS.contains(clazz))
            throw new NotBaseComponentException(clazz.getName());
        components.put(clazz, component);
        return this;
    }
}
