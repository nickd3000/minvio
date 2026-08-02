package com.physmo.minvio.utils;

import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void publicPaletteColorsAreConstants() {
        Field[] colorFields = Arrays.stream(Palette.class.getFields())
                .filter(field -> field.getType().equals(Color.class))
                .toArray(Field[]::new);

        assertFalse(colorFields.length == 0);
        for (Field field : colorFields) {
            int modifiers = field.getModifiers();
            assertTrue(Modifier.isStatic(modifiers), field.getName());
            assertTrue(Modifier.isFinal(modifiers), field.getName());
        }
    }
}
