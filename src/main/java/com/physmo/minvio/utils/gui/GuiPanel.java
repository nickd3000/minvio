package com.physmo.minvio.utils.gui;

import com.physmo.minvio.Rect;
import com.physmo.minvio.utils.gui.support.GuiMessage;

import java.awt.Color;

public class GuiPanel extends GuiContainer {

    public GuiPanel(Rect rect) {
        super(rect);
    }

    @Override
    public void draw() {
        //System.out.println("drawing GuiPanel");
        dc.setDrawColor(Color.RED);
        dc.drawLine(0, 0, dc.getWidth(), dc.getHeight());
        dc.drawLine(0, dc.getHeight(), dc.getWidth(), 0);
    }

    @Override
    public void onMessage(GuiMessage guiMessage, Object object) {

    }


}
