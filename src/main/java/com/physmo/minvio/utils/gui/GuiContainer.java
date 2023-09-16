package com.physmo.minvio.utils.gui;

import com.physmo.minvio.DrawingContext;
import com.physmo.minvio.DrawingContextAwt;
import com.physmo.minvio.PointInt;
import com.physmo.minvio.Rect;
import com.physmo.minvio.utils.gui.support.GuiMessage;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class GuiContainer {
    List<GuiContainer> children;
    Rect rect;
    boolean dirty = false;
    BufferedImage buffer;
    DrawingContext dc;
    GuiContainer parent;

    public GuiContainer(Rect rect) {
        children = new ArrayList<>();
        this.rect = rect;
        buffer = new BufferedImage(rect.w, rect.h, BufferedImage.TYPE_INT_ARGB);
        dc = new DrawingContextAwt(buffer);
        dirty = true;
    }

    public Rect getRect() {
        return rect;
    }

    public DrawingContext getDc() {
        return dc;
    }

    public abstract void draw();

    public void add(GuiContainer child) {
        child.parent = this;
        children.add(child);
    }

    public PointInt getInheritedPosition() {

        PointInt p = new PointInt(rect.x, rect.y);
        GuiContainer reader = this;
        while (reader.parent != null) {
            reader = parent;
            p.add(reader.getRect().x, reader.getRect().y);
        }
        return p;
    }


    public void recursiveDraw(DrawingContext baseContext, int offsetX, int offsetY) {
        int ox = offsetX + rect.x;
        int oy = offsetY + rect.y;
        drawIfDirty();
        baseContext.drawImage(buffer, ox, oy);
        for (GuiContainer child : children) {
            child.recursiveDraw(baseContext, ox, oy);
        }

    }

    // Used to build a list of container locations
    public void recursiveLocate(Collection<GuiContainer> list) {
        list.add(this);
        for (GuiContainer child : children) {
            child.recursiveLocate(list);
        }
    }


    public void drawIfDirty() {
        if (!dirty) return;
        draw();
        dirty = false;
    }

    private boolean isDirty() {
        return dirty;
    }

    public abstract void onMessage(GuiMessage guiMessage, Object object);

    public boolean getDirty() {
        return dirty;
    }

    public void setDirty(boolean val) {
        this.dirty = val;
    }
}

