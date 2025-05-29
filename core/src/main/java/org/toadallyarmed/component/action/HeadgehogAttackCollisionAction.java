package org.toadallyarmed.component.action;

import com.badlogic.gdx.math.Vector2;
import org.toadallyarmed.component.action.payload.BasicCollisionActionPayload;
import org.toadallyarmed.component.action.payload.HeadgehogAttackCollisionActionPayload;
import org.toadallyarmed.state.BooleanState;
import org.toadallyarmed.state.HedgehogState;
import org.toadallyarmed.util.action.Action;
import org.toadallyarmed.util.action.PayloadExtractor;

public class HeadgehogAttackCollisionAction implements Action<HeadgehogAttackCollisionActionPayload, BasicCollisionActionPayload> {

    private final int damage;

    public HeadgehogAttackCollisionAction(int damage) {
        this.damage = damage;
    }

    @Override
    public void run(HeadgehogAttackCollisionActionPayload payload) {
        payload.headgehogAttackTimerComponent().updateLastActionNano();
        payload.frogIsAttackedStateMachine().setNextTmpState(BooleanState.TRUE);
        payload.stateMachine().setNextTmpState(HedgehogState.ACTION);
        payload.frogHealthComponent().removeHealth(damage);
        float curNano = java.lang.System.nanoTime();
        payload.transformComponent().setPosition(payload.transformComponent().getAdvancedPosition(curNano), curNano);
        payload.transformComponent().setVelocity(new Vector2(0f, 0f), curNano);
    }

    @Override
    public PayloadExtractor<HeadgehogAttackCollisionActionPayload, BasicCollisionActionPayload> extractor() {
        return HeadgehogAttackCollisionActionPayload.EXTRACTOR;
    }
}
