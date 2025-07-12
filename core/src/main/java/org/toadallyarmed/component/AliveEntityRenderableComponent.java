package org.toadallyarmed.component;

import org.toadallyarmed.component.interfaces.RenderableComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.util.rendering.*;
import org.toadallyarmed.util.rendering.effect.aliveentity.AliveEntityTextureRenderer;
import org.toadallyarmed.util.rendering.effect.aliveentity.Effect;
import org.toadallyarmed.util.rendering.effect.aliveentity.HurtEffect;

import java.util.ArrayList;
import java.util.List;

public class AliveEntityRenderableComponent<State extends Enum<State>> implements RenderableComponent {
    final TransformComponent transformComponent;

    final AnimatedStateMachineSpriteInstance<State> spriteInstance;
    final List<Effect> effects;

    public AliveEntityRenderableComponent(
        TransformComponent transformComponent,
        AliveEntityStateComponent<State> fullStateComponent,
        AnimatedStateSprite<State> animatedStateSprite) {
        this.transformComponent = transformComponent;
        this.spriteInstance = new AnimatedStateMachineSpriteInstance<>(
            animatedStateSprite,
            fullStateComponent.getGeneralStateMachine()
        );
        effects = new ArrayList<>();
        effects.add(new HurtEffect(
            fullStateComponent.getIsAttackedStateMachine(),
            0.5f
        ));
    }

    @Override
    public void render(Renderer renderer, float deltaTime, float currentNanoTime) {
        AliveEntityTextureRenderer textureRenderer = new AliveEntityTextureRenderer(renderer);
        for (Effect effect : effects)
            effect.applyEffect(textureRenderer, deltaTime);
        spriteInstance.render(
            textureRenderer,
            transformComponent.getAdvancedPosition(currentNanoTime),
            deltaTime);
    }
}
