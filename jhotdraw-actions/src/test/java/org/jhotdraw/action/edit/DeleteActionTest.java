package org.jhotdraw.action.edit;

import org.jhotdraw.api.gui.EditableComponent;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.event.ActionEvent;

import static org.junit.Assert.*;

public class DeleteActionTest {

    private JTextArea textArea;

    static class TestEditableComponent extends JTextArea implements EditableComponent {
        boolean deleteCalled = false;

        TestEditableComponent(String text) {
            super(text);
        }

        @Override
        public void delete() {
            deleteCalled = true;
        }

        @Override public void duplicate() {}
        @Override public void selectAll() {}
        @Override public void clearSelection() {}
        @Override public boolean isSelectionEmpty() {return false;}
    }

    @Before
    public void setUp() {
        textArea = new JTextArea("Hello");
        textArea.setCaretPosition(1);
        textArea.setEditable(true);
    }

    @Test
    public void testDeleteChar() {
        textArea.setCaretPosition(1);
        DeleteAction action = new DeleteAction(textArea);
        action.actionPerformed(new ActionEvent(textArea, 0, ""));

        assertEquals("Hllo", textArea.getText());
    }

    @Test
    public void testDeleteCalledForEditableComponent() {
        TestEditableComponent editable = new TestEditableComponent("Hello");
        DeleteAction action = new DeleteAction(editable);

        action.actionPerformed(new ActionEvent(editable, 0, ""));

        assertTrue("delete() should be called on EditableComponent", editable.deleteCalled);
    }
}