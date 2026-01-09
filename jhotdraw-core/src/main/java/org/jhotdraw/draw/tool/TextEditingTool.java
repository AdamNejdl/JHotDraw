/*
 * @(#)TextEditingTool.java
 *
 * Copyright (c) 2009-2010 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.draw.tool;

import org.jhotdraw.draw.figure.TextHolderFigure;
import java.awt.*;
import java.awt.event.*;
import javax.swing.undo.UndoableEdit;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.text.*;

/**
 * A tool to edit figures which implement the {@code TextHolderFigure} interface,
 * such as {@code TextFigure}.
 *
 * <hr>
 * <b>Design Patterns</b>
 *
 * <p>
 * <em>Framework</em><br>
 * The text creation and editing tools and the {@code TextHolderFigure}
 * interface define together the contracts of a smaller framework inside of the
 * JHotDraw framework for structured drawing editors.<br>
 * Contract: {@link TextHolderFigure}, {@link TextCreationTool},
 * {@link TextAreaCreationTool}, {@link TextEditingTool},
 * {@link TextAreaEditingTool}, {@link FloatingTextField},
 * {@link FloatingTextArea}.
 *
 * <p>
 * <em>Prototype</em><br>
 * The text creation tools create new figures by cloning a prototype
 * {@code TextHolderFigure} object.<br>
 * Prototype: {@link TextHolderFigure}; Client: {@link TextCreationTool},
 * {@link TextAreaCreationTool}.
 * <hr>
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class TextEditingTool extends AbstractTool implements ActionListener {

    private static final long serialVersionUID = 1L;
    private transient FloatingTextField textField;
    private TextHolderFigure typingTarget;

    /**
     * Creates a new instance.
     */
    public TextEditingTool(TextHolderFigure typingTarget) {
        this.typingTarget = typingTarget;
    }

    @Override
    public void deactivate(DrawingEditor editor) {
        endEdit();
        super.deactivate(editor);
    }

    /**
     * If the pressed figure is a TextHolderFigure it can be edited.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (typingTarget != null) {
            beginEdit(typingTarget);
            updateCursor(getView(), e.getPoint());
        }
    }

    protected FloatingTextField createTextField() {
        FloatingTextField field = new FloatingTextField();
        field.addActionListener(this);
        return field;
    }

    protected void beginEdit(TextHolderFigure textHolder) {
        if (textField == null) {
            textField = createTextField();
        }

        if (textHolder != typingTarget && typingTarget != null) {
            endEdit();
        }
        textField.createOverlay(getView(), textHolder);
        textField.requestFocus();
        typingTarget = textHolder;
    }

    @Override
    public void mouseReleased(MouseEvent evt) {
        // intentionally left empty as no action is required on mouse release for this tool
    }

    protected UndoableEdit createTextUndoableEdit(
            TextHolderFigure figure,
            String oldText,
            String newText) {

        return new TextChangeUndoableEdit(figure, oldText, newText);
    }

    protected void endEdit() {
        if (typingTarget == null) { return; }

        final TextHolderFigure editedFigure = typingTarget;
        final String oldText = typingTarget.getText();
        final String newText = textField.getText();

        assert textField != null : "textField is null after edit";

        applyTextIfNeeded(typingTarget, newText);

        UndoableEdit edit =
                createTextUndoableEdit(editedFigure, oldText, newText);

        getDrawing().fireUndoableEditHappened(edit);
        finishEditing();

    }

    protected void applyTextIfNeeded(TextHolderFigure figure, String newText) {
        assert figure != null : "applyTextIfNeeded called with null figure";

        if (!newText.isEmpty()) {
            figure.willChange();
            figure.setText(newText);
            figure.changed();
        }
    }

    protected void finishEditing() {
        typingTarget = null;
        textField.endOverlay();
    }


    @Override
    public void keyReleased(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            fireToolDone();
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        endEdit();
        fireToolDone();
    }

    public boolean isEditing() {
        return typingTarget != null;
    }

    @Override
    public void updateCursor(DrawingView view, Point p) {
        if (view.isEnabled()) {
            view.setCursor(Cursor.getPredefinedCursor(isEditing() ? Cursor.DEFAULT_CURSOR : Cursor.CROSSHAIR_CURSOR));
        } else {
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
