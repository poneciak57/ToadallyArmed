package org.toadallyarmed.component.interfaces;

import com.badlogic.gdx.math.Vector2;
import org.toadallyarmed.util.collision.ConvexShape;

public interface ColliderComponent extends Component {
    ConvexShape getConvexShape(Vector2 position);
}
