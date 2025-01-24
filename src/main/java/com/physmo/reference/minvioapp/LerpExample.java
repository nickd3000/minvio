package com.physmo.reference.minvioapp;

import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.Utils;
import com.physmo.minvio.types.Point;

import java.awt.Color;

class LerpExample extends MinvioApp {

    public static void main(String... args) {
        MinvioApp app = new LerpExample();
        // Start the app running with a window size of 200x200 pixels, at 60 frames per second.
        app.start(200, 200, "LerpExample", 60);
    }

    @Override
    public void draw(double delta) {
        cls(Color.DARK_GRAY);

        Color c1 = new Color(22, 150, 211);
        Color c2 = new Color(255, 214, 89);
        Point p1 = new Point(10, 50);
        Point p2 = new Point(190, 50);

        int i = 0;
        int width = getWidth() / 5;

        setDrawColor(c1);
        drawFilledRect((i++ * width), 75, width, 30);

        setDrawColor(Utils.lerp(c1, c2, 0.25));
        drawFilledRect((i++ * width), 75, width, 30);

        setDrawColor(Utils.lerp(c1, c2, 0.50));
        drawFilledRect((i++ * width), 75, width, 30);

        setDrawColor(Utils.lerp(c1, c2, 0.75));
        drawFilledRect((i++ * width), 75, width, 30);

        setDrawColor(c2);
        drawFilledRect((i++ * width), 75, width, 30);

        setDrawColor(Utils.lerp(c1, c2, getMouseX() / 200.0));
        drawFilledCircle(Utils.lerp(p1, p2, getMouseX() / 200.0), 10);

    }
}
