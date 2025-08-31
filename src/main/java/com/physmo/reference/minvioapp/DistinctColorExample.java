package com.physmo.reference.minvioapp;

import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.Palette;

import java.awt.Color;

/**
 * The DistinctColorExample class demonstrates an example of using distinct colors
 * within a grid of filled circles that dynamically update based on mouse position.
 * The number of rows/columns in the grid and the color saturation are determined
 * by the x and y coordinates of the mouse cursor, respectively.
 * <p>
 * This class extends the MinvioApp framework to create a graphical application.
 */
class DistinctColorExample extends MinvioApp {

    public static void main(String... args) {
        MinvioApp app = new DistinctColorExample();
        app.start(400, 400, "Distinct Color Example", 30);
    }

    @Override
    public void draw(double delta) {
        int numRows;
        int space;
        int halfSpace;

        cls(Color.GRAY);
        numRows = 5 + (getMouseX() / 20);
        if (numRows < 1) numRows = 1;
        space = 400 / numRows;
        halfSpace = space / 2;
        double saturation = ((double) getMouseY() / (double) getHeight());

        for (int y = 0; y < numRows; y++) {
            for (int x = 0; x < numRows; x++) {
                setDrawColor(Palette.getDistinctColor(x + (y * numRows), saturation));
                drawFilledCircle(halfSpace + x * space, halfSpace + y * space, halfSpace);
            }
        }
    }
}
