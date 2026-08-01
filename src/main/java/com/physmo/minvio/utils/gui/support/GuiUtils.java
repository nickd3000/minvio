package com.physmo.minvio.utils.gui.support;

import com.physmo.minvio.DrawingContext;
import com.physmo.minvio.types.Rect;

import java.awt.Color;
import java.awt.Font;

import static com.physmo.minvio.BasicDisplay.TEXT_SIZE_DESCENT;
import static com.physmo.minvio.BasicDisplay.TEXT_SIZE_WIDTH;

/**
 * Drawing helpers for retained GUI controls.
 */
public class GuiUtils {
    private static final Color COL_BG = new Color(201, 201, 201, 255);
    private static final Color COL_LIGHT = new Color(255, 255, 255, 210);
    private static final Color COL_SHADE = new Color(0, 0, 0, 210);

    /**
     * Draws an outward bevel border.
     *
     * @param dc target drawing context
     * @param guiStyle style supplying bevel colors
     * @param x border x-coordinate
     * @param y border y-coordinate
     * @param w border width
     * @param h border height
     */
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

    /**
     * Draws an inward bevel border.
     *
     * @param dc target drawing context
     * @param guiStyle style supplying bevel colors
     * @param x border x-coordinate
     * @param y border y-coordinate
     * @param w border width
     * @param h border height
     */
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

    /**
     * Draws centered text inside a rectangle.
     *
     * @param dc target drawing context
     * @param rect rectangle used for centering
     * @param guiStyle style supplying text color
     * @param font font to set before measuring and drawing
     * @param text text to draw
     */
    public static void drawTextWithinRect(DrawingContext dc, Rect rect, GuiStyle guiStyle, Font font, String text) {
        drawTextWithinRect(dc, rect, guiStyle, font, text, 0, 0);
    }

    /**
     * Draws centered text inside a rectangle with additional offsets.
     *
     * <p>This method changes the drawing context's active color and font and
     * does not restore their previous values.</p>
     *
     * @param dc target drawing context
     * @param rect rectangle used for centering
     * @param guiStyle style supplying text color
     * @param font font to set before measuring and drawing
     * @param text text to draw
     * @param offsetX additional x offset
     * @param offsetY additional y offset
     */
    public static void drawTextWithinRect(DrawingContext dc, Rect rect, GuiStyle guiStyle, Font font, String text, int offsetX, int offsetY) {
        dc.setDrawColor(guiStyle.getTextColor());

        dc.setFont(font);
        int[] textSize = dc.getTextSize(text);
        int textWidth = textSize[TEXT_SIZE_WIDTH];
        int textDescent = textSize[TEXT_SIZE_DESCENT];
        dc.drawText(text, offsetX + (rect.w / 2) - (textWidth / 2), offsetY + (rect.h / 2) + (textDescent));
    }
}
