package org.toadallyarmed.component;

import org.toadallyarmed.state.BooleanState;
import org.toadallyarmed.util.StateMachine;

public class AliveEntityStateComponent<State extends Enum<State>> extends BasicStateComponent<State> {
    private final StateMachine<BooleanState> isAttackedStateMachine;

    public AliveEntityStateComponent(StateMachine<State> generalStateMachine) {
        super(generalStateMachine);
        this.isAttackedStateMachine
            = new StateMachine<>(BooleanState.FALSE)
            .addState(BooleanState.FALSE, BooleanState.FALSE)
            .addState(BooleanState.TRUE, BooleanState.FALSE);
    }

    public StateMachine<BooleanState> getIsAttackedStateMachine() {
        return isAttackedStateMachine;
    }
}
