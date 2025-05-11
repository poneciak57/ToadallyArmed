package org.toadallyarmed.component.coin;

import org.toadallyarmed.component.interfaces.StateComponent;
import org.toadallyarmed.util.StateMachine;

public class CoinStateComponent implements StateComponent {
    final StateMachine<CoinState> generalStateMachine = new StateMachine<>(CoinState.IDLE);

    public CoinStateComponent() {
        generalStateMachine.setNextStateFrom(CoinState.IDLE, CoinState.IDLE);
        generalStateMachine.setNextStateFrom(CoinState.NONEXISTENT, CoinState.NONEXISTENT);
    }

    public StateMachine<CoinState> getGeneralStateMachine() {
        return generalStateMachine;
    }

    public CoinState getGeneralState() {
        return generalStateMachine.getCurState();
    }

    /// Enqueues the state change as a temporary next. advanceState() should be called
    /// to actually change the state.
    public void setNextGeneralState(CoinState nextState) {
        generalStateMachine.setNextTmpStateFrom(getGeneralState(), nextState);
    }

    /// Changes the state to the default next or the enqueued temporary next.
    /// It should be usually called after the animation is fully executed.
    public void advanceState() {
        generalStateMachine.advanceState();
    }
}
