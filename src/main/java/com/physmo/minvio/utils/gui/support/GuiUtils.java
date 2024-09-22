package com.physmo.minvio.utils.gui.support;

import com.physmo.minvio.DrawingContext;
import com.physmo.minvio.types.Rect;

import java.awt.Color;
import java.awt.Font;

import static com.physmo.minvio.BasicDisplay.TEXT_SIZE_DESCENT;
import static com.physmo.minvio.BasicDisplay.TEXT_SIZE_WIDTH;

public class GuiUtils {
    static Color colBG = new Color(201, 201, 201, 255);
    static Color colLight = new Color(255, 255, 255, 210);
    static Color colShade = new Color(0, 0, 0, 210);

    public static void drawBevelBorderOut(DrawingContext dc, GuiStyle guiStyle, int x, int y, int w, int h) {
        w -= 1;
        h -= 1;
        dc.setDrawColor(guiStyle.getBevelLight());
        dc.drawLine(x, y, x + w, y);
        dc.drawLine(x, y, x, y + h);
        dc.setDrawColor(guiStyle.getBevelDark());
        dc.drawLine(x + w, y, x + w, y + h);
        dc.drawLine(x, y + h, x + w, y + h);
    }

    public static void drawBevelBorderIn(DrawingContext dc, GuiStyle guiStyle, int x, int y, int w, int h) {
        w -= 1;
        h -= 1;
        dc.setDrawColor(guiStyle.getBevelDark());
        dc.drawLine(x, y, x + w, y);
        dc.drawLine(x, y, x, y + h);
        dc.setDrawColor(guiStyle.getBevelLight());
        dc.drawLine(x + w, y, x + w, y + h);
        dc.drawLine(x, y + h, x + w, y + h);
    }

    public static void drawTextWithinRect(DrawingContext dc, Rect rect, GuiStyle guiStyle, Font font, String text) {
        drawTextWithinRect(dc, rect, guiStyle, font, text, 0, 0);
    }

    public static void drawTextWithinRect(DrawingContext dc, Rect rect, GuiStyle guiStyle, Font font, String text, int offsetX, int offsetY) {
        dc.setDrawColor(guiStyle.getTextColor());

        dc.setFont(font);
        int[] textSize = dc.getTextSize(text);
        int textWidth = textSize[TEXT_SIZE_WIDTH];
        int textDescent = textSize[TEXT_SIZE_DESCENT];
        dc.drawText(text, offsetX + (rect.w / 2) - (textWidth / 2), offsetY + (rect.h / 2) + (textDescent));
    }
}
