package com.physmo.reference.minvioapp;

import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.RollingAverage;

import java.awt.Color;

public class RollingAverageExample extends MinvioApp {

    RollingAverage rax = new RollingAverage(100);
    RollingAverage ray = new RollingAverage(100);

    public static void main(String... args) {
        MinvioApp app = new RollingAverageExample();
        // Start the app running with a window size of 200x200 pixels, at 60 frames per second.
        app.start(200, 200, "RollingAverageExample", 60);
    }

    @Override
    public void draw(double delta) {
        cls(Color.DARK_GRAY);
        rax.add(getMouseX());
        ray.add(getBasicDisplay().getMouseY());
        setDrawColor(Color.BLUE);
        drawFilledCircle(rax.getAverage(), ray.getAverage(), 10);
    }
}
