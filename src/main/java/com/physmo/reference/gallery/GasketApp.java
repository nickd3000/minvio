package com.physmo.reference.gallery;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.DrawingContext;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.Utils;
import com.physmo.minvio.types.Point;

import java.awt.Color;

/**
 * The GasketApp class extends MinvioApp to render a fractal pattern known
 * as the Sierpinski Gasket. It achieves this by iteratively plotting points
 * that are half the distance between a current point and a randomly selected
 * vertex of a triangle. Over time, the rendered points form the fractal pattern.
 */
public class GasketApp extends MinvioApp {

    // Define 3 points representing the 3 vertices of the triangle.
    Point[] pointList = {
            new Point(0.5, 0.1),
            new Point(0.1, 0.9),
            new Point(0.9, 0.9)
    };

    Point currentPoint = new Point(pointList[0]); // Start at the first point.
    int loopCount = 0;
    int iterationsPerFrame = 50;

    public static void main(String... args) {
        MinvioApp app = new GasketApp();
        app.start(400, 400, "Gasket", 30);
    }

    @Override
    public void init(BasicDisplay bd) {
        DrawingContext dc = bd.getDrawingContext();
        dc.cls(Color.darkGray);
        dc.setDrawColor(Color.BLUE);
    }

    @Override
    public void draw(double delta) {
        for (int i = 0; i < iterationsPerFrame; i++) {
            loopCount++;

            // Move the current point towards a randomly selected corner.
            int pointIndex = (int) (Math.random() * 3);
            Point selectedPoint = pointList[pointIndex];

            currentPoint.add(selectedPoint);    // Add the selected point to the current point.
            currentPoint.multiply(0.5);     // Scale the result to compute the midpoint.

            // Draw the point.
            drawFilledRect((int) (currentPoint.x * 400), (int) (currentPoint.y * 400), 2, 2);

            // Choose a random distinct color every so often.
            if (loopCount % 50000 == 0) {
                setDrawColor(Utils.getDistinctColor((int) (Math.random() * 100), 0.7));
            }
        }
    }
}
