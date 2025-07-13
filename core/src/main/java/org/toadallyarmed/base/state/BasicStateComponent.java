package org.toadallyarmed.base.state;

import org.toadallyarmed.util.state.StateMachine;

public class BasicStateComponent<State extends Enum<State>> implements StateComponent {
    private final StateMachine<State> stateMachine;

    public BasicStateComponent(StateMachine<State> stateMachine) {
        this.stateMachine = stateMachine;
    }

    public StateMachine<State> getGeneralStateMachine() {
        return stateMachine;
    }
}
