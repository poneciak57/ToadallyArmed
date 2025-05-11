package org.toadallyarmed.component.coin;

import org.toadallyarmed.component.interfaces.RenderableComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.util.rendering.AnimatedStateMachineSpriteInstance;
import org.toadallyarmed.util.rendering.AnimatedStateSprite;
import org.toadallyarmed.util.rendering.Renderer;

public class CoinRenderableComponent implements RenderableComponent {
    final TransformComponent transformComponent;
    final CoinStateComponent fullStateComponent;

    AnimatedStateMachineSpriteInstance<CoinState> spriteInstance;

    public CoinRenderableComponent(
        TransformComponent transformComponent,
        CoinStateComponent fullStateComponent,
        AnimatedStateSprite<CoinState> animatedStateSprite) {
        this.transformComponent = transformComponent;
        this.fullStateComponent = fullStateComponent;
        spriteInstance = new AnimatedStateMachineSpriteInstance<>(
            animatedStateSprite,
            fullStateComponent.getGeneralStateMachine()
        );
    }

    @Override
    public void render(Renderer renderer, float deltaTime, float currentTimestamp) {
        spriteInstance.render(renderer, transformComponent.getAdvancedPosition(currentTimestamp), deltaTime);
    }
}
