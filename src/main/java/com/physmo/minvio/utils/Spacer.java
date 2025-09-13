
package com.physmo.minvio.utils;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A utility class that provides values at evenly spaced increments.
 * It can be used as an iterator in a for loop.
 *
 * @param <T> the numeric type of the values (e.g., Integer, Double, Float)
 */
public class Spacer<T extends Number> implements Iterator<T>, Iterable<T> {

    private final T start;
    private final T end;
    private final int segments;
    private final int totalPoints;
    private int currentIndex = 0;

    /**
     * Constructs a Spacer with the given start, end, and number of segments.
     *
     * @param start    the starting value
     * @param end      the ending value
     * @param segments the number of segments (spaces between points)
     */
    public Spacer(T start, T end, int segments) {
        this.start = start;
        this.end = end;
        this.segments = segments;
        this.totalPoints = segments + 1; // Number of points is one more than segments
    }

    /**
     * Returns the value at the specified index.
     *
     * @param index the index of the value to retrieve
     * @return the value at the specified index
     */
    @SuppressWarnings("unchecked")
    public T getValue(int index) {
        if (index < 0 || index > segments) {
            throw new IndexOutOfBoundsException("Index must be between 0 and " + segments);
        }

        if (index == 0) return start;
        if (index == segments) return end;

        double startValue = start.doubleValue();
        double endValue = end.doubleValue();
        double step = (endValue - startValue) / segments;
        double value = startValue + (step * index);

        // Convert back to the original type
        if (start instanceof Integer) {
            return (T) Integer.valueOf((int) Math.round(value));
        } else if (start instanceof Double) {
            return (T) Double.valueOf(value);
        } else if (start instanceof Float) {
            return (T) Float.valueOf((float) value);
        } else if (start instanceof Long) {
            return (T) Long.valueOf(Math.round(value));
        } else {
            throw new UnsupportedOperationException("Unsupported number type: " + start.getClass());
        }
    }

    /**
     * Returns the number of points in this spacer.
     *
     * @return the total number of points
     */
    public int getPointCount() {
        return totalPoints;
    }

    /**
     * Returns the number of segments in this spacer.
     *
     * @return the number of segments
     */
    public int getSegments() {
        return segments;
    }

    @Override
    public boolean hasNext() {
        return currentIndex <= segments;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more elements in Spacer");
        }
        return getValue(currentIndex++);
    }

    @Override
    public Iterator<T> iterator() {
        return new Spacer<>(start, end, segments);
    }

    /**
     * Resets the iterator to the beginning.
     */
    public void reset() {
        currentIndex = 0;
    }
}