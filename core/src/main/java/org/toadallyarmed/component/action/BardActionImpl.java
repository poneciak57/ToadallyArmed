package org.toadallyarmed.component.action;

import org.toadallyarmed.component.BasicStateComponent;
import org.toadallyarmed.component.WalletComponent;
import org.toadallyarmed.component.interfaces.StateComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.factory.CoinFactory;
import org.toadallyarmed.state.BasicEntityState;
import org.toadallyarmed.state.FrogState;
import org.toadallyarmed.util.StateMachine;
import org.toadallyarmed.util.action.Action;

import java.util.Collection;

record BardActionPayload(
    TransformComponent pos,
    StateMachine<FrogState> stateMachine,
    Collection<Entity> entities,
    WalletComponent walletComponent,
    int bardIncomeDelta
) {
}

class BardActionImpl implements Action<BardActionPayload> {
    boolean firstTime = true;

    @SuppressWarnings("deprecated")
    @Override
    public void run(BardActionPayload payload) {
        if (!firstTime){
            payload.stateMachine().setNextTmpState(FrogState.IDLE, FrogState.HOP, () -> {
                Entity coin= CoinFactory.get().createCoin(payload.pos().getPosition());
                payload.walletComponent().increase(payload.bardIncomeDelta());//upload money
                payload.entities().add(coin);

                StateMachine<BasicEntityState> stateMachine;
                var stateComponent = coin.get(StateComponent.class);
                BasicStateComponent<BasicEntityState> ESC = (BasicStateComponent<BasicEntityState>) stateComponent.get();
                stateMachine= ESC.getGeneralStateMachine();
                stateMachine.setNextTmpState(BasicEntityState.IDLE, BasicEntityState.NONEXISTENT, coin::markForRemoval);
            });
        }
        firstTime = false;
    }
}
