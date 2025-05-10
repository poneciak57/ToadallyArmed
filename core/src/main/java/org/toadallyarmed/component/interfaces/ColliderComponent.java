package org.toadallyarmed.component.interfaces;

import com.badlogic.gdx.math.Vector2;
import org.toadallyarmed.util.collision.ConvexShape;

public interface ColliderComponent extends Component {
    /// Should return convex with origin at position
    /// @param position position of the origin
    ConvexShape getConvexShape(Vector2 position);
}
