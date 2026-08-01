package com.physmo.minvio;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BasicDisplayTest {

    @Test
    void loadImageReportsMissingResourceName() {
        IOException exception = assertThrows(
                IOException.class,
                () -> BasicDisplay.loadImage("/missing-minvio-resource.png"));

        assertTrue(exception.getMessage().contains("/missing-minvio-resource.png"));
    }

    @Test
    void awtSetDisplaySizeKeepsReportedSizeAndDrawingContextInSync() {
        BasicDisplayAwt display = new BasicDisplayAwt(20, 30);

        display.setDisplaySize(40, 50);

        assertEquals(40, display.getWidth());
        assertEquals(50, display.getHeight());
        assertEquals(40, display.getDrawingContext().getWidth());
        assertEquals(50, display.getDrawingContext().getHeight());

        BufferedImage drawBuffer = (BufferedImage) display.getDrawBuffer();
        assertEquals(40, drawBuffer.getWidth());
        assertEquals(50, drawBuffer.getHeight());
    }

    @Test
    void awtSetDisplaySizeRejectsNonPositiveDimensions() {
        BasicDisplayAwt display = new BasicDisplayAwt(20, 30);

        assertThrows(IllegalArgumentException.class, () -> display.setDisplaySize(0, 30));
        assertThrows(IllegalArgumentException.class, () -> display.setDisplaySize(20, 0));
    }
}
