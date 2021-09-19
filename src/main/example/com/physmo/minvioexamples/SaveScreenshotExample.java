package com.physmo.minvioexamples;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.MinvioApp;

import java.awt.Color;
import java.io.File;

class SaveScreenshotExample extends MinvioApp {

    boolean saved = false;

    public static void main(String... args) {
        MinvioApp app = new SaveScreenshotExample();
        // Start the app running with a window size of 200x200 pixels, at 60 frames per second.
        app.start(new BasicDisplayAwt(200, 200), "Save Screenshot Example", 60);
    }

    @Override
    public void draw(BasicDisplay bd, double delta) {
        bd.cls(Color.LIGHT_GRAY);
        bd.setDrawColor(Color.WHITE);
        bd.drawFilledRect(75, 75, 50, 50);
        bd.setDrawColor(Color.BLUE);
        bd.drawCircle(100, 100, 70);
        bd.drawText("X:" + bd.getMouseX() + " Y:" + bd.getMouseY(), 10, 190);
        bd.drawText("Tick :" + getFps(), 10, 160);

        if (saved == false) {
            String filePath = "";
            filePath = System.getProperty("user.home") + File.separator + bd.getTitle() + ".png";
            bd.saveScreenshot(filePath);

            // You could also use this version which saves to the
            // user home folder with the app name as the file name.
            // bd.saveScreenshot();

            saved = true;
        }
    }
}
