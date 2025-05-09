package org.toadallyarmed.system;

import org.toadallyarmed.entity.Entity;

import java.util.concurrent.ConcurrentLinkedQueue;

public interface System {
    /// Tick will be executed tickRate times per second
    /// @param deltaTime time before previous execution of tick()
    /// @param entities list of entities
    void tick(float deltaTime, ConcurrentLinkedQueue<Entity> entities); // should not throw an exception
}
