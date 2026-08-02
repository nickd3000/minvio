package com.physmo.reference.basics;

import com.physmo.minvio.MinvioApp;

import java.awt.Color;

public class ScreenshotHotkeyExample extends MinvioApp {
    public static void main(String[] args) {
        MinvioApp app = new ScreenshotHotkeyExample();
        app.start(400, 400, "Screenshot Hotkey Example", 60);
    }

    @Override
    public void draw(double delta) {
        cls(Color.DARK_GRAY);
        setDrawColor(Color.WHITE);
        drawText("Press F12 to take a screenshot", 50, 200);
        drawText("Check console for 'Screenshot saved' message", 50, 220);

        // Visual feedback for key state
        int[] keyState = getBasicDisplay().getKeyState();
        if (keyState[java.awt.event.KeyEvent.VK_F12] != 0) {
            setDrawColor(Color.RED);
            drawFilledCircle(200, 300, 20);
        }
    }
}
