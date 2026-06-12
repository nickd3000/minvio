package com.physmo.minvio.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RollingAverageTest {
    private static final double DELTA = 1e-10;

    @Test
    void averagesPartialThenRollingWindow() {
        RollingAverage average = new RollingAverage(3);

        average.add(3);
        assertState(average, 3, 3);
        average.add(6);
        assertState(average, 9, 4.5);
        average.add(9);
        assertState(average, 18, 6);
        average.add(12);
        assertState(average, 27, 9);
        average.add(-3);
        assertState(average, 18, 6);
    }

    @Test
    void rejectsNonPositiveWindowSize() {
        assertThrows(IllegalArgumentException.class, () -> new RollingAverage(0));
        assertThrows(IllegalArgumentException.class, () -> new RollingAverage(-1));
    }

    private static void assertState(RollingAverage average, double sum, double value) {
        assertEquals(sum, average.getSum(), DELTA);
        assertEquals(value, average.getAverage(), DELTA);
    }
}
