package com.physmo.reference.beginner;

import com.physmo.minvio.MinvioApp;

import java.awt.Color;

/**
 * Teaches delta time for frame-independent animation.
 */
class AnimationDeltaExample extends MinvioApp {

    double x = 40;
    double speed = 120;

    public static void main(String... args) {
        MinvioApp app = new AnimationDeltaExample();
        app.start(400, 220, "Animation Delta Example", 60);
    }

    @Override
    public void draw(double delta) {
        // Java note: delta is the time since the previous frame, in seconds.
        x += speed * delta;

        if (x > getWidth() - 30 || x < 30) {
            speed = -speed;
        }

        cls(new Color(34, 39, 48));
        setDrawColor(new Color(255, 196, 80));
        drawFilledCircle(x, 110, 30);

        setDrawColor(Color.WHITE);
        drawText("Movement uses speed * delta", 20, 30);
    }
}
