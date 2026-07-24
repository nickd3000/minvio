package com.physmo.reference.input;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.BasicUtils;
import com.physmo.minvio.utils.Palette;

import java.awt.Color;

/**
 * Demonstrates mouse position and mouse-button input using the default
 * MinvioApp style.
 */
public class MouseExample extends MinvioApp {

    private int previousX;
    private int previousY;
    private int colorIndex;

    public static void main(String... args) {
        MinvioApp app = new MouseExample();
        app.start(400, 400, "Mouse Example", 60);
    }

    @Override
    public void init(BasicDisplay bd) {
        previousX = getMouseX();
        previousY = getMouseY();
        cls(Color.BLACK);
    }

    @Override
    public void draw(double delta) {
        int mouseX = getMouseX();
        int mouseY = getMouseY();
        int distance = BasicUtils.distance(previousX, previousY, mouseX, mouseY);

        setDrawColor(Palette.getDistinctColor(colorIndex, 0.7));
        drawFilledCircle(mouseX, mouseY, Math.max(2, distance / 2.0));

        colorIndex++;
        previousX = mouseX;
        previousY = mouseY;

        // Fade the previous drawing by painting a transparent layer over it.
        cls(new Color(0, 0, 0, 10));

        if (getBasicDisplay().getMouseButtonLeft()) {
            cls(Color.BLACK);
        }
    }
}
