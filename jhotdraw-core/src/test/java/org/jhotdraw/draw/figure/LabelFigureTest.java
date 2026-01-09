package org.jhotdraw.draw.figure;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.awt.geom.Point2D;

import org.jhotdraw.draw.event.FigureEvent;
import org.junit.Test;
import org.jhotdraw.draw.tool.TextEditingTool;
import org.jhotdraw.draw.tool.Tool;
import org.mockito.ArgumentCaptor;

public class LabelFigureTest {

    @Test
    public void testLabelText() {
        LabelFigure label = new LabelFigure("Etiqueta");

        assertEquals("Etiqueta", label.getText());
        label.setText("Nuevo texto");
        assertEquals("Nuevo texto", label.getText());
    }

    @Test
    public void testSetLabelForRegistersListener() {
        LabelFigure label = new LabelFigure();
        TextHolderFigure target = mock(TextHolderFigure.class);

        label.setLabelFor(target);

        // Capturamos que se haya agregado un FigureListener al target
        ArgumentCaptor<org.jhotdraw.draw.event.FigureListener> captor = ArgumentCaptor.forClass(org.jhotdraw.draw.event.FigureListener.class);
        verify(target).addFigureListener(captor.capture());

        // Simulamos que el target se elimina
        FigureEvent event = new FigureEvent(target, null);
        captor.getValue().figureRemoved(event);

        // LabelFigure debe apuntar a sí mismo y remover el listener
        assertSame(label, label.getLabelFor());
        verify(target).removeFigureListener(captor.getValue());
    }

    @Test
    public void testGetLabelForReturnsTargetOrSelf() {
        LabelFigure label = new LabelFigure();
        TextHolderFigure target = mock(TextHolderFigure.class);

        // Sin target, retorna a sí mismo
        assertSame(label, label.getLabelFor());

        label.setLabelFor(target);
        assertSame(target, label.getLabelFor());
    }

    @Test
    public void testGetToolReturnsTextEditingToolWhenTargetContainsPoint() {
        LabelFigure label = new LabelFigure();
        TextHolderFigure target = mock(TextHolderFigure.class);

        label.setLabelFor(target);

        // Mockeamos que label contiene el punto
        LabelFigure spyLabel = spy(label);
        doReturn(true).when(spyLabel).contains(any(Point2D.Double.class));

        Tool tool = spyLabel.getTool(new Point2D.Double(10, 10));
        assertTrue(tool instanceof TextEditingTool);
    }

    @Test
    public void testGetToolReturnsNullWhenNoTargetOrOutsidePoint() {
        LabelFigure label = new LabelFigure();
        Tool tool = label.getTool(new Point2D.Double(0, 0));
        assertNull(tool);

        TextHolderFigure target = mock(TextHolderFigure.class);
        label.setLabelFor(target);
        LabelFigure spyLabel = spy(label);
        doReturn(false).when(spyLabel).contains(any(Point2D.Double.class));

        tool = spyLabel.getTool(new Point2D.Double(0, 0));
        assertNull(tool);
    }
}
