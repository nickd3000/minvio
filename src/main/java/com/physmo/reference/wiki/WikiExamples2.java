package com.physmo.reference.wiki;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.types.Point;
import com.physmo.minvio.utils.Palette;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class is used to generate example images for the wiki
 */
class WikiExamples2 extends MinvioApp {

    String imageSubPath = "minvioImages/";
    boolean hasRun = false;

    public static void main(String... args) {
        MinvioApp app = new WikiExamples2();
        app.start(200, 200, "Simple Example", 60);
    }

    @Override
    public void update(BasicDisplay bd, double delta) {
        super.update(bd, delta);
        if (hasRun) return;
        hasRun = true;

        runExample(200, 200, "drawText", () -> {

            drawText("Hello, world", 30, 70);

        });

        runExample(200, 200, "drawText_b", () -> {

            Font font1 = new Font("Times New Roman", Font.PLAIN, 20);
            Font font2 = new Font("Menlo", Font.PLAIN, 20);

            setFont(font1);
            drawText("Hello, world", 30, 70);
            setFont(font2);
            drawText("Hello, world", 30, 100);

        });

        runExample(200, 200, "drawText_c", () -> {

            String[] fonts = BasicDisplay.getAvailableFontNames();

            Font font = new Font(fonts[fonts.length / 2], Font.PLAIN, 30);
            setFont(font);
            drawText("Hello, world", 30, 70);

        });

        runExample(200, 200, "getDistinctColor", () -> {

            setDrawColor(Palette.getDistinctColor(1, 1));
            drawFilledRect(50, 50, 50, 100);

            setDrawColor(Palette.getDistinctColor(16, 0.2));
            drawFilledRect(100, 50, 50, 100);

        });

        runExample(220, 200, "getDisplaySize", () -> {

            Point displaySize = bd.getDisplaySize();
            drawText("width=" + displaySize.x, 20, 40);
            drawText("height=" + displaySize.y, 20, 70);

        });

        runExample(200, 150, "getWidth", () -> {

            int width = bd.getWidth();
            drawText("width=" + width, 20, 40);

        });

        runExample(200, 150, "getHeight", () -> {

            int height = bd.getHeight();
            drawText("height=" + height, 20, 40);

        });

        runExample(200, 150, "close", () -> {

            bd.close();

        });

        runExample(200, 200, "repaint", () -> {

            drawText("repaint", 20, 40);
            // Repaint causes anything drawn in this frame to be updated and made visible.
            // The FPS parameter controls how long to wait until the next repaint.
            bd.repaint(30);

        });

        runExample(200, 200, "setTitle", () -> {

            // Set the name of the app shown in the title bar.
            bd.setTitle("Example title");
            drawText(bd.getTitle(), 20, 40);

        });

        runExample(200, 200, "cls", () -> {

            // Clear the screen to supplied colour.
            cls(Palette.ORANGE);

        });

        runExample(200, 200, "cls_b", () -> {

            // cls() with no colour supplied uses the default or user-defined background colour.
            cls();

        });

        runExample(200, 200, "setDrawColor", () -> {

            setDrawColor(Palette.BLUE); // Use a built-in colour.
            drawLine(0, 30, 200, 30);

            setDrawColor(new Color(255, 0, 0)); // or create a new one.
            drawLine(0, 60, 200, 60);

            setDrawColor(Palette.getDistinctColor(12, 1)); // Or use distinct colour helper.
            drawLine(0, 90, 200, 90);

        });


        runExample(200, 200, "setBackgroundColor", () -> {

            setBackgroundColor(Color.CYAN);
            cls();

        });


        runExample(200, 200, "drawImage", () -> {

            BufferedImage bufferedImage = null;
            try {
                bufferedImage = BasicDisplay.loadImage("/odin.jpg");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            drawImage(bufferedImage, 0, 0);

        });

        runExample(200, 200, "drawImage_b", () -> {

            BufferedImage bufferedImage = null;
            try {
                bufferedImage = BasicDisplay.loadImage("/odin.jpg");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            drawImage(bufferedImage, 10, 10, 64, 50);
            drawImage(bufferedImage, 10, 70, 64 * 2, 50 * 2);

        });

        runExample(200, 200, "getColorAtPoint", () -> {

            cls(new Color(123, 134, 156));

            Color colorAtPoint1 = getColorAtPoint(100, 100);
            Color colorAtPoint2 = getColorAtPoint(new Point(150, 150));

            setFont(10);
            drawText(colorAtPoint1.toString(), 10, 30);
            drawText(colorAtPoint2.toString(), 10, 60);

        });


        runExample(200, 200, "getRGBAtPoint", () -> {

            cls(new Color(156, 211, 60));

            int rgbAtPoint = getRGBAtPoint(100, 100);

            setFont(16);

            int r = (rgbAtPoint >> 16) & 0xff;
            int g = (rgbAtPoint >> 8) & 0xff;
            int b = (rgbAtPoint) & 0xff;

            drawText("Red value:   " + r, 10, 30);
            drawText("Green value: " + g, 10, 60);
            drawText("Blue value:  " + b, 10, 90);


        });


        runExample(200, 200, "drawPoint", () -> {

            Point point = new Point(80, 80);
            drawPoint(point);

            drawPoint(120, 120);

        });

        runExample(200, 200, "drawLine", () -> {

            drawLine(20, 20, 160, 160);
            drawLine(20.0, 100.0, 160, 160);

        });

        runExample(200, 200, "drawLine_b", () -> {

            Point p1 = new Point(10, 10);
            Point p2 = new Point(100, 100);
            drawLine(p1, p2);

        });


        runExample(200, 200, "drawLine_thickness", () -> {

            Point p1 = new Point(10, 10);
            Point p2 = new Point(100, 100);
            drawLine(p1, p2, 3);

            drawLine(130, 60, 20, 100, 5);

        });

        runExample(200, 200, "drawRect", () -> {

            drawRect(50, 50, 100, 100);

        });

        runExample(200, 200, "drawFilledRect", () -> {

            drawFilledRect(50, 50, 100, 100);

        });

        runExample(200, 200, "drawFilledPolygon", () -> {

            int[] xPoints = new int[]{20, 100, 180};
            int[] yPoints = new int[]{20, 180, 20};

            drawFilledPolygon(xPoints, yPoints, 3);

        });

        runExample(200, 200, "drawCircle", () -> {

            drawCircle(100, 100, 50);

        });

        runExample(200, 200, "drawFilledCircle", () -> {

            drawFilledCircle(100, 100, 50);

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
