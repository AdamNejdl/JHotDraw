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

public class SelectAllActionTest {
    private ActionEvent mockEvent;

    @Before
    public void setUp() {
        mockEvent = mock(ActionEvent.class);
    }

    @Test
    public void testActionPerformed_SelectsAll_OnJTextComponent() {
        JTextComponent mockText = mock(JTextComponent.class);
        when(mockText.isEnabled()).thenReturn(true);

        SelectAllAction action = new SelectAllAction(mockText);

        action.actionPerformed(mockEvent);

        verify(mockText, times(1)).selectAll();
    }

    @Test
    public void testActionPerformed_SelectsAll_OnEditableComponent() {
        JComponent mockEditable = Mockito.mock(JComponent.class,
                withSettings().extraInterfaces(EditableComponent.class));
        when(mockEditable.isEnabled()).thenReturn(true);

        SelectAllAction action = new SelectAllAction(mockEditable);

        action.actionPerformed(mockEvent);

        verify((EditableComponent) mockEditable, times(1)).selectAll();
    }
}