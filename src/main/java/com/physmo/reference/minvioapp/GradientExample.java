package com.physmo.reference.minvioapp;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.Gradient;

import java.awt.Color;

/**
 * The GradientExample class demonstrates an application that creates and displays
 * a vertical gradient using the MinvioApp framework.
 * <p>
 * The application initializes a gradient with specified colors at different points
 * and renders this gradient vertically on the screen.
 * Additionally, it draws a circle in the middle of the screen to represent the sun.
 */
public class GradientExample extends MinvioApp {

    Gradient gradient = new Gradient();

    public static void main(String... args) {
        MinvioApp app = new GradientExample();
        app.start(200, 200, "Gradient Example", 30);
    }

    @Override
    public void init(BasicDisplay bd) {
        gradient.addColor(0.0, new Color(14, 2, 2));
        gradient.addColor(0.2, new Color(18, 62, 205));
        gradient.addColor(0.4, new Color(209, 47, 225));
        gradient.addColor(0.5, new Color(245, 199, 56));
        gradient.addColor(0.55, new Color(104, 75, 141));
        gradient.addColor(1.0, new Color(26, 54, 117));
    }

    @Override
    public void draw(double delta) {

        for (int y = 0; y < getHeight(); y++) {
            Color color = gradient.getColor((double) y / (double) getHeight());
            setDrawColor(color);
            drawFilledRect(0, y, getWidth(), 1);

            // Draw the sun for fun.
            if (y == getHeight() / 2) {
                drawFilledCircle(getHeight() / 2, getHeight() / 2, 50);
            }
        }

    }

}
