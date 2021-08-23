package com.physmo.minvio;

import org.junit.Test;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;

public class WikiExamples {

    String imageSubPath = "minvioImages/";


    @Test
    public void drawText() {
        runExample(200, 200, "drawText", bd -> {

            bd.drawText("Hello, world", 30, 70);

        });
    }

    @Test
    public void drawText_b() {
        runExample(200, 200, "drawText_b", bd -> {

            Font font1 = new Font("Times New Roman", Font.PLAIN, 20);
            Font font2 = new Font("Menlo", Font.PLAIN, 20);

            bd.setFont(font1);
            bd.drawText("Hello, world", 30, 70);
            bd.setFont(font2);
            bd.drawText("Hello, world", 30, 100);

        });
    }

    @Test
    public void drawText_c() {
        runExample(200, 200, "drawText_c", bd -> {

            String fonts[] = BasicDisplay.getAvailableFontNames();

            Font font = new Font(fonts[fonts.length / 2], Font.PLAIN, 30);

            bd.setFont(font);
            bd.drawText("Hello, world", 30, 70);

        });
    }

    @Test
    public void getDistinctColor() {

        runExample(200, 200, "getDistinctColor", bd -> {

            bd.setDrawColor(bd.getDistinctColor(1, 1));
            bd.drawFilledRect(50, 50, 50, 100);

            bd.setDrawColor(bd.getDistinctColor(16, 0.2));
            bd.drawFilledRect(100, 50, 50, 100);

        });

    }

    @Test
    public void getDisplaySize() {

        runExample(220, 200, "getDisplaySize", bd -> {

            Point displaySize = bd.getDisplaySize();
            bd.drawText("width=" + displaySize.x, 20, 40);
            bd.drawText("height=" + displaySize.y, 20, 70);

        });
    }

    @Test
    public void getWidth() {

        runExample(200, 150, "getWidth", bd -> {

            int width = bd.getWidth();
            bd.drawText("width=" + width, 20, 40);


        });
    }

    @Test
    public void getHeight() {

        runExample(200, 150, "getHeight", bd -> {

            int height = bd.getHeight();
            bd.drawText("height=" + height, 20, 40);

        });
    }

    @Test
    public void close() {

        runExample(200, 200, "close", bd -> {

            bd.close();

        });
    }

    @Test
    public void repaint() {

        runExample(200, 200, "repaint", bd -> {

            bd.drawText("repaint", 20, 40);
            // Repaint causes anything drawn this frame to be updated and made visible.
            // The FPS parameter controls how long to wait until the next repaint.
            bd.repaint(30);

        });
    }

    @Test
    public void setTitle() {

        runExample(200, 200, "setTitle", bd -> {

            // Set the name of the app shown in the title bar.
            bd.setTitle("Example title");
            bd.drawText(bd.getTitle(), 20, 40);

        });
    }

    @Test
    public void cls() {

        runExample(200, 200, "cls", bd -> {

            // Clear the screen to supplied colour.
            bd.cls(Color.ORANGE);

        });
    }

    @Test
    public void cls_b() {

        runExample(200, 200, "cls_b", bd -> {

            // cls() with no colour supplied uses the default or user defined background colour.
            bd.cls();

        });
    }

    @Test
    public void setDrawColor() {

        runExample(200, 200, "setDrawColor", bd -> {

            bd.setDrawColor(Color.BLUE); // Use a built in colour.
            bd.drawLine(0, 30, 200, 30);

            bd.setDrawColor(new Color(255, 0, 0)); // or create a new one.
            bd.drawLine(0, 60, 200, 60);

            bd.setDrawColor(bd.getDistinctColor(12, 1)); // Or use distinct colour helper.
            bd.drawLine(0, 90, 200, 90);

        });
    }

    @Test
    public void setBackgroundColor() {

        runExample(200, 200, "setBackgroundColor", bd -> {

            bd.setBackgroundColor(Color.CYAN);
            bd.cls();

        });
    }


