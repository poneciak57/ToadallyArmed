package org.toadallyarmed.base.component;

import org.toadallyarmed.base.action.ActionComponent;
import org.toadallyarmed.base.physics.collision.ColliderComponent;
import org.toadallyarmed.base.health.HealthComponent;
import org.toadallyarmed.entity.character.hedgehog.HeadgehogAttackTimerComponent;
import org.toadallyarmed.base.render.RenderableComponent;
import org.toadallyarmed.base.state.StateComponent;
import org.toadallyarmed.base.transform.TransformComponent;

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
