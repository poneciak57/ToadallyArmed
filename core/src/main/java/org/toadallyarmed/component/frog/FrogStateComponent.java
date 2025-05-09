package org.toadallyarmed.component.frog;

import org.toadallyarmed.component.interfaces.Component;
import org.toadallyarmed.util.StateMachine;

public class FrogStateComponent implements Component {
    final StateMachine<FrogState> generalStateMachine = new StateMachine<>(FrogState.IDLE);
    boolean isAttacked;

    public FrogStateComponent() {
        generalStateMachine.setNextStateFrom(FrogState.IDLE, FrogState.IDLE);
        generalStateMachine.setNextStateFrom(FrogState.ACTION, FrogState.IDLE);
        generalStateMachine.setNextStateFrom(FrogState.DYING, FrogState.NONEXISTENT);
        generalStateMachine.setNextStateFrom(FrogState.NONEXISTENT, FrogState.NONEXISTENT);
    }

    boolean getIsAttacked() {
        return isAttacked;
    }

    void setIsAttacked(boolean isAttacked) {
        this.isAttacked = isAttacked;
    }

    FrogState getGeneralState() {
        return generalStateMachine.getCurState();
    }

    void setNextGeneralState(FrogState nextState) {
        generalStateMachine.setNextTmpStateFrom(getGeneralState(), nextState);
    }
}
