package com.physmo.reference.concepts;

import com.physmo.minvio.MinvioApp;

import java.awt.Color;

/**
 * Demonstrates a simple gravity-like attraction toward the mouse.
 */
class GravityOrbitExample extends MinvioApp {

    private double x = 250;
    private double y = 120;
    private double dx = 160;
    private double dy = 0;

    public static void main(String... args) {
        MinvioApp app = new GravityOrbitExample();
        app.start(500, 400, "Gravity Orbit Example", 60);
    }

    @Override
    public void draw(double delta) {
        double targetX = getMouseX();
        double targetY = getMouseY();
        double diffX = targetX - x;
        double diffY = targetY - y;
        double distanceSquared = diffX * diffX + diffY * diffY;
        double distance = Math.sqrt(distanceSquared);

        if (distance > 1) {
            double force = 12000 / Math.max(900, distanceSquared);
            dx += (diffX / distance) * force;
            dy += (diffY / distance) * force;
        }

        x += dx * delta;
        y += dy * delta;

        dx *= 0.997;
        dy *= 0.997;

        cls(new Color(14, 18, 28, 35));
        setDrawColor(new Color(255, 220, 96));
        drawFilledCircle(targetX, targetY, 14);
        setDrawColor(new Color(98, 190, 255));
        drawFilledCircle(x, y, 8);
    }
}
