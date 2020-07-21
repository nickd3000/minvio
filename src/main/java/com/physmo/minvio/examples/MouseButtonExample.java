package com.physmo.minvio.examples;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;

import java.awt.*;

// BasicDisplay.getMouseButtonLeft()
// BasicDisplay.getMouseButtonMiddle()
// BasicDisplay.getMouseButtonRight()
class MouseButtonExample {


    public static void main(String... args) {
        int width = 400;
        int height = 400;
        BasicDisplay bd = new BasicDisplayAwt(width, height);

        bd.setTitle("Mouse Button Example");

        // Define colours to use.
        Color colOn = new Color(0x3C9ABB, false);
        Color colOff = new Color(0x652727, false);

        // Loop forever.
        while (true) {
            bd.cls(Color.lightGray);

            // Draw rectangles representing mouse buttons.
            int span = width / 3;
            int pad = (bd.getMouseX() * 50 / width);
            for (int i = 0; i < 3; i++) {

                if (i == 0 && bd.getMouseButtonLeft()) bd.setDrawColor(colOn);
                else if (i == 1 && bd.getMouseButtonMiddle()) bd.setDrawColor(colOn);
                else if (i == 2 && bd.getMouseButtonRight()) bd.setDrawColor(colOn);
                else bd.setDrawColor(colOff);

                bd.drawFilledRect((i * span) + pad, 0 + pad, (span) - (pad * 2), (height) - pad * 2);
            }

            // Draw the message.
            bd.setFont(15);
            bd.setDrawColor(Color.black);
            bd.drawText("Click Left, Middle and Right mouse button.", 40, height - 30);

            // Refresh the display.
            bd.repaint(60);
        }
    }

}
