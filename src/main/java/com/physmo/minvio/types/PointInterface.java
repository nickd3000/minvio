package com.physmo.minvio.types;

/**
 * Callback that consumes a generated point.
 */
@FunctionalInterface
public interface PointInterface {
    /**
     * Processes a point.
     *
     * @param p generated mutable point
     */
    void process(Point p);
}
