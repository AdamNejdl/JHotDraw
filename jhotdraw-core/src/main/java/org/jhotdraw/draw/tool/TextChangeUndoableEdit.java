package org.jhotdraw.draw.tool;

import org.jhotdraw.draw.figure.TextHolderFigure;
import org.jhotdraw.util.ResourceBundleUtil;

import javax.swing.undo.AbstractUndoableEdit;

public class TextChangeUndoableEdit extends AbstractUndoableEdit {

    private final TextHolderFigure figure;
    private final String oldText;
    private final String newText;

    public TextChangeUndoableEdit(
            TextHolderFigure figure,
            String oldText,
            String newText) {
        this.figure = figure;
        this.oldText = oldText;
        this.newText = newText;
    }

    @Override
    public String getPresentationName() {
        ResourceBundleUtil labels =
                ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");
        return labels.getString("attribute.text.text");
    }

    @Override
    public void undo() {
        super.undo();
        figure.willChange();
        figure.setText(oldText);
        figure.changed();
    }

    @Override
    public void redo() {
        super.redo();
        figure.willChange();
        figure.setText(newText);
        figure.changed();
    }
}

