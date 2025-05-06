package org.toadallyarmed.system;

import org.toadallyarmed.entity.Entity;

import java.util.concurrent.ConcurrentLinkedQueue;

public interface System {
    /// Should return tickRate in which this system should be executed
    default int getTickRate() {
        return 60; // 60 hz
    }

    /// Tick will be executed tickRate times per second
    /// @param deltaTime time before previous execution of tick()
    /// @param entities list of entities
    void tick(float deltaTime, ConcurrentLinkedQueue<Entity> entities); // should not throw an exception
}
