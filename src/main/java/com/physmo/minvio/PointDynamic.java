package com.physmo.minvio;

/**
 * Extended point type class with velocity
 */
public class PointDynamic extends Position {

    public double dx;
    public double dy;
    public double mass;

    public PointDynamic() {
        x = 0;
        y = 0;
        dx = 0;
        dy = 0;
        mass = 1;
    }

    public PointDynamic(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public PointDynamic(PointDynamic p) {
        this.x = p.x;
        this.y = p.y;
    }
}
