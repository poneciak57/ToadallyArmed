package org.toadallyarmed.component;

import org.toadallyarmed.state.BooleanState;
import org.toadallyarmed.state.FadeOutState;
import org.toadallyarmed.state.HedgehogState;
import org.toadallyarmed.util.StateMachine;

public class HedgehogStateComponent extends BasicStateComponent<HedgehogState> {
    private final StateMachine<BooleanState> isAttackedStateMachine;
    private final StateMachine<FadeOutState> fadeOutStateMachine;

    public HedgehogStateComponent(StateMachine<HedgehogState> generalStateMachine) {
        super(generalStateMachine);
        this.isAttackedStateMachine
            = new StateMachine<>(BooleanState.FALSE)
            .addState(BooleanState.FALSE, BooleanState.FALSE, true)
            .addState(BooleanState.TRUE, BooleanState.FALSE, false);
        this.fadeOutStateMachine
            = new StateMachine<>(FadeOutState.EXISTS)
            .addState(FadeOutState.EXISTS, FadeOutState.EXISTS, true)
            .addState(FadeOutState.FADES, FadeOutState.NONEXISTENT, false)
            .addState(FadeOutState.NONEXISTENT, FadeOutState.NONEXISTENT, false);
    }

    public StateMachine<BooleanState> getIsAttackedStateMachine() {
        return isAttackedStateMachine;
    }

    public StateMachine<FadeOutState> getFadeOutStateMachine() { return fadeOutStateMachine; }
}
