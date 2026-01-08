package org.jhotdraw.action.edit;

import org.jhotdraw.api.gui.EditableComponent;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import java.awt.event.ActionEvent;

import static org.junit.Assert.*;

public class DuplicateActionTest {

    private DuplicateAction duplicateAction;
    private TestEditableComponent component;

    static class TestEditableComponent extends JPanel implements EditableComponent {
        boolean duplicated = false;

        @Override
        public void duplicate() {
            duplicated = true;
        }

        @Override public void delete() {}
        @Override public void selectAll() {}
        @Override public void clearSelection() {}
        @Override public boolean isSelectionEmpty() {return false;}

        public boolean isDuplicated() {
            return duplicated;
        }
    }

    @Before
    public void setUp() {
        component = new TestEditableComponent();
        duplicateAction = new DuplicateAction(component);
    }

    @Test
    public void testDuplicateCalled() {
        duplicateAction.actionPerformed(new ActionEvent(component, 0, ""));
        assertTrue("duplicate() should have been called", component.isDuplicated());
    }

    @Test
    public void testDuplicateNotCalledOnDisabledComponent() {
        component.setEnabled(false);
        duplicateAction.actionPerformed(new ActionEvent(component, 0, ""));
        assertFalse("duplicate() should not be called on disabled component", component.isDuplicated());
    }

    @Test
    public void testDuplicateNotCalledOnNonEditableComponent() {
        JPanel panel = new JPanel();
        DuplicateAction action = new DuplicateAction(panel);
        action.actionPerformed(new ActionEvent(panel, 0, ""));
        assertFalse("duplicate() should not be called on disabled component", component.isDuplicated());
    }
}
