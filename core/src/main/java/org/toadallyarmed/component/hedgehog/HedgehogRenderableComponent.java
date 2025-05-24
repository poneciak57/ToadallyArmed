package org.toadallyarmed.component.hedgehog;

import org.toadallyarmed.component.interfaces.RenderableComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.state.HedgehogState;
import org.toadallyarmed.util.rendering.AnimatedStateMachineSpriteInstance;
import org.toadallyarmed.util.rendering.AnimatedStateSprite;
import org.toadallyarmed.util.rendering.Renderer;

public class HedgehogRenderableComponent implements RenderableComponent {
    final TransformComponent transformComponent;
    final HedgehogStateComponent fullStateComponent;

    AnimatedStateMachineSpriteInstance<HedgehogState> spriteInstance;

    public HedgehogRenderableComponent(
        TransformComponent transformComponent,
        HedgehogStateComponent fullStateComponent,
        AnimatedStateSprite<HedgehogState> animatedStateSprite) {
        this.transformComponent = transformComponent;
        this.fullStateComponent = fullStateComponent;
        spriteInstance = new AnimatedStateMachineSpriteInstance<>(
            animatedStateSprite,
            fullStateComponent.getGeneralStateMachine()
        );
    }

    @Override
    public void render(Renderer renderer, float deltaTime, float currentNanoTime) {
        spriteInstance.render(renderer, transformComponent.getAdvancedPosition(currentNanoTime), deltaTime);
    }
}
