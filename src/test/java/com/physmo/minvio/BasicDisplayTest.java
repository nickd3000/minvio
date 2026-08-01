package com.physmo.minvio;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BasicDisplayTest {

    @TempDir
    Path tempDir;

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

    @Test
    void trySaveScreenshotReturnsTrueAndWritesPng() throws IOException {
        TestBasicDisplay display = new TestBasicDisplay(4, 5);
        Path outputPath = tempDir.resolve("shot.png");

        assertTrue(display.trySaveScreenshot(outputPath));
        assertTrue(Files.exists(outputPath));

        BufferedImage image = ImageIO.read(outputPath.toFile());
        assertNotNull(image);
        assertEquals(4, image.getWidth());
        assertEquals(5, image.getHeight());
    }

    @Test
    void saveScreenshotPathThrowsAndTryReturnsFalseWhenWriteFails() {
        TestBasicDisplay display = new TestBasicDisplay(4, 5);
        Path outputPath = tempDir.resolve("missing").resolve("shot.png");

        assertFalse(display.trySaveScreenshot(outputPath));
        assertThrows(IOException.class, () -> display.saveScreenshot(outputPath));
    }

    @Test
    void awtPanelInputSnapshotsDoNotExposeLiveKeyState() {
        BasicDisplayAwt.BPanel panel = new BasicDisplayAwt.BPanel(10, 10, new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB));
        panel.addMouseConnectors(List.of());

        panel.keyPressed(new KeyEvent(panel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_A, 'A'));

        int[] keySnapshot = panel.getKeyDownSnapshot();
        assertEquals(1, keySnapshot[KeyEvent.VK_A]);

        keySnapshot[KeyEvent.VK_A] = 0;

        assertEquals(1, panel.getKeyDownSnapshot()[KeyEvent.VK_A]);
        panel.tickInput();
        assertEquals(1, panel.getKeyDownPreviousSnapshot()[KeyEvent.VK_A]);
    }

    @Test
    void awtPanelMouseButtonsUseSynchronizedState() {
        BasicDisplayAwt.BPanel panel = new BasicDisplayAwt.BPanel(10, 10, new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB));
        panel.addMouseConnectors(List.of());

        panel.mousePressed(new MouseEvent(panel, MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(), 0, 2, 3, 1, false, MouseEvent.BUTTON1));

        assertTrue(panel.isMouseButtonPressed(MouseEvent.BUTTON1));

        panel.mouseReleased(new MouseEvent(panel, MouseEvent.MOUSE_RELEASED, System.currentTimeMillis(), 0, 2, 3, 1, false, MouseEvent.BUTTON1));

        assertFalse(panel.isMouseButtonPressed(MouseEvent.BUTTON1));
    }
}
