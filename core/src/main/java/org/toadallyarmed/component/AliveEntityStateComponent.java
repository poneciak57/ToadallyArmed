package org.toadallyarmed.component;

import org.toadallyarmed.util.StateMachine;

public class AliveEntityStateComponent<State extends Enum<State>> extends BasicStateComponent<State> {
    volatile boolean isAttacked;

    public AliveEntityStateComponent(StateMachine<State> generalStateMachine) {
        super(generalStateMachine);
    }

    public boolean getIsAttacked() {
        return isAttacked;
    }

    public void setIsAttacked(boolean isAttacked) {
        this.isAttacked = isAttacked;
    }
}
