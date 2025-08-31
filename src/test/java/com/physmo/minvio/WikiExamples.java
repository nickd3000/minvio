package com.physmo.minvio;

import com.physmo.minvio.types.Point;
import com.physmo.minvio.utils.Gradient;
import com.physmo.minvio.utils.MatrixDrawer;
import com.physmo.minvio.utils.Palette;
import com.physmo.minvio.utils.VoronoiNoise;
import org.junit.Test;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/*
    These tests represent the wiki reference entries and generate screenshots for use in the wiki.
 */
public class WikiExamples {

    String imageSubPath = "minvioImages/";

    @Test
    public void appLogo() {
        runExample(800, 200, "appLogo", bd -> {
            MatrixDrawer matrixDrawer = new MatrixDrawer(400, 100);

            Color background = new Color(48, 60, 107);
            Color colMin = new Color(45, 121, 167);
            Color colV = new Color(220, 220, 220);
            Color colIo = new Color(248, 175, 86);
            Color colShadow = new Color(1, 1, 45, 100);

            Color gradCol1 = new Color(0, 0, 0, 255);
            Color gradCol2 = new Color(207, 218, 231, 255);
            DrawingContext dc = bd.getDrawingContext();
            dc.cls(background);
            Font font1 = new Font("Arial Black", Font.PLAIN, 180);
            dc.setFont(font1);

            matrixDrawer.draw(dc, 0, 0, 2, 5.1, (x, y, a, d, t) -> {
                double val = VoronoiNoise.noise(x * 3 * 3, y * 3, t);
                return val * val;
            }, new Gradient(gradCol1, gradCol2)); // We can supply null if we don't want to use a gradient.

            int x = 100, y = 160;
            int o1 = 325;
            int o2 = 410;
            int so = 5;

            // Min
            dc.setDrawColor(colShadow);
            dc.drawText("Min", x, y + so);
            dc.setDrawColor(colMin);
            dc.drawText("Min", x, y);

            // io
            dc.setDrawColor(colShadow);
            dc.drawText("io", x + o2, y + so);
            dc.setDrawColor(colIo);
            dc.drawText("io", x + o2, y);

            // v
            dc.setDrawColor(colShadow);
            dc.drawText("v", x + o1, y + so);
            dc.setDrawColor(colV);
            dc.drawText("v", x + o1, y - so);
        });
    }

    @Test
    public void drawText() {
        runExample(200, 200, "drawText", bd -> {
            DrawingContext dc = bd.getDrawingContext();
            dc.drawText("Hello, world", 70, 90);

        });
    }

    @Test
    public void drawText_b() {
        runExample(200, 200, "drawText_b", bd -> {
            DrawingContext dc = bd.getDrawingContext();
            Font font1 = new Font("Times New Roman", Font.PLAIN, 20);
            Font font2 = new Font("Menlo", Font.PLAIN, 20);

            dc.setFont(font1);
            dc.drawText("Hello, world", 30, 70);
            dc.setFont(font2);
            dc.drawText("Hello, world", 30, 100);

        });
    }

    @Test
    public void drawText_c() {
        runExample(200, 200, "drawText_c", bd -> {

            String fonts[] = BasicDisplay.getAvailableFontNames();

            Font font = new Font(fonts[fonts.length / 2], Font.PLAIN, 30);
            DrawingContext dc = bd.getDrawingContext();
            dc.setFont(font);
            dc.drawText("Hello, world", 30, 70);

        });
    }

    @Test
    public void getDistinctColor() {

        runExample(200, 200, "getDistinctColor", bd -> {
            DrawingContext dc = bd.getDrawingContext();
            dc.setDrawColor(Palette.getDistinctColor(1, 1));
            dc.drawFilledRect(50, 50, 50, 100);

            dc.setDrawColor(Palette.getDistinctColor(16, 0.2));
            dc.drawFilledRect(100, 50, 50, 100);

        });

    }

    @Test
    public void getDisplaySize() {

        runExample(220, 200, "getDisplaySize", bd -> {
            DrawingContext dc = bd.getDrawingContext();
            Point displaySize = bd.getDisplaySize();
            dc.drawText("width=" + displaySize.x, 20, 40);
            dc.drawText("height=" + displaySize.y, 20, 70);

        });
    }

    @Test
    public void getWidth() {

        runExample(200, 150, "getWidth", bd -> {
            DrawingContext dc = bd.getDrawingContext();
            int width = bd.getWidth();
            dc.drawText("width=" + width, 20, 40);


        });
    }

    @Test
    public void getHeight() {

        runExample(200, 150, "getHeight", bd -> {
            DrawingContext dc = bd.getDrawingContext();
            int height = bd.getHeight();
            dc.drawText("height=" + height, 20, 40);

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

            bd.getDrawingContext().drawText("repaint", 20, 40);
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
            bd.getDrawingContext().drawText(bd.getTitle(), 20, 40);

        });
    }

    @Test
    public void cls() {

        runExample(200, 200, "cls", bd -> {

            // Clear the screen to supplied colour.
            bd.getDrawingContext().cls(Color.ORANGE);

        });
    }

    @Test
    public void cls_b() {

        runExample(200, 200, "cls_b", bd -> {

            // cls() with no colour supplied uses the default or user defined background colour.
            bd.getDrawingContext().cls();

        });
    }

