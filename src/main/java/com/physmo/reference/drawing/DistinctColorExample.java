package com.physmo.reference.drawing;

import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.Palette;

import java.awt.Color;

class DistinctColorExample extends MinvioApp {

    public static void main(String... args) {
        MinvioApp app = new DistinctColorExample();
        app.start(400, 400, "Distinct Color Example", 30);
    }

    @Override
    public void draw(double delta) {
        int numRows;
        int space;
        int halfSpace;

        cls(Color.GRAY);
        numRows = 5 + (getMouseX() / 20);
        if (numRows < 1) numRows = 1;
        space = 400 / numRows;
        halfSpace = space / 2;
        double saturation = ((double) getMouseY() / (double) getHeight());

        for (int y = 0; y < numRows; y++) {
            for (int x = 0; x < numRows; x++) {
                setDrawColor(Palette.getDistinctColor(x + (y * numRows), saturation));
                drawFilledCircle(halfSpace + x * space, halfSpace + y * space, halfSpace);
            }
        }
    }
}
