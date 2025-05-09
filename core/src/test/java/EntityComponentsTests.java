import org.junit.Test;
import org.toadallyarmed.component.interfaces.BaseComponentsRegistry;
import org.toadallyarmed.component.interfaces.Component;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.entity.EntityType;
import org.toadallyarmed.exception.NotBaseComponentException;

import java.util.List;

import static org.junit.Assert.*;

public class EntityComponentsTests {
    static class TestComponent1 implements Component {}
    static class TestComponent2 implements Component {}

    static abstract class SuperComponent implements Component {}
    static class TestSuperComponent1 extends SuperComponent {}
    static class TestSuperComponent2 extends SuperComponent {}

    @Test
    public void testNotBaseComponent() {
        BaseComponentsRegistry.BASE_COMPONENTS = List.of();
        Entity e = new Entity.EntityBuilder(EntityType.OTHER).build();
        TestComponent1 c = new TestComponent1();
        assertThrows(NotBaseComponentException.class ,() -> e.put(TestComponent1.class, c));
    }

    @Test
    public void testSimpleCase() {
        BaseComponentsRegistry.BASE_COMPONENTS = List.of(Component.class);
        Entity e = new Entity.EntityBuilder(EntityType.OTHER).build();
        Component c = new Component() {};
        e.put(Component.class, c);

        assertEquals(e.get(Component.class).get(), c);
    }

    @Test
    public void testMoreAdvancedCase() {
        BaseComponentsRegistry.BASE_COMPONENTS = List.of(TestComponent1.class, TestComponent2.class);
        Entity e = new Entity.EntityBuilder(EntityType.OTHER).build();
        TestComponent1 c1 = new TestComponent1();
        TestComponent2 c2 = new TestComponent2();
        e.put(TestComponent1.class, c1);
        e.put(TestComponent2.class, c2);
        assertEquals(e.get(TestComponent1.class).get(), c1);
        assertEquals(e.get(TestComponent2.class).get(), c2);
    }

    @Test
    public void testAdvancedCase() {
        BaseComponentsRegistry.BASE_COMPONENTS = List.of(SuperComponent.class);
        Entity e = new Entity.EntityBuilder(EntityType.OTHER).build();
        TestSuperComponent1 c1 = new TestSuperComponent1();
        TestSuperComponent2 c2 = new TestSuperComponent2();
        assertFalse(e.get(TestSuperComponent1.class).isPresent());
        e.put(SuperComponent.class, c1);
        assertEquals(e.get(SuperComponent.class).get(), c1);
        e.put(SuperComponent.class, c2);
        assertEquals(e.get(SuperComponent.class).get(), c2);
    }
}
