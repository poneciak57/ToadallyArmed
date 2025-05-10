package org.toadallyarmed.component;

import com.badlogic.gdx.math.Vector2;
import org.toadallyarmed.component.interfaces.ColliderComponent;
import org.toadallyarmed.util.collision.ConvexShape;
import org.toadallyarmed.util.collision.RectangleShape;

public class BoxColliderComponent implements ColliderComponent {
    private final RectangleShape shape;

    public BoxColliderComponent(RectangleShape shape) {
        this.shape = shape;
    }

    @Override
    public ConvexShape getConvexShape(Vector2 position) {
        return shape.shiftedBy(position);
    }
}