    @Test
    public void setDrawColor() {

        runExample(200, 200, "setDrawColor", bd -> {

            bd.getDrawingContext().setDrawColor(Color.BLUE); // Use a built in colour.
            bd.getDrawingContext().drawLine(0, 30, 200, 30);

            bd.getDrawingContext().setDrawColor(new Color(255, 0, 0)); // or create a new one.
            bd.getDrawingContext().drawLine(0, 60, 200, 60);

            bd.getDrawingContext().setDrawColor(Palette.getDistinctColor(12, 1)); // Or use distinct colour helper.
            bd.getDrawingContext().drawLine(0, 90, 200, 90);

        });
    }

    @Test
    public void setBackgroundColor() {

        runExample(200, 200, "setBackgroundColor", bd -> {

            bd.getDrawingContext().setBackgroundColor(Color.CYAN);
            bd.getDrawingContext().cls();

        });
    }


    @Test
    public void drawImage() {

        runExample(200, 200, "drawImage", bd -> {

            BufferedImage bufferedImage = BasicDisplay.loadImage("/odin.jpg");
            bd.getDrawingContext().drawImage(bufferedImage, 0, 0);

        });
    }

    @Test
    public void drawImage_b() {

        runExample(200, 200, "drawImage_b", bd -> {

            BufferedImage bufferedImage = BasicDisplay.loadImage("/odin.jpg");
            bd.getDrawingContext().drawImage(bufferedImage, 10, 10, 64, 50);
            bd.getDrawingContext().drawImage(bufferedImage, 10, 70, 64 * 2, 50 * 2);

        });
    }

    @Test
    public void getColorAtPoint() {
        runExample(200, 200, "getColorAtPoint", bd -> {

            bd.getDrawingContext().cls(new Color(123, 134, 156));

            Color colorAtPoint1 = bd.getDrawingContext().getColorAtPoint(100, 100);
            Color colorAtPoint2 = bd.getDrawingContext().getColorAtPoint(new Point(150, 150));

            bd.getDrawingContext().setFont(10);
            bd.getDrawingContext().drawText(colorAtPoint1.toString(), 10, 30);
            bd.getDrawingContext().drawText(colorAtPoint2.toString(), 10, 60);

        });
    }

    @Test
    public void getRGBAtPoint() {
        runExample(200, 200, "getRGBAtPoint", bd -> {

            bd.getDrawingContext().cls(new Color(156, 211, 60));

            int rgbAtPoint = bd.getDrawingContext().getRGBAtPoint(100, 100);

            bd.getDrawingContext().setFont(16);

            int r = (rgbAtPoint >> 16) & 0xff;
            int g = (rgbAtPoint >> 8) & 0xff;
            int b = (rgbAtPoint) & 0xff;

            bd.getDrawingContext().drawText("Red value:   " + r, 10, 30);
            bd.getDrawingContext().drawText("Green value: " + g, 10, 60);
            bd.getDrawingContext().drawText("Blue value:  " + b, 10, 90);


        });
    }


    @Test
    public void drawPoint() {
        runExample(200, 200, "drawPoint", bd -> {

            Point point = new Point(80, 80);

            bd.getDrawingContext().drawPoint(point);
            bd.getDrawingContext().drawPoint(120, 120);

        });
    }


    @Test
    public void drawline() {
        runExample(200, 200, "drawLine", bd -> {

            bd.getDrawingContext().drawLine(20, 20, 160, 160);
            bd.getDrawingContext().drawLine(20.0, 100.0, 160, 160);

        });
    }

    @Test
    public void drawLine_b() {
        runExample(200, 200, "drawLine_b", bd -> {

            Point p1 = new Point(10, 10);
            Point p2 = new Point(100, 100);
            bd.getDrawingContext().drawLine(p1, p2);

        });
    }

    @Test
    public void drawLine_thickness() {
        runExample(200, 200, "drawLine_thickness", bd -> {

            Point p1 = new Point(10, 10);
            Point p2 = new Point(100, 100);
            bd.getDrawingContext().drawLine(p1, p2, 3);

            bd.getDrawingContext().drawLine(130, 60, 20, 100, 5);

        });
    }

    @Test
    public void drawRect() {
        runExample(200, 200, "drawRect", bd -> {

            bd.getDrawingContext().drawRect(50, 50, 100, 100);

        });
    }

    @Test
    public void drawFilledRect() {
        runExample(200, 200, "drawFilledRect", bd -> {

            bd.getDrawingContext().drawFilledRect(50, 50, 100, 100);

        });
    }

    @Test
    public void drawFilledPolygon() {
        runExample(200, 200, "drawFilledPolygon", bd -> {

            int[] xPoints = new int[]{20, 100, 180};
            int[] yPoints = new int[]{20, 180, 20};

            bd.getDrawingContext().drawFilledPolygon(xPoints, yPoints, 3);

        });
    }

    @Test
    public void drawCircle() {
        runExample(200, 200, "drawCircle", bd -> {

            bd.getDrawingContext().drawCircle(100, 100, 50);

        });
    }

    @Test
    public void drawFilledCircle() {
        runExample(200, 200, "drawFilledCircle", bd -> {

            bd.getDrawingContext().drawFilledCircle(100, 100, 50);

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
        String filePath = "/tmp/"; //System.getProperty("user.home");
        filePath += File.separator + imageSubPath + fileName + ".png";

        BasicDisplay bd = new BasicDisplayAwt(width, height);

        try {
            code.run(bd);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
