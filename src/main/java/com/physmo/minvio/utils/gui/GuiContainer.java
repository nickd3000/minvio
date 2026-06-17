package com.physmo.minvio.utils.gui;

import com.physmo.minvio.DrawingContext;
import com.physmo.minvio.DrawingContextAwt;
import com.physmo.minvio.types.PointInt;
import com.physmo.minvio.types.Rect;
import com.physmo.minvio.utils.gui.layout.Layout;
import com.physmo.minvio.utils.gui.support.GuiMessage;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Base class for retained GUI controls.
 *
 * <p>Each container owns an off-screen ARGB buffer and a drawing context for
 * that buffer. Containers are arranged in a parent/child tree; drawing walks
 * the tree in pre-order and composites each retained buffer into the supplied
 * top-level drawing context. This class is not thread-safe.</p>
 */
public abstract class GuiContainer {
    List<GuiContainer> children;
    Rect rect;
    boolean dirty = false;
    BufferedImage buffer;
    DrawingContext dc;
    GuiContainer parent;
    Layout layout = null;

    /**
     * Creates a container with a copied rectangle and a fresh retained buffer.
     *
     * @param rect initial bounds; copied on construction
     * @throws NullPointerException     if {@code rect} is null
     * @throws IllegalArgumentException if width or height is not positive
     */
    public GuiContainer(Rect rect) {
        children = new ArrayList<>();

        setRect(rect);
        dirty = true;
    }

    /**
     * Returns the live mutable rectangle used by this container.
     *
     * <p>Mutating the returned rectangle bypasses buffer reallocation and dirty
     * handling. Prefer {@link #setRect(Rect)} when changing size.</p>
     *
     * @return internal rectangle
     */
    public Rect getRect() {
        return rect;
    }

    /**
     * Replaces this container's rectangle and retained buffer.
     *
     * <p>The supplied rectangle's values are copied. If the new values equal the
     * existing rectangle, no buffer or drawing-context replacement occurs.
     * Otherwise a new buffer and drawing context are created.</p>
     *
     * @param rect replacement bounds
     * @throws NullPointerException if {@code rect} is null
     * @throws IllegalArgumentException if width or height is not positive
     */
    public void setRect(Rect rect) {
        Objects.requireNonNull(rect, "rect");
        if (rect.w <= 0 || rect.h <= 0) {
            throw new IllegalArgumentException("Rectangle dimensions must be positive");
        }
        if (this.rect != null && this.rect.equals(rect)) return;

        if (this.rect == null) {
            this.rect = new Rect(rect);
        } else {
            this.rect.set(rect);
        }

        buffer = new BufferedImage(rect.w, rect.h, BufferedImage.TYPE_INT_ARGB);
        dc = new DrawingContextAwt(buffer);
    }

    /**
     * Returns the drawing context for this container's retained buffer.
     *
     * @return current retained-buffer drawing context
     */
    public DrawingContext getDc() {
        return dc;
    }

    /**
     * Redraws this container into its retained buffer.
     *
     * @param guiContext GUI context providing style and shared state
     */
    public abstract void draw(GuiContext guiContext);

    /**
     * Adds a child container.
     *
     * <p>Adding the same child to the same parent is idempotent. Reparenting a
     * child from another parent is rejected. There is currently no remove API.</p>
     *
     * @param child child to add
     * @throws NullPointerException if {@code child} is null
     * @throws IllegalArgumentException if {@code child} already belongs to a
     * different parent
     */
    public void add(GuiContainer child) {
        Objects.requireNonNull(child, "child");
        if (child.parent != null && child.parent != this) {
            throw new IllegalArgumentException("Child already has a parent");
        }
        child.parent = this;
        if (!children.contains(child)) {
            children.add(child);
        }
    }

    /**
     * Returns this container's absolute position in the GUI tree.
     *
     * @return new mutable point containing the sum of this container and all
     * ancestor rectangle origins
     */
    public PointInt getInheritedPosition() {

        PointInt p = new PointInt(rect.x, rect.y);
        GuiContainer reader = this;
        while (reader.parent != null) {
            reader = reader.parent;
            p.add(reader.getRect().x, reader.getRect().y);
        }
        return p;
    }


    /**
     * Draws this container and its children recursively.
     *
     * <p>Dirty containers are redrawn before their retained buffer is composited.
     * Children are drawn in insertion order with accumulated parent offsets.</p>
     *
     * @param guiContext GUI context providing style and shared state
     * @param baseContext target context receiving retained buffers
     * @param offsetX accumulated parent x offset
     * @param offsetY accumulated parent y offset
     */
    public void recursiveDraw(GuiContext guiContext, DrawingContext baseContext, int offsetX, int offsetY) {
        int ox = offsetX + rect.x;
        int oy = offsetY + rect.y;
        drawIfDirty(guiContext);
        baseContext.drawImage(buffer, ox, oy);
        for (GuiContainer child : children) {
            child.recursiveDraw(guiContext, baseContext, ox, oy);
        }
    }


    /**
     * Appends this container and descendants to a collection in pre-order.
     *
     * @param list destination collection
     */
    public void recursiveLocate(Collection<GuiContainer> list) {
        list.add(this);
        for (GuiContainer child : children) {
            child.recursiveLocate(list);
        }
    }


    /**
     * Redraws this container only when dirty.
     *
     * @param guiContext GUI context passed to {@link #draw(GuiContext)}
     */
    public void drawIfDirty(GuiContext guiContext) {
        if (!dirty) return;
        draw(guiContext);
        dirty = false;
    }

    /**
     * Handles a GUI event.
     *
     * @param guiMessage message kind
     * @param object message payload; mouse messages use
     * {@link com.physmo.minvio.utils.gui.support.MouseMessageData}
     */
    public abstract void onMessage(GuiMessage guiMessage, Object object);

    /** @return whether this container needs its retained buffer redrawn */
    public boolean getDirty() {
        return dirty;
    }

    /**
     * Sets the dirty flag.
     *
     * @param val new dirty flag
     */
    public void setDirty(boolean val) {
        this.dirty = val;
    }

    /**
     * Sets the dirty flag on this container and all descendants.
     *
     * @param val new dirty flag
     */
    public void setDirtyRecursive(boolean val) {
        this.dirty = val;
        for (GuiContainer child : children) {
            child.setDirtyRecursive(val);
        }
    }

    /**
     * Sets the layout callback used by {@link #calculateLayout()}.
     *
     * @param layout layout callback, or {@code null} to disable layout
     */
    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    /** @return current layout callback, or {@code null} */
    public Layout getLayout() {
        return layout;
    }

    /**
     * Applies the configured layout to this container's live child list.
     *
     * <p>If no layout is configured, this method has no effect.</p>
     */
    public void calculateLayout() {
        if (layout != null) layout.handleLayout(this, children);
    }
}
