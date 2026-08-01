package com.physmo.minvio;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.io.TempDir;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MinvioAppTest {

    @TempDir
    Path tempDir;

    @Test
    @Timeout(2)
    void applicationStopsAfterFiveDrawCalls() {
        TestBasicDisplay display = new TestBasicDisplay(200, 200);
        AtomicInteger drawCalls = new AtomicInteger();
        MinvioApp app = new MinvioApp() {
            @Override
            public void draw(double delta) {
                if (drawCalls.incrementAndGet() == 5) {
                    stop();
                }
            }
        };

        assertDoesNotThrow(() -> app.start(display, "Simple Example", 60));

        assertEquals(5, drawCalls.get());
        assertNotNull(app.getDrawingContext());
    }

    @Test
    @Timeout(2)
    void drawingOverloadsWorkThroughContextAndApplication() {
        TestBasicDisplay display = new TestBasicDisplay(200, 200);
        AtomicInteger drawCalls = new AtomicInteger();
        MinvioApp app = new MinvioApp() {
            @Override
            public void draw(double delta) {
                DrawingContextTestSupport.exerciseAll(getDrawingContext());
                DrawingContextTestSupport.exerciseAll(this);
                drawCalls.incrementAndGet();
                stop();
            }
        };

        assertDoesNotThrow(() -> app.start(display, "Simple Example", 60));

        assertEquals(1, drawCalls.get());
    }

    @Test
    void rejectsInvalidFpsTargets() {
        MinvioApp app = new MinvioApp();
        TestBasicDisplay display = new TestBasicDisplay(20, 20);

        assertThrows(IllegalArgumentException.class, () -> app.setFpsTarget(0));
        assertThrows(IllegalArgumentException.class, () -> app.setFpsTarget(-1));
        assertThrows(IllegalArgumentException.class, () -> app.start(display, "Invalid", 0));
        assertThrows(IllegalArgumentException.class, () -> display.repaint(0));
    }

    @Test
    @Timeout(2)
    void closesDisplayAndCallsDestroyWhenStopped() {
        TestBasicDisplay display = new TestBasicDisplay(20, 20);
        AtomicInteger destroyCalls = new AtomicInteger();
        MinvioApp app = new MinvioApp() {
            @Override
            public void draw(double delta) {
                stop();
            }

            @Override
            public void destroy(BasicDisplay bd) {
                destroyCalls.incrementAndGet();
            }
        };

        app.start(display);

        assertEquals(1, destroyCalls.get());
        assertFalse(display.isVisible());
    }

    @Test
    @Timeout(2)
    void exitsWhenDisplayIsClosed() {
        TestBasicDisplay display = new TestBasicDisplay(20, 20);
        AtomicInteger drawCalls = new AtomicInteger();
        MinvioApp app = new MinvioApp() {
            @Override
            public void draw(double delta) {
                drawCalls.incrementAndGet();
                display.close();
            }
        };

        app.start(display);

        assertEquals(1, drawCalls.get());
        assertFalse(display.isVisible());
    }

    @Test
    @Timeout(2)
    void ignoresScreenshotHotkeyWhenDisplayKeyArraysAreShorterThanKeyCode() {
        TestBasicDisplay display = new TestBasicDisplay(20, 20, 1);
        AtomicInteger drawCalls = new AtomicInteger();
        MinvioApp app = new MinvioApp() {
            @Override
            public void draw(double delta) {
                drawCalls.incrementAndGet();
                stop();
            }
        };

        assertDoesNotThrow(() -> app.start(display));

        assertEquals(1, drawCalls.get());
    }

    @Test
    @Timeout(2)
    void takeScreenshotAndQuitCapturesRequestedDrawFrame() throws IOException {
        TestBasicDisplay display = new TestBasicDisplay(20, 20);
        Path outputPath = tempDir.resolve("frame-3.png");
        AtomicInteger drawCalls = new AtomicInteger();
        MinvioApp app = new MinvioApp() {
            @Override
            public void draw(double delta) {
                int frame = drawCalls.incrementAndGet();
                cls(new Color(frame, frame + 10, frame + 20));
            }
        };

        assertSame(app, app.takeScreenshotAndQuit(outputPath.toString(), 3));
        app.start(display);

        assertEquals(3, drawCalls.get());
        assertFalse(display.isVisible());
        assertTrue(Files.exists(outputPath));

        BufferedImage screenshot = ImageIO.read(outputPath.toFile());
        assertNotNull(screenshot);
        assertEquals(new Color(3, 13, 23).getRGB(), screenshot.getRGB(0, 0));
    }

    @Test
    void takeScreenshotAndQuitRejectsInvalidArguments() {
        MinvioApp app = new MinvioApp();

        assertThrows(NullPointerException.class, () -> app.takeScreenshotAndQuit(null, 1));
        assertThrows(IllegalArgumentException.class, () -> app.takeScreenshotAndQuit(" ", 1));
        assertThrows(IllegalArgumentException.class, () -> app.takeScreenshotAndQuit("out.png", 0));
    }
}
