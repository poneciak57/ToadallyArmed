package org.toadallyarmed.entity.character.hedgehog;

import com.badlogic.gdx.math.Vector2;
import org.toadallyarmed.base.transform.TransformComponent;
import org.toadallyarmed.util.state.StateMachine;
import org.toadallyarmed.util.action.Action;

record HedgehogAttackResetActionPayload(
    HeadgehogAttackTimerComponent timer,
    StateMachine<HedgehogState> stateMachine,
    TransformComponent transformComponent
) { }

class HedgehogAttackResetActionImpl implements Action<HedgehogAttackResetActionPayload> {
    private final long threshold;
    private final Vector2 velocity;
    private boolean handled = true;

    public HedgehogAttackResetActionImpl(long threshold, Vector2 velocity) {
        this.threshold = threshold;
        this.velocity = velocity;
    }

    @Override
    public void run(HedgehogAttackResetActionPayload payload) {
        float currentNanoTime = System.nanoTime();
        if (payload.timer().lastActionNano() + threshold <= currentNanoTime) {
            if (handled) return;
            handled = true;
            payload.stateMachine().setNextTmpState(payload.stateMachine().getCurState(), HedgehogState.WALKING, () -> {
                payload.transformComponent().setVelocity(velocity, java.lang.System.nanoTime());
            });
        } else if (handled) handled = false;
    }
}
