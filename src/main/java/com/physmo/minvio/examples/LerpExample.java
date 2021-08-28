package com.physmo.minvio.examples;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.MinvioApp;

import java.awt.Color;

class LerpExample extends MinvioApp {

    public static void main(String... args) {
        MinvioApp app = new LerpExample();
        // Start the app running with a window size of 200x200 pixels, at 60 frames per second.
        app.start(new BasicDisplayAwt(200, 200), "LerpExample", 60);
    }

    @Override
    public void draw(BasicDisplay bd, double delta) {
        bd.cls(Color.DARK_GRAY);

        Color c1 = new Color(22, 150, 211);
        Color c2 = new Color(255, 214, 89);

        int i = 0;
        int width = bd.getWidth() / 5;

        bd.setDrawColor(c1);
        bd.drawFilledRect((i++ * width), 75, width, 30);

        bd.setDrawColor(bd.lerp(c1, c2, 0.25));
        bd.drawFilledRect((i++ * width), 75, width, 30);

        bd.setDrawColor(bd.lerp(c1, c2, 0.50));
        bd.drawFilledRect((i++ * width), 75, width, 30);

        bd.setDrawColor(bd.lerp(c1, c2, 0.75));
        bd.drawFilledRect((i++ * width), 75, width, 30);

        bd.setDrawColor(c2);
        bd.drawFilledRect((i++ * width), 75, width, 30);

    }
}
