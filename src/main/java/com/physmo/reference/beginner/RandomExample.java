package com.physmo.reference.beginner;

import com.physmo.minvio.MinvioApp;

import java.awt.Color;

/**
 * Teaches random numbers by drawing new stars each frame.
 */
class RandomExample extends MinvioApp {

    public static void main(String... args) {
        MinvioApp app = new RandomExample();
        app.start(400, 300, "Random Example", 60);
    }

    @Override
    public void draw(double delta) {
        cls(new Color(18, 24, 35));

        for (int i = 0; i < 80; i++) {
            // Math.random returns a double from 0.0 up to, but not including, 1.0.
            double x = Math.random() * getWidth();
            double y = Math.random() * getHeight();
            double size = 1 + Math.random() * 3;

            setDrawColor(new Color(190, 220, 255));
            drawFilledCircle(x, y, size);
        }
    }
}
