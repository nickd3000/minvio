package com.physmo.reference.concepts;

import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.Palette;

/**
 * MatrixExample demonstrates the use of coordinate system transformations
 * (translate, rotate, scale) and the matrix stack (pushMatrix, popMatrix).
 * <p>
 * This allows for hierarchical drawing where objects can be positioned and
 * rotated relative to their parent objects.
 */
public class MatrixExample extends MinvioApp {

    double time = 0;

    public static void main(String... args) {
        MinvioApp app = new MatrixExample();
        app.start(600, 600, "Matrix Transformation Example", 60);
    }

    @Override
    public void draw(double delta) {
        time += delta;

        // Clear the screen
        cls(Palette.GRAY_900);

        // --- HIERARCHICAL DRAWING EXAMPLE ---

        // 1. Center the coordinate system
        pushMatrix();
        translate(getWidth() / 2.0, getHeight() / 2.0);

        // Draw a central "Sun" at the new (0,0)
        setDrawColor(Palette.YELLOW);
        drawFilledCircle(0, 0, 30);

        // 2. A "Planet" orbiting the Sun
        pushMatrix();
        rotate(time * 0.7);    // Orbit rotation
        translate(180, 0);      // Distance from the Sun

        // Draw the Planet
        setDrawColor(Palette.BLUE);
        drawFilledCircle(0, 0, 15);

        // 3. A "Moon" orbiting the Planet
        pushMatrix();
        rotate(time * 3.0);    // Moon orbit rotation
        translate(40, 0);       // Distance from the Planet

        // Draw the Moon
        setDrawColor(Palette.WHITE);
        drawFilledCircle(0, 0, 6);
        popMatrix(); // End Moon

        popMatrix(); // End Planet

        // --- SCALING AND RECTANGLE EXAMPLE ---

        // 4. A rotating, pulsating square in the corner
        pushMatrix();
        translate(100, 100);
        rotate(-time);
        double s = 1.0 + Math.sin(time * 2) * 0.5;
        scale(s);

        setDrawColor(Palette.NEON_PINK);
        drawRect(-25, -25, 50, 50);
        popMatrix();

        // --- NESTED ROTATION EXAMPLE ---

        pushMatrix();
        // 5. Multiple nested rotating squares
        translate(getWidth() - 100, getHeight() - 100);
        setDrawColor(Palette.CYAN);
        for (int i = 0; i < 5; i++) {
            rotate(time * 0.5);
            scale(0.8);
            drawRect(-50, -50, 100, 100);
        }
        popMatrix();

        popMatrix(); // End Center (1.)
    }
}
