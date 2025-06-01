package com.physmo.minvio.utils.gui;

import com.physmo.minvio.types.Rect;
import com.physmo.minvio.utils.gui.support.GuiMessage;

public class GuiPanel extends GuiContainer {

    public GuiPanel(Rect rect) {
        super(rect);
    }

    @Override
    public void draw(GuiContext guiContext) {
        dc.setDrawColor(guiContext.getGuiStyle().getBackgroundColor());
        dc.drawFilledRect(0, 0, this.rect.w, this.rect.h);
    }

    @Override
    public void onMessage(GuiMessage guiMessage, Object object) {

    }


}
