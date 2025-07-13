package org.toadallyarmed.component.action;

import com.badlogic.gdx.math.Vector2;
import org.toadallyarmed.component.HeadgehogAttackTimerComponent;
import org.toadallyarmed.component.HealthComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.state.BooleanState;
import org.toadallyarmed.state.HedgehogState;
import org.toadallyarmed.util.StateMachine;
import org.toadallyarmed.util.action.Action;

record HedgehogAttackCollisionActionPayload(
    StateMachine<BooleanState> frogIsAttackedStateMachine,
    HealthComponent frogHealthComponent,
    HeadgehogAttackTimerComponent headgehogAttackTimerComponent,
    TransformComponent transformComponent,
    StateMachine<HedgehogState> stateMachine,
    float currentNanoTime
) { }

class HedgehogAttackCollisionActionImpl implements Action<HedgehogAttackCollisionActionPayload> {
    private final int damage;

    public HedgehogAttackCollisionActionImpl(int damage) {
        this.damage = damage;
    }

    @Override
    public void run(HedgehogAttackCollisionActionPayload payload) {
        // // Nie dziala :c
        // if (payload.frogHealthComponent().getHealth() <= 0) {
        //     Logger.error("Attacking dead frog");
        //     return;
        // }
        payload.headgehogAttackTimerComponent().updateLastActionNano();
        payload.frogIsAttackedStateMachine().setNextTmpState(BooleanState.TRUE);
        payload.stateMachine().setNextTmpState(HedgehogState.ACTION);
        payload.frogHealthComponent().removeHealth(damage);
        payload.transformComponent().setPosition(
            payload.transformComponent().getAdvancedPosition(payload.currentNanoTime()),
            payload.currentNanoTime());
        payload.transformComponent().setVelocity(new Vector2(0f, 0f), payload.currentNanoTime());
    }
}
