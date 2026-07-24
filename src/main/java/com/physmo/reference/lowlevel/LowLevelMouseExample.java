package com.physmo.reference.lowlevel;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.DrawingContext;
import com.physmo.minvio.utils.BasicUtils;
import com.physmo.minvio.utils.Palette;

import java.awt.Color;


/**
 * Demonstrates the low-level BasicDisplay style.
 *
 * <p>This example manually creates the display, retrieves its drawing context,
 * reads input state, and owns the repaint loop. Most examples should use
 * {@code MinvioApp} instead.</p>
 */
public class LowLevelMouseExample {

    public static void main(String... args) {
        int width = 400;
        int height = 400;
        BasicDisplay bd = new BasicDisplayAwt(width, height);
        DrawingContext dc = bd.getDrawingContext();

        bd.setTitle("Low-Level Mouse Example");

        // Clear the screen to dark gray.
        dc.cls(Color.black);
        dc.setDrawColor(Color.BLUE);

        int prevX = bd.getMouseX();
        int prevY = bd.getMouseY();
        int count = 0;

        while (bd.isVisible()) {

            bd.repaint(30);
            int dst = BasicUtils.distance(prevX, prevY, bd.getMouseX(), bd.getMouseY());

            // Draw the point.
            dc.drawFilledCircle(bd.getMouseX(), bd.getMouseY(), dst / 2);

            // Choose a distinct colour every frame.
            count++;
            dc.setDrawColor(Palette.getDistinctColor(count, 0.7));

            prevX = bd.getMouseX();
            prevY = bd.getMouseY();

            // Fade the previous drawing by painting a transparent layer over it.
            dc.cls(new Color(0, 0, 0, 10));

            // Clear on mouse click.
            if (bd.getMouseButtonLeft()) {
                dc.cls(Color.black);
            }

        }
    }
}
