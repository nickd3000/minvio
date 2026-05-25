package com.physmo.reference.minvioapp;

import com.physmo.minvio.MinvioApp;
import com.physmo.minvio.utils.LookupTable;

import java.awt.Color;

/**
 * The LookupTableExample class demonstrates the use of a lookup table to optimize
 * function evaluations and the rendering of those evaluations on a graphical canvas.
 * <p>
 * This class extends MinvioApp and leverages its functionality to create a graphical
 * application. It uses a lookup table to precompute and store values of a sine-based
 * function for faster access during rendering. The example also compares the use of
 * the lookup table with dynamic function computation.
 * <p>
 * The application initializes a 400x400 canvas and updates the rendering at 30 FPS.
 * Within the overridden draw() method, the lookup table is instantiated with parameters
 * that define its range, resolution, and the function it approximates. It then renders
 * both the precomputed values from the lookup table and the dynamically calculated
 * function values for comparison.
 * <p>
 * Key concepts demonstrated include:
 * - Utilizing lookup tables for optimizing repetitive computations.
 * - Instantiating and configuring a graphical application with MinvioApp.
 * - Drawing computed points on a graphical canvas with different colors for comparison.
 */
public class LookupTableExample extends MinvioApp {

    int x = 0;

    public static void main(String... args) {
        MinvioApp app = new LookupTableExample();
        app.start(400, 400, "Lookup Table Example", 30);
    }

    @Override
    public void draw(double delta) {
        LookupTable lookupTable = new LookupTable(0.0, 400.0, 100,
                operand -> (Math.sin(operand / 100) + 1.0) * 50.0);

        for (int x = 0; x < 400; x++) {
            setDrawColor(Color.black);
            drawPoint(x, (int) (lookupTable.getValue(x)));
            setDrawColor(Color.blue);
            drawPoint(x, (int) ((Math.sin((double) x / 100) + 1.0) * 50.0));
        }
    }
}