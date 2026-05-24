package com.physmo.reference;

import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.Palette;

/**
 * KaleidoscopeExample demonstrates how matrix transformations can be used to create
 * complex symmetrical patterns (kaleidoscopes). By drawing a simple scene and
 * repeating it with different rotations, we create a symmetrical mandala-like effect.
 */
public class KaleidoscopeExample extends MinvioApp {

    double time = 0;

    public static void main(String... args) {
        MinvioApp app = new KaleidoscopeExample();
        app.start(600, 600, "Kaleidoscope Example", 60);
    }

    @Override
    public void draw(double delta) {
        time += delta;

        // Dark background for contrast
        cls(Palette.BLACK);

        pushMatrix();
        // Move the origin to the center of the screen
        translate(getWidth() / 2.0, getHeight() / 2.0);

        int segments = 9; // Number of symmetrical slices
        double angleStep = (Math.PI * 2.0) / segments;

        for (int i = 0; i < segments; i++) {
            pushMatrix();
            rotate(i * angleStep + time * 0.5);

            // Draw the "slice" of the kaleidoscope
            drawSlice();

            popMatrix();
        }
        popMatrix();
    }

    /**
     * Draws a single slice of the kaleidoscope.
     * The slice is repeated multiple times in the main draw loop.
     */
    private void drawSlice() {
        // We can draw anything here. Using relative coordinates and time-based
        // animation makes it look like a shifting kaleidoscope.

        // A moving, rotating line
        pushMatrix();
        double offset = Math.sin(time * 0.7) * 100;
        translate(offset + 50, 0);
        rotate(time * 1.5);
        setDrawColor(Palette.CYAN);
        drawLine(-30, 0, 30, 0, 2);
        popMatrix();

        // A pulsating circle
        double circlePos = 150 + Math.cos(time * 0.5) * 50;
        double size = 10 + Math.sin(time * 2) * 5;
        setDrawColor(Palette.NEON_PINK);
        drawFilledCircle(circlePos, 0, size);

        // Nested shapes
        pushMatrix();
        translate(200, 50);
        rotate(-time * 0.8);
        setDrawColor(Palette.AMBER);
        drawRect(-20, -20, 40, 40);

        // Even further nesting
        translate(30, 0);
        scale(0.5);
        setDrawColor(Palette.WHITE);
        drawFilledRect(-10, -10, 20, 20);
        popMatrix();
    }
}
