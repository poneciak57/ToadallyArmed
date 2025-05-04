package org.toadallyarmed.core;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Entity {
    private final ConcurrentHashMap<Class<? extends Component>, Component> components =
        new ConcurrentHashMap<>();

    public <T extends Component> Optional<T> get(Class<T> clazz) {
        return Optional.ofNullable(clazz.cast(components.get(clazz)));
    }

    public <T1 extends Component, T2 extends T1> void put(Class<T1> clazz, T2 component) {
        components.put(clazz, component);
    }
}
