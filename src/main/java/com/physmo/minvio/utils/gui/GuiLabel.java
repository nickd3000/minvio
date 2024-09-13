package com.physmo.minvio.utils.gui;

import com.physmo.minvio.Rect;
import com.physmo.minvio.utils.gui.support.GuiMessage;
import com.physmo.minvio.utils.gui.support.GuiUtils;

import java.awt.Font;

public class GuiLabel extends GuiContainer {

    private final Font font = new Font("Verdana", Font.PLAIN, 15);
    private String text = "blank";
    private boolean showBorder = false;

    public GuiLabel(Rect rect, String text) {
        super(rect);
        this.text = text;
    }

    public boolean isShowBorder() {
        return showBorder;
    }

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

    public void setText(String text) {
        this.text = text;
        this.setDirty(true);
    }
}