    @Test
    public void drawImage() {

        runExample(200, 200, "drawImage", bd -> {

            BufferedImage bufferedImage = BasicDisplay.loadImage("/odin.jpg");
            bd.drawImage(bufferedImage, 0, 0);

        });
    }

    @Test
    public void drawImage_b() {

        runExample(200, 200, "drawImage_b", bd -> {

            BufferedImage bufferedImage = BasicDisplay.loadImage("/odin.jpg");
            bd.drawImage(bufferedImage, 10, 10, 64, 50);
            bd.drawImage(bufferedImage, 10, 70, 64 * 2, 50 * 2);

        });
    }

    @Test
    public void getColorAtPoint() {
        runExample(200, 200, "getColorAtPoint", bd -> {

            bd.cls(new Color(123, 134, 156));

            Color colorAtPoint1 = bd.getColorAtPoint(100, 100);
            Color colorAtPoint2 = bd.getColorAtPoint(new Point(150, 150));

            bd.setFont(10);
            bd.drawText(colorAtPoint1.toString(), 10, 30);
            bd.drawText(colorAtPoint2.toString(), 10, 60);

        });
    }

    @Test
    public void getRGBAtPoint() {
        runExample(200, 200, "getRGBAtPoint", bd -> {

            bd.cls(new Color(156, 211, 60));

            int rgbAtPoint = bd.getRGBAtPoint(100, 100);

            bd.setFont(16);

            int r = (rgbAtPoint >> 16) & 0xff;
            int g = (rgbAtPoint >> 8) & 0xff;
            int b = (rgbAtPoint) & 0xff;

            bd.drawText("Red value:   " + r, 10, 30);
            bd.drawText("Green value: " + g, 10, 60);
            bd.drawText("Blue value:  " + b, 10, 90);


        });
    }


    @Test
    public void drawPoint() {
        runExample(200, 200, "drawPoint", bd -> {

            Point point = new Point(80, 80);

            bd.drawPoint(point);
            bd.drawPoint(120, 120);

        });
    }


    @Test
    public void drawline() {
        runExample(200, 200, "drawLine", bd -> {

            bd.drawLine(20, 20, 160, 160);
            bd.drawLine(20.0, 100.0, 160, 160);

        });
    }

    @Test
    public void drawLine_b() {
        runExample(200, 200, "drawLine_b", bd -> {

            Point p1 = new Point(10, 10);
            Point p2 = new Point(100, 100);
            bd.drawLine(p1, p2);

        });
    }

    @Test
    public void drawLine_thickness() {
        runExample(200, 200, "drawLine_thickness", bd -> {

            Point p1 = new Point(10, 10);
            Point p2 = new Point(100, 100);
            bd.drawLine(p1, p2, 3);

            bd.drawLine(130, 60, 20, 100, 3);

        });
    }

    @Test
    public void drawRect() {
        runExample(200, 200, "drawRect", bd -> {

            bd.drawRect(50, 50, 100, 100);

        });
    }

    @Test
    public void drawFilledRect() {
        runExample(200, 200, "drawFilledRect", bd -> {

            bd.drawFilledRect(50, 50, 100, 100);

        });
    }

    @Test
    public void drawFilledPolygon() {
        runExample(200, 200, "drawFilledPolygon", bd -> {

            int[] xPoints = new int[]{20, 100, 180};
            int[] yPoints = new int[]{20, 180, 20};

            bd.drawFilledPolygon(xPoints, yPoints, 3);

        });
    }

//    @Test
//    public void thing() {
//        runExample(200,200,"thing",bd -> {
//
//            bd.drawRect(50,50,100,100);
//
//        });
//    }

    /****************************************************************/

    public void runExample(int width, int height, String fileName, BDRunnable code) {
        String filePath = System.getProperty("user.home");
        filePath += File.separator + imageSubPath + fileName + ".png";

        BasicDisplay bd = new BasicDisplayAwt(width, height);

        code.run(bd);

        bd.repaint();
        bd.saveScreenshot(filePath);
    }

    @Test
    public void t1() {
        String fonts[] = BasicDisplay.getAvailableFontNames();

        for (String font : fonts) {
            System.out.println(font);
        }
    }

}
