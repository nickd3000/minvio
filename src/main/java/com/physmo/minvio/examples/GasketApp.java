package com.physmo.minvio.examples;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.MinvioApp;

import java.awt.Color;

public class GasketApp extends MinvioApp {

    // Define 3 points representing the 3 points of the triangle.
    double[] pointList = {0.5, 0.1, 0.1, 0.9, 0.9, 0.9};
    double x = pointList[0];
    double y = pointList[1];
    int loopCount = 0;

    public static void main(String... args) {
        MinvioApp app = new GasketApp();
        app.start(new BasicDisplayAwt(400, 400), "Gasket", 30);
    }

    @Override
    public void init(BasicDisplay bd) {
        bd.cls(Color.darkGray);
        bd.setDrawColor(Color.BLUE);
    }

    @Override
    public void draw(BasicDisplay bd, double delta) {
        for (int i = 0; i < 50; i++) {
            loopCount++;

            // Move point towards a randomly selected corner.
            int pointIndex = (int) (Math.random() * 3);
            x = (x + pointList[pointIndex * 2]) / 2;
            y = (y + pointList[(pointIndex * 2) + 1]) / 2;

            // Draw the point.
            bd.drawFilledRect((int) (x * 400), (int) (y * 400), 2, 2);

            // Chose a random distinct colour every so often.
            if (loopCount % 50000 == 0)
                bd.setDrawColor(bd.getDistinctColor((int) (Math.random() * 100), 0.7));
        }
    }


}
