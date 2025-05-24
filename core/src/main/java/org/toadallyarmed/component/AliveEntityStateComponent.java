package org.toadallyarmed.component;

import org.toadallyarmed.component.interfaces.StateComponent;
import org.toadallyarmed.util.StateMachine;

public class AliveEntityStateComponent<State extends Enum<State>> implements StateComponent {
    final StateMachine<State> generalStateMachine;
    volatile boolean isAttacked;

    public AliveEntityStateComponent(StateMachine<State> generalStateMachine) {
        this.generalStateMachine = generalStateMachine;
    }

    public boolean getIsAttacked() {
        return isAttacked;
    }

    public void setIsAttacked(boolean isAttacked) {
        this.isAttacked = isAttacked;
    }

    public StateMachine<State> getGeneralStateMachine() {
        return generalStateMachine;
    }
}
