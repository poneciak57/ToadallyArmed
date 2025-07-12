package org.toadallyarmed.component;

import org.toadallyarmed.state.FadeOutState;
import org.toadallyarmed.state.HedgehogState;
import org.toadallyarmed.util.StateMachine;

public class HedgehogStateComponent extends AliveEntityStateComponent<HedgehogState> {
    private final StateMachine<FadeOutState> fadeOutStateMachine;

    public HedgehogStateComponent(StateMachine<HedgehogState> generalStateMachine) {
        super(generalStateMachine);
        this.fadeOutStateMachine
            = new StateMachine<>(FadeOutState.EXISTS)
            .addState(FadeOutState.EXISTS, FadeOutState.EXISTS, true)
            .addState(FadeOutState.FADES, FadeOutState.NONEXISTENT, false)
            .addState(FadeOutState.NONEXISTENT, FadeOutState.NONEXISTENT, false);
    }

    public StateMachine<FadeOutState> getFadeOutStateMachine() { return fadeOutStateMachine; }
}
