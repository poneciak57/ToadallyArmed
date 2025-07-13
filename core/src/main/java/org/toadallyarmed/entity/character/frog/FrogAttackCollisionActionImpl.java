package org.toadallyarmed.entity.character.frog;

import com.badlogic.gdx.math.Vector2;
import org.toadallyarmed.base.transform.TransformComponent;
import org.toadallyarmed.base.entity.Entity;
import org.toadallyarmed.util.state.StateMachine;
import org.toadallyarmed.util.action.Action;
import org.toadallyarmed.util.log.Logger;

import java.util.Collection;
import java.util.function.Function;

record FrogAttackCollisionActionPayload(
    Vector2 pos,
    StateMachine<FrogState> stateMachine,
    Collection<Entity> entities
) {
}

class FrogAttackCollisionActionImpl implements Action<FrogAttackCollisionActionPayload> {
    private final Function<Vector2, Entity> bulletProduce;

    public FrogAttackCollisionActionImpl(Function<Vector2, Entity> bulletProducer) {
        this.bulletProduce = bulletProducer;
    }

    @Override
    public void run(FrogAttackCollisionActionPayload payload) {
        payload.stateMachine().setNextTmpState(FrogState.IDLE, FrogState.ACTION, () -> {
            var bullet = bulletProduce.apply(payload.pos());
            var pos = bullet.get(TransformComponent.class);
            float currentNano = java.lang.System.nanoTime();
            /// We need to shift spawn origin of bullet to make it spawn in fron of a frog for better UI
            if (pos.isEmpty()) Logger.error("Bullet has no position at FrogAttackCollisionActionPerformer");
            else pos.get().setPosition(pos.get().getAdvancedPosition(currentNano), currentNano);

            payload.entities().add(bullet);
        });
    }
}
