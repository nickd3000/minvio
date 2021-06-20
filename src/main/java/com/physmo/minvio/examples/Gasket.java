package com.physmo.minvio.examples;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;

import java.awt.*;

class Gasket {


    public static void main(String... args) {
        BasicDisplay bd = new BasicDisplayAwt(400, 400);

        bd.setTitle("Gasket");

        // Define 3 points representing the 3 points of the triangle.
        double[] pointList = {0.5, 0.1, 0.1, 0.9, 0.9, 0.9};

        // Set the initial position for the point.
        double x = pointList[0];
        double y = pointList[1];

        // Clear the screen to dark gray.
        bd.cls(Color.darkGray);
        bd.setDrawColor(Color.BLUE);
        int loopCount = 0;

        // Loop forever.
        while (true) {
            loopCount++;

            // Move point towards a randomly selected corner.
            int pointIndex = (int) (Math.random() * 3);
            x = (x + pointList[pointIndex * 2]) / 2;
            y = (y + pointList[(pointIndex * 2) + 1]) / 2;

            // Draw the point.
            bd.drawFilledRect((int) (x * 400), (int) (y * 400), 2, 2);

            // Refresh screen every 200 pixels drawn.
            if (loopCount % 200 == 0)
                bd.repaint(60);

            // Chose a random distinct colour every so often.
            if (loopCount % 50000 == 0)
                bd.setDrawColor(bd.getDistinctColor((int) (Math.random() * 100), 0.7));

        }
    }
}
