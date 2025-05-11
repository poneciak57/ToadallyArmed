package org.toadallyarmed.component.hedgehog;

import org.toadallyarmed.component.interfaces.StateComponent;
import org.toadallyarmed.util.StateMachine;

public class HedgehogStateComponent implements StateComponent {
    final StateMachine<HedgehogState> generalStateMachine = new StateMachine<>(HedgehogState.WALKING);
    volatile boolean isAttacked;

    public HedgehogStateComponent() {
        generalStateMachine.setNextStateFrom(HedgehogState.IDLE, HedgehogState.IDLE);
        generalStateMachine.setNextStateFrom(HedgehogState.WALKING, HedgehogState.WALKING);
        generalStateMachine.setNextStateFrom(HedgehogState.ACTION, HedgehogState.IDLE);
        generalStateMachine.setNextStateFrom(HedgehogState.DYING, HedgehogState.NONEXISTENT);
        generalStateMachine.setNextStateFrom(HedgehogState.NONEXISTENT, HedgehogState.NONEXISTENT);
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

    public HedgehogState getGeneralState() {
        return generalStateMachine.getCurState();
    }

    /// Enqueues the state change as a temporary next. advanceState() should be called
    /// to actually change the state.
    public void setNextGeneralState(HedgehogState nextState) {
        generalStateMachine.setNextTmpStateFrom(getGeneralState(), nextState);
    }

    /// Changes the state to the default next or the enqueued temporary next.
    /// It should be usually called after the animation is fully executed.
    public void advanceState() {
        generalStateMachine.advanceState();
    }
}
