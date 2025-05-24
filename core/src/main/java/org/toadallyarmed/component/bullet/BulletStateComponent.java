package org.toadallyarmed.component.bullet;

import org.toadallyarmed.component.interfaces.StateComponent;
import org.toadallyarmed.state.BulletState;
import org.toadallyarmed.util.StateMachine;

public class BulletStateComponent implements StateComponent {
    final StateMachine<BulletState> generalStateMachine = new StateMachine<>(BulletState.IDLE);

    public BulletStateComponent() {
        generalStateMachine.addState(BulletState.IDLE, BulletState.IDLE);
        generalStateMachine.addState(BulletState.NONEXISTENT, BulletState.NONEXISTENT);
    }

    public StateMachine<BulletState> getGeneralStateMachine() {
        return generalStateMachine;
    }

    public BulletState getGeneralState() {
        return generalStateMachine.getCurState();
    }
}
