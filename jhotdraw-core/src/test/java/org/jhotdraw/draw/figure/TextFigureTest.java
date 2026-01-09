package org.jhotdraw.draw.figure;

import org.junit.Test;
import static org.junit.Assert.*;

public class TextFigureTest {
    @Test
    public void testSetAndGetText_BestCase() {
        TextFigure figure = new TextFigure();
        figure.setText("Hello World");

        assertEquals("Hello World", figure.getText());
    }

    @Test
    public void testSetEmptyText() {
        TextFigure figure = new TextFigure();
        figure.setText("");

        assertEquals("", figure.getText());
    }

    @Test
    public void testSetNullText() {
        TextFigure figure = new TextFigure();
        figure.setText(null);

        assertNull(figure.getText());
    }

    @Test
    public void testEditableFlag() {
        TextFigure figure = new TextFigure();

        figure.setEditable(false);
        assertFalse(figure.isEditable());

        figure.setEditable(true);
        assertTrue(figure.isEditable());
    }

    @Test
    public void testFontSizeChange() {
        TextFigure figure = new TextFigure();

        figure.setFontSize(18f);
        assertEquals(18f, figure.getFontSize(), 0.001);
    }

    @Test
    public void testTextColumnsConstant() {
        TextFigure figure = new TextFigure();
        assertEquals(4, figure.getTextColumns());
    }

    @Test
    public void testTabSizeConstant() {
        TextFigure figure = new TextFigure();
        assertEquals(8, figure.getTabSize());
    }

    @Test
    public void testCloneCreatesIndependentCopy() {
        TextFigure original = new TextFigure("Original");
        TextFigure clone = original.clone();

        assertNotSame(original, clone);
        assertEquals(original.getText(), clone.getText());

        clone.setText("Changed");
        assertNotEquals(original.getText(), clone.getText());
    }

}
