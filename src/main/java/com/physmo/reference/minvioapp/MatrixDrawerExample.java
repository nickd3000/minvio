package com.physmo.reference.minvioapp;

import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.Gradient;
import com.physmo.minvio.utils.MatrixDrawer;
import com.physmo.minvio.utils.PerlinNoise;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/*
    Use the MatrixDrawer helper class to quickly
    trial equations on the x/y axis.
 */
class MatrixDrawerExample extends MinvioApp {

    double time = 0;
    MatrixDrawer matrixDrawer;
    List<Gradient> gradients;

    public static void main(String... args) {
        MinvioApp app = new MatrixDrawerExample();
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

        matrixDrawer.draw(getDrawingContext(), 0, 0, 2, time,
                (x, y, a, d, t) -> ((1.0 + Math.sin(t * 2 + a * 6)) + (1.0 + Math.cos(t * 5 + d * 6 + a * 3))) * 0.25, gradients.get(0));

        matrixDrawer.draw(getDrawingContext(), 200, 0, 2, time, (x, y, a, d, t) ->
                        (1.0 + Math.sin(a * 2 + d * 2 + (t * -3))) / 2
                , gradients.get(1));

        matrixDrawer.draw(getDrawingContext(), 0, 200, 2, time, (x, y, a, d, t) -> {
            double xx = PerlinNoise.noise(x * 4, y * 4, t + 2.5);
            double yy = PerlinNoise.noise(x * 4, y * 4, t + 2.5);
            return PerlinNoise.noise((x + xx) * 2, (y + yy) * 2, t);
        }, null); // We can supply null if we don't want to use a gradient.

        matrixDrawer.draw(getDrawingContext(), 200, 200, 2, time, (x, y, a, d, t) -> {
            return ((Math.sin(t) + y + d) % 0.5) > 0.2 ? 1 : 0;
        }, gradients.get(3));
    }
}
