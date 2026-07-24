package com.physmo.reference.beginner;

import com.physmo.minvio.MinvioApp;

import java.awt.Color;

/**
 * Teaches arrays by storing several circle positions.
 */
class ArrayExample extends MinvioApp {

    // Arrays store a fixed number of values of the same type.
    int[] xPositions = {60, 120, 180, 240, 300, 360};
    int[] yPositions = {120, 170, 110, 190, 140, 160};

    public static void main(String... args) {
        MinvioApp app = new ArrayExample();
        app.start(420, 280, "Array Example", 60);
    }

    @Override
    public void draw(double delta) {
        cls(new Color(29, 36, 45));

        // Java note: array indexes start at 0, so the last index is length - 1.
        for (int i = 0; i < xPositions.length; i++) {
            setDrawColor(new Color(90 + i * 25, 170, 230));
            drawFilledCircle(xPositions[i], yPositions[i], 18);

            setDrawColor(Color.WHITE);
            drawText("i=" + i, xPositions[i] - 10, yPositions[i] + 35);
        }
    }
}
