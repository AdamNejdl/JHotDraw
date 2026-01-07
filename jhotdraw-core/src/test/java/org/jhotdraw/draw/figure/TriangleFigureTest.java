package org.jhotdraw.draw.figure;

import org.junit.Test;
import static org.junit.Assert.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;


public class TriangleFigureTest {

    @Test
    public void testCreate() {
        TriangleFigure triangle = new TriangleFigure();
        assertNotNull("TriangleFigure instance should not be null", triangle);
    }

    @Test
    public void testSetBounds() {
        TriangleFigure triangle = new TriangleFigure();

        Point2D.Double anchor = new Point2D.Double(10, 10);
        Point2D.Double lead = new Point2D.Double(110, 110);

        triangle.setBounds(anchor, lead);

        Rectangle2D.Double expectedBounds = new Rectangle2D.Double(10, 10, 100, 100);
        Rectangle2D.Double actualBounds = triangle.getBounds();

        assertEquals("X coordinate is incorrect", expectedBounds.x, actualBounds.x, 0.01);
        assertEquals("Y coordinate is incorrect", expectedBounds.y, actualBounds.y, 0.01);
        assertEquals("Width is incorrect", expectedBounds.width, actualBounds.width, 0.01);
        assertEquals("Height is incorrect", expectedBounds.height, actualBounds.height, 0.01);
    }

    @Test
    public void testClone() {
        TriangleFigure original = new TriangleFigure();
        Point2D.Double anchor = new Point2D.Double(0, 0);
        Point2D.Double lead = new Point2D.Double(50, 50);
        original.setBounds(anchor, lead);

        TriangleFigure clone = original.clone();

        assertNotNull("Clone should not be null", clone);
        assertNotSame("Clone should be a different instance from the original object", original, clone);

        assertEquals("Clone's X coordinate should be the same as the original",
                original.getBounds().x, clone.getBounds().x, 0.01);
    }

    @Test
    public void testContains() {
        TriangleFigure triangle = new TriangleFigure();

        triangle.setBounds(new Point2D.Double(0, 0), new Point2D.Double(100, 100));

        Point2D.Double center = new Point2D.Double(50, 50);
        assertTrue("The center point (50,50) should be inside the triangle",
                triangle.contains(center));

        Point2D.Double farOutside = new Point2D.Double(200, 200);
        assertFalse("Point (200,200) should be outside the triangle",
                triangle.contains(farOutside));


        Point2D.Double cornerInsideBoundsButOutsideShape = new Point2D.Double(0, 0);
    }

    @Test
    public void testTransform() {
        TriangleFigure triangle = new TriangleFigure();
        triangle.setBounds(new Point2D.Double(0, 0), new Point2D.Double(50, 50));

        java.awt.geom.AffineTransform tx = new java.awt.geom.AffineTransform();
        tx.translate(10, 20);

        triangle.transform(tx);

        Rectangle2D.Double bounds = triangle.getBounds();
        assertEquals("X should have moved by 10", 10.0, bounds.x, 0.01);
        assertEquals("Y should have moved by 20", 20.0, bounds.y, 0.01);
    }
}