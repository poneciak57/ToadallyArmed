package org.toadallyarmed.component.frog;

import org.toadallyarmed.component.interfaces.StateComponent;
import org.toadallyarmed.util.StateMachine;

public class FrogStateComponent implements StateComponent {
    final StateMachine<FrogState> generalStateMachine = new StateMachine<>(FrogState.IDLE);
    volatile boolean isAttacked;

    public FrogStateComponent() {
        generalStateMachine.setNextStateFrom(FrogState.IDLE, FrogState.IDLE);
        generalStateMachine.setNextStateFrom(FrogState.ACTION, FrogState.IDLE);
        generalStateMachine.setNextStateFrom(FrogState.DYING, FrogState.NONEXISTENT);
        generalStateMachine.setNextStateFrom(FrogState.NONEXISTENT, FrogState.NONEXISTENT);
    }

    public boolean getIsAttacked() {
        return isAttacked;
    }

    public void setIsAttacked(boolean isAttacked) {
        this.isAttacked = isAttacked;
    }

    public StateMachine<FrogState> getGeneralStateMachine() {
        return generalStateMachine;
    }

    public FrogState getGeneralState() {
        return generalStateMachine.getCurState();
    }

    /// Enqueues the state change as a temporary next. advanceState() should be called
    /// to actually change the state.
    public void setNextGeneralState(FrogState nextState) {
        generalStateMachine.setNextTmpStateFrom(getGeneralState(), nextState);
    }

    /// Changes the state to the default next or the enqueued temporary next.
    /// It should be usually called after the animation is fully executed.
    public void advanceState() {
        generalStateMachine.advanceState();
    }
}
