package com.physmo.reference.beginner;

import com.physmo.minvio.MinvioApp;

import java.awt.Color;

/**
 * Teaches if statements by changing color when the mouse crosses the center.
 */
class IfStatementExample extends MinvioApp {

    public static void main(String... args) {
        MinvioApp app = new IfStatementExample();
        app.start(400, 300, "If Statement Example", 60);
    }

    @Override
    public void draw(double delta) {
        cls(new Color(34, 39, 46));

        // An if statement chooses which block of code should run.
        if (getMouseX() < getWidth() / 2) {
            setDrawColor(new Color(90, 190, 255));
            drawText("The mouse is on the left", 95, 40);
        } else {
            setDrawColor(new Color(255, 176, 74));
            drawText("The mouse is on the right", 90, 40);
        }

        drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
        drawFilledCircle(getMouseX(), getMouseY(), 25);
    }
}
