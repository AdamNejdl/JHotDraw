package org.jhotdraw.draw.tool;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.awt.Point;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.figure.TextHolderFigure;
import org.jhotdraw.draw.text.FloatingTextField;

public class TextEditingToolTest {
    private TextHolderFigure textFigure;
    private FloatingTextField textField;
    private Drawing drawing;
    private DrawingView view;
    private TextEditingTool tool;

    @Before
    public void setUp() {
        textFigure = mock(TextHolderFigure.class);
        drawing = mock(Drawing.class);
        view = mock(DrawingView.class);
        when(view.isEnabled()).thenReturn(true);
        when(textFigure.getText()).thenReturn("old text");

        tool = new TextEditingTool(textFigure) {
            @Override
            protected FloatingTextField createTextField() {
                textField = mock(FloatingTextField.class);
                when(textField.getText()).thenReturn("new text");
                doNothing().when(textField).createOverlay(any(), any());
                doNothing().when(textField).requestFocus();
                doNothing().when(textField).endOverlay();
                return textField;
            }

            @Override
            protected Drawing getDrawing() {
                return drawing;
            }

            @Override
            protected DrawingView getView() {
                return view;
            }

            @Override
            protected void fireToolDone() {
                // no operation
            }
        };

        tool.beginEdit(textFigure);
    }


    @Test
    public void testApplyTextIfNeededAppliesText() {
        tool.beginEdit(textFigure);
        tool.applyTextIfNeeded(textFigure, "new text");

        verify(textFigure).willChange();
        verify(textFigure).setText("new text");
        verify(textFigure).changed();
    }

    @Test
    public void testApplyTextIfNeededDoesNothingOnEmpty() {
        tool.beginEdit(textFigure);
        tool.applyTextIfNeeded(textFigure, "");

        verify(textFigure, never()).willChange();
        verify(textFigure, never()).setText(anyString());
        verify(textFigure, never()).changed();
    }

    @Test
    public void testFinishEditingResetsTypingTarget() {
        tool.beginEdit(textFigure);
        tool.finishEditing();

        assertFalse(tool.isEditing());
        verify(textField).endOverlay();
    }

    @Test
    public void testActionPerformedCallsEndEdit() {
        tool.beginEdit(textFigure);
        tool.actionPerformed(null);

        verify(textFigure).willChange();
        verify(textFigure).setText(anyString());
        verify(textFigure).changed();
    }

    @Test
    public void testEndEditAppliesText() {
        when(textFigure.getText()).thenReturn("old text");
        when(textField.getText()).thenReturn("new text");

        tool.beginEdit(textFigure);
        tool.endEdit();

        verify(textFigure).willChange();
        verify(textFigure).setText("new text");
        verify(textFigure).changed();
        verify(drawing).fireUndoableEditHappened(any());
        assertFalse(tool.isEditing());
    }

}

