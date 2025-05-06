import org.junit.Test;
import org.toadallyarmed.component.interfaces.Component;
import org.toadallyarmed.entity.Entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class EntityComponentsTests {

    static class TestComponent1 implements Component {}
    static class TestComponent2 implements Component {}

    static abstract class SuperComponent implements Component {}
    static class TestSuperComponent1 extends SuperComponent {}
    static class TestSuperComponent2 extends SuperComponent {}

    @Test
    public void testSimpleCase() {
        Entity e = new Entity() {};
        Component c = new Component() {};
        e.put(Component.class, c);

        assertEquals(e.get(Component.class).get(), c);
    }

    @Test
    public void testMoreAdvancedCase() {
        Entity e = new Entity() {};
        TestComponent1 c1 = new TestComponent1();
        TestComponent2 c2 = new TestComponent2();
        e.put(TestComponent1.class, c1);
        e.put(TestComponent2.class, c2);
        assertEquals(e.get(TestComponent1.class).get(), c1);
        assertEquals(e.get(TestComponent2.class).get(), c2);
    }

    @Test
    public void testAdvancedCase() {
        Entity e = new Entity() {};
        TestSuperComponent1 c1 = new TestSuperComponent1();
        TestSuperComponent2 c2 = new TestSuperComponent2();
        assertFalse(e.get(TestSuperComponent1.class).isPresent());
        e.put(SuperComponent.class, c1);
        assertEquals(e.get(SuperComponent.class).get(), c1);
        e.put(SuperComponent.class, c2);
        assertEquals(e.get(SuperComponent.class).get(), c2);
    }
}
