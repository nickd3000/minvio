package com.physmo.minvio.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MatrixDrawerTest {

    @Test
    void rejectsNonPositiveDimensions() {
        assertThrows(IllegalArgumentException.class, () -> new MatrixDrawer(0, 1));
        assertThrows(IllegalArgumentException.class, () -> new MatrixDrawer(1, 0));
    }

    @Test
    void exactCenterCellHasFiniteZeroAngleAndDistance() {
        MatrixDrawer drawer = new MatrixDrawer(4, 4);
        int centerIndex = 2 + (2 * 4);

        assertEquals(0.0, drawer.angles[centerIndex]);
        assertEquals(0.0, drawer.distances[centerIndex]);
    }
}
