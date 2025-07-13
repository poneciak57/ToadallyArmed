package org.toadallyarmed.entity.character.hedgehog;

import org.toadallyarmed.base.transform.TransformComponent;
import org.toadallyarmed.entity.character.aliveentity.AliveEntityRenderableComponent;
import org.toadallyarmed.util.render.AnimatedStateSprite;
import org.toadallyarmed.entity.character.aliveentity.effect.FadeOutEffect;

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
