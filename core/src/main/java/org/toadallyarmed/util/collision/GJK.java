package org.toadallyarmed.util.collision;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class GJK {
    private static final int MAX_ITER = 20;

    public static boolean intersects(ConvexShape P, ConvexShape Q) {
        Vector2 d = new Vector2(1, 0);
        Vector2 a = support(P, Q, d);
        if (a.dot(d) <= 0) return false;

        List<Vector2> simplex = new ArrayList<>();
        simplex.add(a);
        d.set(-a.x, -a.y);

        for (int i = 0; i < MAX_ITER; i++) {
            a = support(P, Q, d);
            if (a.dot(d) <= 0) return false;
            simplex.add(a);
            if (containsOrigin(simplex, d)) return true;
        }
        return false;
    }

    private static Vector2 support(ConvexShape P, ConvexShape Q, Vector2 d) {
        Vector2 p = P.support(d);
        Vector2 negD = new Vector2(-d.x, -d.y);
        Vector2 q = Q.support(negD);
        return p.cpy().sub(q);
    }

    private static boolean containsOrigin(List<Vector2> simplex, Vector2 d) {
        Vector2 a = simplex.get(simplex.size() - 1);
        Vector2 ao = new Vector2(-a.x, -a.y); // Direction from A to origin

        if (simplex.size() == 3) {
            // Triangle case - we have 3 points
            Vector2 b = simplex.get(1);
            Vector2 c = simplex.get(0);
            
            // Edges from A
            Vector2 ab = b.cpy().sub(a);
            Vector2 ac = c.cpy().sub(a);
            
            // Compute normals to edges pointing toward the origin
            Vector2 abNormal = tripleProduct(ac, ab, ab);
            Vector2 acNormal = tripleProduct(ab, ac, ac);
            
            // Check which region contains the origin
            if (abNormal.dot(ao) > 0) {
                // Origin is in region outside AB edge
                simplex.remove(0); // Remove point C
                d.set(abNormal); // New direction is toward origin from AB edge
                return false;
            } else if (acNormal.dot(ao) > 0) {
                // Origin is in region outside AC edge
                simplex.remove(1); // Remove point B
                d.set(acNormal); // New direction is toward origin from AC edge
                return false;
            } else {
                // Origin is inside both halfspaces, thus inside triangle
                return true;
            }
        } else {
            // Line segment case - we have 2 points
            Vector2 b = simplex.get(0);
            Vector2 ab = b.cpy().sub(a);
            
            // Compute direction perpendicular to AB toward origin
            Vector2 abPerp = tripleProduct(ab, ao, ab);
            
            // If abPerp is zero, AB and AO are parallel and we need another fallback
            if (abPerp.len2() < 0.000001f) {
                abPerp.set(ab.y, -ab.x); // Simple perpendicular
                if (abPerp.dot(ao) < 0) {
                    abPerp.scl(-1);
                }
            }
            
            d.set(abPerp);
            return false;
        }
    }
    
    // Helper function: computes (A × B) × C
    private static Vector2 tripleProduct(Vector2 a, Vector2 b, Vector2 c) {
        // (A × B) × C = B(A·C) - A(B·C)
        float acDot = a.dot(c);
        float bcDot = b.dot(c);
        return new Vector2(
            b.x * acDot - a.x * bcDot,
            b.y * acDot - a.y * bcDot
        );
    }

    // Removed old implementations that are no longer used
}
