package com.physmo.minvio.utils.gui.support;

import com.physmo.minvio.DrawingContext;

import java.awt.Color;

public class GuiUtils {
    static Color colBG = new Color(201, 201, 201, 255);
    static Color colLight = new Color(255, 255, 255, 210);
    static Color colShade = new Color(0, 0, 0, 210);

    public static void drawBevelBoxOut(DrawingContext dc, int x, int y, int w, int h) {
        w -= 1;
        h -= 1;
        dc.setDrawColor(colBG);
        dc.drawFilledRect(x, y, x + w, y + w);
        dc.setDrawColor(colLight);
        dc.drawLine(x, y, x + w, y);
        dc.drawLine(x, y, x, y + h);
        dc.setDrawColor(colShade);
        dc.drawLine(x + w, y, x + w, y + h);
        dc.drawLine(x, y + h, x + w, y + h);
    }

    public static void drawBevelBoxIn(DrawingContext dc, int x, int y, int w, int h) {
        w -= 1;
        h -= 1;
        dc.setDrawColor(colBG);
        dc.drawFilledRect(x, y, x + w, y + w);
        dc.setDrawColor(colShade);
        dc.drawLine(x, y, x + w, y);
        dc.drawLine(x, y, x, y + h);
        dc.setDrawColor(colLight);
        dc.drawLine(x + w, y, x + w, y + h);
        dc.drawLine(x, y + h, x + w, y + h);
    }
}
