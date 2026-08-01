package com.physmo.minvio.types;

import java.util.Locale;
import java.util.Objects;

/**
 * Mutable two-dimensional point using double-precision coordinates.
 *
 * <p>Because coordinates are public and mutable, do not mutate an instance
 * while it is used as a key in a hash-based collection.</p>
 */
public class Point {

    /**
     * Mutable x and y coordinates.
     */
    public double x, y;

    /** Creates a point at the origin. */
    public Point() {
        x = 0;
        y = 0;
    }

    /**
     * Creates a point with the supplied coordinates.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a coordinate copy of another point.
     *
     * @param p point to copy
     * @throws IllegalArgumentException if {@code p} is null
     */
    public Point(Point p) {
        if (p == null) throw new IllegalArgumentException("Point cannot be null");
        this.x = p.x;
        this.y = p.y;
    }

    /**
     * Adds another point's coordinates to this point in place.
     *
     * @param other point to add
     * @throws IllegalArgumentException if {@code other} is null
     */
    public void add(Point other) {
        if (other == null) throw new IllegalArgumentException("Other point cannot be null");
        this.x += other.x;
        this.y += other.y;
    }

    /**
     * Adds coordinate offsets to this point in place.
     *
     * @param x x offset
     * @param y y offset
     */
    public void add(double x, double y) {
        this.x += x;
        this.y += y;
    }

    /**
     * Multiplies both coordinates in place.
     *
     * @param val scale factor
     */
    public void multiply(double val) {
        this.x *= val;
        this.y *= val;
    }

    /**
     * Returns the Euclidean distance between two points.
     *
     * @param p1 first point
     * @param p2 second point
     * @return Euclidean distance
     * @throws NullPointerException if either point is null
     */
    public static double distance(Point p1, Point p2) {
        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;
        return Math.sqrt((dx * dx) + (dy * dy));
    }

    /**
     * Returns the Euclidean distance from this point to another.
     *
     * @param other destination point
     * @return Euclidean distance
     * @throws NullPointerException if {@code other} is null
     */
    public double distance(Point other) {
        return distance(this, other);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Point point = (Point) obj;
        return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0;
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "Point{x=%.2f, y=%.2f}", x, y);
    }
}
