package org.toadallyarmed.component.interfaces;

import org.toadallyarmed.component.ColliderComponent;
import org.toadallyarmed.component.HeadgehogAttackTimerComponent;
import org.toadallyarmed.component.HealthComponent;

import java.util.List;

public class BaseComponentsRegistry {
    public static List<Class<? extends Component>> BASE_COMPONENTS = List.of(
        StateComponent.class,
        RenderableComponent.class,
        TransformComponent.class,
        HealthComponent.class,
        ColliderComponent.class,
        ActionComponent.class,
        HeadgehogAttackTimerComponent.class
    );
}
