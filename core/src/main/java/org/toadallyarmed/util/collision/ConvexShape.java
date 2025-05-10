package org.toadallyarmed.util.collision;

import com.badlogic.gdx.math.Vector2;

public interface ConvexShape {
    /// @param dir search direction in world space;
    /// @return furthest point on shape along dir
    Vector2 support(Vector2 dir);

    /// Returns a new ConvexShape whose vertices are shifted by the
    /// specified world position. Does not modify the original shape.
    /// @param position world-space offset to apply to each vertex
    /// @return a new ConvexShape instance with shifted vertices
    ConvexShape shiftedBy(Vector2 position);
}
