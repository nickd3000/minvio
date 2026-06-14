package com.physmo.reference;

import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.Palette;

/**
 * FractalTreeExample demonstrates the power of the matrix stack for recursive drawing.
 * By using pushMatrix and popMatrix, we can easily create a branching structure
 * where each branch is drawn relative to its parent.
 */
public class FractalTreeExample extends MinvioApp {

    double time = 0;

    public static void main(String... args) {
        MinvioApp app = new FractalTreeExample();
        app.start(800, 600, "Fractal Tree Example", 60);
    }

    @Override
    public void draw(double delta) {
        time += delta;

        // Clear the screen with a dark background
        cls(Palette.GRAY_900);

        pushMatrix();
        // Position the base of the tree at the bottom center
        translate(getWidth() / 2.0, getHeight() - 50);

        // Initial trunk
        double initialLength = 150;
        double angleMod = Math.sin(time * 0.5) * 0.5; // Swaying effect

        setDrawColor(Palette.BROWN);
        drawBranch(initialLength, angleMod, 10);
        popMatrix();
    }

    /**
     * Recursively draws branches.
     *
     * @param length   Current branch length.
     * @param angleMod Dynamic angle adjustment for animation.
     * @param depth    Current recursion depth.
     */
    private void drawBranch(double length, double angleMod, int depth) {
        if (depth == 0) return;

        // Draw the branch (pointing "up" in our local coordinate system)
        // Note: In AWT/Minvio, negative Y is up.
        drawLine(0, 0, 0, -length, depth);

        pushMatrix();
        // Move to the end of the branch
        translate(0, -length);

        // Right branch
        pushMatrix();
        pushStyle();
        rotate(0.5 + angleMod);
        scale(0.8);
        if (depth < 4) setDrawColor(Palette.GREEN); // Leaves
        drawBranch(length, angleMod, depth - 1);
        popStyle();
        popMatrix();

        // Left branch
        pushMatrix();
        pushStyle();
        rotate(-0.4 + angleMod * 0.5);
        scale(0.75);
        if (depth < 4) setDrawColor(Palette.MINT); // Slightly different leaf color
        drawBranch(length, angleMod, depth - 1);
        popStyle();
        popMatrix();

        popMatrix();
    }
}
