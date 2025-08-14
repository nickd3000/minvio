package com.physmo.minvio.types;

import java.util.Objects;

public class Point {

    public double x, y;

    public Point() {
        x = 0;
        y = 0;
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point p) {
        if (p == null) throw new IllegalArgumentException("Point cannot be null");
        this.x = p.x;
        this.y = p.y;
    }

    public void add(Point other) {
        if (other == null) throw new IllegalArgumentException("Other point cannot be null");
        this.x += other.x;
        this.y += other.y;
    }

    public void add(double x, double y) {
        this.x += x;
        this.y += y;
    }

    public void multiply(double val) {
        this.x *= val;
        this.y *= val;
    }

    public static double distance(Point p1, Point p2) {
        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;
        return Math.sqrt((dx * dx) + (dy * dy));
    }

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
        return String.format("Point{x=%.2f, y=%.2f}", x, y);
    }
}