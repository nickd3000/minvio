package com.physmo.reference.minvioapp;

import com.physmo.minvio.DrawingContext;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.BasicUtils;
import com.physmo.minvio.utils.PerlinNoise;

import java.awt.Color;

public class PerlinNoiseExample extends MinvioApp {

    double scrollPosX = 0;
    double scrollPosY = 0;

    public static void main(String... args) {
        MinvioApp app = new PerlinNoiseExample();
        app.start(200, 200, "Perlin Noise Example", 30);
    }

    @Override
    public void draw(double delta) {
        int cellSize = 5;
        double scale = 0.1;
        scrollPosX += delta * 0.5;
        scrollPosY += delta * 0.2;

        for (int y = 0; y < getHeight() / cellSize; y++) {
            for (int x = 0; x < getWidth() / cellSize; x++) {

                double noise = PerlinNoise.noise(scrollPosX + (x * scale), scrollPosY + (y * scale), scrollPosY);

                setColourFromNoise(this, noise);

                drawFilledRect(x * cellSize, y * cellSize, cellSize, cellSize);
            }
        }

    }

    public void setColourFromNoise(DrawingContext dc, double noise) {
        // Noise value is in range -1..1 - convert to 0..1
        // We can use the BasicUtils.mapper helper for this.
        // noise = (1.0 + noise) / 2.0;
        noise = BasicUtils.mapper(noise, -1, 1, 0, 1);

        dc.setDrawColor(new Color((float) noise, (float) noise, (float) noise));
    }
}
