package org.jhotdraw.action.edit;

import org.jhotdraw.api.gui.EditableComponent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.event.ActionEvent;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ClearSelectionActionTest {
    private ActionEvent mockEvent;

    @Before
    public void setUp() {
        mockEvent = mock(ActionEvent.class);
    }

    @Test
    public void testActionPerformed_ClearsSelection_OnJTextComponent() {
        JTextComponent mockText = mock(JTextComponent.class);
        when(mockText.isEnabled()).thenReturn(true);
        when(mockText.getSelectionStart()).thenReturn(10);

        ClearSelectionAction action = new ClearSelectionAction(mockText);

        action.actionPerformed(mockEvent);

        verify(mockText, times(1)).select(10, 10);
    }

    @Test
    public void testActionPerformed_ClearsSelection_OnEditableComponent() {
        JComponent mockEditable = Mockito.mock(JComponent.class,
                withSettings().extraInterfaces(EditableComponent.class));
        when(mockEditable.isEnabled()).thenReturn(true);

        ClearSelectionAction action = new ClearSelectionAction(mockEditable);

        action.actionPerformed(mockEvent);

        verify((EditableComponent) mockEditable, times(1)).clearSelection();
    }
}