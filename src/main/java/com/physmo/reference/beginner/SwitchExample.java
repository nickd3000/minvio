package com.physmo.reference.beginner;

import com.physmo.minvio.MinvioApp;

import java.awt.Color;

import static java.awt.event.KeyEvent.VK_1;
import static java.awt.event.KeyEvent.VK_2;
import static java.awt.event.KeyEvent.VK_3;

/**
 * Teaches switch statements by choosing between drawing modes.
 */
class SwitchExample extends MinvioApp {

    int mode = 1;

    public static void main(String... args) {
        MinvioApp app = new SwitchExample();
        app.start(400, 300, "Switch Example", 60);
    }

    @Override
    public void draw(double delta) {
        int[] keys = getBasicDisplay().getKeyState();
        if (keys[VK_1] != 0) mode = 1;
        if (keys[VK_2] != 0) mode = 2;
        if (keys[VK_3] != 0) mode = 3;

        cls(new Color(30, 35, 42));
        setDrawColor(Color.WHITE);
        drawText("Press 1, 2, or 3", 20, 30);

        // A switch is useful when one value can choose between several cases.
        switch (mode) {
            case 1:
                setDrawColor(new Color(111, 196, 255));
                drawFilledCircle(200, 150, 55);
                break;
            case 2:
                setDrawColor(new Color(255, 190, 72));
                drawFilledRect(145, 95, 110, 110);
                break;
            case 3:
                setDrawColor(new Color(140, 220, 150));
                drawFilledTriangle(200, 80, 130, 210, 270, 210);
                break;
            default:
                drawText("Unknown mode", 150, 150);
                break;
        }
    }
}
