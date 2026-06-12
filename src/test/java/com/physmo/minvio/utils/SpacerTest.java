package com.physmo.minvio.utils;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SpacerTest {

    @Test
    void interpolatesSupportedNumericTypesIncludingEndpoints() {
        assertEquals(List.of(0, 3, 5, 8, 10), values(new Spacer<>(0, 10, 4)));
        assertEquals(List.of(0.0, 2.5, 5.0, 7.5, 10.0), values(new Spacer<>(0.0, 10.0, 4)));
        assertEquals(List.of(0.0f, 2.5f, 5.0f, 7.5f, 10.0f), values(new Spacer<>(0.0f, 10.0f, 4)));
        assertEquals(List.of(0L, 3L, 5L, 8L, 10L), values(new Spacer<>(0L, 10L, 4)));
    }

    @Test
    void exposesCountsBoundsAndResettableIteration() {
        Spacer<Integer> spacer = new Spacer<>(0, 4, 2);

        assertEquals(3, spacer.getPointCount());
        assertEquals(2, spacer.getSegments());
        assertEquals(0, spacer.next());
        assertEquals(2, spacer.next());
        spacer.reset();
        assertEquals(0, spacer.next());
        assertThrows(IndexOutOfBoundsException.class, () -> spacer.getValue(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> spacer.getValue(3));
    }

    @Test
    void iteratorIsIndependentAndExhaustionThrows() {
        Spacer<Integer> spacer = new Spacer<>(0, 2, 2);
        Iterator<Integer> first = spacer.iterator();
        Iterator<Integer> second = spacer.iterator();

        assertNotSame(first, second);
        assertEquals(0, first.next());
        assertEquals(0, second.next());
        assertEquals(1, first.next());
        assertEquals(2, first.next());
        assertFalse(first.hasNext());
        assertThrows(NoSuchElementException.class, first::next);
        assertTrue(second.hasNext());
    }

    @Test
    void rejectsInvalidConstructionAndUnsupportedNumberTypes() {
        assertThrows(NullPointerException.class, () -> new Spacer<Integer>(null, 1, 1));
        assertThrows(NullPointerException.class, () -> new Spacer<Integer>(0, null, 1));
        assertThrows(IllegalArgumentException.class, () -> new Spacer<>(0, 1, 0));
        assertThrows(IllegalArgumentException.class, () -> new Spacer<>(0, 1, -1));
        assertThrows(IllegalArgumentException.class, () -> new Spacer<Number>(0, 1L, 1));

        Spacer<BigDecimal> unsupported = new Spacer<>(BigDecimal.ZERO, BigDecimal.TEN, 2);
        assertThrows(UnsupportedOperationException.class, () -> unsupported.getValue(1));
    }

    private static <T extends Number> List<T> values(Spacer<T> spacer) {
        List<T> values = new ArrayList<>();
        for (T value : spacer) {
            values.add(value);
        }
        return values;
    }
}
