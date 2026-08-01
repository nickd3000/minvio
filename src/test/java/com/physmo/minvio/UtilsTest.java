package com.physmo.minvio;

import com.physmo.minvio.types.Point;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UtilsTest {
    private static final double DELTA = 1e-10;

    @Test
    void lerpSupportsNumbersPointsAndClampedColors() {
        assertEquals(12, Utils.lerp(10, 20, 0.25));
        assertEquals(12.5, Utils.lerp(10.0, 20.0, 0.25), DELTA);
        assertEquals(new Point(2.5, 5.0), Utils.lerp(new Point(0, 0), new Point(10, 20), 0.25));
        assertEquals(new Color(127, 63, 31, 127),
                Utils.lerp(new Color(0, 0, 0, 0), new Color(255, 127, 63, 255), 0.5));
        assertEquals(Color.BLACK, Utils.lerp(Color.BLACK, Color.WHITE, -1.0));
        assertEquals(Color.WHITE, Utils.lerp(Color.BLACK, Color.WHITE, 2.0));
    }

    @Test
    void clampHandlesLowerMiddleAndUpperValues() {
        assertEquals(0.0, Utils.clamp(0.0, 10.0, -1.0));
        assertEquals(5.0, Utils.clamp(0.0, 10.0, 5.0));
        assertEquals(10.0, Utils.clamp(0.0, 10.0, 11.0));
    }

    @Test
    void invertDistanceIsBoundedAndRejectsInvalidMaximum() {
        assertEquals(1.0, Utils.invertDistance(-1.0, 10.0));
        assertEquals(0.5, Utils.invertDistance(5.0, 10.0));
        assertEquals(0.0, Utils.invertDistance(20.0, 10.0));
        assertThrows(IllegalArgumentException.class, () -> Utils.invertDistance(1.0, 0.0));
        assertThrows(IllegalArgumentException.class, () -> Utils.invertDistance(1.0, -1.0));
    }
}
