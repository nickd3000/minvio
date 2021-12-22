package com.physmo.minvioexamples;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.Gradient;
import com.physmo.minvio.utils.VoronoiNoise;

import java.awt.Color;

public class VoronoiNoiseExample extends MinvioApp {

    double time = 0;
    Gradient gradient = new Gradient(Color.BLACK, Color.WHITE);

    public static void main(String... args) {
        MinvioApp app = new VoronoiNoiseExample();
        app.start(new BasicDisplayAwt(400, 400), "Voronoi Noise Example", 30);
    }

    @Override
    public void draw(BasicDisplay bd, double delta) {
        int cellSize = 2;
        double scale = 0.025;
        time += delta * 0.5;

        for (int y = 0; y < bd.getHeight() / cellSize; y++) {
            for (int x = 0; x < bd.getWidth() / cellSize; x++) {

                double noise = VoronoiNoise.noise((x * scale), (y * scale), time);

                noise = BasicDisplay.clamp(0, 1, noise);

                bd.setDrawColor(gradient.getColor(noise));

                bd.drawFilledRect(x * cellSize, y * cellSize, cellSize, cellSize);
            }
        }

    }

}
