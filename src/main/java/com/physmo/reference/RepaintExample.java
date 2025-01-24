package com.physmo.reference;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.DrawingContext;

import java.awt.Color;

/**
 * A demonstration of a simple graphical application with a custom rendering loop and dynamic animation.
 * This class creates a window with a grid of animated flowing circles that adjust their positions
 * over time. The frame rate can be dynamically adjusted based on user interaction.
 * <p>
 * The application utilizes:
 * - A `BasicDisplay` implementation for providing the window and rendering context.
 * - A `DrawingContext` for rendering shapes and managing colors.
 * - A loop that maintains a fixed frame rate for smooth animation.
 * <p>
 * User interactions include:
 * - Left mouse button click to reduce the frame rate, simulating a slower redraw cycle.
 */
class RepaintExample {

    public static void main(String... args) {
        BasicDisplay bd = new BasicDisplayAwt(400, 400);
        DrawingContext dc = bd.getDrawingContext();
        Color colDots = new Color(59, 59, 59);
        Color colBackground = new Color(181, 129, 72);
        int framesPerSecond = 60;
        bd.setTitle("Repaint Example");
        dc.setFont(10);

        double previousTime = (double) System.nanoTime() / 1_000_000_000.0;
        double scroll = 0;

        while (true) {

            double newTime = (double) System.nanoTime() / 1_000_000_000.0;
            double deltaTime = newTime - previousTime;
            previousTime = newTime;

            scroll += deltaTime * 2;

            dc.cls(colBackground);
            dc.setDrawColor(colDots);

            int rows = 5;
            for (int y = 0; y < rows; y++) {
                for (int x = 0; x < rows; x++) {
                    int span = bd.getWidth() / rows;

                    dc.drawFilledCircle(x * span + ((scroll * 60) % span), (span / 2) + y * span, 15);
                }
            }

            if (bd.getMouseButtonLeft()) bd.repaint(framesPerSecond / 3);
            else bd.repaint(framesPerSecond);
        }
    }

}
