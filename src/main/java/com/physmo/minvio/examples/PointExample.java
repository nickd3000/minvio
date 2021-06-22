package com.physmo.minvio.examples;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.BasicUtils;
import com.physmo.minvio.Point;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PointExample {
    public static void main(String... args) {

        Color colBackground = new Color(27, 74, 121);
        Color colDots = new Color(75, 190, 190);
        Color colLines = new Color(243, 221, 154);
        Point mousePos;

        BasicDisplay bd = new BasicDisplayAwt(300, 300);

        bd.setTitle("Point Example");

        List<Point> points = new ArrayList<Point>();

        // Create a list of random points within a circle.
        for (int i = 0; i < 100; i++) {
            points.add(BasicUtils.createRandomPointInCircle(bd.getWidth() / 2, bd.getHeight() / 2, 130));
        }

        while (true) {

            // Get mouse position as a Point.
            mousePos = bd.getMousePoint();

            // Clear window.
            bd.cls(colBackground);

            // Draw lines.
            bd.setDrawColor(colLines);
            for (Point p : points) {
                bd.drawLine(mousePos, p);
            }

            // Draw dots.
            bd.setDrawColor(colDots);
            for (Point p : points) {
                bd.drawFilledCircle(p, 3);
            }

            BasicUtils.drawCursorPosition(bd, 5, 5);

            // Refresh the window.
            bd.repaint(60);
        }
    }
}
