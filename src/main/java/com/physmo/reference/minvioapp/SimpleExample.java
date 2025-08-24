package com.physmo.reference.minvioapp;

import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.Palette;

class SimpleExample extends MinvioApp {


    public static void main(String... args) {
        MinvioApp app = new SimpleExample();
        app.start(200, 200, "Simple Example", 60);
    }

    @Override
    public void draw(double delta) {
        cls(Palette.SLATE);
        setDrawColor(Palette.AMBER);

        drawFilledRect(50, 50, 40, 40);
        drawFilledCircle(120, 70, 20);
        drawCircle(120, 120, 20);
        drawRect(50, 100, 40, 40);

        drawText("X:" + getMouseX() + " Y:" + getMouseY(), 10, 190);

    }
}
