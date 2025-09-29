package com.physmo.reference.minvioapp;

import com.physmo.minvio.MinvioApp;

import java.awt.Color;

class SimpleExample extends MinvioApp {


    public static void main(String... args) {
        MinvioApp app = new SimpleExample();
        app.start(600, 300, "Simple Example", 60);
    }

    @Override
    public void draw(double delta) {
        // Clear the screen with a light beige color
        cls(new Color(207, 198, 179));

        // Set drawing color to a semi-transparent dark blue
        setDrawColor(new Color(17, 52, 69, 215));

        // Draw some text that moves across the screen
        int x = (int) (System.currentTimeMillis() / 20) % 200;
        drawText("Hello, MinVio World!", x, 100);

        // Draw some simple shapes
        drawFilledRect(50, 150, 40, 40);
        drawFilledCircle(150, 170, 20);
        drawCircle(250, 170, 20);

        // Show mouse coordinates
        drawText("Mouse: X:" + getMouseX() + " Y:" + getMouseY(), 10, 270);
    }
}
