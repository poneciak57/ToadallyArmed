package org.toadallyarmed.component.interfaces;

import org.toadallyarmed.component.ColliderComponent;
import org.toadallyarmed.component.HeadgehogAttackTimerComponent;
import org.toadallyarmed.component.HealthComponent;

import java.util.Set;

public class BaseComponentsRegistry {
    public static Set<Class<? extends Component>> BASE_COMPONENTS = Set.of(
        StateComponent.class,
        RenderableComponent.class,
        TransformComponent.class,
        HealthComponent.class,
        ColliderComponent.class,
        ActionComponent.class,
        HeadgehogAttackTimerComponent.class
    );
}
