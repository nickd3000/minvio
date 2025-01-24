package com.physmo.reference.minvioapp;

import com.physmo.minvio.MinvioApp;

import java.awt.Color;

public class MinvioAppExample extends MinvioApp {

    int x = 0;

    public static void main(String... args) {
        MinvioApp app = new MinvioAppExample();
        app.start(600, 300);
    }

    @Override
    public void draw(double delta) {
        x++;
        if (x > 200) x = 0;
        cls(new Color(207, 198, 179));
        setDrawColor(new Color(17, 52, 69, 215));
        drawText("Hello, World", x, 100);
    }
}
