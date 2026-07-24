package com.physmo.reference.concepts;

import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.Gradient;

import java.awt.Color;

/**
 * Demonstrates color interpolation with a Gradient.
 */
class ColorInterpolationExample extends MinvioApp {

    private final Gradient gradient = new Gradient();

    public static void main(String... args) {
        MinvioApp app = new ColorInterpolationExample();
        app.start(500, 260, "Color Interpolation Example", 60);
    }

    @Override
    public void init(com.physmo.minvio.BasicDisplay bd) {
        gradient.addColor(0.00, new Color(34, 52, 104));
        gradient.addColor(0.35, new Color(80, 190, 160));
        gradient.addColor(0.70, new Color(255, 210, 90));
        gradient.addColor(1.00, new Color(230, 80, 95));
    }

    @Override
    public void draw(double delta) {
        cls(new Color(25, 28, 35));

        for (int x = 0; x < getWidth(); x++) {
            double ratio = (double) x / (getWidth() - 1);
            setDrawColor(gradient.getColor(ratio));
            drawLine(x, 70, x, 190);
        }

        double mouseRatio = Math.max(0, Math.min(1, (double) getMouseX() / getWidth()));
        setDrawColor(Color.WHITE);
        drawLine(getMouseX(), 55, getMouseX(), 205, 2);
        drawText(String.format("position %.2f", mouseRatio), 20, 35);
    }
}
