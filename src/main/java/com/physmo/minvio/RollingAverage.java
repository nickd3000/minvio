package com.physmo.minvio;

public class RollingAverage {

    int size = 0;
    int index = 0;
    double[] values;
    double sum = 0;
    double average = 0;

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

    ;

    public double getAverage() {
        return average;
    }

    ;
}
