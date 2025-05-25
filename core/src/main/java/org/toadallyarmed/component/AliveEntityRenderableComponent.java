package org.toadallyarmed.component;

import org.toadallyarmed.component.interfaces.RenderableComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.util.rendering.*;
import org.toadallyarmed.util.rendering.effect.hurt.HurtEffect;

public class AliveEntityRenderableComponent<State extends Enum<State>> implements RenderableComponent {
    final TransformComponent transformComponent;
    final AliveEntityStateComponent<State> fullStateComponent;

    final AnimatedStateMachineSpriteInstance<State> spriteInstance;
    final HurtEffect hurtEffect;

    public AliveEntityRenderableComponent(
        TransformComponent transformComponent,
        AliveEntityStateComponent<State> fullStateComponent,
        AnimatedStateSprite<State> animatedStateSprite) {
        this.transformComponent = transformComponent;
        this.fullStateComponent = fullStateComponent;
        this.spriteInstance = new AnimatedStateMachineSpriteInstance<>(
            animatedStateSprite,
            fullStateComponent.getGeneralStateMachine()
        );
        this.hurtEffect = new HurtEffect(
            fullStateComponent.getIsAttackedStateMachine(),
            0.5f
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
