package com.physmo.reference.gallery;


import com.physmo.minvio.MinvioApp;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class MandalaGenerator extends MinvioApp {

    double time = 0;
    List<MandalaLayer> layers = new ArrayList<>();

    public static void main(String[] args) {
        MinvioApp app = new MandalaGenerator();
        app.start(800, 800, "Dynamic Mandala Generator", 60);
    }

    @Override
    public void init(com.physmo.minvio.BasicDisplay bd) {
        // Create multiple mandala layers with different properties
        layers.add(new MandalaLayer(12, 80, 0.5, new Color(255, 100, 150, 120)));
        layers.add(new MandalaLayer(8, 120, -0.3, new Color(100, 255, 150, 100)));
        layers.add(new MandalaLayer(16, 60, 0.7, new Color(150, 100, 255, 140)));
        layers.add(new MandalaLayer(6, 160, -0.2, new Color(255, 200, 100, 80)));
        layers.add(new MandalaLayer(24, 40, 1.2, new Color(100, 200, 255, 160)));
    }

    @Override
    public void draw(double delta) {
        time += delta;

        // Black background with subtle gradient
        cls(new Color(5, 5, 15));

        // Center point
        double centerX = getWidth() / 2.0;
        double centerY = getHeight() / 2.0;

        // Mouse influence
        double mouseX = getMouseX();
        double mouseY = getMouseY();
        double mouseDistanceFromCenter = Math.sqrt(
                Math.pow(mouseX - centerX, 2) + Math.pow(mouseY - centerY, 2)
        );
        double mouseInfluence = Math.max(0, 1.0 - mouseDistanceFromCenter / 300);

        // Draw each mandala layer
        for (MandalaLayer layer : layers) {
            drawMandalaLayer(layer, centerX, centerY, mouseInfluence);
        }

        // Draw connecting lines between layers
        setDrawColor(new Color(255, 255, 255, 30));
        for (int i = 0; i < 360; i += 15) {
            double angle = Math.toRadians(i + time * 20);
            double innerRadius = 20 + Math.sin(time * 2 + i * 0.1) * 10;
            double outerRadius = 200 + mouseInfluence * 50;

            double x1 = centerX + Math.cos(angle) * innerRadius;
            double y1 = centerY + Math.sin(angle) * innerRadius;
            double x2 = centerX + Math.cos(angle) * outerRadius;
            double y2 = centerY + Math.sin(angle) * outerRadius;

            drawLine(x1, y1, x2, y2, 0.5);
        }

        // Central pulsing core
        double coreSize = 15 + Math.sin(time * 3) * 5 + mouseInfluence * 10;
        setDrawColor(new Color(255, 255, 255, 200));
        drawFilledCircle(centerX, centerY, coreSize);
        setDrawColor(new Color(255, 200, 100));
        drawCircle(centerX, centerY, coreSize + 3);

        // Instructions
        setDrawColor(new Color(200, 200, 200, 150));
        setFont(14);
        drawText("Move mouse to influence the mandala", 20, 30);
        setFont(12);
        drawText("Each layer rotates at different speeds", 20, 50);
        drawText("Mouse distance from center: " + (int) mouseDistanceFromCenter, 20, 780);
    }

    private void drawMandalaLayer(MandalaLayer layer, double centerX, double centerY, double mouseInfluence) {
        setDrawColor(layer.color);

        double layerRotation = time * layer.rotationSpeed;
        double dynamicRadius = layer.radius + Math.sin(time + layer.hashCode()) * 20 + mouseInfluence * 30;

        // Draw petals/segments
        for (int i = 0; i < layer.segments; i++) {
            double baseAngle = (Math.PI * 2 / layer.segments) * i + layerRotation;

            // Create petal shape
            for (int j = 0; j < 8; j++) {
                double subAngle = baseAngle + (j - 4) * 0.1;
                double petalRadius = dynamicRadius * (0.7 + 0.3 * Math.cos(j * 0.5));

                double x = centerX + Math.cos(subAngle) * petalRadius;
                double y = centerY + Math.sin(subAngle) * petalRadius;

                // Draw petal segment
                double size = 3 + Math.sin(time * 2 + i * 0.3) * 1;
                drawFilledCircle(x, y, size);

                // Connect to center with thin lines
                if (j == 4) { // Middle of petal
                    Color lineColor = new Color(
                            layer.color.getRed(),
                            layer.color.getGreen(),
                            layer.color.getBlue(),
                            30
                    );
                    setDrawColor(lineColor);
                    drawLine(centerX, centerY, x, y, 0.5);
                    setDrawColor(layer.color);
                }
            }

            // Draw geometric patterns at petal tips
            double tipAngle = baseAngle;
            double tipX = centerX + Math.cos(tipAngle) * dynamicRadius;
            double tipY = centerY + Math.sin(tipAngle) * dynamicRadius;

            // Small rotating geometric shape at tip
            double miniRotation = time * (layer.rotationSpeed * 3) + i;
            for (int k = 0; k < 6; k++) {
                double miniAngle = (Math.PI * 2 / 6) * k + miniRotation;
                double miniRadius = 8 + mouseInfluence * 5;
                double mx = tipX + Math.cos(miniAngle) * miniRadius;
                double my = tipY + Math.sin(miniAngle) * miniRadius;

                drawFilledCircle(mx, my, 2);
            }
        }
    }

    static class MandalaLayer {
        int segments;
        double radius;
        double rotationSpeed;
        Color color;

        MandalaLayer(int segments, double radius, double rotationSpeed, Color color) {
            this.segments = segments;
            this.radius = radius;
            this.rotationSpeed = rotationSpeed;
            this.color = color;
        }
    }
}
