package org.toadallyarmed.component.action;

import com.badlogic.gdx.math.Vector2;
import org.toadallyarmed.component.action.payload.BasicActionPayload;
import org.toadallyarmed.component.action.payload.HeadgehogAttackResetActionPayload;
import org.toadallyarmed.state.HedgehogState;
import org.toadallyarmed.util.action.Action;
import org.toadallyarmed.util.action.PayloadExtractor;

public class HeadgehogAttackResetAction implements Action<HeadgehogAttackResetActionPayload, BasicActionPayload> {

    private final long threshold;
    private final Vector2 velocity;
    private boolean handled = true;

    public HeadgehogAttackResetAction(long threshold, Vector2 velocity) {
        this.threshold = threshold;
        this.velocity = velocity;
    }

    @Override
    public void run(HeadgehogAttackResetActionPayload payload) {
        float currentNanoTime = System.nanoTime();
        if (payload.timer().lastActionNano() + threshold <= currentNanoTime) {
            if (handled) return;
            handled = true;
            payload.stateMachine().setNextTmpState(payload.stateMachine().getCurState(), HedgehogState.WALKING, () -> {
                payload.transformComponent().setVelocity(velocity, java.lang.System.nanoTime());
            });
        } else if (handled) handled = false;
    }

    @Override
    public PayloadExtractor<HeadgehogAttackResetActionPayload, BasicActionPayload> extractor() {
        return HeadgehogAttackResetActionPayload.EXTRACTOR;
    }
}
