import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.toadallyarmed.component.HealthComponent;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.entity.EntityType;
import org.toadallyarmed.system.GarbageCollectorSystem;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Supplier;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class GarbageCollectorSystemTest {
    public static class EntityRemovalTest {
        @Test
        public void basicRemoval() throws InterruptedException {
            ConcurrentLinkedQueue<Entity> entities = new ConcurrentLinkedQueue<>();
            GarbageCollectorSystem garbageCollectorSystem = new GarbageCollectorSystem();

            entities.add(new Entity(EntityType.OTHER));
            entities.add(new Entity(EntityType.OTHER));
            entities.add(new Entity(EntityType.OTHER));

            assertEquals(3, entities.size());

            assert entities.peek() != null;
            entities.peek().markForRemoval();
            garbageCollectorSystem.tick(0, entities);
            assertEquals(2, entities.size());

            assert entities.peek() != null;
            entities.peek().markForRemoval();
            garbageCollectorSystem.tick(0, entities);
            assertEquals(1, entities.size());

            assert entities.peek() != null;
            entities.peek().markForRemoval();
            garbageCollectorSystem.tick(0, entities);
            assertEquals(0, entities.size());
        }

        @Test
        public void removalWithHealth() {
            ConcurrentLinkedQueue<Entity> entities = new ConcurrentLinkedQueue<>();
            Supplier<Entity> createEntityRemovedWhenNoHealth = () -> {
                Entity entity = new Entity(EntityType.OTHER);
                HealthComponent healthComponent = new HealthComponent(10, entity.getMarkForRemovalRunnable());
                entity.put(HealthComponent.class, healthComponent);
                return entity;
            };
            entities.add(createEntityRemovedWhenNoHealth.get());
            entities.add(createEntityRemovedWhenNoHealth.get());
            entities.add(createEntityRemovedWhenNoHealth.get());
            GarbageCollectorSystem garbageCollectorSystem = new GarbageCollectorSystem();

            assertEquals(3, entities.size());

            assert entities.peek() != null;
            entities.peek().get(HealthComponent.class).orElseThrow().removeHealth(15);
            garbageCollectorSystem.tick(0, entities);
            assertEquals(2, entities.size());

            assert entities.peek() != null;
            entities.peek().get(HealthComponent.class).orElseThrow().removeHealth(15);
            garbageCollectorSystem.tick(0, entities);
            assertEquals(1, entities.size());

            assert entities.peek() != null;
            entities.peek().get(HealthComponent.class).orElseThrow().removeHealth(15);
            garbageCollectorSystem.tick(0, entities);
            assertEquals(0, entities.size());
        }

        @Test
        public void removalWithStateMachine() {
            ConcurrentLinkedQueue<Entity> entities = new ConcurrentLinkedQueue<>();
            Supplier<Entity> createEntity = () -> {
                Entity entity = new Entity(EntityType.OTHER);
                HealthComponent healthComponent = new HealthComponent(10, entity.getMarkForRemovalRunnable());
                entity.put(HealthComponent.class, healthComponent);
                return entity;
            };
            entities.add(createEntity.get());
            entities.add(createEntity.get());
            entities.add(createEntity.get());
            GarbageCollectorSystem garbageCollectorSystem = new GarbageCollectorSystem();

            assertEquals(3, entities.size());

            assert entities.peek() != null;
            entities.peek().get(HealthComponent.class).orElseThrow().removeHealth(15);
            garbageCollectorSystem.tick(0, entities);
            assertEquals(2, entities.size());

            assert entities.peek() != null;
            entities.peek().get(HealthComponent.class).orElseThrow().removeHealth(15);
            garbageCollectorSystem.tick(0, entities);
            assertEquals(1, entities.size());

            assert entities.peek() != null;
            entities.peek().get(HealthComponent.class).orElseThrow().removeHealth(15);
            garbageCollectorSystem.tick(0, entities);
            assertEquals(0, entities.size());
        }
    }
}
