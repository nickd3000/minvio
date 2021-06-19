package com.physmo.minvio;

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
        this.x = p.x;
        this.y = p.y;
    }

    public void add(Point other) {
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

    public double distance(Point other) {
        return distance(this, other);
    }

    public static double distance(Point p1, Point p2) {
        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;
        return Math.sqrt((dx * dx) + (dy * dy));
    }

    public String toString() {
        return "[" + x + "," + y + "]";
    }

}
