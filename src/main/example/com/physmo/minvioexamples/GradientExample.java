package com.physmo.minvioexamples;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.BasicUtils;
import com.physmo.minvio.utils.Gradient;

import java.awt.Color;

public class GradientExample extends MinvioApp {

    Gradient gradient = new Gradient();

    public static void main(String... args) {
        MinvioApp app = new GradientExample();
        app.start(new BasicDisplayAwt(200, 200), "Gradient Example", 30);
    }

    @Override
    public void init(BasicDisplay bd) {
        gradient.addColor(0.0, new Color(14, 2, 2));
        gradient.addColor(0.2, new Color(18, 62, 205));
        gradient.addColor(0.4, new Color(209, 47, 225));
        gradient.addColor(0.5, new Color(245, 199, 56));
        gradient.addColor(0.55, new Color(104, 75, 141));
        gradient.addColor(1.0, new Color(26, 54, 117));
    }

    @Override
    public void draw(BasicDisplay bd, double delta) {

        for (int y = 0; y < bd.getHeight(); y++) {
            Color color = gradient.getColor((double) y / (double) bd.getHeight());
            bd.setDrawColor(color);
            bd.drawFilledRect(0, y, bd.getWidth(), 1);

            // Draw the sun for fun.
            if (y == bd.getHeight() / 2) {
                bd.drawFilledCircle(bd.getHeight() / 2, bd.getHeight() / 2, 50);
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
