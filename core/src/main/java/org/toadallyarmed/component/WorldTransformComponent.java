package org.toadallyarmed.component;

import com.badlogic.gdx.math.Vector2;
import org.toadallyarmed.component.interfaces.TransformComponent;

import java.util.concurrent.atomic.AtomicReference;

import static org.toadallyarmed.config.GameConfig.VELOCITY_SCALE;

public class WorldTransformComponent implements TransformComponent {
    public record WorldTransformPayload(
        Vector2 position,
        Vector2 velocity,
        float lastUpdateTime
    ){}
    final AtomicReference<WorldTransformPayload> payload = new AtomicReference<>(new WorldTransformPayload(
        new Vector2(),
        new Vector2(),
        0.f
    ));

    public WorldTransformComponent(Vector2 position, Vector2 velocity) {
        this.payload.set(new WorldTransformPayload(position, velocity, 0.0f));
    }

    public WorldTransformComponent(WorldTransformPayload payload) {
        this.payload.set(payload);
    }

    @Deprecated
    @Override
    public Vector2 getPosition() {
        return payload.get().position.cpy();
    }

    /// @param currentNanoTime if 0 means that value has not been yet updated and getAdvancedPosition should work like getPosition
    @Override
    public void setPosition(Vector2 position, float currentNanoTime) {
        var oldPayload = payload.get();
        payload.set(new WorldTransformPayload(
            position.cpy(),
            oldPayload.velocity.cpy(),
            currentNanoTime
        ));
    }

    public Vector2 getVelocity() {
        return this.payload.get().velocity.cpy();
    }

    @Override
    public Vector2 getAdvancedPosition(float currentNanoTime) {
        var oldPayload = payload.get();
        if (oldPayload.lastUpdateTime == 0) return oldPayload.position.cpy();
        float deltaTime = (currentNanoTime - oldPayload.lastUpdateTime) / 1_000_000_000f;
        return oldPayload.position().cpy().add(oldPayload.velocity().cpy().scl(deltaTime * VELOCITY_SCALE));
    }
}
