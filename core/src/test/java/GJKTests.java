import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.Test;
import org.toadallyarmed.util.collision.ConvexShape;
import org.toadallyarmed.util.collision.GJK;
import org.toadallyarmed.util.collision.PolygonShape;
import org.toadallyarmed.util.collision.RectangleShape;

import static org.junit.Assert.*;

public class GJKTests {

    private ConvexShape squareA;
    private ConvexShape squareB;
    private ConvexShape squareC;
    private ConvexShape triangle;
    private ConvexShape rectangle;
    
    // Additional shapes for complex tests
    private ConvexShape pentagon;
    private ConvexShape hexagon;
    private ConvexShape diamond;
    private ConvexShape rotatedRectangle;
    private ConvexShape skinnyTriangle;

    @Before
    public void setUp() {
        // Square of size 2×2 at origin
        squareA = new RectangleShape(2f, 2f).shiftedBy(new Vector2(0f, 0f));
        // Square of size 2×2 shifted right by 1 (overlaps squareA)
        squareB = new RectangleShape(2f, 2f).shiftedBy(new Vector2(1f, 0f));
        // Square of size 2×2 shifted right by 3 (no overlap with squareA)
        squareC = new RectangleShape(2f, 2f).shiftedBy(new Vector2(3f, 0f));
        // Right triangle with points (0,0), (1,0), (0,-1)
        Vector2[] vertices = new Vector2[]{
            new Vector2(0f, 0f),
            new Vector2(1f, 0f),
            new Vector2(0f, -1f),
        };

        triangle = new PolygonShape(vertices).shiftedBy(new Vector2(0.5f, -0.5f));
        // Rectangle of size 3×1 shifted to overlap triangle
        rectangle = new RectangleShape(3f, 1f).shiftedBy(new Vector2(-1f, -1f));
        
        // Create a pentagon
        Vector2[] pentagonVertices = new Vector2[] {
            new Vector2(0f, 0f),
            new Vector2(1f, 0.5f),
            new Vector2(0.5f, 1.5f),
            new Vector2(-0.5f, 1.0f),
            new Vector2(-0.5f, 0.25f)
        };
        pentagon = new PolygonShape(pentagonVertices).shiftedBy(new Vector2(5f, -1f));
        
        // Create a hexagon
        Vector2[] hexagonVertices = new Vector2[] {
            new Vector2(0f, 0f),
            new Vector2(1f, 0f),
            new Vector2(1.5f, 0.866f), // 30 degrees up
            new Vector2(1f, 1.732f),   // 60 degrees up
            new Vector2(0f, 1.732f),   // 60 degrees up
            new Vector2(-0.5f, 0.866f) // 30 degrees up
        };
        hexagon = new PolygonShape(hexagonVertices).shiftedBy(new Vector2(-3f, -3f));
        
        // Create a diamond
        Vector2[] diamondVertices = new Vector2[] {
            new Vector2(0f, 0f),
            new Vector2(1f, 1f),
            new Vector2(0f, 2f),
            new Vector2(-1f, 1f)
        };
        diamond = new PolygonShape(diamondVertices).shiftedBy(new Vector2(2.5f, -3.5f));
        
        // Create a rotated rectangle (45 degrees)
        Vector2[] rotatedRectVertices = new Vector2[] {
            new Vector2(0f, 0f),
            new Vector2(1f, 1f),
            new Vector2(0f, 2f),
            new Vector2(-1f, 1f)
        };
        rotatedRectangle = new PolygonShape(rotatedRectVertices).shiftedBy(new Vector2(-2f, 2f));
        
        // Create a skinny triangle (for edge cases)
        Vector2[] skinnyTriangleVertices = new Vector2[] {
            new Vector2(0f, 0f),
            new Vector2(0.1f, 3f),
            new Vector2(-0.1f, 3f)
        };
        skinnyTriangle = new PolygonShape(skinnyTriangleVertices).shiftedBy(new Vector2(-4f, 0f));
    }

