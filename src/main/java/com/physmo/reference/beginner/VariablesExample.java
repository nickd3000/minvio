package com.physmo.reference.beginner;

import com.physmo.minvio.MinvioApp;

import java.awt.Color;

/**
 * Teaches variables by moving a circle with stored x/y position values.
 */
class VariablesExample extends MinvioApp {

    // These variables remember where the circle is between frames.
    double x = 60;
    double y = 200;
    double speed = 90;
    double size = 30;

    public static void main(String... args) {
        MinvioApp app = new VariablesExample();
        app.start(400, 400, "Variables Example", 60);
    }

    @Override
    public void draw(double delta) {
        // Java note: changing a variable changes what future code does with it.
        x += speed * delta;

        // When the circle moves past the right side, put it back on the left.
        if (x > getWidth() + size) {
            x = -size;
        }

        cls(new Color(31, 37, 46));
        setDrawColor(new Color(247, 190, 76));
        drawFilledCircle(x, y, size);

        setDrawColor(Color.WHITE);
        drawText("x = " + (int) x, 20, 30);
        drawText("speed = " + (int) speed, 20, 50);
    }
}
