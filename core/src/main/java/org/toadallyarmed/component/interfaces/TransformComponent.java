package org.toadallyarmed.component.interfaces;

import com.badlogic.gdx.math.Vector2;

public interface TransformComponent extends Component {
    Vector2 getPosition();

    void setPosition(Vector2 position);
}
