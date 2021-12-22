package com.physmo.minvio.utils;

public class VoronoiNoise {

    public static double noise(double x, double y, double z, int order) {
        double sum = 0;
        double total = 0;
        for (int i = 1; i < order; i++) {
            double dOrder = (double) i;
            sum += noise(x * dOrder, y * dOrder, z * dOrder) / dOrder;
            total += 1.0 / order;
        }
        return (sum / total);
    }

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

    public static double noiseb(double x, double y, double z) {

        double[] distances = getDistances(x, y, z);

        double mind = 100;
        double mind2 = 100;
        double mind3 = 100;

        for (double d : distances) {
            if (d < mind) {
                mind3 = mind2;
                mind2 = mind;
                mind = d;
            }

        }

        return mind2 - mind;
    }

    public static double rescale(double val) {
        if (val < 0) return 0;
        if (val > 1) return 1;
        return val;
    }

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

                        QuickRandom random = new QuickRandom((_x + xo) * 3333 + (_z + zo) * 4844 + (_y + yo) * 5525 * m);
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
