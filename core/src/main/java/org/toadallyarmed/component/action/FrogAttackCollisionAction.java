package org.toadallyarmed.component.action;

import com.badlogic.gdx.math.Vector2;
import org.toadallyarmed.component.action.payload.BasicCollisionActionPayload;
import org.toadallyarmed.component.action.payload.FrogAttackCollisionActionPayload;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.util.action.Action;
import org.toadallyarmed.util.action.PayloadExtractor;

import java.util.function.Function;

public class FrogAttackCollisionAction implements Action<FrogAttackCollisionActionPayload, BasicCollisionActionPayload> {

    private final Function<Vector2, Entity> bulletProduce;

    public FrogAttackCollisionAction(Function<Vector2, Entity> bulletProducer) {
        this.bulletProduce = bulletProducer;
    }

    @Override
    public void run(FrogAttackCollisionActionPayload payload) {
        payload.entities().add(
            bulletProduce.apply(payload.pos())
        );
    }

    @Override
    public PayloadExtractor<FrogAttackCollisionActionPayload, BasicCollisionActionPayload> extractor() {
        return FrogAttackCollisionActionPayload.EXTRACTOR;
    }
}
