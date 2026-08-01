package com.physmo.reference.basics;

import com.physmo.minvio.MinvioApp;

import java.awt.Color;
import java.io.File;

class SaveScreenshotExample extends MinvioApp {

    boolean saved = false;

    public static void main(String... args) {
        MinvioApp app = new SaveScreenshotExample();
        app.start(200, 200, "Save Screenshot Example", 60);
    }

    @Override
    public void draw(double delta) {
        cls(Color.LIGHT_GRAY);
        setDrawColor(Color.WHITE);
        drawFilledRect(75, 75, 50, 50);
        setDrawColor(Color.BLUE);
        drawCircle(100, 100, 70);
        drawText("X:" + getMouseX() + " Y:" + getMouseY(), 10, 190);
        drawText("Tick :" + getFps(), 10, 160);

        if (!saved) {
            String filePath = "";
            filePath = System.getProperty("user.home") + File.separator + getTitle() + ".png";
            saveScreenshot(filePath);

            saved = true;
        }
    }
}
