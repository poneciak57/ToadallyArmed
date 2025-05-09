package org.toadallyarmed.component;

import com.badlogic.gdx.math.Vector2;
import org.toadallyarmed.component.interfaces.TransformComponent;

public class WorldTransformComponent implements TransformComponent {
    Vector2 position = new Vector2();
    Vector2 velocity = new Vector2();

    @Override
    public Vector2 getPosition() {
        return new Vector2(position);
    }

    @Override
    public void setPosition(Vector2 position) {
        this.position.set(position);
    }

    public Vector2 getVelocity() {
        return new Vector2(velocity);
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity.set(velocity);
    }

    public Vector2 getAdvancedPosition(float deltaTime) {
        return getPosition().add(getVelocity().scl(deltaTime));
    }
}
