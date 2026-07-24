package com.physmo.reference.input;

import com.physmo.minvio.MinvioApp;

import java.awt.Color;

/**
 * Demonstrates following the mouse with smooth interpolation.
 */
class MouseFollowExample extends MinvioApp {

    private double x = 200;
    private double y = 200;
    private double speed = 0.1;

    public static void main(String... args) {
        MinvioApp app = new MouseFollowExample();
        app.start(400, 400, "Mouse Follow Example", 60);
    }

    @Override
    public void draw(double delta) {
        if (!getBasicDisplay().getMouseButtonLeft()) {
            cls(Color.BLACK);
        }

        x += (getMouseX() - x) * speed;
        y += (getMouseY() - y) * speed;

        setDrawColor(Color.WHITE);
        drawCircle(x, y, 20);
    }
}
