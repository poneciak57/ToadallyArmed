package org.toadallyarmed.component.action;

import com.badlogic.gdx.math.Vector2;
import org.toadallyarmed.component.action.payload.BasicCollisionActionPayload;
import org.toadallyarmed.component.action.payload.OnCollisionShootActionPayload;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.util.action.Action;
import org.toadallyarmed.util.action.PayloadExtractor;

import java.util.function.Function;

public class OnCollisionShootAction implements Action<OnCollisionShootActionPayload, BasicCollisionActionPayload> {

    private final Function<Vector2, Entity> bulletProduce;

    public OnCollisionShootAction(Function<Vector2, Entity> bulletProducer) {
        this.bulletProduce = bulletProducer;
    }

    @Override
    public void run(OnCollisionShootActionPayload payload) {
        payload.entities().add(
            bulletProduce.apply(payload.pos())
        );
    }

    @Override
    public PayloadExtractor<OnCollisionShootActionPayload, BasicCollisionActionPayload> extractor() {
        return OnCollisionShootActionPayload.EXTRACTOR;
    }
}
