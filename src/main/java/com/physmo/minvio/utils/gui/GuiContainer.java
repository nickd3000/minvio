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

public abstract class GuiContainer {
    List<GuiContainer> children;
    Rect rect;
    boolean dirty = false;
    BufferedImage buffer;
    DrawingContext dc;
    GuiContainer parent;
    Layout layout = null;

    public GuiContainer(Rect rect) {
        children = new ArrayList<>();

        setRect(rect);
        dirty = true;
    }

    public Rect getRect() {
        return rect;
    }

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

    public DrawingContext getDc() {
        return dc;
    }

    public abstract void draw(GuiContext guiContext);

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

    public PointInt getInheritedPosition() {

        PointInt p = new PointInt(rect.x, rect.y);
        GuiContainer reader = this;
        while (reader.parent != null) {
            reader = reader.parent;
            p.add(reader.getRect().x, reader.getRect().y);
        }
        return p;
    }


    public void recursiveDraw(GuiContext guiContext, DrawingContext baseContext, int offsetX, int offsetY) {
        int ox = offsetX + rect.x;
        int oy = offsetY + rect.y;
        drawIfDirty(guiContext);
        baseContext.drawImage(buffer, ox, oy);
        for (GuiContainer child : children) {
            child.recursiveDraw(guiContext, baseContext, ox, oy);
        }
    }


    // Used to build a list of container locations
    public void recursiveLocate(Collection<GuiContainer> list) {
        list.add(this);
        for (GuiContainer child : children) {
            child.recursiveLocate(list);
        }
    }


    public void drawIfDirty(GuiContext guiContext) {
        if (!dirty) return;
        draw(guiContext);
        dirty = false;
    }

    public abstract void onMessage(GuiMessage guiMessage, Object object);

    public boolean getDirty() {
        return dirty;
    }

    public void setDirty(boolean val) {
        this.dirty = val;
    }

    public void setDirtyRecursive(boolean val) {
        this.dirty = val;
        for (GuiContainer child : children) {
            child.setDirtyRecursive(val);
        }
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    public Layout getLayout() {
        return layout;
    }

    public void calculateLayout() {
        if (layout != null) layout.handleLayout(this, children);
    }
}
