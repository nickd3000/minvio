package com.physmo.minvio.utils;

/**
 * A utility class for calculating a rolling average over a fixed number of values.
 *
 * This class maintains a fixed-size array to store the most recent values and automatically 
 * calculates their sum and average whenever a new value is added. The oldest value is 
 * automatically discarded when the array reaches its maximum size.
 */
public class RollingAverage {

    final int size;
    int index = 0;
    final double[] values;
    double sum = 0;
    double average = 0;
    private int count = 0;  // Tracks the number of added values

    /**
     * @param size The number of items to average.
     */
    public RollingAverage(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be greater than zero.");
        }
        this.size = size;
        values = new double[size];
    }

    public void add(double v) {
        sum -= values[index];
        sum += v;
        values[index] = v;
        index = (index + 1) % size;
        count = Math.min(count + 1, size); // Increment count up to the buffer size
        average = sum / count;            // Average based on number of elements
    }

    public double getSum() {
        return sum;
    }

    public double getAverage() {
        return average;
    }

}