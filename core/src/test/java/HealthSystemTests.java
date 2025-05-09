import org.junit.Test;
import org.toadallyarmed.component.HealthComponent;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.system.HealthSystem;

import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.Assert.*;

public class HealthSystemTests {

    @Test
    public void testBasicHealthSystem() {
        ConcurrentLinkedQueue<Entity> entities = new ConcurrentLinkedQueue<>();
        Entity e = new Entity();
        HealthComponent healthComponent = new HealthComponent(100);
        HealthSystem healthSystem = new HealthSystem();
        e.put(HealthComponent.class, healthComponent);
        entities.add(e);
        healthSystem.tick(0, entities);
        assertFalse(entities.isEmpty());

        healthComponent.access().set(1);
        healthSystem.tick(0, entities);
        assertFalse(entities.isEmpty());

        healthComponent.access().set(0);
        healthSystem.tick(0, entities);
        assertTrue(entities.isEmpty());
    }
}
