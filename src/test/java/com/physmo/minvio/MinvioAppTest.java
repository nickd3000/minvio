package com.physmo.minvio;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MinvioAppTest {

    @Test
    @Timeout(2)
    void applicationStopsAfterFiveDrawCalls() {
        AtomicInteger drawCalls = new AtomicInteger();
        MinvioApp app = new MinvioApp() {
            @Override
            public void draw(double delta) {
                if (drawCalls.incrementAndGet() == 5) {
                    stop();
                }
            }
        };

        assertDoesNotThrow(() -> app.start(200, 200, "Simple Example", 60));

        assertEquals(5, drawCalls.get());
        assertNotNull(app.getDrawingContext());
    }

    @Test
    @Timeout(2)
    void drawingOverloadsWorkThroughContextAndApplication() {
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

        assertDoesNotThrow(() -> app.start(200, 200, "Simple Example", 60));

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
}
