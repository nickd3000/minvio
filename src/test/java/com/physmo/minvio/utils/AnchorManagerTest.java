package com.physmo.minvio.utils;

import com.physmo.minvio.TestBasicDisplay;
import com.physmo.minvio.types.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AnchorManagerTest {
    private static final double DELTA = 1e-10;

    @Test
    void addsAnchorsFindsClosestAndExposesConfiguration() {
        AnchorManager manager = new AnchorManager(5);
        manager.add(10, 10);
        manager.add(20, 20);

        assertEquals(2, manager.getAnchors().size());
        assertEquals(0, manager.findCloseAnchor(11, 11, 5));
        assertEquals(-1, manager.findCloseAnchor(15, 15, 5));
        assertEquals(3.0, manager.getHitBoxMultiplier());

        manager.setHitBoxMultiplier(2.0);
        manager.setConstrainToScreen(false);
        assertEquals(2.0, manager.getHitBoxMultiplier());
        assertFalse(manager.getConstrainToScreen());
    }

    @Test
    void constrainsMouseToDisplayBoundsWhenEnabled() {
        TestBasicDisplay display = new TestBasicDisplay(100, 80);
        AnchorManager manager = new AnchorManager(5);

        display.setMouse(-10, 100);
        Point constrained = manager.constrainMouseToScreen(display);
        assertPoint(constrained, 0, 80);

        manager.setConstrainToScreen(false);
        Point raw = manager.constrainMouseToScreen(display);
        assertPoint(raw, -10, 100);
    }

    @Test
    void pressDragAndReleaseUpdatesAnchorState() {
        TestBasicDisplay display = new TestBasicDisplay(100, 80);
        AnchorManager manager = new AnchorManager(5);
        manager.add(10, 10);

        display.setMouse(10, 10);
        manager.update(display);
        assertEquals(0, manager.mouseOverId);

        display.setLeftButton(true);
        manager.update(display);
        assertTrue(manager.grabActive);
        assertEquals(0, manager.grabbedId);

        display.setMouse(150, -20);
        manager.update(display);
        assertPoint(manager.getAnchors().get(0), 100, 0);

        display.setLeftButton(false);
        manager.update(display);
        assertFalse(manager.grabActive);
    }

    @Test
    void returnsLiveAnchorListAndAcceptsCustomDelegate() {
        AnchorManager manager = new AnchorManager(5);
        AnchorDrawDelegate delegate = (dc, point, radius, mouseOver, grabbed) -> {
        };

        manager.getAnchors().add(new Point(1, 2));
        manager.setAnchorDrawDelegate(delegate);

        assertEquals(1, manager.getAnchors().size());
        assertSame(delegate, manager.anchorDrawDelegate);
    }

    private static void assertPoint(Point point, double x, double y) {
        assertEquals(x, point.x, DELTA);
        assertEquals(y, point.y, DELTA);
    }
}
