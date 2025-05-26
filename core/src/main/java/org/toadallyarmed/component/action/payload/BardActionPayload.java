package org.toadallyarmed.component.action.payload;

import org.toadallyarmed.component.AliveEntityStateComponent;
import org.toadallyarmed.component.WalletComponent;
import org.toadallyarmed.component.interfaces.StateComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.state.FrogState;
import org.toadallyarmed.util.StateMachine;
import org.toadallyarmed.util.action.PayloadExtractor;

import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

public record BardActionPayload(
    TransformComponent pos,
    StateMachine<FrogState> stateMachine,
    ConcurrentLinkedQueue<Entity> entities,
    WalletComponent walletComponent,
    int bardIncomeDelta
) {
    public static final PayloadExtractor<BardActionPayload, BasicActionPayload> EXTRACTOR = rawPayload -> {
        var pos= rawPayload.entity().get(TransformComponent.class);
        var stateComponent = rawPayload.entity().get(StateComponent.class);
        if (stateComponent.isEmpty()) return Optional.empty();
        if (pos.isEmpty()) return Optional.empty();

        AliveEntityStateComponent<FrogState> frogESC = (AliveEntityStateComponent<FrogState>) stateComponent.get();
        return Optional.of(new BardActionPayload(
            pos.get(),
            frogESC.getGeneralStateMachine(),
            rawPayload.gameState().getEntities(),
            rawPayload.gameState().getWallet(),
            rawPayload.gameState().getGameConfig().moneyFrog().damage()
        ));
    };
}
