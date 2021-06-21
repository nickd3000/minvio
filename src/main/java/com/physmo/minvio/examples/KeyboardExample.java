package com.physmo.minvio.examples;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;

import java.awt.Color;

import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_D;
import static java.awt.event.KeyEvent.VK_S;
import static java.awt.event.KeyEvent.VK_W;

class KeyboardExample {

    private static final double force = 0.5;
    private static final double friction = 0.05;

    public static void main(String... args) {
        BasicDisplay bd = new BasicDisplayAwt(400, 400);

        bd.setTitle("Keyboard Example");

        // Set starting position and zero velocity.
        double x = 170, y = 200, dx = 0, dy = 0;

        // Loop forever.
        while (true) {

            bd.tickInput();
            int[] keyStates = bd.getKeyState();

            if (keyStates[VK_W] != 0) {
                dy -= force;
            }
            if (keyStates[VK_S] != 0) {
                dy += force;
            }
            if (keyStates[VK_A] != 0) {
                dx -= force;
            }
            if (keyStates[VK_D] != 0) {
                dx += force;
            }

            // Add velocity to position.
            x += dx;
            y += dy;

            // Reduce velocity by friction value.
            dx -= (dx * friction);
            dy -= (dy * friction);

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
            bd.cls(Color.black);

            // Draw text.
            bd.setDrawColor(Color.orange);
            bd.drawText("WASD", (int) x, (int) y);

            // Refresh screen at 60 frames per second.
            bd.repaint(60);
        }

    }

}
