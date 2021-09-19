package com.physmo.minvioexamples;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.MinvioApp;

import java.awt.*;

class MouseButtonExample extends MinvioApp {

    // Define colours for indicators.
    Color colOn = new Color(0x3C9ABB, false);
    Color colOff = new Color(0x652727, false);

    public static void main(String... args) {
        MinvioApp app = new MouseButtonExample();
        app.start(new BasicDisplayAwt(400, 400), "Mouse Button Example", 60);
    }

    @Override
    public void draw(BasicDisplay bd, double delta) {
        bd.cls(Color.lightGray);

        // Draw rectangles representing mouse buttons.
        int span = bd.getWidth() / 3;
        int pad = (bd.getMouseX() * 50 / bd.getWidth());
        for (int i = 0; i < 3; i++) {

            if (i == 0 && bd.getMouseButtonLeft()) bd.setDrawColor(colOn);
            else if (i == 1 && bd.getMouseButtonMiddle()) bd.setDrawColor(colOn);
            else if (i == 2 && bd.getMouseButtonRight()) bd.setDrawColor(colOn);
            else bd.setDrawColor(colOff);

            bd.drawFilledRect((i * span) + pad, 0 + pad, (span) - (pad * 2), (bd.getHeight()) - pad * 2);
        }

        // Draw the message.
        bd.setFont(15);
        bd.setDrawColor(Color.black);
        bd.drawText("Click Left, Middle and Right mouse button.", 40, bd.getHeight() - 30);
    }
}
