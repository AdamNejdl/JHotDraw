package org.jhotdraw.draw.action;

import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.figure.EllipseFigure;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.draw.figure.RectangleFigure;
import org.junit.Before;
import org.junit.Test;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SelectSameActionTest {
    private SelectSameAction action;
    private DrawingEditor mockEditor;
    private DrawingView mockView;
    private Drawing mockDrawing;
    private ActionEvent mockEvent;

    @Before
    public void setUp() {
        mockEditor = mock(DrawingEditor.class);
        mockView = mock(DrawingView.class);
        mockDrawing = mock(Drawing.class);
        mockEvent = mock(ActionEvent.class);

        when(mockEditor.getActiveView()).thenReturn(mockView);
        when(mockView.getDrawing()).thenReturn(mockDrawing);
        when(mockView.isEnabled()).thenReturn(true);
        when(mockView.getSelectionCount()).thenReturn(1);

        action = new SelectSameAction(mockEditor);
    }

    @Test
    public void testSelectSame_SelectsFiguresOfSameClass() {
        Figure selectedRect = new RectangleFigure();
        Figure unselectedRect = new RectangleFigure();
        Figure unselectedEllipse = new EllipseFigure();

        when(mockView.getSelectedFigures()).thenReturn(new HashSet<>(Collections.singletonList(selectedRect)));
        when(mockDrawing.getChildren()).thenReturn(Arrays.asList(selectedRect, unselectedRect, unselectedEllipse));

        action.actionPerformed(mockEvent);

        verify(mockView).addToSelection(unselectedRect);
        verify(mockView, never()).addToSelection(unselectedEllipse);
    }

    @Test
    public void testSelectSame_WhenNothingSelected() {
        when(mockView.getSelectedFigures()).thenReturn(Collections.emptySet());
        when(mockDrawing.getChildren()).thenReturn(Arrays.asList(new RectangleFigure()));

        action.actionPerformed(mockEvent);

        verify(mockView, never()).addToSelection(any(Figure.class));
    }
}