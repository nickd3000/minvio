package com.physmo.reference.experiments.gravity;


import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.MinvioApp;

import java.awt.Color;

public class GravityParticles extends MinvioApp {

    int numParticles = 4096;

    int cellSize = 128;
    int tileScope = 1;
    double dampening = 1;//0.9999;
    int cellDistanceCull = 8;
    GravityGrid grid;

    // Instead of interleaved data, separate arrays for better cache locality
    double[] px = new double[numParticles];
    double[] py = new double[numParticles];
    double[] pdx = new double[numParticles];
    double[] pdy = new double[numParticles];
    boolean[] pa = new boolean[numParticles];

    public static void main(String... args) {
        MinvioApp app = new GravityParticles();
        app.start(800, 800, "GravityParticles", 30);
    }

    @Override
    public void init(BasicDisplay bd) {
        super.init(bd);
        grid = new GravityGrid(getWidth(), getHeight(), cellSize, numParticles);
        for (int i = 0; i < numParticles; i++) {
            double x = Math.random() * getWidth();
            double y = Math.random() * getHeight();
            double dx = (Math.random() - 0.5) * 15;
            double dy = (Math.random() - 0.5) * 15;
            createParticle(i, x, y, dx, dy);
        }
    }

    public void createParticle(int index, double x, double y, double dx, double dy) {

        px[index] = x;
        py[index] = y;
        pdx[index] = dx;
        pdy[index] = dy;
        pa[index] = true;
    }


    @Override
    public void draw(double delta) {
        cls(Color.black);
        setDrawColor(new Color(200, 200, 255));
        for (int i = 0; i < numParticles; i++) {
            drawPoint((int) px[i], (int) py[i]);
        }
    }

    @Override
    public void update(BasicDisplay bd, double delta) {

        refreshBucketGrid();

        // move particles
        for (int i = 0; i < numParticles; i++) {
            doPhysics(i, delta);
        }

        for (int gy = 0; gy < grid.height; gy++) {
            for (int gx = 0; gx < grid.width; gx++) {
                // other cells around this one
                for (int oy = 0; oy < grid.height; oy++) {
                    for (int ox = 0; ox < grid.width; ox++) {

                        processGridSquare(gx, gy, ox, oy, Math.abs(gx - ox) + Math.abs(gy - oy));
                    }
                }

            }
        }

    }

    public void processGridSquare(int gx, int gy, int ox, int oy, int cellDist) {
        int thisCellCount = grid.getCellCount(gx, gy);
        int otherCellCount = grid.getCellCount(ox, oy);

        // Skip if either cell is empty
        if (thisCellCount == 0 || otherCellCount == 0) return;

        int[] thisCell = grid.getCell(gx, gy);
        int[] otherCell = grid.getCell(ox, oy);

        if (cellDist > cellDistanceCull) return;

        boolean simplify = false;
        if (otherCellCount > 200 && cellDist > 1) simplify = true;

        if (cellDist < tileScope && !simplify) {
            for (int i = 0; i < thisCellCount; i++) {
                int particleI = thisCell[i];
                for (int j = 0; j < otherCellCount; j++) {
                    int particleO = otherCell[j];
                    if (particleI == particleO) continue;
                    attractParticles(particleI, px[particleO], py[particleO], 1, 1.0);
                }
            }

        } else {
            double p2x = ox * grid.cellSize + grid.cellSize / 2.0;
            double p2y = oy * grid.cellSize + grid.cellSize / 2.0;

            for (int i = 0; i < thisCellCount; i++) {
                attractParticles(thisCell[i], p2x, p2y, otherCellCount, 1.0);
            }

        }


    }

    public void doPhysics(int i, double delta) {

        // Cache values to reduce array access
        double x = px[i];
        double y = py[i];
        double dx = pdx[i];
        double dy = pdy[i];

        // Update position based on velocity
        x += dx * delta;
        y += dy * delta;

        if (x < 0) x = 0;
        if (x > getWidth()) x = getWidth();
        if (y < 0) y = 0;
        if (y > getHeight()) y = getHeight();


        // Write back cached values
        px[i] = x;
        py[i] = y;
        pdx[i] = dx;
        pdy[i] = dy;
    }


    public void attractParticles(int p1, double p2x, double p2y, double p2mass, double delta) {
        double G = 0.21; // gravitational constant

        double dx = p2x - px[p1];
        double dy = p2y - py[p1];
        double distSquared = dx * dx + dy * dy;


        if (distSquared < 4) {     // Larger collision radius
            distSquared = 4;
            pdx[p1] *= 0.995;       // More dampening on collision
            pdy[p1] *= 0.995;
        }


        double d = Math.sqrt(distSquared);
        double force = (G / d) * p2mass;

        dx /= d;
        dy /= d;

        pdx[p1] += dx * force * delta;
        pdy[p1] += dy * force * delta;
    }

    public void refreshBucketGrid() {
        grid.clear();
        for (int i = 0; i < numParticles; i++) {

            grid.addParticle((int) px[i], (int) py[i], i);

        }
    }
}
