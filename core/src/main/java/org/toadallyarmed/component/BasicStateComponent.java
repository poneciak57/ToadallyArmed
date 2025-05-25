package org.toadallyarmed.component;

import org.toadallyarmed.component.interfaces.StateComponent;
import org.toadallyarmed.util.StateMachine;

public class BasicStateComponent<State extends Enum<State>> implements StateComponent {
    private final StateMachine<State> stateMachine;

    public BasicStateComponent(StateMachine<State> stateMachine) {
        this.stateMachine = stateMachine;
    }

    public StateMachine<State> getGeneralStateMachine() {
        return stateMachine;
    }
}
