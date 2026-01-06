package org.jhotdraw.draw.figure;

import org.testng.annotations.Test;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import static org.testng.Assert.*;

public class RectangleFigureTest {

    @Test
    public void testRectangleBoundsAfterSetBounds() {
        // Arrange
        RectangleFigure rectangle = new RectangleFigure();

        Point2D.Double start = new Point2D.Double(10, 20);
        Point2D.Double end   = new Point2D.Double(110, 70);

        // Act
        rectangle.setBounds(start, end);

        // Assert
        Rectangle2D.Double bounds = rectangle.getBounds();

        assertEquals(bounds.getX(), 10.0, 0.001);
        assertEquals(bounds.getY(), 20.0, 0.001);
        assertEquals(bounds.getWidth(), 100.0, 0.001);
        assertEquals(bounds.getHeight(), 50.0, 0.001);
    }
    @Test
public void testNewRectangleHasZeroSize() {
    RectangleFigure rectangle = new RectangleFigure();

    assertEquals(rectangle.getBounds().getWidth(), 0.0, 0.001);
    assertEquals(rectangle.getBounds().getHeight(), 0.0, 0.001);
}
@Test
public void testRectangleHasMinimumSize() {
    RectangleFigure rectangle = new RectangleFigure();

    Point2D.Double start = new Point2D.Double(10, 10);
    Point2D.Double end   = new Point2D.Double(11, 11);

    rectangle.setBounds(start, end);

    Rectangle2D.Double bounds = rectangle.getBounds();

    assertTrue(bounds.getWidth() >= 5);
    assertTrue(bounds.getHeight() >= 5);
}


}
