package com.physmo.reference.minvioapp;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.Palette;

import java.awt.Color;

class ResizeExample extends MinvioApp {

    int counter = 0;
    Color drawColor = new Color(counter, 128, 200);


    public static void main(String... args) {
        MinvioApp app = new ResizeExample();
        app.start(400, 400, "ResizeExample", 60);
    }


    @Override
    public void init(BasicDisplay bd) {
        super.init(bd);

        // Do some work when a resize event takes place.
        bd.addResizeListener((left, right) -> {
            counter = (counter + 25) % 250;
            drawColor = new Color(counter, 128, 200);
            return left;
        });

    }

    @Override
    public void draw(double delta) {
        cls(Palette.SLATE);
        setDrawColor(drawColor);

        drawFilledCircle((double) getWidth() / 2, (double) getHeight() / 2, 20);

        setDrawColor(Palette.AMBER);
        drawText("Window width:" + getWidth() + " height:" + getHeight(),
                10, getHeight() - 20);

    }
}
