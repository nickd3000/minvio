package com.physmo.minvio;

// TODO: rethink all the point stuff
// we may not need position, just extend point if we have to
// consider making point a templated class too.
public abstract class Position {

    public double x, y;

    public void add(Position other) {
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

    public double distance(Position other) {
        return distance(this, other);
    }

    public static double distance(Position p1, Position p2) {
        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;
        return Math.sqrt((dx * dx) + (dy * dy));
    }

    public String toString() {
        return "[" + x + "," + y + "]";
    }

}
