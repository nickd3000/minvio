package com.physmo.reference.gallery;

import com.physmo.minvio.MinvioApp;

import java.awt.Color;

public class Rotations1 extends MinvioApp {

    public static void main(String... args) {
        MinvioApp app = new Rotations1();
        app.start(600, 600, "Rotations 1", 60);
    }

    double time = 0;
    int numRings = 12;
    double[] rotations;

    @Override
    public void draw(double delta) {
        if (rotations == null) {
            rotations = new double[numRings];
        }

        cls(Color.BLACK);
        time += delta;

        for (int i = 0; i < numRings; i++) {
            double ringIndex = (double) i / numRings;

            double sizeParam = (Math.sin(time * 2 + (0.2 - i * 0.2)) + 1.0) / 2.0;
            double offsetParam = (Math.sin(time + (0.15 - i * 0.002)) + 1.0) / 2.0;
            double colorParam = (Math.sin(time / 2 + (0.05 - i * 0.001)) + 1.0) / 2.0;

            double ringRadius = 50 + i * 50;
            double circleSize = 10 + sizeParam * 5;

            // Incremental rotation update
            double modulation = Math.sin(time + (i * 0.75)) * 0.002;
            rotations[i] += 0.001 + modulation;

            double rotation = rotations[i];

            int numCircles = 8 + i * 4;

            Color ringColor = Color.getHSBColor((float) (colorParam + ringIndex), 0.8f, 1.0f);
            setDrawColor(ringColor);

            drawRingOfCircles(getWidth() / 2.0, getHeight() / 2.0, numCircles, circleSize, ringRadius, rotation);
        }
    }

    public void drawRingOfCircles(double x, double y, int numCircles, double circleSize, double ringRadius, double rotation) {
        for (int i = 0; i < numCircles; i++) {
            double angle = rotation + (Math.PI * 2.0 / numCircles) * i;
            double cx = x + Math.cos(angle) * ringRadius;
            double cy = y + Math.sin(angle) * ringRadius;
            drawFilledCircle(cx, cy, circleSize);
        }
    }


}
