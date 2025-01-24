package com.physmo.reference.minvioapp;

import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.QuickRandom;

import java.awt.Color;

class QuickRandomExample extends MinvioApp {

    public static void main(String... args) {
        MinvioApp app = new QuickRandomExample();
        // Start the app running with a window size of 200x200 pixels, at 60 frames per second.
        app.start(400, 400, "QuickRandom Example", 60);
    }

    @Override
    public void draw(double delta) {
        cls(Color.DARK_GRAY);
        QuickRandom quickRandom = new QuickRandom(1);
        for (int y = 0; y < getWidth(); y++) {
            for (int x = 0; x < getHeight(); x++) {
                quickRandom.setSeed((x + (y * 400L)));
                float v = (float) quickRandom.nextDouble();
                setDrawColor(new Color(v, v, v));
                drawPoint(x, y);
            }
        }

    }
}
