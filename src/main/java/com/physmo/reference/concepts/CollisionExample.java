package com.physmo.reference.concepts;

import com.physmo.minvio.MinvioApp;

import java.awt.Color;

/**
 * Demonstrates circle collision by highlighting overlap with the mouse.
 */
class CollisionExample extends MinvioApp {

    private static final double FIXED_X = 250;
    private static final double FIXED_Y = 200;
    private static final double FIXED_RADIUS = 70;

    public static void main(String... args) {
        MinvioApp app = new CollisionExample();
        app.start(500, 400, "Collision Example", 60);
    }

    @Override
    public void draw(double delta) {
        double mouseRadius = 45;
        double dx = getMouseX() - FIXED_X;
        double dy = getMouseY() - FIXED_Y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        boolean overlapping = distance < FIXED_RADIUS + mouseRadius;

        cls(new Color(31, 36, 45));

        setDrawColor(overlapping ? new Color(255, 105, 105) : new Color(120, 180, 255));
        drawFilledCircle(FIXED_X, FIXED_Y, FIXED_RADIUS);

        setDrawColor(overlapping ? new Color(255, 220, 120) : new Color(120, 230, 170));
        drawFilledCircle(getMouseX(), getMouseY(), mouseRadius);

        setDrawColor(Color.WHITE);
        drawText(overlapping ? "overlap" : "no overlap", 20, 30);
    }
}
