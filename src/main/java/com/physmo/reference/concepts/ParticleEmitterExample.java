package com.physmo.reference.concepts;

import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.Palette;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Demonstrates a small particle emitter with velocity, lifespan, and fading.
 */
class ParticleEmitterExample extends MinvioApp {

    private final List<Particle> particles = new ArrayList<>();

    public static void main(String... args) {
        MinvioApp app = new ParticleEmitterExample();
        app.start(500, 400, "Particle Emitter Example", 60);
    }

    @Override
    public void draw(double delta) {
        emitParticles();
        updateParticles(delta);

        cls(new Color(15, 21, 30));

        for (Particle particle : particles) {
            double lifeRatio = particle.life / particle.maxLife;
            setDrawColor(new Color(255, 190, 80, (int) (255 * lifeRatio)));
            drawFilledCircle(particle.x, particle.y, particle.size * lifeRatio);
        }

        setDrawColor(Palette.WHITE);
        drawText("Move the mouse to move the emitter", 20, 30);
        drawText("Particles: " + particles.size(), 20, 50);
    }

    private void emitParticles() {
        for (int i = 0; i < 4; i++) {
            double angle = Math.random() * Math.PI * 2;
            double speed = 50 + Math.random() * 120;
            particles.add(new Particle(
                    getMouseX(),
                    getMouseY(),
                    Math.cos(angle) * speed,
                    Math.sin(angle) * speed,
                    1.0 + Math.random() * 0.8,
                    4 + Math.random() * 8));
        }
    }

    private void updateParticles(double delta) {
        Iterator<Particle> iterator = particles.iterator();
        while (iterator.hasNext()) {
            Particle particle = iterator.next();
            particle.life -= delta;
            particle.x += particle.dx * delta;
            particle.y += particle.dy * delta;
            particle.dy += 90 * delta;

            if (particle.life <= 0) {
                iterator.remove();
            }
        }
    }

    static class Particle {
        double x;
        double y;
        double dx;
        double dy;
        double life;
        double maxLife;
        double size;

        Particle(double x, double y, double dx, double dy, double life, double size) {
            this.x = x;
            this.y = y;
            this.dx = dx;
            this.dy = dy;
            this.life = life;
            this.maxLife = life;
            this.size = size;
        }
    }
}
