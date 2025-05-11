package org.toadallyarmed.component;

import com.badlogic.gdx.math.Vector2;
import org.toadallyarmed.component.interfaces.TransformComponent;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;

public class WorldTransformComponent implements TransformComponent {
    public record WorldTransformPayload(
        Vector2 position,
        Vector2 velocity,
        float lastUpdateTime
    ){}
    AtomicReference<WorldTransformPayload> payload = new AtomicReference<>(new WorldTransformPayload(
        new Vector2(),
        new Vector2(),
        0.f
    ));

    @Deprecated
    @Override
    public Vector2 getPosition() {
        return payload.get().position.cpy();
    }

    /// @param currentTimestamp if 0 means that value has not been yet updated and getAdvancedPosition should work like getPosition
    @Override
    public void setPosition(Vector2 position, float currentTimestamp) {
        var oldPayload = payload.get();
        payload.set(new WorldTransformPayload(
            position.cpy(),
            oldPayload.velocity.cpy(),
            currentTimestamp
        ));
    }

    public Vector2 getVelocity() {
        return this.payload.get().velocity.cpy();
    }

    @Override
    public Vector2 getAdvancedPosition(float currentTimestamp) {
        var oldPayload = payload.get();
        if (oldPayload.lastUpdateTime == 0) return oldPayload.position.cpy();
        float deltaTime = currentTimestamp - oldPayload.lastUpdateTime;
        return oldPayload.position().add(oldPayload.velocity().scl(deltaTime));
    }
}
