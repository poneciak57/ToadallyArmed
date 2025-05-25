package org.toadallyarmed.component.action;

import com.badlogic.gdx.math.Vector2;
import org.toadallyarmed.component.action.payload.BasicCollisionActionPayload;
import org.toadallyarmed.component.action.payload.FrogAttackCollisionActionPayload;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.state.FrogState;
import org.toadallyarmed.util.action.Action;
import org.toadallyarmed.util.action.PayloadExtractor;
import org.toadallyarmed.util.logger.Logger;

import java.util.function.Function;

public class FrogAttackCollisionAction implements Action<FrogAttackCollisionActionPayload, BasicCollisionActionPayload> {

    private final Function<Vector2, Entity> bulletProduce;

    public FrogAttackCollisionAction(Function<Vector2, Entity> bulletProducer) {
        this.bulletProduce = bulletProducer;
    }

    @Override
    public void run(FrogAttackCollisionActionPayload payload) {
        payload.stateMachine().setNextTmpState(FrogState.IDLE, FrogState.ACTION, () -> {
            var bullet = bulletProduce.apply(payload.pos());
            var pos = bullet.get(TransformComponent.class);

            /// We need to shift spawn origin of bullet to make it spawn in fron of a frog for better UI
            if (pos.isEmpty()) Logger.error("Bullet has no position at FrogAttackCollisionAction");
            else pos.get().setPosition(pos.get().getAdvancedPosition(payload.currentNano()), payload.currentNano());

            payload.entities().add(
                bulletProduce.apply(payload.pos())
            );
        });
    }

    @Override
    public PayloadExtractor<FrogAttackCollisionActionPayload, BasicCollisionActionPayload> extractor() {
        return FrogAttackCollisionActionPayload.EXTRACTOR;
    }
}
