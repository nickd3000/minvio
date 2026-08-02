package com.physmo.reference.gui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IQPaletteTest {

    @Test
    void shorterControlUpdatesClearPreviousTailValues() {
        IQPalette palette = new IQPalette();
        palette.setControls(new double[]{1.0, 2.0, 3.0, 4.0});

        palette.setControls(new double[]{9.0});

        assertEquals(9.0, palette.controls[0]);
        assertEquals(0.0, palette.controls[1]);
        assertEquals(0.0, palette.controls[2]);
        assertEquals(0.0, palette.controls[3]);
    }

    @Test
    void rejectsNullControls() {
        IQPalette palette = new IQPalette();

        assertThrows(NullPointerException.class, () -> palette.setControls(null));
    }
}
