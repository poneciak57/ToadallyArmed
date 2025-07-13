package org.toadallyarmed.base.transform;

import com.badlogic.gdx.math.Vector2;
import org.toadallyarmed.base.component.Component;

public interface TransformComponent extends Component {
    /// Should not be used
    /// we should use possibly outdated position
    @Deprecated
    Vector2 getPosition();

    void setPosition(Vector2 position, float currentNanoTime);
    void setVelocity(Vector2 velocity, float currentNanoTime);

    Vector2 getAdvancedPosition(float currentNanoTime);
}
