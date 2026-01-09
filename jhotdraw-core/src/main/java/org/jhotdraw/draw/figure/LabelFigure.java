/*
 * @(#)LabelFigure.java
 *
 * Copyright (c) 1996-2010 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.draw.figure;

import java.awt.geom.*;
import java.util.*;
import org.jhotdraw.draw.event.FigureEvent;
import org.jhotdraw.draw.event.FigureListener;
import org.jhotdraw.draw.tool.TextEditingTool;
import org.jhotdraw.draw.tool.Tool;

/**
 * A LabelFigure can be used to provide more double clickable area for a
 * TextHolderFigure.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class LabelFigure extends TextFigure {

    private static final long serialVersionUID = 1L;
    private TextHolderFigure target;
    private final transient TargetFigureListener targetListener;

    /**
     * Creates a new instance.
     */
    public LabelFigure() {
        this("Label");
    }

    public LabelFigure(String text) {
        setText(text);
        setEditable(false);
        targetListener = new TargetFigureListener();
    }

    public void setLabelFor(TextHolderFigure target) {
        if (this.target != null) {
            this.target.removeFigureListener(targetListener);
        }
        this.target = target;
        if (this.target != null) {
            this.target.addFigureListener(targetListener);
        }
    }

    @Override
    public TextHolderFigure getLabelFor() {
        return (target == null) ? this : target;
    }

    /**
     * Returns a specialized tool for the given coordinate.
     * Returns null if no specialized tool is available.
     */
    @Override
    public Tool getTool(Point2D.Double coordinate) {
        return (target != null && contains(coordinate)) ? new TextEditingTool(target) : null;
    }

    @Override
    public void remap(Map<Figure, Figure> oldToNew, boolean disconnectIfNotInMap) {
        super.remap(oldToNew, disconnectIfNotInMap);
        if (target != null) {
            Figure newTarget = oldToNew.get(target);
            if (newTarget != null) {
                target.removeFigureListener(targetListener);
                target = (TextHolderFigure) newTarget;
                newTarget.addFigureListener(targetListener);
            }
        }
    }

    /**
     * Inner class to handle FigureListener events for the target figure.
     * Encapsulates listener logic so LabelFigure itself does not implement
     * all empty FigureListener methods.
     */
    private class TargetFigureListener implements FigureListener {

        @Override
        public void areaInvalidated(FigureEvent e) {
            // No action needed for LabelFigure
        }

        @Override
        public void attributeChanged(FigureEvent e) {
            // No action needed for LabelFigure
        }

        @Override
        public void figureHandlesChanged(FigureEvent e) {
            // No action needed for LabelFigure
        }

        @Override
        public void figureChanged(FigureEvent e) {
            // No action needed for LabelFigure
        }

        @Override
        public void figureAdded(FigureEvent e) {
            // No action needed for LabelFigure
        }

        @Override
        public void figureRemoved(FigureEvent e) {
            if (e.getFigure() == target) {
                target.removeFigureListener(this);
                target = null;
            }
        }

        @Override
        public void figureRequestRemove(FigureEvent e) {
            // No action needed for LabelFigure
        }
    }
}