package org.toadallyarmed.entity.character.frog.bard;

import org.toadallyarmed.base.state.BasicStateComponent;
import org.toadallyarmed.scene.gameplay.WalletComponent;
import org.toadallyarmed.base.state.StateComponent;
import org.toadallyarmed.base.transform.TransformComponent;
import org.toadallyarmed.base.entity.Entity;
import org.toadallyarmed.entity.passive.coin.CoinFactory;
import org.toadallyarmed.base.entity.BasicEntityState;
import org.toadallyarmed.entity.character.frog.FrogState;
import org.toadallyarmed.util.state.StateMachine;
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
