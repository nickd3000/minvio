package com.physmo.minvio.utils;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.DrawingContext;

/**
 * Convenience drawing routines built on {@link DrawingContext}.
 */
public class DrawingHelpers {

    /**
     * Draws a rectangular grid with {@code numSegments + 1} lines in each
     * direction.
     *
     * <p>Integer division determines spacing, so the final line may not reach
     * the requested far edge when dimensions are not divisible by the segment
     * count.</p>
     *
     * @param bd target display
     * @param x grid x-coordinate
     * @param y grid y-coordinate
     * @param width grid width
     * @param height grid height
     * @param numSegments positive segment count
     * @throws IllegalArgumentException if {@code numSegments} is not positive
     */
    public static void drawGrid(BasicDisplay bd, int x, int y, int width, int height, int numSegments) {
        if (numSegments <= 0) {
            throw new IllegalArgumentException("Number of segments must be greater than zero");
        }
        int horizontalSpace = width / numSegments;
        int verticalSpace = height / numSegments;
        DrawingContext dc = bd.getDrawingContext();
        for (int i = 0; i <= numSegments; i++) {
            dc.drawLine(x + (i * horizontalSpace), y, x + (i * horizontalSpace), y + height);
            dc.drawLine(x, y + (i * verticalSpace), x + width, y + (i * verticalSpace));
        }
    }

}
