package com.physmo.minvio.examples;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.RollingAverage;

import java.awt.Color;

public class RollingAverageExample extends MinvioApp {

    RollingAverage rax = new RollingAverage(100);
    RollingAverage ray = new RollingAverage(100);

    public static void main(String... args) {
        MinvioApp app = new RollingAverageExample();
        // Start the app running with a window size of 200x200 pixels, at 60 frames per second.
        app.start(new BasicDisplayAwt(200, 200), "RollingAverageExample", 60);
    }

    @Override
    public void draw(BasicDisplay bd, double delta) {
        bd.cls(Color.DARK_GRAY);
        rax.add(bd.getMouseX());
        ray.add(bd.getMouseY());
        bd.drawFilledCircle(rax.getAverage(), ray.getAverage(), 10);
    }
}
