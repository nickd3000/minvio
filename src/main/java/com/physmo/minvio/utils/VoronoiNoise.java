package com.physmo.minvio.utils;

/**
 * The VoronoiNoise class provides methods for generating Voronoi noise
 * in three-dimensional space. Voronoi noise is a procedural texture
 * generation technique often used in computer graphics for creating natural
 * patterns such as cellular textures, stone, or terrain.
 */
public class VoronoiNoise {

    /**
     * Combines several scaled nearest-feature noise samples.
     *
     * @param x     x-coordinate
     * @param y     y-coordinate
     * @param z     z-coordinate
     * @param order number of scale iterations plus one
     * @return combined noise value
     * @throws IllegalArgumentException if {@code order} is less than two
     */
    public static double noise(double x, double y, double z, int order) {
        if (order < 2) {
            throw new IllegalArgumentException("Order must be at least 2");
        }
        double sum = 0;
        double total = 0;
        for (int i = 1; i < order; i++) {
            sum += noise(x * (double) i, y * (double) i, z * (double) i) / (double) i;
            total += 1.0 / order;
        }
        return (sum / total);
    }

    /**
     * Returns the distance to the nearest generated feature point.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @param z z-coordinate
     * @return nearest clamped feature distance
     */
    public static double noise(double x, double y, double z) {

        double[] distances = getDistances(x, y, z);

        double mind = 100;

        for (double d : distances) {
            if (d < mind) {
                mind = d;
            }
        }

        return mind;
    }

    /**
     * Returns the difference between the two nearest feature distances.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @param z z-coordinate
     * @return second-nearest distance minus nearest distance
     */
    public static double noiseb(double x, double y, double z) {

        double[] distances = getDistances(x, y, z);

        double mind = 100;
        double mind2 = 100;
        for (double d : distances) {
            if (d < mind) {
                mind2 = mind;
                mind = d;
            } else if (d < mind2) {
                mind2 = d;
            }

        }

        return mind2 - mind;
    }

    /**
     * Clamps a value to the inclusive range zero through one.
     *
     * @param val input value
     * @return clamped value
     */
    public static double rescale(double val) {
        if (val < 0) return 0;
        if (val > 1) return 1;
        return val;
    }

    /**
     * Calculates clamped distances to deterministic feature points in the
     * surrounding 3-by-3-by-3 cells.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @param z z-coordinate
     * @return newly allocated array of 27 distances
     */
    public static double[] getDistances(double x, double y, double z) {
        int _x = (int) x;
        int _y = (int) y;
        int _z = (int) z;

        int pointsPerCell = 1;

        double[] distances = new double[3 * 9 * pointsPerCell];
        int index = 0;
        for (int zo = -1; zo < 2; zo++) {
            for (int yo = -1; yo < 2; yo++) {
                for (int xo = -1; xo < 2; xo++) {
                    for (int m = 1; m <= pointsPerCell; m++) {

                        QuickRandom random = new QuickRandom((_x + xo) * 3333L + (_z + zo) * 4844L + (long) (_y + yo) * 5525 * m);
                        double px = random.nextDouble();
                        double py = random.nextDouble();
                        double pz = random.nextDouble();

                        double dx = (px + _x + xo) - x;
                        double dy = (py + _y + yo) - y;
                        double dz = (pz + _z + zo) - z;

                        distances[index++] = rescale(Math.sqrt(dx * dx + dy * dy + dz * dz));
                    }
                }
            }
        }

        return distances;
    }

}
