package org.toadallyarmed.component;

import org.toadallyarmed.component.interfaces.RenderableComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.state.HedgehogState;
import org.toadallyarmed.util.rendering.AnimatedStateMachineSpriteInstance;
import org.toadallyarmed.util.rendering.AnimatedStateSprite;
import org.toadallyarmed.util.rendering.Renderer;
import org.toadallyarmed.util.rendering.effect.hurt.HurtEffect;

public class HedgehogRenderableComponent implements RenderableComponent {
    final TransformComponent transformComponent;
    final HedgehogStateComponent fullStateComponent;

    final AnimatedStateMachineSpriteInstance<HedgehogState> spriteInstance;
    final HurtEffect hurtEffect;

    public HedgehogRenderableComponent(
        TransformComponent transformComponent,
        HedgehogStateComponent fullStateComponent,
        AnimatedStateSprite<HedgehogState> animatedStateSprite) {
        this.transformComponent = transformComponent;
        this.fullStateComponent = fullStateComponent;
        this.spriteInstance = new AnimatedStateMachineSpriteInstance<>(
            animatedStateSprite,
            fullStateComponent.getGeneralStateMachine()
        );
        this.hurtEffect = new HurtEffect(
            fullStateComponent.getIsAttackedStateMachine(),
            0.5f,
            fullStateComponent.getFadeOutStateMachine(),
            0.75f
        );
    }

    @Override
    public void render(Renderer renderer, float deltaTime, float currentNanoTime) {
        spriteInstance.render(
            hurtEffect.getTextureRenderer(renderer, deltaTime),
            transformComponent.getAdvancedPosition(currentNanoTime),
            deltaTime);
    }
}
