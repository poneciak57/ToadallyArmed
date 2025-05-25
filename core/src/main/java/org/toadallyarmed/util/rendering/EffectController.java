package org.toadallyarmed.util.rendering;

import org.toadallyarmed.util.StateMachine;

import java.util.Map;
import java.util.function.Function;

public class EffectController<State extends Enum<State>> {
    public record TimedState<State>(State state, float elapsedTime) {}

    private final StateMachine<State> stateMachine;
    private final Function<State, Float> animationDuration;

    State prevState;
    float stateElapsedTime = 0f;

    public EffectController(StateMachine<State> stateMachine, Function<State, Float> animationDuration) {
        this.stateMachine = stateMachine;
        this.animationDuration = animationDuration;

        prevState = stateMachine.getCurState();
    }
    public EffectController(StateMachine<State> stateMachine, Map<State, Float> animationDuration) {
        this(stateMachine, animationDuration::get);
    }
    public EffectController(StateMachine<State> stateMachine, AnimatedStateSprite<State> animatedStateSprite) {
        this(stateMachine, animatedStateSprite::getAnimationDurationForState);
    }

    public TimedState<State> performEffect(float deltaTime) {
        stateElapsedTime += deltaTime;
        State state = getAndUpdateState();
        return new TimedState<>(state, stateElapsedTime);
    }

    State getAndUpdateState() {
        if (stateElapsedTime >= animationDuration.apply(prevState)) {
            stateElapsedTime = 0f;
            stateMachine.advanceState();
            prevState = stateMachine.getCurState();
        }
        return prevState;
    }
}
