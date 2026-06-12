package com.physmo.minvio.utils;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuickRandomTest {

    @Test
    void fixedSeedsAndReseedingProduceRepeatableSequences() {
        QuickRandom first = new QuickRandom(12345);
        QuickRandom second = new QuickRandom(12345);

        for (int i = 0; i < 20; i++) {
            assertEquals(first.nextDouble(), second.nextDouble());
        }

        first.setSeed(12345);
        second.setSeed(12345);
        assertEquals(first.nextDouble(), second.nextDouble());
    }

    @Test
    void generatedValuesStayInUnitRangeAndVary() {
        QuickRandom random = new QuickRandom(9876);
        Set<Double> values = new HashSet<>();

        for (int i = 0; i < 1_000; i++) {
            double value = random.nextDouble();
            assertTrue(value >= 0.0);
            assertTrue(value < 1.0);
            values.add(value);
        }

        assertTrue(values.size() > 900);
    }
}
