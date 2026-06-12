package com.physmo.minvio.utils;

import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class PaletteTest {

    @Test
    void colorsAreStablePerIndexAndSaturation() {
        Color first = Palette.getDistinctColor(10, 0.75);
        Color repeated = Palette.getDistinctColor(10, 0.75);

        assertSame(first, repeated);
        assertNotEquals(first, Palette.getDistinctColor(11, 0.75));
    }

    @Test
    void saturationParticipatesInCacheKeyAndIsClamped() {
        Color gray = Palette.getDistinctColor(20, 0.0);
        Color saturated = Palette.getDistinctColor(20, 1.0);

        assertEquals(Color.WHITE, gray);
        assertNotEquals(gray, saturated);
        assertEquals(gray, Palette.getDistinctColor(20, -1.0));
        assertEquals(saturated, Palette.getDistinctColor(20, 2.0));
    }
}
