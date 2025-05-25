package org.toadallyarmed.util.rendering.effect.hurt;

import org.toadallyarmed.state.BooleanState;
import org.toadallyarmed.util.StateMachine;
import org.toadallyarmed.util.rendering.EffectController;
import org.toadallyarmed.util.rendering.Renderer;
import org.toadallyarmed.util.rendering.SimpleTextureRenderer;
import org.toadallyarmed.util.rendering.TextureRenderer;

import java.util.Map;

public class HurtEffect {
    private final EffectController<BooleanState> effectController;
    private final float hurtAnimationDuration;

    public HurtEffect(StateMachine<BooleanState> stateMachine, float hurtAnimationDuration) {
        this.effectController = new EffectController<>(
            stateMachine,
            Map.of(
                BooleanState.FALSE, 0f,
                BooleanState.TRUE, hurtAnimationDuration
            )
        );
        this.hurtAnimationDuration = hurtAnimationDuration;
    }

    public TextureRenderer getTextureRenderer(Renderer renderer, float deltaTime) {
        final var timedState = effectController.performEffect(deltaTime);
        final var state = timedState.state();
        final var elapsedTime = timedState.elapsedTime();
        if (state == BooleanState.FALSE)
            return new SimpleTextureRenderer(renderer);
        else {
            final float ratio = 1f - Math.abs(0.5f - elapsedTime / hurtAnimationDuration) * 2f;
            return new HurtEffectTextureRenderer(renderer, ratio);
        }
    }
}
