package com.physmo.minvio.utils;

import com.physmo.minvio.TestBasicDisplay;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class DrawingHelpersTest {

    @Test
    void drawGridRejectsNonPositiveSegments() {
        TestBasicDisplay display = new TestBasicDisplay(20, 20);

        assertThrows(IllegalArgumentException.class, () -> DrawingHelpers.drawGrid(display, 0, 0, 10, 10, 0));
        assertThrows(IllegalArgumentException.class, () -> DrawingHelpers.drawGrid(display, 0, 0, 10, 10, -1));
    }
}
