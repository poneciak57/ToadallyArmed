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
        public void tick(float deltaTime, ConcurrentLinkedQueue<Entity> __) {
            counter++;
        }
    }

    public static class SleepingSystem implements System {
        public int counter = 0;

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
        TestBehaviorSystem testBehaviorSystem = new TestBehaviorSystem();
        TestRenderSystem testRenderSystem = new TestRenderSystem();
        SystemsManager systemsManager = new SystemsManager.Builder()
            .addThrottledSystem(5, testBehaviorSystem)
            .addSystem(testRenderSystem)
            .tickRate(60)
            .build(null);

        // 60hz, 2 seconds
        simulate(2, systemsManager);

        assertEquals(2 * 5, testBehaviorSystem.counter, 1);
        assertEquals(2 * 60, testRenderSystem.counter, 1);
    }

    @Test
    public void testCorrectTickRatesWithHighTickRate() throws InterruptedException {
        TestBehaviorSystem testBehaviorSystem = new TestBehaviorSystem();
        TestRenderSystem testRenderSystem = new TestRenderSystem();
        SystemsManager systemsManager = new SystemsManager.Builder()
            .addThrottledSystem(5, testBehaviorSystem)
            .addThrottledSystem(60, testRenderSystem)
            .tickRate(120)
            .build(null);

        // 120hz, 3 seconds
        simulate(3, systemsManager);

        assertEquals(3 * 5, testBehaviorSystem.counter, 1);
        assertEquals(3 * 60, testRenderSystem.counter, 1);
    }

    @Test
    public void testCorrectTickRatesWithSleeping() throws InterruptedException {
        TestBehaviorSystem testBehaviorSystem = new TestBehaviorSystem();
        SleepingSystem sleepingSystem = new SleepingSystem();
        TestRenderSystem testRenderSystem = new TestRenderSystem();
        SystemsManager systemsManager = new SystemsManager.Builder()
            .addThrottledSystem(5, testBehaviorSystem)
            .addThrottledSystem(1, sleepingSystem)
            .addThrottledSystem(60, testRenderSystem)
            .tickRate(120)
            .build(null);

        // 120hz, 3 seconds
        simulate(3, systemsManager);

        assertEquals(3 * 5, testBehaviorSystem.counter, 1);
        assertEquals(3 * 60, testRenderSystem.counter, 1);
        assertEquals(3, sleepingSystem.counter, 1);
    }

    @Test
    public void testPause() throws InterruptedException {
        TestBehaviorSystem testBehaviorSystem = new TestBehaviorSystem();
        SleepingSystem sleepingSystem = new SleepingSystem();
        TestRenderSystem testRenderSystem = new TestRenderSystem();
        SystemsManager systemsManager = new SystemsManager.Builder()
            .addThrottledSystem(5, testBehaviorSystem)
            .addThrottledSystem(1, sleepingSystem)
            .addThrottledSystem(60, testRenderSystem)
            .tickRate(120)
            .build(null);

        systemsManager.start();
        Thread.sleep(2 * 1000);
        systemsManager.pause();

        Thread.sleep(2 * 1000);

        systemsManager.resume();
        Thread.sleep(1000);
        systemsManager.stop();

        assertEquals(3 * 5, testBehaviorSystem.counter, 1);
        assertEquals(3 * 60, testRenderSystem.counter, 1);
        assertEquals(3, sleepingSystem.counter, 1);
    }

    void simulate(long simulatedTime, SystemsManager systemsManager) throws InterruptedException {
        systemsManager.start();
        Thread.sleep(simulatedTime * 1000);
        systemsManager.stop();
    }


}
