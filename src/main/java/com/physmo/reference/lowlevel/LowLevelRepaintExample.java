package com.physmo.reference.lowlevel;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.DrawingContext;

import java.awt.Color;

/**
 * Demonstrates the low-level repaint loop.
 *
 * <p>This example manually calculates delta time and chooses when to call
 * {@link BasicDisplay#repaint(int)}. Most animation examples should use
 * {@code MinvioApp} instead.</p>
 */
class LowLevelRepaintExample {

    public static void main(String... args) {
        BasicDisplay bd = new BasicDisplayAwt(400, 400);
        DrawingContext dc = bd.getDrawingContext();
        Color colDots = new Color(59, 59, 59);
        Color colBackground = new Color(181, 129, 72);
        int framesPerSecond = 60;

        bd.setTitle("Low-Level Repaint Example");
        dc.setFont(10);

        double previousTime = System.nanoTime() / 1_000_000_000.0;
        double scroll = 0;

        while (bd.isVisible()) {
            double newTime = System.nanoTime() / 1_000_000_000.0;
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
