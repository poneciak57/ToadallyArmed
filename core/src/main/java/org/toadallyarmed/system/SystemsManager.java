package org.toadallyarmed.system;

import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.util.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;

public class SystemsManager {
    private final List<System> systems;
    private final float tickRate;
    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private final ConcurrentLinkedQueue<Entity> entities;

    private volatile boolean running = false;
    private volatile boolean paused = false;

    private Thread tickThread;

    SystemsManager(float tickRate, List<System> systems, ConcurrentLinkedQueue<Entity> entities) {
        this.entities = entities;
        this.systems = systems;
        this.tickRate = tickRate;
        Logger.debug("Initialized SystemsManager successfully");
    }

    public void start() {
        Logger.debug("Starting SystemsManager");
        if (running) return;
        running = true;

        tickThread = new Thread(this::tickLoop);
        tickThread.start();
    }

    public void stop() {
        Logger.debug("Stopping SystemsManager");
        running = false;
        resume(); // Unpark if paused

        if (tickThread != null && tickThread != Thread.currentThread()) {
            try {
                tickThread.join();
            } catch (InterruptedException e) {
                tickThread.interrupt();
            }
        }
        Logger.trace("Shutting down executor");
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
        Logger.trace("SystemsManager stopped successfully");
    }

    public void pause() {
        Logger.debug("Pausing SystemsManager");
        paused = true;
    }

    public void resume() {
        Logger.debug("Resuming SystemsManager");
        if (!paused) return;
        paused = false;
        LockSupport.unpark(tickThread);
    }

    private void tickLoop() {
        final long tickIntervalNanos = (long) (1_000_000_000L / tickRate);
        long lastTime = java.lang.System.nanoTime();

        while (running) {
            if (paused) {
                LockSupport.park();
                lastTime = java.lang.System.nanoTime();
                continue;
            }

            long now = java.lang.System.nanoTime();
            float delta = (now - lastTime) / 1_000_000_000f;
            lastTime = now;

            tick(delta);

            long elapsed = java.lang.System.nanoTime() - now;
            long sleepTime = tickIntervalNanos - elapsed;
            if (sleepTime > 0) {
                LockSupport.parkNanos(sleepTime);
            }
        }
    }

    public void tick(float delta) {
        for (System system : systems) {
            executor.submit(() -> system.tick(delta, this.entities));
        }
    }

    public static class Builder {
        float tickRate = 60;
        List<System> systems = new ArrayList<>();
        public Builder() {}

        public Builder addSystem(System system) {
            systems.add(system);
            return this;
        }
        public Builder addThrottledSystem(float tickRate, System system) {
            return addSystem(new ThrottledSystem(tickRate, system));
        }
        public Builder tickRate(float tickRate) {
            this.tickRate = tickRate;
            return this;
        }

        public SystemsManager build(ConcurrentLinkedQueue<Entity> entities) {
            return new SystemsManager(tickRate, systems, entities);
        }
    }
}
