package com.physmo.reference.beginner;

import com.physmo.minvio.MinvioApp;

import java.awt.Color;

/**
 * Teaches for loops by drawing repeated shapes.
 */
class ForLoopExample extends MinvioApp {

    public static void main(String... args) {
        MinvioApp app = new ForLoopExample();
        app.start(400, 300, "For Loop Example", 60);
    }

    @Override
    public void draw(double delta) {
        cls(new Color(28, 34, 43));
        setDrawColor(new Color(110, 210, 165));

        // A for loop repeats code. Here it draws one circle each time.
        for (int i = 0; i < 10; i++) {
            int x = 40 + i * 35;
            int y = 100;
            drawFilledCircle(x, y, 12);
        }

        // Nested loops are loops inside loops. This draws a grid.
        setDrawColor(new Color(255, 205, 96));
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 6; x++) {
                drawRect(70 + x * 42, 160 + y * 25, 18, 18);
            }
        }
    }
}
