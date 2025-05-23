package org.toadallyarmed.component.interfaces;

import com.badlogic.gdx.math.Vector2;

public interface TransformComponent extends Component {
    /// Should not be used
    /// we should use possibly outdated position
    @Deprecated
    Vector2 getPosition();

    void setPosition(Vector2 position, float currentNanoTime);

    Vector2 getAdvancedPosition(float currentNanoTime);
}
