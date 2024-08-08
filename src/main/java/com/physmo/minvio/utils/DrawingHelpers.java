package com.physmo.minvio.utils;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.DrawingContext;

public class DrawingHelpers {

    public static void drawGrid(BasicDisplay bd, int x, int y, int width, int height, int numSegments) {
        int horizontalSpace = width / numSegments;
        int verticalSpace = height / numSegments;
        DrawingContext dc = bd.getDrawingContext();
        for (int i = 0; i <= numSegments; i++) {
            dc.drawLine(x + (i * horizontalSpace), y, x + (i * horizontalSpace), y + height);
            dc.drawLine(x, y + (i * verticalSpace), x + width, y + (i * verticalSpace));
        }
    }

}
