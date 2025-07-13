package org.toadallyarmed.entity.character.aliveentity;

import org.toadallyarmed.base.render.RenderableComponent;
import org.toadallyarmed.base.transform.TransformComponent;
import org.toadallyarmed.util.render.*;
import org.toadallyarmed.entity.character.aliveentity.effect.AliveEntityTextureRenderer;
import org.toadallyarmed.entity.character.aliveentity.effect.Effect;
import org.toadallyarmed.entity.character.aliveentity.effect.HurtEffect;

import java.util.ArrayList;
import java.util.List;

public class AliveEntityRenderableComponent<State extends Enum<State>> implements RenderableComponent {
    final TransformComponent transformComponent;

    final AnimatedStateMachineSpriteInstance<State> spriteInstance;
    final protected List<Effect> effects;

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
