package com.physmo.reference.minvioapp;

import com.physmo.minvio.MinvioApp;

import java.awt.Color;

class MouseButtonExample extends MinvioApp {

    // Define colours for indicators.
    Color colOn = new Color(0x3C9ABB, false);
    Color colOff = new Color(0x652727, false);

    public static void main(String... args) {
        MinvioApp app = new MouseButtonExample();
        app.start(400, 400, "Mouse Button Example", 60);
    }

    @Override
    public void draw(double delta) {
        cls(Color.lightGray);

        // Draw rectangles representing mouse buttons.
        int span = getWidth() / 3;
        int pad = (getMouseX() * 50 / getWidth());
        for (int i = 0; i < 3; i++) {

            if (i == 0 && getBasicDisplay().getMouseButtonLeft()) setDrawColor(colOn);
            else if (i == 1 && getBasicDisplay().getMouseButtonMiddle()) setDrawColor(colOn);
            else if (i == 2 && getBasicDisplay().getMouseButtonRight()) setDrawColor(colOn);
            else setDrawColor(colOff);

            drawFilledRect((i * span) + pad, pad, (span) - (pad * 2), (getHeight()) - pad * 2);
        }

        // Draw the message.
        setFont(15);
        setDrawColor(Color.black);
        drawText("Click Left, Middle and Right mouse button.", 40, getHeight() - 30);
    }
}
