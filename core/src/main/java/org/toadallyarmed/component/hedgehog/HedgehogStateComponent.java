package org.toadallyarmed.component.hedgehog;

import org.toadallyarmed.component.interfaces.StateComponent;
import org.toadallyarmed.state.HedgehogState;
import org.toadallyarmed.util.StateMachine;

public class HedgehogStateComponent implements StateComponent {
    final StateMachine<HedgehogState> generalStateMachine = new StateMachine<>(HedgehogState.WALKING);
    volatile boolean isAttacked;

    public HedgehogStateComponent(Runnable entityRemovalRunnable) {
        generalStateMachine.addState(HedgehogState.IDLE, HedgehogState.IDLE);
        generalStateMachine.addState(HedgehogState.WALKING, HedgehogState.WALKING);
        generalStateMachine.addState(HedgehogState.ACTION, HedgehogState.IDLE);
        generalStateMachine.addState(HedgehogState.DYING, HedgehogState.NONEXISTENT, entityRemovalRunnable);
        generalStateMachine.addState(HedgehogState.NONEXISTENT, HedgehogState.NONEXISTENT);
    }

    public boolean getIsAttacked() {
        return isAttacked;
    }

    public void setIsAttacked(boolean isAttacked) {
        this.isAttacked = isAttacked;
    }

    public StateMachine<HedgehogState> getGeneralStateMachine() {
        return generalStateMachine;
    }
}
