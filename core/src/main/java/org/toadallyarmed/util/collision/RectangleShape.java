package org.toadallyarmed.util.collision;

import com.badlogic.gdx.math.Vector2;

public class RectangleShape implements ConvexShape {
    private final PolygonShape shape;

    /// Creates rectangle with given properties
    public RectangleShape(float width, float height) {
        final Vector2[] vertices = new Vector2[4];
        vertices[0] = new Vector2(0f, 0f);
        vertices[1] = new Vector2(width, 0f);
        vertices[2] = new Vector2(width, -height);
        vertices[3] = new Vector2(0f, -height);
        shape = new PolygonShape(vertices);
    }

    private RectangleShape(PolygonShape shape) {
        this.shape = shape;
    }

    @Override
    public Vector2 support(Vector2 dir) {
        return shape.support(dir);
    }

    @Override
    public RectangleShape shiftedBy(Vector2 position) {
        return new RectangleShape(shape.shiftedBy(position));
    }
}
