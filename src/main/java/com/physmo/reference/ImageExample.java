package com.physmo.reference;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.DrawingContext;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * The ImageExample class demonstrates rendering of an image and its manipulation
 * using a custom drawing context. It handles loading an image, scaling, rotating
 * it in a circular formation, and continuously updating the display.
 */
class ImageExample {
    static double t = 0;
    static String imagePath = "/odin.jpg";

    public static void main(String... args) {

        BasicDisplay bd = new BasicDisplayAwt(400, 400);
        DrawingContext dc = bd.getDrawingContext();

        BufferedImage image = null;
        try {
            image = BasicDisplay.loadImage(imagePath);
        } catch (IOException e) {
            System.out.println("Could not locat path: " + imagePath);
        }

        bd.setTitle("Image Example");
        dc.setFont(10);

        while (true) {
            dc.cls(Color.lightGray);

            dc.drawImage(image, 0, 0);
            dc.drawImage(image, image.getWidth(), 0, 100, 100);

            ringOfImages(dc, image);

            bd.repaint(60);
        }
    }

    // Draw many small scaled images in a rotating ring formation.
    public static void ringOfImages(DrawingContext dc, BufferedImage image) {
        t += 0.01;

        int numSprites = 30 * 5;
        double angleSpan = (Math.PI * 2) / (double) numSprites;
        double radius = 170;
        for (int i = 0; i < numSprites; i++) {
            int x = (int) (Math.sin(t + i * angleSpan) * radius);
            int y = (int) (Math.cos(t + i * angleSpan) * radius);
            dc.drawImage(image, x + (dc.getWidth() / 2) - 16, y + (dc.getHeight() / 2) - 16, 32, 32);
        }

    }
}
