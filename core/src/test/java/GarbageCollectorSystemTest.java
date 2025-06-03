import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.toadallyarmed.component.BasicStateComponent;
import org.toadallyarmed.component.HealthComponent;
import org.toadallyarmed.component.interfaces.StateComponent;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.entity.EntityType;
import org.toadallyarmed.system.GarbageCollectorSystem;
import org.toadallyarmed.util.StateMachine;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class GarbageCollectorSystemTest {
    public static class EntityRemovalTest {
        @Test
        public void basicRemoval() {
            ConcurrentLinkedQueue<Entity> entities = new ConcurrentLinkedQueue<>();
            GarbageCollectorSystem garbageCollectorSystem = new GarbageCollectorSystem();

            entities.add(new Entity(EntityType.OTHER));
            entities.add(new Entity(EntityType.OTHER));
            entities.add(new Entity(EntityType.OTHER));

            assertEquals(3, entities.size());

            assertNotNull(entities.peek());
            entities.peek().markForRemoval();
            garbageCollectorSystem.tick(0, entities);
            assertEquals(2, entities.size());

            assertNotNull(entities.peek());
            entities.peek().markForRemoval();
            garbageCollectorSystem.tick(0, entities);
            assertEquals(1, entities.size());

            assertNotNull(entities.peek());
            entities.peek().markForRemoval();
            garbageCollectorSystem.tick(0, entities);
            assertEquals(0, entities.size());
        }

        @Test
        public void removalWithHealth() {
            ConcurrentLinkedQueue<Entity> entities = new ConcurrentLinkedQueue<>();
            Supplier<Entity> createEntityRemovedWhenNoHealth = () -> {
                Entity entity = new Entity(EntityType.OTHER);
                HealthComponent healthComponent = new HealthComponent(10).setNoHealthAction(entity.getMarkForRemovalRunnable());
                entity.put(HealthComponent.class, healthComponent);
                return entity;
            };
            entities.add(createEntityRemovedWhenNoHealth.get());
            entities.add(createEntityRemovedWhenNoHealth.get());
            entities.add(createEntityRemovedWhenNoHealth.get());
            GarbageCollectorSystem garbageCollectorSystem = new GarbageCollectorSystem();

            assertEquals(3, entities.size());

            assertNotNull(entities.peek());
            entities.peek().get(HealthComponent.class).orElseThrow().removeHealth(15);
            garbageCollectorSystem.tick(0, entities);
            assertEquals(2, entities.size());

            assertNotNull(entities.peek());
            entities.peek().get(HealthComponent.class).orElseThrow().removeHealth(15);
            garbageCollectorSystem.tick(0, entities);
            assertEquals(1, entities.size());

            assertNotNull(entities.peek());
            entities.peek().get(HealthComponent.class).orElseThrow().removeHealth(15);
            garbageCollectorSystem.tick(0, entities);
            assertEquals(0, entities.size());
        }

        enum SimpleState {
            IDLE,
            DYING,
            NONEXISTENT
        }

        @Test
        public void removalWithStateMachineAndHealth() {
            ConcurrentLinkedQueue<Entity> entities = new ConcurrentLinkedQueue<>();
            Supplier<Entity> createEntity = () -> {
                Entity entity = new Entity(EntityType.OTHER);

                StateMachine<SimpleState> stateMachine = new StateMachine<>(SimpleState.IDLE);
                stateMachine.addState(SimpleState.IDLE, SimpleState.IDLE, true);
                stateMachine.addState(SimpleState.DYING, SimpleState.NONEXISTENT, entity.getMarkForRemovalRunnable(), false);
                stateMachine.addState(SimpleState.NONEXISTENT, SimpleState.NONEXISTENT, false);
                BasicStateComponent<SimpleState> stateComponent = new BasicStateComponent<>(stateMachine);

                HealthComponent healthComponent = new HealthComponent(
                        10).setNoHealthAction(() -> stateMachine.setNextTmpState(SimpleState.DYING));

                entity
                        .put(StateComponent.class, stateComponent)
                        .put(HealthComponent.class, healthComponent);
                return entity;
            };
            Consumer<Entity> advanceState = (Entity entity) -> {
                StateComponent stateComponent = entity.get(StateComponent.class).orElseThrow();
                assertTrue(stateComponent instanceof BasicStateComponent<?>);
                @SuppressWarnings("unchecked") BasicStateComponent<SimpleState> basicStateComponent
                    = (BasicStateComponent<SimpleState>) stateComponent;
                basicStateComponent.getGeneralStateMachine().advanceState();
            };
            entities.add(createEntity.get());
            entities.add(createEntity.get());
            entities.add(createEntity.get());
            GarbageCollectorSystem garbageCollectorSystem = new GarbageCollectorSystem();

            assertEquals(3, entities.size());

            assertNotNull(entities.peek());
            entities.peek().get(HealthComponent.class).orElseThrow().removeHealth(15);
            advanceState.accept(entities.peek());
            advanceState.accept(entities.peek());
            garbageCollectorSystem.tick(0, entities);
            assertEquals(2, entities.size());

            assertNotNull(entities.peek());
            entities.peek().get(HealthComponent.class).orElseThrow().removeHealth(15);
            advanceState.accept(entities.peek());
            advanceState.accept(entities.peek());
            garbageCollectorSystem.tick(0, entities);
            assertEquals(1, entities.size());

            assertNotNull(entities.peek());
            entities.peek().get(HealthComponent.class).orElseThrow().removeHealth(15);
            advanceState.accept(entities.peek());
            advanceState.accept(entities.peek());
            garbageCollectorSystem.tick(0, entities);
            assertEquals(0, entities.size());
        }
    }
}
