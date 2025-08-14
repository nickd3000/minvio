package com.physmo.reference.gallery;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.Gradient;
import com.physmo.minvio.utils.MatrixDrawer;
import com.physmo.minvio.utils.PerlinNoise;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * NoseStyles is a graphical application that extends the MinvioApp framework.
 * It demonstrates the usage of a MatrixDrawer to render grid-based visual patterns
 * using dynamic gradients and noise functions.
 * <p>
 * This class creates a graphical display window and utilizes Perlin noise and other
 * mathematical visual functions to generate animated effects. Multiple gradients are
 * applied to different sections of the grid to provide varied visual aesthetics.
 * <p>
 * Features:
 * - Utilizes the MatrixDrawer for per-pixel rendering with color computation through lambdas.
 * - Demonstrates the application of Perlin noise for natural-looking animations.
 * - Supports custom gradients for rendering varied visual effects.
 * - Includes animated, grid-based visualizations with dynamic updates controlled by delta time.
 * <p>
 * Methods:
 * - main: Entry point for starting the application.
 * - init: Initializes the application state, including the MatrixDrawer and gradients.
 * - draw: Defines the per-frame rendering behavior, applying different visual functions
 * to the MatrixDrawer on different grid sections.
 */
class NoseStyles extends MinvioApp {

    double time = 0;
    MatrixDrawer matrixDrawer;
    List<Gradient> gradients;

    public static void main(String... args) {
        MinvioApp app = new NoseStyles();
        app.start(400, 400, "Matrix Drawer Example", 30);
    }

    @Override
    public void init(BasicDisplay bd) {

        // Create the matrix drawer object that will allow us to supply a lambda that is
        // called per-pixel to supply color information to a renderer.
        matrixDrawer = new MatrixDrawer(100, 100);

        // Create 4 gradients we will use to draw the different matrix objects
        gradients = new ArrayList<>();
        gradients.add(new Gradient(Color.YELLOW, Color.MAGENTA));
        gradients.add(new Gradient(Color.BLACK, Color.ORANGE));
        gradients.add(new Gradient(Color.green, Color.GRAY));
        gradients.add(new Gradient(Color.red, Color.WHITE));
    }


    @Override
    public void draw(double delta) {
        time += delta;

        matrixDrawer.draw(this, 0, 0, 2, time, (x, y, a, d, t) -> {
            double xx = PerlinNoise.noise(x * 4, y * 4, t + 2.5);
            double yy = PerlinNoise.noise(x * 4, y * 4, t + 2.5);
            return PerlinNoise.noise((x + xx) * 2, (y + yy) * 2, t);

            //return ((1.0 + Math.sin(t * 2 + a * 6)) + (1.0 + Math.cos(t * 5 + d * 6 + a * 3))) * 0.25;
        }, gradients.get(0));

        matrixDrawer.draw(this, 200, 0, 2, time, (x, y, a, d, t) ->
                (1.0 + Math.sin(a * 2 + d * 2 + (t * -3))) / 2, gradients.get(1));

        matrixDrawer.draw(this, 0, 200, 2, time, (x, y, a, d, t) -> {
            return (1.0 + Math.sin(x * 4 + d * 4 + t * 2 + y)) / 2;
        }, null); // We can supply null if we don't want to use a gradient.

        matrixDrawer.draw(this, 200, 200, 2, time, (x, y, a, d, t) -> {
            return ((Math.sin(t) + y + d) % 0.5) > 0.2 ? 1 : 0;
        }, gradients.get(3));
    }
}
