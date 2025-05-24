package org.toadallyarmed.component;

import org.toadallyarmed.component.interfaces.RenderableComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.util.rendering.AnimatedStateMachineSpriteInstance;
import org.toadallyarmed.util.rendering.AnimatedStateSprite;
import org.toadallyarmed.util.rendering.Renderer;

public class BasicStatefulRenderableComponent<State extends Enum<State>> implements RenderableComponent {
    final TransformComponent transformComponent;
    final BasicStateComponent<State> stateComponent;

    AnimatedStateMachineSpriteInstance<State> spriteInstance;

    public BasicStatefulRenderableComponent(
        TransformComponent transformComponent,
        BasicStateComponent<State> stateComponent,
        AnimatedStateSprite<State> animatedStateSprite) {
        this.transformComponent = transformComponent;
        this.stateComponent = stateComponent;
        spriteInstance = new AnimatedStateMachineSpriteInstance<>(
            animatedStateSprite,
            stateComponent.getGeneralStateMachine()
        );
    }

    @Override
    public void render(Renderer renderer, float deltaTime, float currentNanoTime) {
        spriteInstance.render(renderer, transformComponent.getAdvancedPosition(currentNanoTime), deltaTime);
    }
}
