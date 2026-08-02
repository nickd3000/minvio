package com.physmo.minvio.types;

import java.util.Locale;
import java.util.Objects;

/**
 * Mutable integer rectangle represented by an origin, width, and height.
 *
 * <p>Containment and intersection methods treat right and bottom boundaries as
 * inclusive.</p>
 */
public class Rect {
    /**
     * Mutable x, y, width, and height values.
     */
    public int x, y, w, h;

    /**
     * Creates a rectangle.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @param w width; not validated
     * @param h height; not validated
     */
    public Rect(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    /**
     * Creates a coordinate copy of another rectangle.
     *
     * @param rect rectangle to copy
     * @throws NullPointerException if {@code rect} is null
     */
    public Rect(Rect rect) {
        this.x = rect.x;
        this.y = rect.y;
        this.w = rect.w;
        this.h = rect.h;
    }

    /**
     * Tests whether a point lies within the inclusive rectangle boundaries.
     *
     * @param xx point x-coordinate
     * @param yy point y-coordinate
     * @return {@code true} when the point is inside or on an edge
     */
    public boolean isPointInside(int xx, int yy) {
        if (xx < x || yy < y) return false;
        return xx <= x + w && yy <= y + h;
    }

    /**
     * Tests whether this rectangle overlaps or touches another rectangle.
     *
     * @param other rectangle to test
     * @return {@code true} for overlap or edge contact
     * @throws NullPointerException if {@code other} is null
     */
    public boolean intersects(Rect other) {
        return !(other.x > x + w ||
                other.x + other.w < x ||
                other.y > y + h ||
                other.y + other.h < y);
    }

    /**
     * Returns an independent coordinate copy.
     *
     * @return copied rectangle
     */
    public Rect copy() {
        return new Rect(x, y, w, h);
    }

    /**
     * Returns width multiplied by height.
     *
     * @return signed area product; integer overflow is not checked
     */
    public int getArea() {
        return w * h;
    }

    /**
     * Tests whether this rectangle fully contains another rectangle, including
     * shared boundaries.
     *
     * @param other rectangle to test
     * @return {@code true} when fully contained
     * @throws NullPointerException if {@code other} is null
     */
    public boolean contains(Rect other) {
        return other.x >= x &&
                other.y >= y &&
                other.x + other.w <= x + w &&
                other.y + other.h <= y + h;
    }

    /** @return integer horizontal center using truncating division */
    public int getCenterX() {
        return x + w / 2;
    }

    /** @return integer vertical center using truncating division */
    public int getCenterY() {
        return y + h / 2;
    }

    /**
     * Replaces all rectangle values.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @param w width
     * @param h height
     */
    public void set(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    /**
     * Copies all values from another rectangle.
     *
     * @param other rectangle to copy
     * @throws NullPointerException if {@code other} is null
     */
    public void set(Rect other) {
        this.x = other.x;
        this.y = other.y;
        this.w = other.w;
        this.h = other.h;
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "Rect{x=%d, y=%d, w=%d, h=%d}", x, y, w, h);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Rect rect = (Rect) obj;
        return x == rect.x && y == rect.y && w == rect.w && h == rect.h;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, w, h);
    }
}
