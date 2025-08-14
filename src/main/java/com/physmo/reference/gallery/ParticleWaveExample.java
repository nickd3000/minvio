package com.physmo.reference.gallery;

import com.physmo.minvio.MinvioApp;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ParticleWaveExample extends MinvioApp {

    List<Particle> particles = new ArrayList<>();
    double time = 0;

    public static void main(String[] args) {
        MinvioApp app = new ParticleWaveExample();
        app.start(800, 600, "Interactive Particle Wave", 60);
    }

    @Override
    public void init(com.physmo.minvio.BasicDisplay bd) {
        // Create a grid of particles
        for (int x = 0; x < 40; x++) {
            for (int y = 0; y < 30; y++) {
                particles.add(new Particle(x * 20 + 40, y * 20 + 40));
            }
        }
    }

    @Override
    public void draw(double delta) {
        time += delta;

        // Dark gradient background
        cls(new Color(15, 15, 35));

        // Update and draw particles
        double mouseInfluence = 100.0;

        for (Particle p : particles) {
            // Wave motion
            double waveX = Math.sin(p.baseX * 0.01 + time * 2) * 15;
            double waveY = Math.cos(p.baseY * 0.008 + time * 1.5) * 10;

            // Mouse interaction
            double dx = getMouseX() - p.baseX;
            double dy = getMouseY() - p.baseY;
            double distance = Math.sqrt(dx * dx + dy * dy);

            double mouseForceX = 0;
            double mouseForceY = 0;
            if (distance < mouseInfluence && distance > 0) {
                double force = (mouseInfluence - distance) / mouseInfluence;
                mouseForceX = (dx / distance) * force * 30;
                mouseForceY = (dy / distance) * force * 30;
            }

            // Final position
            p.x = p.baseX + waveX + mouseForceX;
            p.y = p.baseY + waveY + mouseForceY;

            // Color based on distance from mouse and wave
            float intensity = (float) (0.3 + 0.7 * Math.sin(time + p.baseX * 0.01));
            float mouseGlow = distance < mouseInfluence ?
                    (float) (1.0 - distance / mouseInfluence) : 0;

            Color particleColor = new Color(
                    Math.min(1.0f, 0.2f + mouseGlow + intensity * 0.3f),
                    Math.min(1.0f, 0.4f + mouseGlow * 0.8f + intensity * 0.5f),
                    Math.min(1.0f, 0.8f + mouseGlow * 0.5f + intensity * 0.7f),
                    0.8f
            );

            setDrawColor(particleColor);
            drawFilledCircle(p.x, p.y, 3 + mouseGlow * 4);
        }

        // Connect nearby particles with lines
        setDrawColor(new Color(100, 150, 255, 50));
        for (int i = 0; i < particles.size(); i++) {
            Particle p1 = particles.get(i);
            for (int j = i + 1; j < particles.size(); j++) {
                Particle p2 = particles.get(j);
                double dist = Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
                if (dist < 50) {
                    float alpha = (float) (1.0 - dist / 50.0) * 0.3f;
                    setDrawColor(new Color(100, 150, 255, (int) (alpha * 255)));
                    drawLine(p1.x, p1.y, p2.x, p2.y, 1.0);
                }
            }
        }

        // Instructions
        setDrawColor(new Color(200, 200, 200, 180));
        setFont(16);
        drawText("Move mouse to interact with particles", 20, 30);
        setFont(12);
        drawText("FPS: " + String.format("%.1f", getFps()), 20, 580);
    }

    static class Particle {
        double baseX, baseY; // Original position
        double x, y;         // Current position

        Particle(double x, double y) {
            this.baseX = this.x = x;
            this.baseY = this.y = y;
        }
    }
}
