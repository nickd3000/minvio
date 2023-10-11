package com.physmo.minvio.utils.gui.support;

import com.physmo.minvio.DrawingContext;

import java.awt.Color;

public class GuiUtils {
    static Color colBG = new Color(201, 201, 201, 255);
    static Color colLight = new Color(255, 255, 255, 210);
    static Color colShade = new Color(0, 0, 0, 210);

    public static void drawBevelBoxOut(DrawingContext dc, GuiStyle guiStyle, int x, int y, int w, int h) {
        w -= 1;
        h -= 1;
        dc.setDrawColor(guiStyle.getBackgroundColor());
        dc.drawFilledRect(x, y, x + w, y + w);
        dc.setDrawColor(guiStyle.getBevelLight());
        dc.drawLine(x, y, x + w, y);
        dc.drawLine(x, y, x, y + h);
        dc.setDrawColor(guiStyle.getBevelDark());
        dc.drawLine(x + w, y, x + w, y + h);
        dc.drawLine(x, y + h, x + w, y + h);
    }

    public static void drawBevelBoxIn(DrawingContext dc, GuiStyle guiStyle, int x, int y, int w, int h) {
        w -= 1;
        h -= 1;
        dc.setDrawColor(guiStyle.getBackgroundColor());
        dc.drawFilledRect(x, y, x + w, y + w);
        dc.setDrawColor(guiStyle.getBevelDark());
        dc.drawLine(x, y, x + w, y);
        dc.drawLine(x, y, x, y + h);
        dc.setDrawColor(guiStyle.getBevelLight());
        dc.drawLine(x + w, y, x + w, y + h);
        dc.drawLine(x, y + h, x + w, y + h);
    }
}
