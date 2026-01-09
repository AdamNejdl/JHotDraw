/*
 * @(#)TextFigure.java
 *
 * Copyright (c) 1996-2010 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.draw.figure;

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;
import org.jhotdraw.draw.AttributeKeys;
import static org.jhotdraw.draw.AttributeKeys.*;
import org.jhotdraw.draw.handle.BoundsOutlineHandle;
import org.jhotdraw.draw.handle.FontSizeHandle;
import org.jhotdraw.draw.handle.Handle;
import org.jhotdraw.draw.handle.MoveHandle;
import org.jhotdraw.draw.locator.RelativeLocator;
import org.jhotdraw.draw.tool.TextEditingTool;
import org.jhotdraw.draw.tool.Tool;
import org.jhotdraw.geom.Dimension2DDouble;
import org.jhotdraw.geom.Geom;
import org.jhotdraw.geom.Insets2D;
import org.jhotdraw.util.*;
import org.jhotdraw.xml.DOMInput;
import org.jhotdraw.xml.DOMOutput;

/**
 * A {@code TextHolderFigure} which holds a single line of text.
 * <p>
 * A DrawingEditor should provide the {@link org.jhotdraw.draw.tool.TextCreationTool} to create a
 * {@code TextFigure}.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class TextFigure extends AbstractAttributedDecoratedFigure
        implements TextHolderFigure {

    private static final long serialVersionUID = 1L;

    private static final int TEXT_COLUMNS = 4;
    private static final int TAB_SIZE = 8;
    private static final double ANTIALIAS_PADDING = 2d;

    protected Point2D.Double origin = new Point2D.Double();
    protected boolean editable = true;
    // cache of the TextFigure's layout
    protected transient TextLayout textLayout;

    /**
     * Creates a new instance.
     */
    public TextFigure() {
        this(ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels").
                getString("TextFigure.defaultText"));
    }

    public TextFigure(String text) {
        setText(text);
    }

    // DRAWING
    @Override
    protected void drawStroke(java.awt.Graphics2D graphics2D) {
        // No stroke drawing required for text
    }

    @Override
    protected void drawFill(java.awt.Graphics2D graphics2D) {
        // No fill drawing required for text
    }

    @Override
    protected void drawText(Graphics2D graphics2D) {
        if (getText() == null && !isEditable()) return;

        TextLayout layout = getTextLayout();
        Graphics2D graphicsTransformed = (Graphics2D) graphics2D.create();
        try {
            applyMirroringIfNeeded(graphicsTransformed, layout);
            layout.draw(graphicsTransformed, (float) origin.x, (float) (origin.y + layout.getAscent()));
        } finally {
            graphicsTransformed.dispose();
        }
    }

    private void applyMirroringIfNeeded(Graphics2D graphicsTransformed, TextLayout layout) {
        if (graphicsTransformed.getTransform().getScaleX() * graphicsTransformed.getTransform().getScaleY() < 0) {
            AffineTransform affineTransform = new AffineTransform();
            affineTransform.translate(0, origin.y + layout.getAscent() / 2);
            affineTransform.scale(1, -1);
            affineTransform.translate(0, -origin.y - layout.getAscent() / 2);
            graphicsTransformed.transform(affineTransform);
        }
    }

    // SHAPE AND BOUNDS
    @Override
    public void transform(AffineTransform affineTransform) {
        affineTransform.transform(origin, origin);
    }

    @Override
    public void setBounds(Point2D.Double anchor, Point2D.Double lead) {
        origin = new Point2D.Double(anchor.x, anchor.y);
    }

    @Override
    public boolean figureContains(Point2D.Double point) {
        return getBounds().contains(point);
    }

    protected TextLayout getTextLayout() {
        if (textLayout == null) {
            String text = getText();
            if (text == null || text.isEmpty()) {
                text = " ";
            }
            FontRenderContext fontRenderContext = getFontRenderContext();
            HashMap<TextAttribute, Object> textAttributes = new HashMap<>();
            textAttributes.put(TextAttribute.FONT, getFont());
            if (Boolean.TRUE.equals(get(FONT_UNDERLINE))) {
                textAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_ONE_PIXEL);
            }
            textLayout = new TextLayout(text, textAttributes, fontRenderContext);
        }
        return textLayout;
    }

    @Override
    public Rectangle2D.Double getBounds() {
        TextLayout layout = getTextLayout();
        return new Rectangle2D.Double(origin.x, origin.y, layout.getAdvance(),
                layout.getAscent() + layout.getDescent());
    }

    @Override
    public Dimension2DDouble getPreferredSize() {
        Rectangle2D.Double b = getBounds();
        return new Dimension2DDouble(b.width, b.height);
    }

    @Override
    public double getBaseline() {
        return origin.y + getTextLayout().getAscent() - getBounds().y;
    }

    /**
     * Gets the drawing area without taking the decorator into account.
     */
    @Override
    protected Rectangle2D.Double getFigureDrawingArea() {
        Rectangle2D.Double rectangle = new Rectangle2D.Double(origin.x, origin.y, 0, 0);
        TextLayout layout = getTextLayout();

        rectangle.setRect(origin.x, origin.y, layout.getAdvance(), layout.getAscent());
        Rectangle2D lBounds = layout.getBounds();
        if (!lBounds.isEmpty() && !Double.isNaN(lBounds.getX())) {
            rectangle.add(new Rectangle2D.Double(
                    lBounds.getX() + origin.x,
                    lBounds.getY() + origin.y + layout.getAscent(),
                    lBounds.getWidth(),
                    lBounds.getHeight()
            ));
        }
        Geom.grow(rectangle, ANTIALIAS_PADDING, ANTIALIAS_PADDING);
        return rectangle;
    }

    @Override
    public void restoreTransformTo(Object geometry) {
        Point2D.Double point = (Point2D.Double) geometry;
        origin.x = point.x;
        origin.y = point.y;
    }

    @Override
    public Object getTransformRestoreData() {
        return origin.clone();
    }

    // ATTRIBUTES
    /**
     * Gets the text shown by the text figure.
     */
    @Override
    public String getText() {
        return get(TEXT);
    }

    /**
     * Sets the text shown by the text figure. This is a convenience method for calling
     * {@code set(TEXT,newText)}.
     */
    @Override
    public void setText(String newText) {
        set(TEXT, newText);
    }

    @Override
    public int getTextColumns() {
        return TEXT_COLUMNS;
    }

    /**
     * Gets the number of characters used to expand tabs.
     */
    @Override
    public int getTabSize() {
        return TAB_SIZE;
    }

    @Override
    public TextHolderFigure getLabelFor() {
        return this;
    }

    @Override
    public Insets2D.Double getInsets() {
        return new Insets2D.Double();
    }

    @Override
    public Font getFont() {
        return AttributeKeys.getFont(this);
    }

    @Override
    public Color getTextColor() {
        return get(TEXT_COLOR);
    }

    @Override
    public Color getFillColor() {
        return get(FILL_COLOR);
    }

    @Override
    public void setFontSize(float size) {
        set(FONT_SIZE, (double) size);
    }

    @Override
    public float getFontSize() {
        return get(FONT_SIZE).floatValue();
    }

    // EDITING
    @Override
    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    @Override
    public Collection<Handle> createHandles(int detailLevel) {
        LinkedList<Handle> handles = new LinkedList<>();

        if (detailLevel == -1) {
            handles.add(new BoundsOutlineHandle(this, false, true));
        } else if (detailLevel == 0) {
            handles.add(new BoundsOutlineHandle(this));
            handles.add(new MoveHandle(this, RelativeLocator.northWest()));
            handles.add(new MoveHandle(this, RelativeLocator.northEast()));
            handles.add(new MoveHandle(this, RelativeLocator.southWest()));
            handles.add(new MoveHandle(this, RelativeLocator.southEast()));
            handles.add(new FontSizeHandle(this));
        }
        return handles;
    }

    /**
     * Returns a specialized tool for the given coordinate.
     * <p>
     * Returns null, if no specialized tool is available.
     */
    @Override
    public Tool getTool(Point2D.Double coordinate) {
        if (isEditable() && contains(coordinate)) {
            return new TextEditingTool(this);
        }
        return null;
    }

    // CONNECTING
    // COMPOSITE FIGURES
    // CLONING
    // EVENT HANDLING
    @Override
    public void invalidate() {
        super.invalidate();
        textLayout = null;
    }

    @Override
    protected void validate() {
        super.validate();
        textLayout = null;
    }

    @Override
    public void read(DOMInput in) throws IOException {
        setBounds(
                new Point2D.Double(in.getAttribute("x", 0d), in.getAttribute("y", 0d)),
                new Point2D.Double(0, 0));
        readAttributes(in);
        readDecorator(in);
        invalidate();
    }

    @Override
    public void write(DOMOutput out) throws IOException {
        Rectangle2D.Double bounds = getBounds();
        out.addAttribute("x", bounds.x);
        out.addAttribute("y", bounds.y);
        writeAttributes(out);
        writeDecorator(out);
    }

    @Override
    public TextFigure clone() {
        TextFigure that = (TextFigure) super.clone();
        that.origin = (Point2D.Double) this.origin.clone();
        that.textLayout = null;
        return that;
    }

    @Override
    public boolean isTextOverflow() {
        return false;
    }
}
