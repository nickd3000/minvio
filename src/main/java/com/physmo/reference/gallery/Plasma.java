package com.physmo.reference.gallery;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.DrawingContext;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.Gradient;

import java.awt.Color;

import static com.physmo.minvio.utils.BasicUtils.mapper;
import static com.physmo.minvio.utils.PerlinNoise.noise;

/**
 * The Plasma class is an extension of the MinvioApp framework, responsible for creating
 * a procedural plasma-style visualization effect using Perlin noise and a customizable gradient.
 * The visualization is drawn dynamically on a graphical display.
 * <p>
 * This class demonstrates the use of noise functions, gradients, and drawing operations
 * to render fluid and visually pleasing effects. The behavior of the plasma is influenced
 * by the scrolling positions and gradient mapping.
 */
public class Plasma extends MinvioApp {

    double scrollPosX = 0;
    double scrollPosY = 0;
    Gradient gradient = new Gradient();

    public static void main(String... args) {
        MinvioApp app = new Plasma();
        app.start(400, 400, "Plasma", 60);
    }

    @Override
    public void init(BasicDisplay bd) {
        gradient.addColor(0.3, new Color(237, 201, 113, 255));
        gradient.addColor(0.5, new Color(48, 25, 23));
        gradient.addColor(0.7, new Color(143, 39, 241));
    }

    @Override
    public void draw(double delta) {
        int cellSize = 5;
        double scale = 0.05;
        scrollPosX += delta * 0.5;
        scrollPosY += delta * 0.2;

        for (int y = 0; y < getHeight() / cellSize; y++) {
            for (int x = 0; x < getWidth() / cellSize; x++) {

                double noise = noise(scrollPosX + (x * scale), scrollPosY + (y * scale), scrollPosX);

                setColourFromNoise(getDrawingContext(), noise);

                //dc.drawFilledRect(x * cellSize, y * cellSize, cellSize, cellSize);
                drawFilledRect(x * cellSize, y * cellSize, cellSize, cellSize);
            }
        }

    }

    public void setColourFromNoise(DrawingContext dc, double noise) {
        // Noise value is in range -1..1 - convert to 0..1
        // We can use the BasicUtils.mapper helper for this.
        // noise = (1.0 + noise) / 2.0;
        noise = mapper(noise, -1, 1, 0, 1);
        Color col = gradient.getColor(noise);
        dc.setDrawColor(col);
    }
}
