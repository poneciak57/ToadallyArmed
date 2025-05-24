package org.toadallyarmed.util.rendering;

import com.badlogic.gdx.math.Vector2;
import org.toadallyarmed.util.StateMachine;

public class AnimatedStateMachineSpriteInstance<State extends Enum<State>> {
    final AnimatedStateSprite<State> animatedStateSprite;
    final StateMachine<State> stateMachine;
    State prevState;
    float stateElapsedTime = 0f;

    public AnimatedStateMachineSpriteInstance(
        AnimatedStateSprite<State> animatedStateSprite,
        StateMachine<State> stateMachine) {
        this.animatedStateSprite = animatedStateSprite;
        this.stateMachine = stateMachine;

        prevState = stateMachine.getCurState();
    }

    public void render(Renderer renderer, Vector2 position, float deltaTime) {
        stateElapsedTime += deltaTime;
        State state = getAndUpdateState();
        animatedStateSprite.render(renderer, position, state, stateElapsedTime);
    }


    State getAndUpdateState() {
        if (stateElapsedTime >= animatedStateSprite.getAnimationDurationForState(prevState)) {
            stateElapsedTime = 0f;
            stateMachine.advanceState();
            prevState = stateMachine.getCurState();
        }
        return prevState;
    }
}
