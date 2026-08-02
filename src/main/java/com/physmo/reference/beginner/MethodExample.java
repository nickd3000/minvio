package com.physmo.reference.beginner;

import com.physmo.minvio.MinvioApp;

import java.awt.Color;

/**
 * Teaches methods by moving repeated drawing code into drawFace.
 */
class MethodExample extends MinvioApp {

    public static void main(String... args) {
        MinvioApp app = new MethodExample();
        app.start(400, 300, "Method Example", 60);
    }

    @Override
    public void draw(double delta) {
        cls(new Color(42, 47, 56));

        // Calling the same method with different values reuses the drawing code.
        drawFace(100, 160, 35);
        drawFace(200, 150, 50);
        drawFace(310, 165, 28);
    }

    private void drawFace(double x, double y, double size) {
        setDrawColor(new Color(255, 207, 93));
        drawFilledCircle(x, y, size);

        setDrawColor(Color.BLACK);
        drawFilledCircle(x - size * 0.35, y - size * 0.2, size * 0.12);
        drawFilledCircle(x + size * 0.35, y - size * 0.2, size * 0.12);
        drawArc(x - size * 0.45, y - size * 0.25, size * 0.9, size * 0.8, 0, Math.PI);
    }
}
