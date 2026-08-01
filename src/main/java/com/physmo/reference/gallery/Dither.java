package com.physmo.reference.gallery;

import com.physmo.minvio.MinvioApp;

import java.awt.Color;

public class Dither extends MinvioApp {
    private static final int[] BAYER_4 = {
            0, 8, 2, 10,
            12, 4, 14, 6,
            3, 11, 1, 9,
            15, 7, 13, 5
    };
    private double time;

    public static void main(String[] args) {
        MinvioApp app = new Dither();
        if (args.length > 0) {
            int screenshotFrame = args.length > 1 ? Integer.parseInt(args[1]) : 20;
            app.takeScreenshotAndQuit(args[0], screenshotFrame);
        }
        app.start(400, 400, "Dither", 20);
    }

    @Override
    public void draw(double delta) {
        time += delta;
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                double ramp = (double) x / Math.max(1, getWidth() - 1);
                double wave = (Math.sin((x + y + time * 40.0) * 0.04) + 1.0) * 0.15;
                int threshold = BAYER_4[(x % 4) + ((y % 4) * 4)];
                double dither = (threshold + 0.5) / 16.0;
                boolean lit = ramp + wave > dither;

                setDrawColor(lit ? Color.WHITE : Color.BLACK);
                drawPoint(x, y);
            }
        }
    }
}
