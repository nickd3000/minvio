package com.physmo.reference.minvioapp;

import com.physmo.minvio.MinvioApp;

import java.awt.Color;

import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_D;
import static java.awt.event.KeyEvent.VK_S;
import static java.awt.event.KeyEvent.VK_W;

class KeyboardExample extends MinvioApp {

    private static final double force = 500;
    private static final double friction = 1;
    Color colorBackGround = new Color(79, 79, 79);
    Color colorText1 = new Color(255, 147, 2);
    Color colorText2 = new Color(127, 127, 127);
    // Set starting position and zero velocity.
    double x = 170, y = 200, dx = 0, dy = 0;

    public static void main(String... args) {
        MinvioApp app = new KeyboardExample();
        app.start(400, 400, "Keyboard Example", 60);
    }

    @Override
    public void draw(double delta) {

        getBasicDisplay().tickInput();
        int[] keyStates = getBasicDisplay().getKeyState();

        if (keyStates[VK_W] != 0) {
            dy -= force * delta;
        }
        if (keyStates[VK_S] != 0) {
            dy += force * delta;
        }
        if (keyStates[VK_A] != 0) {
            dx -= force * delta;
        }
        if (keyStates[VK_D] != 0) {
            dx += force * delta;
        }

        // Add velocity to position.
        x += dx * delta;
        y += dy * delta;

        // Reduce velocity by friction value.
        dx -= (dx * friction * delta);
        dy -= (dy * friction * delta);

        // Handle bouncing on display edges.
        if (x < 0) {
            dx = -dx;
            x = 0;
        }
        if (x > 330) {
            dx = -dx;
            x = 330;
        }
        if (y < 20) {
            dy = -dy;
            y = 20;
        }
        if (y > 400) {
            dy = -dy;
            y = 400;
        }

        // Clear screen.
        cls(colorBackGround);

        // Draw text.
        setDrawColor(colorText1);
        drawText("WASD", (int) x, (int) y);
        setDrawColor(colorText2);
        drawText("Press the WASD keys to move", 10, 30);
    }
}
