package org.toadallyarmed.util.rendering.effect.hurt;

import org.toadallyarmed.state.BooleanState;
import org.toadallyarmed.util.StateMachine;
import org.toadallyarmed.util.rendering.Renderer;
import org.toadallyarmed.util.rendering.SimpleTextureRenderer;
import org.toadallyarmed.util.rendering.TextureRenderer;

import java.util.Map;

public class HurtEffect {
    private final StateMachine<BooleanState> stateMachine;
    private final Map<BooleanState, Float> animationDuration;

    BooleanState prevState;
    float stateElapsedTime = 0f;

    public HurtEffect(StateMachine<BooleanState> stateMachine, float hurtAnimationDuration) {
        this.stateMachine = stateMachine;
        this.animationDuration = Map.of(
            BooleanState.FALSE, 0f,
            BooleanState.TRUE, hurtAnimationDuration
        );

        prevState = stateMachine.getCurState();
    }

    public TextureRenderer getTextureRenderer(Renderer renderer, float deltaTime) {
        stateElapsedTime += deltaTime;
        BooleanState state = getAndUpdateState();

        if (state == BooleanState.FALSE)
            return new SimpleTextureRenderer(renderer);
        else {
            float ratio = 1f - Math.abs(0.5f - stateElapsedTime / animationDuration.get(state)) * 2f;
            return new HurtEffectTextureRenderer(renderer, ratio);
        }
    }

    BooleanState getAndUpdateState() {
        if (stateElapsedTime >= animationDuration.get(prevState)) {
            stateElapsedTime = 0f;
            stateMachine.advanceState();
            prevState = stateMachine.getCurState();
        }
        return prevState;
    }
}
