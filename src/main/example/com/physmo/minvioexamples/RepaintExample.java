package com.physmo.minvioexamples;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;

import java.awt.*;


class RepaintExample {

    public static void main(String... args) {
        BasicDisplay bd = new BasicDisplayAwt(400, 400);
        Color colDots = new Color(59, 59, 59);
        Color colBackground = new Color(181, 129, 72);
        int framesPerSecond = 60;
        bd.setTitle("Repaint Example");
        bd.setFont(10);

        double previousTime = (double) System.nanoTime() / 1_000_000_000.0;
        double scroll = 0;

        while (true) {

            double newTime = (double) System.nanoTime() / 1_000_000_000.0;
            double deltaTime = newTime - previousTime;
            previousTime = newTime;

            scroll += deltaTime * 2;

            bd.cls(colBackground);
            bd.setDrawColor(colDots);

            int rows = 5;
            for (int y = 0; y < rows; y++) {
                for (int x = 0; x < rows; x++) {
                    int span = bd.getWidth() / rows;

                    bd.drawFilledCircle(x * span + ((scroll * 60) % span), (span / 2) + y * span, 15);
                }
            }

            if (bd.getMouseButtonLeft()) bd.repaint(framesPerSecond/3);
            else bd.repaint(framesPerSecond);
        }
    }

}
