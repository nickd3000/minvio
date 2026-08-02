package com.physmo.minvio.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class BasicGraphTest {

    @Test
    void rejectsNonPositivePointCounts() {
        assertThrows(IllegalArgumentException.class, () -> new BasicGraph(0));
        assertThrows(IllegalArgumentException.class, () -> new BasicGraph(-1));
    }
}
