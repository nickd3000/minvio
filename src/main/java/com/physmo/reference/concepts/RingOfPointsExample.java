package com.physmo.reference.concepts;

import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.types.Point;
import com.physmo.minvio.utils.BasicUtils;

import java.awt.Color;

/**
 * Demonstrates placing evenly spaced points around a circle.
 */
public class RingOfPointsExample extends MinvioApp {

    private double angle;

    public static void main(String... args) {
        MinvioApp app = new RingOfPointsExample();
        app.start(200, 200, "Ring Of Points Example", 30);
    }

    @Override
    public void draw(double delta) {
        angle += delta;

        cls(Color.LIGHT_GRAY);
        setDrawColor(Color.WHITE);

        BasicUtils.ringOfPoints(10, 100, 100, 70, angle, (Point p) -> {
            drawFilledCircle(p.x, p.y, 3);
            BasicUtils.ringOfPoints(3, p.x, p.y, 10, -angle * 2, pp -> drawFilledCircle(pp.x, pp.y, 3));
        });
    }
}
