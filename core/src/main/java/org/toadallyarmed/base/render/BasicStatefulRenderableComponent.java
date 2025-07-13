package org.toadallyarmed.base.render;

import org.toadallyarmed.base.state.BasicStateComponent;
import org.toadallyarmed.base.transform.TransformComponent;
import org.toadallyarmed.util.render.AnimatedStateMachineSpriteInstance;
import org.toadallyarmed.util.render.AnimatedStateSprite;
import org.toadallyarmed.util.render.Renderer;
import org.toadallyarmed.util.render.SimpleTextureRenderer;

public class BasicStatefulRenderableComponent<State extends Enum<State>> implements RenderableComponent {
    final TransformComponent transformComponent;
    final BasicStateComponent<State> stateComponent;

    final AnimatedStateMachineSpriteInstance<State> spriteInstance;

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
        spriteInstance.render(new SimpleTextureRenderer(renderer), transformComponent.getAdvancedPosition(currentNanoTime), deltaTime);
    }
}
