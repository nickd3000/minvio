package com.physmo.minvioexamples;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.MinvioApp;

import java.awt.Color;

public class MinvioAppExample extends MinvioApp {

    int x = 0;

    public static void main(String... args) {
        MinvioApp app = new MinvioAppExample();
        app.start(new BasicDisplayAwt(600, 300), "Minvio App Example", 30);
    }

    @Override
    public void draw(BasicDisplay bd, double delta) {
        x++;
        if (x > 200) x = 0;
        bd.cls(new Color(207, 198, 179));
        bd.setDrawColor(new Color(17, 52, 69, 215));
        bd.drawText("Hello, World", x, 100);
    }
}
