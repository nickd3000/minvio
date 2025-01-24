package com.physmo.reference.minvioapp;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.types.Point;
import com.physmo.minvio.utils.BasicUtils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class PointExampleApp extends MinvioApp {

    Color colBackground = new Color(27, 74, 121);
    Color colDots = new Color(75, 190, 190);
    Color colLines = new Color(243, 221, 154);

    List<Point> points = new ArrayList<Point>();

    public static void main(String... args) {
        MinvioApp app = new PointExampleApp();
        app.start(new BasicDisplayAwt(300, 300));
    }

    @Override
    public void init(BasicDisplay bd) {
        bd.setTitle("Point Example");

        // Create a list of random points within a circle.
        for (int i = 0; i < 100; i++) {
            points.add(BasicUtils.createRandomPointInCircle(bd.getWidth() / 2, bd.getHeight() / 2, 130));
        }
    }

    @Override
    public void draw(double delta) {
        // Get mouse position as a Point.
        Point mousePos = getBasicDisplay().getMousePoint();

        // Clear window.
        cls(colBackground);

        // Draw lines.
        setDrawColor(colLines);
        for (Point p : points) {
            drawLine(mousePos, p);
        }

        // Draw dots.
        setDrawColor(colDots);
        for (Point p : points) {
            drawFilledCircle(p, 3);
        }

        BasicUtils.drawCursorPosition(getBasicDisplay(), 5, 5);
    }
}
