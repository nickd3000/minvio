package com.physmo.minvio.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
