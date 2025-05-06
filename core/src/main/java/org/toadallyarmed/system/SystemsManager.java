package org.toadallyarmed.system;

import org.toadallyarmed.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class SystemsManager {
    private final List<System> systems = new ArrayList<>();
    private final Map<System, Float> systemTimeAccumulators = new ConcurrentHashMap<>();
    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private final ConcurrentLinkedQueue<Entity> entities;

    public SystemsManager(ConcurrentLinkedQueue<Entity> entities) {
        this.entities = entities;
    }

    /// Adds system to the manager
    /// Should be called before ever calling tick
    public void addSystem(System system) {
        systems.add(system);
        systemTimeAccumulators.put(system, 0f);
    }

    /// # Tick method
    /// run tick method on all registered systems if their their accumulated time has reached their tickrate
    public void tick(float delta) {
        for (System system : systems) {
            float accumulated = systemTimeAccumulators.get(system);
            accumulated += delta;

            float interval = 1f / system.getTickRate();

            if (accumulated >= interval) {
                float remainder = accumulated % interval;
                systemTimeAccumulators.put(system, remainder);

                float finalAccumulated = accumulated;
                executor.submit(() -> system.tick(finalAccumulated, this.entities));
            } else {
                systemTimeAccumulators.put(system, accumulated);
            }
        }
    }

    /// Stops all tasks that are being currently executed with timeout of 5 seconds
    public void dispose() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}
