package com.physmo.minvio.utils;

import com.physmo.minvio.DrawingContext;
import com.physmo.minvio.types.Point;

/**
 * Callback applied to a point together with a drawing context.
 */
@FunctionalInterface
public interface PointWorker {
    /**
     * Processes a point.
     *
     * @param dc drawing context supplied by the caller
     * @param p point from the processed collection
     */
    void go(DrawingContext dc, Point p);
}
