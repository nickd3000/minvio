package com.physmo.minvio;

import java.awt.Color;

public class TestMinvioApp extends MinvioApp {

    public static void main(String... args) {
        MinvioApp app = new TestMinvioApp();
        // Start the app running with a window size of 200x200 pixels, at 60 frames per second.
        app.start(new BasicDisplayAwt(200, 200), "Simple Example", 60);
    }

    @Override
    public void draw(double delta) {

        cls(Color.LIGHT_GRAY);
        setDrawColor(Color.WHITE);
        drawFilledRect(75, 75, 50, 50);
        setDrawColor(Color.BLUE);
        drawCircle(100, 100, 70);
        drawText("X:" + getMouseX() + " Y:" + bd.getMouseY(), 10, 190);
        drawText("Tick :" + getFps(), 10, 160);
    }
}
