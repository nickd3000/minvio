package com.physmo.minvio.utils;

/**
 * Callback used by {@link MatrixDrawer} to calculate one scalar cell value.
 */
@FunctionalInterface
public interface MonoPixelWorker {
    /**
     * Calculates a cell value.
     *
     * @param x normalized horizontal position
     * @param y normalized vertical position
     * @param d precomputed angle for the cell
     * @param a precomputed normalized distance from the matrix center
     * @param t caller-supplied time value
     * @return value, subsequently clamped to the range {@code [0, 1]}
     */
    double go(double x, double y, double d, double a, double t);
}
