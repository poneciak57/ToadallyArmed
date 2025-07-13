package org.toadallyarmed.entity.character.aliveentity;

import org.toadallyarmed.base.state.BasicStateComponent;
import org.toadallyarmed.util.state.BooleanState;
import org.toadallyarmed.util.state.StateMachine;

public class AliveEntityStateComponent<State extends Enum<State>> extends BasicStateComponent<State> {
    private final StateMachine<BooleanState> isAttackedStateMachine;

    public AliveEntityStateComponent(StateMachine<State> generalStateMachine) {
        super(generalStateMachine);
        this.isAttackedStateMachine
            = new StateMachine<>(BooleanState.FALSE)
            .addState(BooleanState.FALSE, BooleanState.FALSE, true)
            .addState(BooleanState.TRUE, BooleanState.FALSE, false);
    }

    public StateMachine<BooleanState> getIsAttackedStateMachine() {
        return isAttackedStateMachine;
    }
}
