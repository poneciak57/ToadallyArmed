package org.toadallyarmed.util.collision;

import com.badlogic.gdx.math.Vector2;

/// A convex polygon defined by its vertices in local space,
/// with origin at the top-left of its bounding box.
/// Provides shifted copies for world-space positioning.
public class PolygonShape implements ConvexShape {
    private final Vector2[] vertices;


     /// @param localVerts array of vertices in local space, ordered (e.g.) counter-clockwise, relative to top-left origin (0,0)
    public PolygonShape(Vector2[] localVerts) {
        if (localVerts == null || localVerts.length < 3) {
            throw new IllegalArgumentException("Need at least 3 vertices");
        }
        // Copy to avoid external mutation
        this.vertices = new Vector2[localVerts.length];
        for (int i = 0; i < localVerts.length; i++) {
            this.vertices[i] = localVerts[i].cpy();
        }
    }

    private PolygonShape(Vector2[] shiftedVerts, boolean internal) {
        // private ctor expects already-copied array
        this.vertices = shiftedVerts;
    }

    @Override
    public Vector2 support(Vector2 dir) {
        Vector2 best = vertices[0];
        float bestDot = best.dot(dir);
        for (int i = 1; i < vertices.length; i++) {
            float d = vertices[i].dot(dir);
            if (d > bestDot) {
                bestDot = d;
                best = vertices[i];
            }
        }
        return best.cpy();
    }

    /**
     * Returns a new PolygonShape whose vertices are all shifted
     * by the given world-space offset. Does not modify this instance.
     */
    @Override
    public PolygonShape shiftedBy(Vector2 position) {
        Vector2[] shifted = new Vector2[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            shifted[i] = new Vector2(
                vertices[i].x + position.x,
                vertices[i].y + position.y
            );
        }
        return new PolygonShape(shifted, true);
    }
}
