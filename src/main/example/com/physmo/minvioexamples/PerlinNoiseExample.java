package com.physmo.minvioexamples;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.BasicUtils;
import com.physmo.minvio.utils.PerlinNoise;

import java.awt.Color;

public class PerlinNoiseExample extends MinvioApp {

    double scrollPosX = 0;
    double scrollPosY = 0;

    public static void main(String... args) {
        MinvioApp app = new PerlinNoiseExample();
        app.start(new BasicDisplayAwt(200, 200), "Perlin Noise Example", 30);
    }

    @Override
    public void draw(BasicDisplay bd, double delta) {
        int cellSize = 5;
        double scale = 0.1;
        scrollPosX += delta * 0.5;
        scrollPosY += delta * 0.2;

        for (int y = 0; y < bd.getHeight() / cellSize; y++) {
            for (int x = 0; x < bd.getWidth() / cellSize; x++) {

                double noise = PerlinNoise.noise(scrollPosX + (x * scale), scrollPosY + (y * scale), scrollPosY);

                setColourFromNoise(bd, noise);

                bd.drawFilledRect(x * cellSize, y * cellSize, cellSize, cellSize);
            }
        }

    }

    public void setColourFromNoise(BasicDisplay bd, double noise) {
        // Noise value is in range -1..1 - convert to 0..1
        // We can use the BasicUtils.mapper helper for this.
        // noise = (1.0 + noise) / 2.0;
        noise = BasicUtils.mapper(noise, -1, 1, 0, 1);

        bd.setDrawColor(new Color((float) noise, (float) noise, (float) noise));
    }
}
