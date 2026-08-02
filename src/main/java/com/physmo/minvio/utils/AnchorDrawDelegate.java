package com.physmo.minvio.utils;

import com.physmo.minvio.DrawingContext;
import com.physmo.minvio.types.Point;

/**
 * Callback responsible for drawing one interactive anchor.
 */
@FunctionalInterface
public interface AnchorDrawDelegate {
    /**
     * Draws an anchor.
     *
     * @param dc active drawing context
     * @param point live mutable anchor position
     * @param radius configured anchor radius
     * @param mouseOver whether the pointer is within the anchor hit area
     * @param grabbed whether the anchor is currently being dragged
     */
    void draw(DrawingContext dc, Point point, double radius, boolean mouseOver, boolean grabbed);
}
