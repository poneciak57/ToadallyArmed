package org.toadallyarmed.component.coin;

import org.toadallyarmed.component.interfaces.StateComponent;
import org.toadallyarmed.state.CoinState;
import org.toadallyarmed.util.StateMachine;

public class CoinStateComponent implements StateComponent {
    final StateMachine<CoinState> generalStateMachine = new StateMachine<>(CoinState.IDLE);

    public CoinStateComponent() {
        generalStateMachine.addState(CoinState.IDLE, CoinState.IDLE);
        generalStateMachine.addState(CoinState.NONEXISTENT, CoinState.NONEXISTENT);
    }

    public StateMachine<CoinState> getGeneralStateMachine() {
        return generalStateMachine;
    }
}
