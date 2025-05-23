package org.toadallyarmed.component.bullet;

import org.toadallyarmed.component.interfaces.StateComponent;
import org.toadallyarmed.util.StateMachine;

public class BulletStateComponent implements StateComponent {
    final StateMachine<BulletState> generalStateMachine = new StateMachine<>(BulletState.IDLE);

    public BulletStateComponent() {
        generalStateMachine.setNextStateFrom(BulletState.IDLE, BulletState.IDLE);
        generalStateMachine.setNextStateFrom(BulletState.NONEXISTENT, BulletState.NONEXISTENT);
    }

    public StateMachine<BulletState> getGeneralStateMachine() {
        return generalStateMachine;
    }

    public BulletState getGeneralState() {
        return generalStateMachine.getCurState();
    }

    /// Enqueues the state change as a temporary next. advanceState() should be called
    /// to actually change the state.
    public void setNextGeneralState(BulletState nextState) {
        generalStateMachine.setNextTmpStateFrom(getGeneralState(), nextState);
    }

    /// Changes the state to the default next or the enqueued temporary next.
    /// It should be usually called after the animation is fully executed.
    public void advanceState() {
        generalStateMachine.advanceState();
    }
}
