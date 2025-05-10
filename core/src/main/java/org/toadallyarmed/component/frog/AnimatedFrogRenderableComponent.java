package org.toadallyarmed.component.frog;

import org.toadallyarmed.component.interfaces.RenderableComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.util.rendering.AnimatedSprite;
import org.toadallyarmed.util.rendering.AnimatedStateMachineSpriteInstance;
import org.toadallyarmed.util.rendering.AnimatedStateSprite;
import org.toadallyarmed.util.rendering.Renderer;
import org.toadallyarmed.util.logger.Logger;

import java.util.Map;

// WARNING: This is temporary class. Its functionality should be moved to FrogRenderableComponent!
public class AnimatedFrogRenderableComponent implements RenderableComponent {
    final TransformComponent transformComponent;
    final FrogStateComponent fullStateComponent;

    AnimatedStateMachineSpriteInstance<FrogState> spriteInstance;

    public AnimatedFrogRenderableComponent(
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
