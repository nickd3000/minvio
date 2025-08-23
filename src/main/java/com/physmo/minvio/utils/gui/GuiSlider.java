package com.physmo.minvio.utils.gui;

import com.physmo.minvio.types.Rect;
import com.physmo.minvio.utils.Palette;
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
    int sliderPosition = 30;
    int sliderPositionMax = 30;
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
        sliderPositionMax = rect.w - (endPadding * 2);
    }

    @Override
    public void draw(GuiContext guiContext) {
        recalculateMetrics();

        //GuiUtils.drawBevelBoxOut(dc, guiContext.getGuiStyle(), 0, 0, rect.w, rect.h);
        GuiStyle guiStyle = guiContext.getGuiStyle();

        dc.setDrawColor(guiStyle.getBackgroundColor());
        dc.drawFilledRect(0, 0, rect.w, rect.h);

        // tmp
        dc.setDrawColor(Palette.BRICK);
        dc.drawRect(0, 0, rect.w, rect.h);


        if (orientation == SLIDER_HORIZONTAL) {
            dc.setDrawColor(guiStyle.getAccent());

            dc.drawFilledRect(endPadding, (rect.h / 2) - 1, sliderPosition, 2);
            dc.setDrawColor(guiStyle.getBevelDark());
            dc.drawFilledRect(endPadding + sliderPosition, (rect.h / 2) - 1, sliderPositionMax - sliderPosition, 2);


            dc.setDrawColor(guiStyle.getBevelLight());

            // Handle outline
            if (grabbed || mouseOverHandle) {
                dc.setDrawColor(guiStyle.getAccent());
                dc.drawFilledCircle((double) endPadding + sliderPosition, (double) rect.h / 2, 1 + (double) handleSize / 2);
            }

            dc.setDrawColor(guiStyle.getBevelLight());
            // Central part of handle
            dc.drawFilledCircle((double) endPadding + sliderPosition, (double) rect.h / 2, (double) handleSize / 2);

        }
    }

    public void setOnChangedHandler(DoubleConsumer onChanged) {
        this.onChanged = onChanged;
    }

    public boolean isMouseOverHandle(int mouseX, int mouseY) {
        if (orientation == SLIDER_HORIZONTAL) {
            int dy = Math.abs(mouseY - rect.h / 2);
            int dx = Math.abs(mouseX - (endPadding + sliderPosition));
            if (dy > handleSize / 2) return false;
            if (dx < handleSize / 2) return true;
        }
        return false;
    }

    public void storeGrabOffset(int mouseX, int mouseY) {
        grabOffsetY = mouseY - rect.h / 2;
        grabOffsetX = mouseX - (endPadding + sliderPosition);
    }

    @Override
    public void onMessage(GuiMessage guiMessage, Object object) {
        MouseMessageData md = (MouseMessageData) object;

        if (guiMessage == MOUSE_MOVE) {
            if (!grabbed) {
                setMouseOverHandle(isMouseOverHandle(md.x, md.y));
            } else {
                setSliderPosition(md.x - grabOffsetX);
                dirty = true;
            }
        }

        if (guiMessage == MOUSE_BUTTON_DOWN) {
            if (mouseOverHandle && !grabbed) {
                setGrabbed(true);
                storeGrabOffset(md.x, md.y);
            }
            if (grabbed) {
                setSliderPosition(md.x - grabOffsetX);
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

    private void setSliderPosition(int value) {
        sliderPosition = value - (endPadding);
        clampSliderPosition();
        if (onChanged != null) onChanged.accept(getValue());
    }

    private void clampSliderPosition() {
        if (sliderPosition < 0) sliderPosition = 0;
        if (sliderPosition > sliderPositionMax) sliderPosition = sliderPositionMax;
    }

    public double getValue() {
        return (double) sliderPosition / (double) sliderPositionMax;
    }

    public void setValue(double value) {
        sliderPosition = (int) (value * sliderPositionMax);
        setDirty(true);
    }
}
