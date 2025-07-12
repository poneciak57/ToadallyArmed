package org.toadallyarmed.util.rendering.effect.aliveentity;

import org.toadallyarmed.state.BooleanState;
import org.toadallyarmed.util.StateMachine;
import org.toadallyarmed.util.rendering.EffectController;

import java.util.Map;

public class HurtEffect implements Effect {
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

    @Override
    public void applyEffect(AliveEntityTextureRenderer textureRenderer, float deltaTime) {
        final var hurtTimedState = effectController.performEffect(deltaTime);
        float hurtRatio = switch(hurtTimedState.state()) {
            case FALSE -> 0f;
            case TRUE -> 1f - Math.abs(0.5f - hurtTimedState.elapsedTime() / hurtAnimationDuration) * 2f;
        };
        textureRenderer.setHurtRatio(hurtRatio);
    }
}
