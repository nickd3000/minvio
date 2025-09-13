package com.physmo.reference.minvioapp;

import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.Palette;
import com.physmo.minvio.utils.Spacer;

class SpacerExample extends MinvioApp {


    public static void main(String... args) {
        MinvioApp app = new SpacerExample();
        app.start(400, 400, "Spacer Example", 60);
    }

    @Override
    public void draw(double delta) {
        cls(Palette.SLATE);
        setDrawColor(Palette.AMBER);

        // Create spacer before loop...
        Spacer<Integer> ySpacer = new Spacer<>(0, 400, 4);

        for (int y : ySpacer) {

            // ...or create and use inline.
            for (int x : new Spacer<>(0, 400, 6)) {
                drawLine(0, y, getWidth(), y);
                drawLine(x, 0, x, getHeight());
            }

        }

    }
}
