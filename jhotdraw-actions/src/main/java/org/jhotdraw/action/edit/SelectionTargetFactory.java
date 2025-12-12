package org.jhotdraw.action.edit;

import org.jhotdraw.api.gui.EditableComponent;

import javax.swing.*;
import javax.swing.text.JTextComponent;

public class SelectionTargetFactory {
    public static SelectionTarget create(JComponent c) {
        if (c instanceof EditableComponent) {
            return new EditableComponentAdapter((EditableComponent) c);
        } else if (c instanceof JTextComponent) {
            return new JTextComponentAdapter((JTextComponent) c);
        }
        return null;
    }

    private static class EditableComponentAdapter implements SelectionTarget {
        private final EditableComponent target;

        public EditableComponentAdapter(EditableComponent target) {
            this.target = target;
        }
        @Override
        public void selectAll() {
            target.selectAll();
        }
        @Override
        public void clearSelection() {
            target.clearSelection();
        }
    }

    private static class JTextComponentAdapter implements SelectionTarget {
        private final JTextComponent target;

        public JTextComponentAdapter(JTextComponent target) {
            this.target = target;
        }
        @Override
        public void selectAll() {
            target.selectAll();
        }
        @Override
        public void clearSelection() {
            target.select(target.getSelectionStart(), target.getSelectionStart());
        }
    }
}
