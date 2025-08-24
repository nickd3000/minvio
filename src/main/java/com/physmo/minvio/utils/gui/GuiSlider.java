package com.physmo.minvio.utils.gui;

import com.physmo.minvio.types.Rect;
import com.physmo.minvio.utils.gui.support.GuiMessage;
import com.physmo.minvio.utils.gui.support.GuiStyle;
import com.physmo.minvio.utils.gui.support.MouseMessageData;

import java.util.function.DoubleConsumer;

import static com.physmo.minvio.utils.gui.support.GuiMessage.MOUSE_BUTTON_DOWN;
import static com.physmo.minvio.utils.gui.support.GuiMessage.MOUSE_BUTTON_UP;
import static com.physmo.minvio.utils.gui.support.GuiMessage.MOUSE_MOVE;

public class GuiSlider extends GuiContainer {

    public static int SLIDER_HORIZONTAL = 1;
    public static int SLIDER_VERTICAL = 2; // TODO


    boolean grabbed = false;
    boolean mouseOverHandle = false;
    int orientation = SLIDER_HORIZONTAL;
    // Store normalized value [0..1] instead of pixel position
    double value = 0.0;
    // Track length in pixels (derived from size)
    int trackLength = 30;
    int handleSize = 10;
    int endPadding = 0;

    DoubleConsumer onChanged = null;
    int grabOffsetY;
    int grabOffsetX;

    public GuiSlider(Rect rect) {
        super(rect);
        setHandleSize(14);
        recalculateMetrics();
    }

    public void setHandleSize(int val) {
        handleSize = val;
        recalculateMetrics();
    }

    public void recalculateMetrics() {
        endPadding = handleSize;
        trackLength = rect.w - (endPadding * 2);
        if (trackLength < 0) trackLength = 0;
    }

    @Override
    public void draw(GuiContext guiContext) {
        recalculateMetrics();

        //GuiUtils.drawBevelBoxOut(dc, guiContext.getGuiStyle(), 0, 0, rect.w, rect.h);
        GuiStyle guiStyle = guiContext.getGuiStyle();

        dc.setDrawColor(guiStyle.getBackgroundColor());
        dc.drawFilledRect(0, 0, rect.w, rect.h);

        if (orientation == SLIDER_HORIZONTAL) {
            dc.setDrawColor(guiStyle.getAccent());

            int handleCenterX = getHandleCenterX();
            int filled = Math.max(0, Math.min(trackLength, handleCenterX - endPadding));
            int unfilled = Math.max(0, trackLength - filled);

            dc.drawFilledRect(endPadding, (rect.h / 2) - 1, filled, 2);
            dc.setDrawColor(guiStyle.getBevelDark());
            dc.drawFilledRect(endPadding + filled, (rect.h / 2) - 1, unfilled, 2);


            dc.setDrawColor(guiStyle.getBevelLight());

            // Handle outline
            if (grabbed || mouseOverHandle) {
                dc.setDrawColor(guiStyle.getAccent());
                dc.drawFilledCircle((double) handleCenterX, (double) rect.h / 2, 1 + (double) handleSize / 2);
            }

            dc.setDrawColor(guiStyle.getBevelLight());
            // Central part of handle
            dc.drawFilledCircle((double) handleCenterX, (double) rect.h / 2, (double) handleSize / 2);

        }
    }

    private int getHandleCenterX() {
        // endPadding + normalized*trackLength
        return endPadding + (int) Math.round(value * trackLength);
    }

    public void setOnChangedHandler(DoubleConsumer onChanged) {
        this.onChanged = onChanged;
    }

    public boolean isMouseOverHandle(int mouseX, int mouseY) {
        if (orientation == SLIDER_HORIZONTAL) {
            int dy = Math.abs(mouseY - rect.h / 2);
            int dx = Math.abs(mouseX - getHandleCenterX());
            if (dy > handleSize / 2) return false;
            if (dx < handleSize / 2) return true;
        }
        return false;
    }

    public void storeGrabOffset(int mouseX, int mouseY) {
        grabOffsetY = mouseY - rect.h / 2;
        grabOffsetX = mouseX - getHandleCenterX();
    }

    @Override
    public void onMessage(GuiMessage guiMessage, Object object) {
        MouseMessageData md = (MouseMessageData) object;

        if (guiMessage == MOUSE_MOVE) {
            if (!grabbed) {
                setMouseOverHandle(isMouseOverHandle(md.x, md.y));
            } else {
                setValueFromPixel(md.x - grabOffsetX);
                dirty = true;
            }
        }

        if (guiMessage == MOUSE_BUTTON_DOWN) {
            if (mouseOverHandle && !grabbed) {
                setGrabbed(true);
                storeGrabOffset(md.x, md.y);
            }
            if (grabbed) {
                setValueFromPixel(md.x - grabOffsetX);
                dirty = true;
            }
        }

        if (guiMessage == MOUSE_BUTTON_UP) {
            setGrabbed(false);
        }
    }

    private void setMouseOverHandle(boolean value) {
        if (mouseOverHandle != value) {
            mouseOverHandle = value;
            setDirty(true);
        }
    }

    private void setGrabbed(boolean value) {
        if (grabbed != value) {
            grabbed = value;
            setDirty(true);
        }
    }

    // Convert a pixel X (absolute in slider local coords) into normalized [0..1]
    private void setValueFromPixel(int pixelX) {
        int pos = pixelX - endPadding;
        int clampedPos = Math.max(0, Math.min(trackLength, pos));
        double newValue = (trackLength == 0) ? 0.0 : ((double) clampedPos / (double) trackLength);
        setValueInternal(newValue, true);
    }

    private void setValueInternal(double newValue, boolean fireEvent) {
        double clamped = Math.max(0.0, Math.min(1.0, newValue));
        if (clamped != this.value) {
            this.value = clamped;
            if (fireEvent && onChanged != null) onChanged.accept(this.value);
        }
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        setValueInternal(value, false);
        setDirty(true);
    }

    // External setter that updates the value and notifies listeners
    public void setValueAndNotify(double value) {
        setValueInternal(value, true);
        setDirty(true);
    }
}