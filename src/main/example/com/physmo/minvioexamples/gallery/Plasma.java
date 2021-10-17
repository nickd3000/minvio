package com.physmo.minvioexamples.gallery;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.Gradient;

import java.awt.Color;

import static com.physmo.minvio.utils.BasicUtils.mapper;
import static com.physmo.minvio.utils.PerlinNoise.noise;

public class Plasma extends MinvioApp {

    double scrollPosX = 0;
    double scrollPosY = 0;
    Gradient gradient = new Gradient();

    public static void main(String... args) {
        MinvioApp app = new Plasma();
        app.start(new BasicDisplayAwt(400, 400), "Plasma", 60);
    }

    @Override
    public void init(BasicDisplay bd) {
        gradient.addColor(0.3, new Color(237, 201, 113, 255));
        gradient.addColor(0.5, new Color(48, 25, 23));
        gradient.addColor(0.7, new Color(143, 39, 241));
    }

    @Override
    public void draw(BasicDisplay bd, double delta) {
        int cellSize = 5;
        double scale = 0.05;
        scrollPosX += delta * 0.5;
        scrollPosY += delta * 0.2;

        for (int y = 0; y < bd.getHeight() / cellSize; y++) {
            for (int x = 0; x < bd.getWidth() / cellSize; x++) {

                double noise = noise(scrollPosX + (x * scale), scrollPosY + (y * scale), scrollPosX);

                setColourFromNoise(bd, noise);

                bd.drawFilledRect(x * cellSize, y * cellSize, cellSize, cellSize);
            }
        }

    }

    public void setColourFromNoise(BasicDisplay bd, double noise) {
        // Noise value is in range -1..1 - convert to 0..1
        // We can use the BasicUtils.mapper helper for this.
        // noise = (1.0 + noise) / 2.0;
        noise = mapper(noise, -1, 1, 0, 1);
        Color col = gradient.getColor(noise);
        bd.setDrawColor(col);
    }
}
