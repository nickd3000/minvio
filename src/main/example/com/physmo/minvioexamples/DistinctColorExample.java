package com.physmo.minvioexamples;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.MinvioApp;

import java.awt.*;

/***
 * minvio provides support for generating unique colors, useful for quickly
 * creating distinct colours when the specific color isn't important.
 */
class DistinctColorExample extends MinvioApp {

    public static void main(String... args) {
        MinvioApp app = new DistinctColorExample();
        app.start(new BasicDisplayAwt(400, 400), "Distinct Color Example", 30);
    }

    @Override
    public void draw(BasicDisplay bd, double delta) {
        int numRows;
        int space;
        int halfSpace;

        bd.cls(Color.GRAY);
        numRows = 5 + (bd.getMouseX() / 20);
        if (numRows < 1) numRows = 1;
        space = 400 / numRows;
        halfSpace = space / 2;
        double saturation = ((double) bd.getMouseY() / (double) bd.getHeight());

        for (int y = 0; y < numRows; y++) {
            for (int x = 0; x < numRows; x++) {
                bd.setDrawColor(bd.getDistinctColor(x + (y * numRows), saturation));
                bd.drawFilledCircle(halfSpace + x * space, halfSpace + y * space, halfSpace);
            }
        }
    }
}
