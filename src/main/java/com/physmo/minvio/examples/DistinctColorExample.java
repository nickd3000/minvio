package com.physmo.minvio.examples;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;

import java.awt.*;

/***
 * minvio provides support for generating unique colors, useful for quickly
 * creating distinct colours when the specific color isn't important.
 */
class DistinctColorExample {
    public static void main(String... args) {

        int width = 400;
        int height = 400;

        BasicDisplay bd = new BasicDisplayAwt(width, height);

        bd.setTitle("Distinct Color Example");

        int numRows;
        int space;
        int halfSpace;


        while (true) {
            bd.cls(Color.white);
            numRows = 5 + (bd.getMouseX() / 20);
            if (numRows < 1) numRows = 1;
            space = width / numRows;
            halfSpace = space / 2;
            for (int y = 0; y < numRows; y++) {
                for (int x = 0; x < numRows; x++) {
                    double saturation = ((double) bd.getMouseY() / (double) bd.getHeight());
                    bd.setDrawColor(bd.getDistinctColor(x + (y * numRows), saturation));
                    bd.drawFilledCircle(halfSpace + x * space, halfSpace + y * space, halfSpace);
                }
            }

            bd.repaint(30);
        }
    }
}
