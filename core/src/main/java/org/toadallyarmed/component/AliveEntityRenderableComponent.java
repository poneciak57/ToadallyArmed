package org.toadallyarmed.component;

import org.toadallyarmed.component.interfaces.RenderableComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.util.rendering.*;

// TODO See #13. Implement it here.
public class AliveEntityRenderableComponent<State extends Enum<State>> implements RenderableComponent {
    final TransformComponent transformComponent;
    final AliveEntityStateComponent<State> fullStateComponent;

    final AnimatedStateMachineSpriteInstance<State> spriteInstance;

    public AliveEntityRenderableComponent(
        TransformComponent transformComponent,
        AliveEntityStateComponent<State> fullStateComponent,
        AnimatedStateSprite<State> animatedStateSprite) {
        this.transformComponent = transformComponent;
        this.fullStateComponent = fullStateComponent;
        spriteInstance = new AnimatedStateMachineSpriteInstance<>(
            animatedStateSprite,
            fullStateComponent.getGeneralStateMachine()
        );
    }

    @Override
    public void render(Renderer renderer, float deltaTime, float currentNanoTime) {
        if (fullStateComponent.getIsAttacked())
            spriteInstance.render(new HurtEffectTextureRenderer(renderer, 0.5f), transformComponent.getAdvancedPosition(currentNanoTime), deltaTime);
        else
            spriteInstance.render(new SimpleTextureRenderer(renderer), transformComponent.getAdvancedPosition(currentNanoTime), deltaTime);
    }
}
