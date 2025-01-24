package com.physmo.reference.gallery;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.DrawingContext;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.Utils;

import java.awt.Color;

public class GasketApp extends MinvioApp {

    // Define 3 points representing the 3 points of the triangle.
    double[] pointList = {0.5, 0.1, 0.1, 0.9, 0.9, 0.9};
    double x = pointList[0];
    double y = pointList[1];
    int loopCount = 0;

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
        for (int i = 0; i < 50; i++) {
            loopCount++;

            // Move point towards a randomly selected corner.
            int pointIndex = (int) (Math.random() * 3);
            x = (x + pointList[pointIndex * 2]) / 2;
            y = (y + pointList[(pointIndex * 2) + 1]) / 2;

            // Draw the point.
            drawFilledRect((int) (x * 400), (int) (y * 400), 2, 2);

            // Chose a random distinct colour every so often.
            if (loopCount % 50000 == 0)
                setDrawColor(Utils.getDistinctColor((int) (Math.random() * 100), 0.7));
        }
    }


}
