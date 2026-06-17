package com.physmo.minvio.utils.gui;

import com.physmo.minvio.types.Rect;
import com.physmo.minvio.utils.gui.support.GuiMessage;
import com.physmo.minvio.utils.gui.support.GuiUtils;

import java.awt.Font;

/**
 * Static text label with optional beveled border.
 */
public class GuiLabel extends GuiContainer {

    private final Font font = new Font("Verdana", Font.PLAIN, 15);
    private String text = "blank";
    private boolean showBorder = false;

    /**
     * Creates a label.
     *
     * @param rect label bounds
     * @param text initial label text
     */
    public GuiLabel(Rect rect, String text) {
        super(rect);
        this.text = text;
    }

    /** @return whether the label draws a beveled border */
    public boolean isShowBorder() {
        return showBorder;
    }

    /**
     * Sets border visibility and marks the label dirty.
     *
     * @param showBorder true to draw a border
     */
    public void setShowBorder(boolean showBorder) {
        this.showBorder = showBorder;
        setDirty(true);
    }

    @Override
    public void draw(GuiContext guiContext) {

        if (showBorder) {
            GuiUtils.drawBevelBorderOut(dc, guiContext.getGuiStyle(), 0, 0, rect.w, rect.h);
        } else {
            dc.setDrawColor(guiContext.getGuiStyle().getBackgroundColor());
            dc.drawFilledRect(0, 0, rect.w, rect.h);
        }

        // todo: don't pass style if not required
        GuiUtils.drawTextWithinRect(dc, rect, guiContext.getGuiStyle(), font, text);

    }


    @Override
    public void onMessage(GuiMessage guiMessage, Object object) {

    }

    /**
     * Sets label text and marks the label dirty.
     *
     * @param text replacement text
     */
    public void setText(String text) {
        this.text = text;
        this.setDirty(true);
    }
}
