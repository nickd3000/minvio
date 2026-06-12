package com.physmo.minvio.utils;

import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GradientTest {

    @Test
    void defaultGradientIsImmediatelyUsable() {
        Gradient gradient = new Gradient();

        assertEquals(Color.BLACK, gradient.getColor(0));
        assertEquals(Color.BLACK, gradient.getColor(0.5));
        assertEquals(Color.BLACK, gradient.getColor(1));
    }

    @Test
    void interpolatesEndpointsAndClampsLookupPosition() {
        Gradient gradient = new Gradient(Color.BLACK, Color.WHITE);

        assertEquals(Color.BLACK, gradient.getColor(-1));
        assertEquals(new Color(127, 127, 127), gradient.getColor(0.5));
        assertEquals(Color.WHITE, gradient.getColor(1));
        assertEquals(Color.WHITE, gradient.getColor(2));
    }

    @Test
    void insertedColorStopsAreUsedAndCanBeReplaced() {
        Gradient gradient = new Gradient(Color.BLACK, Color.WHITE);

        gradient.addColor(0.5, Color.RED);
        assertEquals(Color.RED, gradient.getColor(0.5));

        gradient.addColor(0.5, Color.BLUE);
        assertEquals(Color.BLUE, gradient.getColor(0.5));
    }

    @Test
    void rejectsInvalidColorStops() {
        Gradient gradient = new Gradient();

        assertThrows(IllegalArgumentException.class, () -> gradient.addColor(-0.1, Color.RED));
        assertThrows(IllegalArgumentException.class, () -> gradient.addColor(1.1, Color.RED));
        assertThrows(NullPointerException.class, () -> gradient.addColor(0.5, null));
    }
}
