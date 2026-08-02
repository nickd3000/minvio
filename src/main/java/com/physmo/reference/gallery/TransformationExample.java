package com.physmo.reference.gallery;

import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.Palette;

public class TransformationExample extends MinvioApp {

    double time = 0;

    public static void main(String... args) {
        MinvioApp app = new TransformationExample();
        app.start(600, 600, "Transformation Example", 60);
    }

    @Override
    public void draw(double delta) {
        time += delta;
        cls(Palette.BLACK);

        pushMatrix();
        // Move to the center of the screen
        translate(getWidth() / 2.0, getHeight() / 2.0);

        // Draw a central "Sun"
        setDrawColor(Palette.YELLOW);
        drawFilledCircle(0, 0, 40);

        // Orbiting Planet
        pushMatrix();
        rotate(time * 0.5); // Orbit speed
        translate(150, 0);  // Distance from Sun

        setDrawColor(Palette.BLUE);
        drawFilledCircle(0, 0, 20); // The Planet

        // Orbiting Moon
        pushMatrix();
        rotate(time * 2.0); // Moon orbit speed
        translate(40, 0);   // Distance from Planet

        setDrawColor(Palette.GRAY_300);
        drawFilledCircle(0, 0, 8); // The Moon
        popMatrix();

        // Second Moon with scaling
        pushMatrix();
        rotate(-time * 1.2);
        translate(60, 0);
        double s = 1.0 + Math.sin(time * 3) * 0.5;
        scale(s); // Pulsating moon

        setDrawColor(Palette.ORANGE);
        drawFilledRect(-5, -5, 10, 10);
        popMatrix();

        popMatrix();

        // Another object using global translation
        pushMatrix();
        rotate(-time * 0.2);
        translate(250, 0);
        rotate(time);
        setDrawColor(Palette.GREEN);
        drawRect(-15, -15, 30, 30);
        popMatrix();
        popMatrix();
    }
}
