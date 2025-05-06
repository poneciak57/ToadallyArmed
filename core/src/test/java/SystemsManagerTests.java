import org.junit.Test;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.system.System;
import org.toadallyarmed.system.SystemsManager;

import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.Assert.assertEquals;

public class SystemsManagerTests {
    public static class TestBehaviorSystem implements System {
        public int counter = 0;

        @Override
        public int getTickRate() {
            return 5;
        }

        @Override
        public void tick(float deltaTime, ConcurrentLinkedQueue<Entity> __) {
            counter++;
        }
    }

    public static class SleepingSystem implements System {
        public int counter = 0;
        @Override
        public int getTickRate() {
            return 1;
        }

        @Override
        public void tick(float deltaTime, ConcurrentLinkedQueue<Entity> __) {
            counter++;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class TestRenderSystem implements System {
        public int counter = 0;
        @Override
        public void tick(float deltaTime, ConcurrentLinkedQueue<Entity> __) {
            counter++;
        }
    }

    @Test
    public void testCorrectTickRatesWithSimpleTickRate() throws InterruptedException {
        SystemsManager systemsManager = new SystemsManager(null);
        TestBehaviorSystem testBehaviorSystem = new TestBehaviorSystem();
        TestRenderSystem testRenderSystem = new TestRenderSystem();
        systemsManager.addSystem(testBehaviorSystem);
        systemsManager.addSystem(testRenderSystem);

        // 60hz, 2 seconds
        simulate(1f / 60f, 2f, systemsManager);

        assertEquals(2 * 5, testBehaviorSystem.counter, 1);
        assertEquals(2 * 60, testRenderSystem.counter, 1);
    }

    @Test
    public void testCorrectTickRatesWithHighTickRate() throws InterruptedException {
        SystemsManager systemsManager = new SystemsManager(null);
        TestBehaviorSystem testBehaviorSystem = new TestBehaviorSystem();
        TestRenderSystem testRenderSystem = new TestRenderSystem();
        systemsManager.addSystem(testBehaviorSystem);
        systemsManager.addSystem(testRenderSystem);

        // 120hz, 3 seconds
        simulate(1f / 120f, 3f, systemsManager);

        assertEquals(3 * 5, testBehaviorSystem.counter, 1);
        assertEquals(3 * 60, testRenderSystem.counter, 1);
    }

    @Test
    public void testCorrectTickRatesWithSleeping() throws InterruptedException {
        SystemsManager systemsManager = new SystemsManager(null);
        TestBehaviorSystem testBehaviorSystem = new TestBehaviorSystem();
        SleepingSystem sleepingSystem = new SleepingSystem();
        TestRenderSystem testRenderSystem = new TestRenderSystem();
        systemsManager.addSystem(testBehaviorSystem);
        systemsManager.addSystem(testRenderSystem);
        systemsManager.addSystem(sleepingSystem);

        // 120hz, 3 seconds
        simulate(1f / 120f, 3f, systemsManager);

        assertEquals(3 * 5, testBehaviorSystem.counter, 1);
        assertEquals(3 * 60, testRenderSystem.counter, 1);
        assertEquals(3, sleepingSystem.counter, 1);
    }

    void simulate(float tickRate, float simulatedTime, SystemsManager systemsManager) throws InterruptedException {
        float elapsed = 0f;

        while (elapsed < simulatedTime) {
            systemsManager.tick(tickRate);
            Thread.sleep((long) (tickRate * 1000));
            elapsed += tickRate;
        }
        systemsManager.dispose();

    }
}
