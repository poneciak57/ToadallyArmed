package org.toadallyarmed.component.bullet;

import org.toadallyarmed.component.interfaces.RenderableComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.util.rendering.AnimatedStateMachineSpriteInstance;
import org.toadallyarmed.util.rendering.AnimatedStateSprite;
import org.toadallyarmed.util.rendering.Renderer;

public class BulletRenderableComponent implements RenderableComponent {
    final TransformComponent transformComponent;
    final BulletStateComponent fullStateComponent;

    AnimatedStateMachineSpriteInstance<BulletState> spriteInstance;

    public BulletRenderableComponent(
        TransformComponent transformComponent,
        BulletStateComponent fullStateComponent,
        AnimatedStateSprite<BulletState> animatedStateSprite) {
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
