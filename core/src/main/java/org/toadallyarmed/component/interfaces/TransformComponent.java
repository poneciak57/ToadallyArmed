package org.toadallyarmed.component.interfaces;

import com.badlogic.gdx.math.Vector2;

public interface TransformComponent extends Component {
    Vector2 getScreenPosition();

    Vector2 getVelocity();

    Vector2 getAdvancedScreenPosition(float deltaTime);

    void setPositionFromScreen(Vector2 position);

    void setVelocity(Vector2 velocity);
}
