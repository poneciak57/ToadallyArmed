package org.toadallyarmed.util.rendering.effect.hurt;

import org.toadallyarmed.state.BooleanState;
import org.toadallyarmed.state.FadeOutState;
import org.toadallyarmed.util.StateMachine;
import org.toadallyarmed.util.rendering.EffectController;
import org.toadallyarmed.util.rendering.Renderer;
import org.toadallyarmed.util.rendering.SimpleTextureRenderer;
import org.toadallyarmed.util.rendering.TextureRenderer;

import java.util.Map;

public class HurtEffect {
    private final EffectController<BooleanState> effectController;
    private final float hurtAnimationDuration;
    private EffectController<FadeOutState> fadeOutController;
    private float fadeOutAnimationDuration;

    public HurtEffect(StateMachine<BooleanState> stateMachine, float hurtAnimationDuration) {
        this.effectController = new EffectController<>(
            stateMachine,
            Map.of(
                BooleanState.FALSE, 0f,
                BooleanState.TRUE, hurtAnimationDuration
            )
        );
        this.hurtAnimationDuration = hurtAnimationDuration;

        this.fadeOutController = null;
        this.fadeOutAnimationDuration = 0;
    }

    public HurtEffect(
        StateMachine<BooleanState> stateMachine, float hurtAnimationDuration,
        StateMachine<FadeOutState> fadeOutStateStateMachine, float fadeOutAnimationDuration) {
        this(stateMachine, hurtAnimationDuration);

        this.fadeOutController = new EffectController<>(
            fadeOutStateStateMachine,
            Map.of(
                FadeOutState.EXISTS, 0f,
                FadeOutState.FADES, fadeOutAnimationDuration,
                FadeOutState.NONEXISTENT, 0f
            )
        );
        this.fadeOutAnimationDuration = fadeOutAnimationDuration;
    }

    public TextureRenderer getTextureRenderer(Renderer renderer, float deltaTime) {
        final var hurtTimedState = effectController.performEffect(deltaTime);
        float hurtRatio;
        if (hurtTimedState.state() == BooleanState.FALSE)
            hurtRatio = 0f;
        else {
            hurtRatio = 1f - Math.abs(0.5f - hurtTimedState.elapsedTime() / hurtAnimationDuration) * 2f;
        }
        float fadeRatio = 0f;
        if (fadeOutController != null) {
            final var fadeTimedState = fadeOutController.performEffect(deltaTime);
            if (fadeTimedState.state() == FadeOutState.EXISTS) fadeRatio = 0f;
            else if (fadeTimedState.state() == FadeOutState.NONEXISTENT) fadeRatio = 1f;
            else fadeRatio = fadeTimedState.elapsedTime() / fadeOutAnimationDuration;
        }
        return new HurtEffectTextureRenderer(renderer, hurtRatio, fadeRatio);
    }
}
