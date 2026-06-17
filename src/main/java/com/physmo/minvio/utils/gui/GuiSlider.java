package com.physmo.minvio.utils.gui;

import com.physmo.minvio.types.Rect;
import com.physmo.minvio.utils.gui.support.GuiMessage;
import com.physmo.minvio.utils.gui.support.GuiStyle;
import com.physmo.minvio.utils.gui.support.MouseMessageData;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleConsumer;

import static com.physmo.minvio.utils.gui.support.GuiMessage.MOUSE_BUTTON_DOWN;
import static com.physmo.minvio.utils.gui.support.GuiMessage.MOUSE_BUTTON_UP;
import static com.physmo.minvio.utils.gui.support.GuiMessage.MOUSE_MOVE;

/**
 * Horizontal normalized-value slider.
 *
 * <p>The current implementation supports horizontal drawing and hit testing
 * only. Values are clamped to the inclusive range zero through one.</p>
 */
public class GuiSlider extends GuiContainer {

    /**
     * Horizontal orientation value; the only fully implemented orientation.
     */
    public static int SLIDER_HORIZONTAL = 1;
    /** Vertical orientation value reserved for future support and not implemented. */
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

    private final List<DoubleConsumer> changeListeners = new ArrayList<>();
    int grabOffsetY;
    int grabOffsetX;

    /**
     * Creates a horizontal slider.
     *
     * @param rect slider bounds
     */
    public GuiSlider(Rect rect) {
        super(rect);
        setHandleSize(14);
        recalculateMetrics();
    }

    /**
     * Sets the handle diameter in pixels and recalculates track metrics.
     *
     * @param val handle size; not validated
     */
    public void setHandleSize(int val) {
        handleSize = val;
        recalculateMetrics();
    }

    /**
     * Recalculates derived track length and padding from current size.
     */
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

    /**
     * Adds a listener invoked when the value changes through notifying paths.
     *
     * @param onChanged listener receiving the new normalized value
     */
    public void addChangeListener(DoubleConsumer onChanged) {
        this.changeListeners.add(onChanged);
    }

    /**
     * Tests whether a local mouse point is inside the horizontal handle.
     *
     * @param mouseX local x-coordinate
     * @param mouseY local y-coordinate
     * @return {@code true} when over the handle
     */
    public boolean isMouseOverHandle(int mouseX, int mouseY) {
        if (orientation == SLIDER_HORIZONTAL) {
            int dy = Math.abs(mouseY - rect.h / 2);
            int dx = Math.abs(mouseX - getHandleCenterX());
            if (dy > handleSize / 2) return false;
            if (dx < handleSize / 2) return true;
        }
        return false;
    }

    /**
     * Stores the offset between a press point and the current handle center.
     *
     * @param mouseX local press x-coordinate
     * @param mouseY local press y-coordinate
     */
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
            if (fireEvent) {
                for (DoubleConsumer listener : changeListeners) {
                    listener.accept(this.value);
                }
            }
        }
    }

    /** @return current normalized slider value */
    public double getValue() {
        return value;
    }

    /**
     * Sets the value without notifying listeners and marks the slider dirty.
     *
     * @param value replacement value, clamped to {@code [0, 1]}
     */
    public void setValue(double value) {
        setValueInternal(value, false);
        setDirty(true);
    }

    /**
     * Sets the value, notifies listeners only if the clamped value changes, and
     * marks the slider dirty.
     *
     * @param value replacement value, clamped to {@code [0, 1]}
     */
    public void setValueAndNotify(double value) {
        setValueInternal(value, true);
        setDirty(true);
    }
}
