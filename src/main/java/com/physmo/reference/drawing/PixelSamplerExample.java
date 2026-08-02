package com.physmo.reference.drawing;

import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.Gradient;

import java.awt.Color;

/**
 * Demonstrates reading pixels back from the drawing buffer.
 */
class PixelSamplerExample extends MinvioApp {

    private final Gradient gradient = new Gradient(new Color(30, 80, 180), new Color(255, 210, 80));

    public static void main(String... args) {
        MinvioApp app = new PixelSamplerExample();
        app.start(500, 320, "Pixel Sampler Example", 60);
    }

    @Override
    public void draw(double delta) {
        drawGradientField();

        int sampleX = Math.max(0, Math.min(getWidth() - 1, getMouseX()));
        int sampleY = Math.max(0, Math.min(getHeight() - 1, getMouseY()));
        Color sampledColor = getColorAtPoint(sampleX, sampleY);

        setDrawColor(Color.WHITE);
        drawCircle(sampleX, sampleY, 12);

        setDrawColor(Color.BLACK);
        drawFilledRect(15, 15, 180, 74);
        setDrawColor(sampledColor);
        drawFilledRect(25, 25, 54, 54);

        setDrawColor(Color.WHITE);
        drawText("Sampled RGB", 90, 42);
        drawText(sampledColor.getRed() + ", " + sampledColor.getGreen() + ", " + sampledColor.getBlue(), 90, 62);
    }

    private void drawGradientField() {
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                double horizontal = (double) x / Math.max(1, getWidth() - 1);
                double wave = (Math.sin((x + y) * 0.035) + 1.0) * 0.5;
                setDrawColor(gradient.getColor((horizontal + wave) * 0.5));
                drawPoint(x, y);
            }
        }
    }
}
