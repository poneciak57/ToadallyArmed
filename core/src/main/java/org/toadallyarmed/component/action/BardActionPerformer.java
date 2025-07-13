package org.toadallyarmed.component.action;

import org.toadallyarmed.component.AliveEntityStateComponent;
import org.toadallyarmed.component.action.payload.BasicActionPayload;
import org.toadallyarmed.component.interfaces.StateComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.state.FrogState;
import org.toadallyarmed.util.action.ActionAdapter;
import org.toadallyarmed.util.action.PayloadExtractor;

import java.util.Optional;

public class BardActionPerformer extends ActionAdapter<BardActionPayload, BasicActionPayload> {
    static final PayloadExtractor<BardActionPayload, BasicActionPayload>
        EXTRACTOR = rawPayload -> {
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
            rawPayload.gameState().getGameConfig().bardFrog().damage()
        ));
    };

    public BardActionPerformer() {
        super(new BardActionImpl(), EXTRACTOR);
    }
}
