package org.toadallyarmed.component;

import com.badlogic.gdx.math.Vector2;
import org.toadallyarmed.component.interfaces.TransformComponent;

public class ScreenTransformComponent implements TransformComponent {
    Vector2 position;
    Vector2 velocity;

    @Override
    public Vector2 getScreenPosition() {
        return new Vector2(position);
    }

    @Override
    public Vector2 getVelocity() {
        return new Vector2(velocity);
    }

    @Override
    public Vector2 getAdvancedScreenPosition(float deltaTime) {
        return getScreenPosition().add(getVelocity().scl(deltaTime));
    }

    @Override
    public void setPositionFromScreen(Vector2 position) {
        this.position.set(position);
    }

    @Override
    public void setVelocity(Vector2 velocity) {
        this.velocity.set(velocity);
    }
}
