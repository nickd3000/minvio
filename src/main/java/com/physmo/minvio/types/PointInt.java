package com.physmo.minvio.types;


/**
 * Mutable two-dimensional point using integer coordinates.
 */
public class PointInt {

    /** Mutable x and y coordinates. */
    public int x, y;

    /**
     * Creates a point with the supplied coordinates.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public PointInt(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Adds another point's coordinates to this point in place.
     *
     * @param other point to add
     * @throws NullPointerException if {@code other} is null
     */
    public void add(PointInt other) {
        this.x += other.x;
        this.y += other.y;
    }

    /**
     * Adds coordinate offsets to this point in place.
     *
     * @param x x offset
     * @param y y offset
     */
    public void add(int x, int y) {
        this.x += x;
        this.y += y;
    }

    /**
     * Returns the Euclidean distance between two integer points.
     *
     * @param p1 first point
     * @param p2 second point
     * @return Euclidean distance
     * @throws NullPointerException if either point is null
     */
    public static double distance(PointInt p1, PointInt p2) {
        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;
        return Math.sqrt((dx * dx) + (dy * dy));
    }

    public String toString() {
        return "[" + x + "," + y + "]";
    }

}
