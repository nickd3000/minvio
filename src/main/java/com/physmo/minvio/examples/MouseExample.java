package com.physmo.minvio.examples;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.BasicUtils;

import java.awt.Color;

// TODO: mouse clicks... is this supported yet?
class MouseExample {


    public static void main(String... args) {
        int width = 400;
        int height = 400;
        BasicDisplay bd = new BasicDisplayAwt(width, height);

        bd.setTitle("Mouse Example");

        // Clear the screen to dark gray.
        bd.cls(Color.black);
        bd.setDrawColor(Color.BLUE);

        int prevX = bd.getMouseX();
        int prevY = bd.getMouseY();
        int count = 0;

        // Loop forever.
        while (true) {

            bd.repaint(30);
            int dst;
            dst = BasicUtils.distance(prevX, prevY, bd.getMouseX(), bd.getMouseY());

            // Draw the point.
            bd.drawFilledCircle(bd.getMouseX(), bd.getMouseY(), dst / 2);

            // Chose a random distinct colour every so often.
            count++;
            bd.setDrawColor(bd.getDistinctColor(count, 0.7));

            prevX = bd.getMouseX();
            prevY = bd.getMouseY();

            // Fade
            bd.cls(new Color(0, 0, 0, 10));

            // Clear on mouse click.
            if (bd.getMouseButtonLeft()) {
                bd.cls(Color.black);
            }

        }
    }


}
