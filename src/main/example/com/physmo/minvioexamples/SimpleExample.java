package com.physmo.minvioexamples;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.MinvioApp;

import java.awt.Color;

class SimpleExample extends MinvioApp {

    public static void main(String... args) {
        MinvioApp app = new SimpleExample();
        // Start the app running with a window size of 200x200 pixels, at 60 frames per second.
        app.start(new BasicDisplayAwt(200, 200), "Simple Example", 60);
    }

    @Override
    public void draw(BasicDisplay bd, double delta) {
        bd.cls(Color.LIGHT_GRAY);
        bd.setDrawColor(Color.WHITE);
        bd.drawFilledRect(75, 75, 50, 50);
        bd.setDrawColor(Color.BLUE);
        bd.drawCircle(100, 100, 70);
        bd.drawText("X:" + bd.getMouseX() + " Y:" + bd.getMouseY(), 10, 190);
        bd.drawText("Tick :" + getFps(), 10, 160);
    }
}
