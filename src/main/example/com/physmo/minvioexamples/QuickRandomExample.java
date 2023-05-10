package com.physmo.minvioexamples;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.QuickRandom;

import java.awt.Color;

class QuickRandomExample extends MinvioApp {

    public static void main(String... args) {
        MinvioApp app = new QuickRandomExample();
        // Start the app running with a window size of 200x200 pixels, at 60 frames per second.
        app.start(new BasicDisplayAwt(400, 400), "QuickRandom Example", 60);
    }

    @Override
    public void draw(BasicDisplay bd, double delta) {
        bd.cls(Color.DARK_GRAY);
        QuickRandom quickRandom = new QuickRandom(1);
        for (int y = 0; y < bd.getWidth(); y++) {
            for (int x = 0; x < bd.getHeight(); x++) {
                quickRandom.setSeed((x + (y * 400)));
                float v = (float) quickRandom.nextDouble();
                bd.setDrawColor(new Color(v, v, v));
                bd.drawPoint(x, y);
            }
        }

    }
}
