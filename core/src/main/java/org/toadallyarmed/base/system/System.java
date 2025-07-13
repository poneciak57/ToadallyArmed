package org.toadallyarmed.base.system;

import org.toadallyarmed.base.entity.Entity;

import java.util.Collection;

public interface System {
    /// Tick will be executed tickRate times per second
    /// @param deltaTime time before previous execution of tick()
    /// @param entities list of entities
    void tick(float deltaTime, Collection<Entity> entities); // should not throw an exception
}
