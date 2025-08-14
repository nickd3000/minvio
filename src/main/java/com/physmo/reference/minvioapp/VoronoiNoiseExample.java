package com.physmo.reference.minvioapp;

import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.Utils;
import com.physmo.minvio.utils.VoronoiNoise;
import com.physmo.reference.gui.IQPalette;

import java.awt.Color;

public class VoronoiNoiseExample extends MinvioApp {

    double time = 0;

    static IQPalette iqPalette = new IQPalette();
    static double[] controls = {0.49, 0.09, 0.20, 0.80, 0.08, 0.78, 0.39, 0.89, 0.00, 0.80, 0.90, 0.57};

    public static void main(String... args) {
        iqPalette.setControls(controls);
        MinvioApp app = new VoronoiNoiseExample();
        app.start(400, 400, "Voronoi Noise Example", 30);
    }

    @Override
    public void draw(double delta) {
        int cellSize = 2;
        double scale = 0.025;
        time += delta * 0.5;

        for (int y = 0; y < getHeight() / cellSize; y++) {
            for (int x = 0; x < getWidth() / cellSize; x++) {

                double noise = VoronoiNoise.noise((x * scale), (y * scale), time);

                noise = Utils.clamp(0, 1, noise);

                //setDrawColor(gradient.getColor(noise));
                setDrawColor(new Color(iqPalette.getRgb(noise)));

                drawFilledRect(x * cellSize, y * cellSize, cellSize, cellSize);
            }
        }

    }

}