    @Test
    public void testOverlappingSquares() {
        assertTrue("Squares A and B should intersect",
            GJK.intersects(squareA, squareB));
    }

    @Test
    public void testNonOverlappingSquares() {
        assertFalse("Squares A and C should not intersect",
            GJK.intersects(squareA, squareC));
    }

    @Test
    public void testSquareAndTriangleOverlap() {
        // triangle centered at (0.5, -0.5), squareA from (0,0) to (2,-2)
        assertTrue("Square A and triangle should intersect",
            GJK.intersects(squareA, triangle));
    }

    @Test
    public void testTriangleAndRectangleOverlap() {
        assertTrue("Triangle and rectangle should intersect",
            GJK.intersects(triangle, rectangle));
    }

    @Test
    public void testRectangleAndSquareNonOverlap() {
        // rectangle at (-1,-1) to (2,-2), squareC starts at (3,0) to (5,-2)
        assertFalse("Rectangle and square C should not intersect",
            GJK.intersects(rectangle, squareC));
    }
    
    // Additional complex tests
    
    @Test
    public void testPentagonAndHexagonNoOverlap() {
        assertFalse("Pentagon and hexagon should not intersect",
            GJK.intersects(pentagon, hexagon));
    }
    
    @Test
    public void testDiamondAndPentagonNoOverlap() {
        assertFalse("Diamond and pentagon should not intersect",
            GJK.intersects(diamond, pentagon));
    }
    
    @Test
    public void testDiamondAndRotatedRectangleSame() {
        // These are the same shape, just at different positions
        assertFalse("Diamond and rotated rectangle should not intersect",
            GJK.intersects(diamond, rotatedRectangle));
    }
    
    @Test
    public void testOverlappingComplexShapes() {
        // Create a shifted copy of the pentagon that overlaps with the diamond
        ConvexShape shiftedPentagon = pentagon.shiftedBy(new Vector2(-2.5f, -2.5f));
        assertTrue("Shifted pentagon and diamond should intersect",
            GJK.intersects(shiftedPentagon, diamond));
    }
    
    @Test
    public void testSkinnyTriangleAndSquareNoOverlap() {
        assertFalse("Skinny triangle and square A should not intersect",
            GJK.intersects(skinnyTriangle, squareA));
    }
    
    @Test
    public void testSkinnyTriangleAndSquareOverlap() {
        // Create a shifted square that overlaps with the skinny triangle
        ConvexShape shiftedSquare = squareA.shiftedBy(new Vector2(-4f, 1f));
        assertTrue("Shifted square and skinny triangle should intersect",
            GJK.intersects(shiftedSquare, skinnyTriangle));
    }
    
    @Test
    public void testAlmostTouchingShapes() {
        // Create shapes that are extremely close but not touching
        ConvexShape almostTouchingSquare = squareA.shiftedBy(new Vector2(2.001f, 0f));
        assertFalse("Almost touching squares should not intersect",
            GJK.intersects(squareA, almostTouchingSquare));
    }
    
    @Test
    public void testEdgeToEdgeTouch() {
        // Create a square that perfectly touches squareA on the edge (edge case)
        ConvexShape edgeTouchingSquare = squareA.shiftedBy(new Vector2(2.0f, 0f));
        // Note: In theory this should be false, but floating-point precision might cause issues
        assertFalse("Edge-to-edge touching squares should not intersect",
            GJK.intersects(squareA, edgeTouchingSquare));
    }
    
    @Test
    public void testCompletelyContainedShape() {
        // Create a small square inside squareA
        ConvexShape smallSquare = new RectangleShape(0.5f, 0.5f).shiftedBy(new Vector2(0.75f, -0.75f));
        assertTrue("Small square should be contained in squareA",
            GJK.intersects(squareA, smallSquare));
    }
}
