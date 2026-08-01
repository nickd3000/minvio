package com.physmo.minvio.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NoiseTest {

    @Test
    void perlinNoiseIsDeterministicFiniteAndWithinDocumentedRange() {
        double first = PerlinNoise.noise(1.25, -2.5, 3.75);
        double repeated = PerlinNoise.noise(1.25, -2.5, 3.75);

        assertEquals(first, repeated);
        assertTrue(Double.isFinite(first));
        assertTrue(first >= -1.0 && first <= 1.0);
        assertEquals(0.0, PerlinNoise.noise(0, 0, 0));
        assertEquals(PerlinNoise.noise(1.25, 2.5, 3.75),
                PerlinNoise.noise(257.25, 258.5, 259.75));
    }

    @Test
    void voronoiDistancesAndNoiseAreDeterministicAndBounded() {
        double[] distances = VoronoiNoise.getDistances(1.25, -2.5, 3.75);

        assertEquals(27, distances.length);
        for (double distance : distances) {
            assertTrue(Double.isFinite(distance));
            assertTrue(distance >= 0.0 && distance <= 1.0);
        }

        double noise = VoronoiNoise.noise(1.25, -2.5, 3.75);
        assertEquals(noise, VoronoiNoise.noise(1.25, -2.5, 3.75));
        assertTrue(noise >= 0.0 && noise <= 1.0);

        double edge = VoronoiNoise.noiseb(1.25, -2.5, 3.75);
        assertTrue(edge >= 0.0 && edge <= 1.0);
    }

    @Test
    void octaveNoiseRequiresAtLeastTwoOrders() {
        assertThrows(IllegalArgumentException.class, () -> VoronoiNoise.noise(0, 0, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> VoronoiNoise.noise(0, 0, 0, 1));
        assertTrue(Double.isFinite(VoronoiNoise.noise(0.1, 0.2, 0.3, 4)));
    }

    @Test
    void voronoiRescaleClampsToUnitInterval() {
        assertEquals(0.0, VoronoiNoise.rescale(-1));
        assertEquals(0.5, VoronoiNoise.rescale(0.5));
        assertEquals(1.0, VoronoiNoise.rescale(2));
    }

    @Test
    void negativeVoronoiCoordinatesUseFloorCellLookup() {
        double x = -0.25;
        double y = -1.5;
        double z = -2.75;
        double[] floorExpected = expectedVoronoiDistances(x, y, z, true);
        double[] truncationExpected = expectedVoronoiDistances(x, y, z, false);

        assertArrayEquals(floorExpected, VoronoiNoise.getDistances(x, y, z), 1e-12);
        assertFalse(Arrays.equals(floorExpected, truncationExpected));
    }

    private static double[] expectedVoronoiDistances(double x, double y, double z, boolean useFloor) {
        int cellX = useFloor ? (int) Math.floor(x) : (int) x;
        int cellY = useFloor ? (int) Math.floor(y) : (int) y;
        int cellZ = useFloor ? (int) Math.floor(z) : (int) z;
        double[] distances = new double[27];
        int index = 0;
        for (int zo = -1; zo < 2; zo++) {
            for (int yo = -1; yo < 2; yo++) {
                for (int xo = -1; xo < 2; xo++) {
                    QuickRandom random = new QuickRandom(
                            (cellX + xo) * 3333L + (cellZ + zo) * 4844L + (long) (cellY + yo) * 5525);
                    double dx = (random.nextDouble() + cellX + xo) - x;
                    double dy = (random.nextDouble() + cellY + yo) - y;
                    double dz = (random.nextDouble() + cellZ + zo) - z;
                    distances[index++] = VoronoiNoise.rescale(Math.sqrt(dx * dx + dy * dy + dz * dz));
                }
            }
        }
        return distances;
    }
}
