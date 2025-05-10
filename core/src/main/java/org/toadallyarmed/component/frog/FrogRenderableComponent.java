package org.toadallyarmed.component.frog;

import org.toadallyarmed.component.interfaces.RenderableComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.util.rendering.AnimatedStateMachineSpriteInstance;
import org.toadallyarmed.util.rendering.AnimatedStateSprite;
import org.toadallyarmed.util.rendering.Renderer;

public class FrogRenderableComponent implements RenderableComponent {
    final TransformComponent transformComponent;
    final FrogStateComponent fullStateComponent;

    AnimatedStateMachineSpriteInstance<FrogState> spriteInstance;

    public FrogRenderableComponent(
        TransformComponent transformComponent,
        FrogStateComponent fullStateComponent,
        AnimatedStateSprite<FrogState> animatedStateSprite) {
        this.transformComponent = transformComponent;
        this.fullStateComponent = fullStateComponent;
        spriteInstance = new AnimatedStateMachineSpriteInstance<>(
            animatedStateSprite,
            fullStateComponent.getGeneralStateMachine()
        );
    }

    @Override
    public void render(Renderer renderer, float deltaTime) {
        spriteInstance.render(renderer, transformComponent.getPosition(), deltaTime);
    }
}
