package org.toadallyarmed.util.rendering.effect.aliveentity;

import org.toadallyarmed.state.FadeOutState;
import org.toadallyarmed.util.StateMachine;
import org.toadallyarmed.util.rendering.EffectController;

import java.util.Map;

public class FadeOutEffect implements Effect {
    private final EffectController<FadeOutState> fadeOutController;
    private final float fadeOutAnimationDuration;

    public FadeOutEffect(StateMachine<FadeOutState> fadeOutStateStateMachine, float fadeOutAnimationDuration) {
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

    @Override
    public void applyEffect(AliveEntityTextureRenderer textureRenderer, float deltaTime) {
        final var fadeTimedState = fadeOutController.performEffect(deltaTime);
        float fadeRatio = switch(fadeTimedState.state()) {
            case EXISTS -> 0f;
            case FADES -> fadeTimedState.elapsedTime() / fadeOutAnimationDuration;
            case NONEXISTENT -> 1f;
        };
        textureRenderer.setFadeRatio(fadeRatio);
    }
}
