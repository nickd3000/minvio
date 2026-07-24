package com.physmo.reference.beginner;

import com.physmo.minvio.MinvioApp;

import java.awt.Color;

/**
 * Teaches a simple class by putting ball data and behavior together.
 */
class SimpleClassExample extends MinvioApp {

    Ball ball = new Ball(80, 140, 120, 80, 22);

    public static void main(String... args) {
        MinvioApp app = new SimpleClassExample();
        app.start(400, 300, "Simple Class Example", 60);
    }

    @Override
    public void draw(double delta) {
        cls(new Color(30, 36, 44));
        ball.update(delta, getWidth(), getHeight());
        ball.draw(this);
    }

    static class Ball {
        double x;
        double y;
        double dx;
        double dy;
        double radius;

        Ball(double x, double y, double dx, double dy, double radius) {
            this.x = x;
            this.y = y;
            this.dx = dx;
            this.dy = dy;
            this.radius = radius;
        }

        void update(double delta, int width, int height) {
            x += dx * delta;
            y += dy * delta;

            if (x < radius || x > width - radius) dx = -dx;
            if (y < radius || y > height - radius) dy = -dy;
        }

        void draw(MinvioApp app) {
            app.setDrawColor(new Color(104, 205, 155));
            app.drawFilledCircle(x, y, radius);
        }
    }
}
