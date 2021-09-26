package com.physmo.minvio.utils;

/**
 * An object that manages a rolling average of double values.
 */
public class RollingAverage {

    final int size;
    int index = 0;
    final double[] values;
    double sum = 0;
    double average = 0;

    /**
     * @param size The number of items to average.
     */
    public RollingAverage(int size) {
        this.size = size;
        values = new double[size];
    }

    public void add(double v) {
        sum -= values[index];
        sum += v;
        values[index] = v;
        index = (index + 1) % size;
        average = sum / (double) size;
    }

    public double getSum() {
        return sum;
    }

    public double getAverage() {
        return average;
    }

}
