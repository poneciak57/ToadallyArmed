package org.toadallyarmed.component;

import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.state.HedgehogState;
import org.toadallyarmed.util.rendering.AnimatedStateSprite;
import org.toadallyarmed.util.rendering.effect.aliveentity.FadeOutEffect;

public class HedgehogRenderableComponent extends AliveEntityRenderableComponent<HedgehogState> {
    public HedgehogRenderableComponent(
        TransformComponent transformComponent,
        HedgehogStateComponent fullStateComponent,
        AnimatedStateSprite<HedgehogState> animatedStateSprite) {

        super(transformComponent, fullStateComponent, animatedStateSprite);
        effects.add(new FadeOutEffect(
            fullStateComponent.getFadeOutStateMachine(),
            0.75f
        ));
    }
}
