import org.junit.Test;
import org.toadallyarmed.base.component.BaseComponentsRegistry;
import org.toadallyarmed.base.component.Component;
import org.toadallyarmed.base.entity.Entity;
import org.toadallyarmed.base.entity.EntityType;
import org.toadallyarmed.util.exception.NotBaseComponentException;

import java.util.Set;

import static org.junit.Assert.*;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class EntityComponentsTests {
    static class TestComponent1 implements Component {}
    static class TestComponent2 implements Component {}

    static abstract class SuperComponent implements Component {}
    static class TestSuperComponent1 extends SuperComponent {}
    static class TestSuperComponent2 extends SuperComponent {}

    @Test
    public void testNotBaseComponent() {
        Set<Class<? extends Component>> components = BaseComponentsRegistry.BASE_COMPONENTS;
        BaseComponentsRegistry.BASE_COMPONENTS = Set.of();
        Entity e = new Entity(EntityType.OTHER);
        TestComponent1 c = new TestComponent1();
        assertThrows(NotBaseComponentException.class ,() -> e.put(TestComponent1.class, c));
        BaseComponentsRegistry.BASE_COMPONENTS = components;
    }

    @Test
    public void testSimpleCase() {
        Set<Class<? extends Component>> components = BaseComponentsRegistry.BASE_COMPONENTS;
        BaseComponentsRegistry.BASE_COMPONENTS = Set.of(Component.class);
        Entity e = new Entity(EntityType.OTHER);
        Component c = new Component() {};
        e.put(Component.class, c);

        assertEquals(e.get(Component.class).get(), c);
        BaseComponentsRegistry.BASE_COMPONENTS = components;
    }

    @Test
    public void testMoreAdvancedCase() {
        Set<Class<? extends Component>> components = BaseComponentsRegistry.BASE_COMPONENTS;
        BaseComponentsRegistry.BASE_COMPONENTS = Set.of(TestComponent1.class, TestComponent2.class);
        Entity e = new Entity(EntityType.OTHER);
        TestComponent1 c1 = new TestComponent1();
        TestComponent2 c2 = new TestComponent2();
        e.put(TestComponent1.class, c1);
        e.put(TestComponent2.class, c2);
        assertEquals(e.get(TestComponent1.class).get(), c1);
        assertEquals(e.get(TestComponent2.class).get(), c2);
        BaseComponentsRegistry.BASE_COMPONENTS = components;
    }

    @Test
    public void testAdvancedCase() {
        Set<Class<? extends Component>> components = BaseComponentsRegistry.BASE_COMPONENTS;
        BaseComponentsRegistry.BASE_COMPONENTS = Set.of(SuperComponent.class);
        Entity e = new Entity(EntityType.OTHER);
        TestSuperComponent1 c1 = new TestSuperComponent1();
        TestSuperComponent2 c2 = new TestSuperComponent2();
        assertFalse(e.get(TestSuperComponent1.class).isPresent());
        e.put(SuperComponent.class, c1);
        assertEquals(e.get(SuperComponent.class).get(), c1);
        e.put(SuperComponent.class, c2);
        assertEquals(e.get(SuperComponent.class).get(), c2);
        BaseComponentsRegistry.BASE_COMPONENTS = components;
    }
}
