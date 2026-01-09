package org.jhotdraw.draw.tool;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.awt.event.ActionEvent;
import javax.swing.undo.UndoableEdit;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.figure.TextHolderFigure;
import org.jhotdraw.draw.text.FloatingTextField;

public class TextCreationToolTest {
    private TextHolderFigure prototype;
    private FloatingTextField textField;
    private Drawing drawing;
    private DrawingView view;
    private TextCreationTool tool;

    @Before
    public void setUp() {
        prototype = mock(TextHolderFigure.class);
        drawing = mock(Drawing.class);
        view = mock(DrawingView.class);
        when(view.isEnabled()).thenReturn(true);
        when(prototype.getText()).thenReturn("old");

        tool = new TextCreationTool(prototype) {
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
    }

    @Test
    public void testBeginEditSetsTypingTarget() {
        tool.beginEdit(prototype);
        assertTrue(tool.isEditing());
    }

    @Test
    public void testEndEditAppliesText() {
        tool.beginEdit(prototype);
        tool.endEdit();

        verify(prototype).willChange();
        verify(prototype).setText("new text");
        verify(prototype).changed();
        verify(drawing).fireUndoableEditHappened(any(UndoableEdit.class));
        assertFalse(tool.isEditing());
    }

    @Test
    public void testActionPerformedCallsEndEdit() {
        tool.beginEdit(prototype);
        tool.actionPerformed(mock(ActionEvent.class));

        verify(prototype).willChange();
        verify(prototype).setText("new text");
        verify(prototype).changed();
        assertFalse(tool.isEditing());
    }
}

