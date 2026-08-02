package com.physmo.minvio.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LookupTableTest {
    private static final double DELTA = 1e-10;

    @Test
    void sampledLookupClampsToStoredRange() {
        LookupTable table = new LookupTable(0, 10, 5, value -> value);

        assertEquals(0, table.getValue(-10), DELTA);
        assertEquals(0, table.getValue(0), DELTA);
        assertEquals(5, table.getValue(5), DELTA);
        assertEquals(10, table.getValue(10), DELTA);
        assertEquals(10, table.getValue(100), DELTA);
    }

    @Test
    void interpolatedLookupBlendsAdjacentSamples() {
        LookupTable table = new LookupTable(0, 10, 5, value -> value);

        assertEquals(0, table.getInterpolatedValue(-10), DELTA);
        assertEquals(1, table.getInterpolatedValue(1), DELTA);
        assertEquals(5, table.getInterpolatedValue(5), DELTA);
        assertEquals(10, table.getInterpolatedValue(10), DELTA);
        assertEquals(10, table.getInterpolatedValue(100), DELTA);
    }

    @Test
    void approximatesNonlinearFunctionsWithinTableResolution() {
        LookupTable table = new LookupTable(0, Math.PI * 2, 10_000, Math::sin);

        assertEquals(Math.sin(1.234), table.getInterpolatedValue(1.234), 0.000001);
    }

    @Test
    void rejectsInvalidConfiguration() {
        assertThrows(IllegalArgumentException.class, () -> new LookupTable(1, 1, 10, value -> value));
        assertThrows(IllegalArgumentException.class, () -> new LookupTable(2, 1, 10, value -> value));
        assertThrows(IllegalArgumentException.class, () -> new LookupTable(0, 1, 0, value -> value));
        assertThrows(IllegalArgumentException.class, () -> new LookupTable(0, 1, 1, value -> value));
        assertThrows(NullPointerException.class, () -> new LookupTable(0, 1, 10, null));
    }
}
