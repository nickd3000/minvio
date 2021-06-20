package com.physmo.minvio.examples;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

// Load an image from resources folder and draw it unscaled and then scaled.
class ImageExample {
    static double t = 0;

    public static void main(String... args) {
        BasicDisplay bd = new BasicDisplayAwt(400, 400);

        BufferedImage image = loadImage("/odin.jpg");

        bd.setTitle("Image Example");
        bd.setFont(10);

        while (true) {
            bd.cls(Color.lightGray);

            bd.drawImage(image, 0, 0);
            bd.drawImage(image, image.getWidth(), 0, 100, 100);

            ringOfImages(bd, image);

            bd.repaint(60);
        }
    }

    public static BufferedImage loadImage(String name) {
        URL file = ImageExample.class.getResource(name);
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    // Draw many small scaled images in a rotating ring formation.
    public static void ringOfImages(BasicDisplay bd, BufferedImage image) {
        t += 0.01;

        int numSprites = 30 * 5;
        double angleSpan = (Math.PI * 2) / (double) numSprites;
        double radius = 170;
        for (int i = 0; i < numSprites; i++) {
            int x = (int) (Math.sin(t + i * angleSpan) * radius);
            int y = (int) (Math.cos(t + i * angleSpan) * radius);
            bd.drawImage(image, x + (bd.getWidth() / 2) - 16, y + (bd.getHeight() / 2) - 16, 32, 32);
        }

    }
}
