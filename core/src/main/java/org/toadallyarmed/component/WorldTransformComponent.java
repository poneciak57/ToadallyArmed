package org.toadallyarmed.component;

import com.badlogic.gdx.math.Vector2;
import org.toadallyarmed.component.interfaces.TransformComponent;

import java.time.Instant;

public class WorldTransformComponent implements TransformComponent {
    public record WorldTransformPayload(
        Vector2 position,
        Vector2 velocity,
        float lastUpdateTime
    ){}
    WorldTransformPayload payload = new WorldTransformPayload(
        new Vector2(),
        new Vector2(),
        0.f
    );

    @Deprecated
    @Override
    public Vector2 getPosition() {
        return payload.position.cpy();
    }

    /// @param currentTimestamp if 0 means that value has not been yet updated and getAdvancedPosition should work like getPosition
    @Override
    public void setPosition(Vector2 position, float currentTimestamp) {
        var oldPayload = payload;
        payload = new WorldTransformPayload(
            position.cpy(),
            oldPayload.velocity.cpy(),
            currentTimestamp
        );
    }

    public Vector2 getVelocity() {
        return this.payload.velocity.cpy();
    }

    @Override
    public Vector2 getAdvancedPosition(float currentTimestamp) {
        var oldPayload = payload;
        if (oldPayload.lastUpdateTime == 0) return oldPayload.position.cpy();
        float deltaTime = currentTimestamp - oldPayload.lastUpdateTime;
        return oldPayload.position().add(oldPayload.velocity().scl(deltaTime));
    }
}
