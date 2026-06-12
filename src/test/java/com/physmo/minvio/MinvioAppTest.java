package com.physmo.minvio;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
}
