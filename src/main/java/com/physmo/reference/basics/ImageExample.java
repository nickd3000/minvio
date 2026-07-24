package com.physmo.reference.basics;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.MinvioApp;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Demonstrates loading and drawing images using the default MinvioApp style.
 */
class ImageExample extends MinvioApp {

    private static final String IMAGE_PATH = "/odin.jpg";
    private BufferedImage image;
    private double time;

    public static void main(String... args) {
        MinvioApp app = new ImageExample();
        app.start(400, 400, "Image Example", 60);
    }

    @Override
    public void init(BasicDisplay bd) {
        try {
            image = BasicDisplay.loadImage(IMAGE_PATH);
        } catch (IOException e) {
            throw new IllegalStateException("Could not load image: " + IMAGE_PATH, e);
        }

        setFont(10);
    }

    @Override
    public void draw(double delta) {
        time += delta;

        cls(Color.LIGHT_GRAY);
        drawImage(image, 0, 0);
        drawImage(image, image.getWidth(), 0, 100, 100);
        drawRingOfImages();
    }

    private void drawRingOfImages() {
        int numSprites = 30;
        double angleSpan = (Math.PI * 2) / numSprites;
        double radius = 170;

        for (int i = 0; i < numSprites; i++) {
            double x = Math.sin(time + i * angleSpan) * radius;
            double y = Math.cos(time + i * angleSpan) * radius;
            drawImage(image, x + (getWidth() / 2.0) - 16, y + (getHeight() / 2.0) - 16, 32, 32);
        }
    }
}
