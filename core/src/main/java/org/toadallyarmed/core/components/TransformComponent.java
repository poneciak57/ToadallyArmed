package org.toadallyarmed.core.components;

import com.badlogic.gdx.math.Vector2;

public class TransformComponent extends Component {
    Vector2 position;
    Vector2 velocity;

    public Vector2 getPosition() {
        return new Vector2(position);
    }

    public Vector2 getVelocity() {
        return new Vector2(velocity);
    }

    public Vector2 getAdvancedPosition(float deltaTime) {
        return getPosition().add(getVelocity().scl(deltaTime));
    }

    public void setPosition(Vector2 position) {
        this.position.set(position);
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity.set(velocity);
    }
}
