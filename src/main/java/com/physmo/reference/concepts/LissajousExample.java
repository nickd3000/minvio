package com.physmo.reference.concepts;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.MinvioApp;

import java.awt.Color;

/**
 * Demonstrates layered sine/cosine motion to draw Lissajous-style traces.
 */
class LissajousExample extends MinvioApp {

    private final int[] multipliers = {1, 2, 3, 4};
    private double angle;
    private double radius1 = 60;
    private double radius2 = 40;
    private int count;

    public static void main(String... args) {
        MinvioApp app = new LissajousExample();
        app.start(400, 400, "Lissajous Example", 60);
    }

    @Override
    public void init(BasicDisplay bd) {
        cls(Color.LIGHT_GRAY);
    }

    @Override
    public void draw(double delta) {
        count++;
        angle += delta * 0.18;

        double x1 = Math.sin(angle * multipliers[0]) * radius1;
        double y1 = Math.cos(angle * multipliers[1]) * radius1;
        double x2 = Math.sin(angle * multipliers[2]) * radius2;
        double y2 = Math.cos(angle * multipliers[3]) * radius2;

        setDrawColor(Color.YELLOW);
        drawPoint(200 + x1, 200 + y1);
        setDrawColor(Color.MAGENTA);
        drawPoint(200 + x2, 200 + y2);
        setDrawColor(Color.BLUE);
        drawPoint(200 + x1 + x2, 200 + y1 + y2);

        if ((count % 20) == 0) {
            radius1 = getMouseX() / 2.0;
            radius2 = 200 - radius1;
        }

        if ((count % 300) == 0) {
            for (int i = 0; i < multipliers.length; i++) {
                multipliers[i] = 1 + (int) (Math.random() * 4);
            }
            cls(Color.LIGHT_GRAY);
        }
    }
}
