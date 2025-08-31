package com.physmo.reference.wiki;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.Gradient;
import com.physmo.minvio.utils.MatrixDrawer;
import com.physmo.minvio.utils.VoronoiNoise;

import java.awt.Color;
import java.awt.Font;
import java.io.File;

/**
 * This class is used to generate example images for the wiki
 */
class LogoCreator extends MinvioApp {

    String imageSubPath = "minvioImages/";
    boolean hasRun = false;

    public static void main(String... args) {
        MinvioApp app = new LogoCreator();
        app.start(800, 200, "Logo creator", 60);
    }

    @Override
    public void update(BasicDisplay bd, double delta) {
        super.update(bd, delta);
        if (hasRun) return;
        hasRun = true;

        runExample(800, 200, "appLogo", () -> {
            MatrixDrawer matrixDrawer = new MatrixDrawer(400, 100);

            Color background = new Color(48, 60, 107);
            Color colMin = new Color(45, 121, 167);
            Color colV = new Color(220, 220, 220);
            Color colIo = new Color(248, 175, 86);
            Color colShadow = new Color(1, 1, 45, 100);

            Color gradCol1 = new Color(0, 0, 0, 255);
            Color gradCol2 = new Color(207, 218, 231, 255);
            //DrawingContext dc = bd.getDrawingContext();
            cls(background);
            Font font1 = new Font("Arial Black", Font.PLAIN, 180);
            setFont(font1);

            matrixDrawer.draw(this.getDrawingContext(), 0, 0, 2, 5.1, (x, y, a, d, t) -> {
                double val = VoronoiNoise.noise(x * 3 * 3, y * 3, t);
                return val * val;
            }, new Gradient(gradCol1, gradCol2)); // We can supply null if we don't want to use a gradient.

            int x = 100, y = 160;
            int o1 = 325;
            int o2 = 410;
            int so = 5;

            // Min
            setDrawColor(colShadow);
            drawText("Min", x, y + so);
            setDrawColor(colMin);
            drawText("Min", x, y);

            // io
            setDrawColor(colShadow);
            drawText("io", x + o2, y + so);
            setDrawColor(colIo);
            drawText("io", x + o2, y);

            // v
            setDrawColor(colShadow);
            drawText("v", x + o1, y + so);
            setDrawColor(colV);
            drawText("v", x + o1, y - so);
        });
    }


    public void runExample(int width, int height, String fileName, Runnable code) {
        String filePath = "/tmp/"; //System.getProperty("user.home");
        filePath += File.separator + imageSubPath + fileName + ".png";

        getBasicDisplay().reset();
        cls();
        code.run();
        getBasicDisplay().repaint();
        saveScreenshot(filePath);
    }
}
