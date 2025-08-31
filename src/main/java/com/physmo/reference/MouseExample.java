package com.physmo.reference;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.DrawingContext;
import com.physmo.minvio.utils.BasicUtils;
import com.physmo.minvio.utils.Palette;

import java.awt.Color;


/**
 * The MouseExample class demonstrates the use of mouse tracking
 * and graphical drawing in a window using the BasicDisplay framework.
 * The mouse's position defines where graphics are rendered, creating
 * visual effects based on movement and interactions.
 * <p>
 * This program showcases:
 * - Real-time rendering of shapes that follow the mouse pointer.
 * - Dynamic color changes with distinct color patterns.
 * - Fading effect by overlaying semi-transparent layers.
 * - Resetting the display on left mouse button click.
 * <p>
 * It continuously repaints the window at a fixed frame rate and
 * handles mouse input for interactions and drawing logic.
 */
class MouseExample {


    public static void main(String... args) {
        int width = 400;
        int height = 400;
        BasicDisplay bd = new BasicDisplayAwt(width, height);
        DrawingContext dc = bd.getDrawingContext();

        bd.setTitle("Mouse Example");

        // Clear the screen to dark gray.
        dc.cls(Color.black);
        dc.setDrawColor(Color.BLUE);

        int prevX = bd.getMouseX();
        int prevY = bd.getMouseY();
        int count = 0;

        // Loop forever.
        while (true) {

            bd.repaint(30);
            int dst;
            dst = BasicUtils.distance(prevX, prevY, bd.getMouseX(), bd.getMouseY());

            // Draw the point.
            dc.drawFilledCircle(bd.getMouseX(), bd.getMouseY(), dst / 2);

            // Chose a random distinct colour every so often.
            count++;
            dc.setDrawColor(Palette.getDistinctColor(count, 0.7));

            prevX = bd.getMouseX();
            prevY = bd.getMouseY();

            // Fade
            dc.cls(new Color(0, 0, 0, 10));

            // Clear on mouse click.
            if (bd.getMouseButtonLeft()) {
                dc.cls(Color.black);
            }

        }
    }


}
