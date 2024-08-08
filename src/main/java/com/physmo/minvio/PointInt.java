package com.physmo.minvio;


public class PointInt {

    public int x, y;

    public PointInt(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void add(PointInt other) {
        this.x += other.x;
        this.y += other.y;
    }

    public void add(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public static double distance(PointInt p1, PointInt p2) {
        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;
        return Math.sqrt((dx * dx) + (dy * dy));
    }

    public String toString() {
        return "[" + x + "," + y + "]";
    }

}
