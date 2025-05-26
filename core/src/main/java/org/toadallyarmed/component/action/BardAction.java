package org.toadallyarmed.component.action;

import com.badlogic.gdx.math.Vector2;
import org.toadallyarmed.component.action.payload.BardActionPayload;
import org.toadallyarmed.component.action.payload.BasicActionPayload;
import org.toadallyarmed.factory.CoinFactory;
import org.toadallyarmed.state.FrogState;
import org.toadallyarmed.util.action.Action;
import org.toadallyarmed.util.action.PayloadExtractor;

public class BardAction implements Action<BardActionPayload, BasicActionPayload> {
    boolean firstTime = true;
    @Override
    public void run(BardActionPayload payload) {
        if (!firstTime){
            payload.stateMachine().setNextTmpState(FrogState.IDLE, FrogState.HOP, () -> {
                var coin= CoinFactory.get().createCoin(payload.pos().getPosition());
                payload.walletComponent().access().addAndGet(payload.bardIncomeDelta());//upload money
                payload.entities().add(coin);
            });
        }
        firstTime = false;
    }

    @Override
    public PayloadExtractor<BardActionPayload, BasicActionPayload> extractor() {
        return BardActionPayload.EXTRACTOR;
    }
}
