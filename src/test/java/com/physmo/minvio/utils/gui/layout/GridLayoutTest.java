package com.physmo.minvio.utils.gui.layout;

import com.physmo.minvio.types.Rect;
import com.physmo.minvio.utils.gui.GuiContainer;
import com.physmo.minvio.utils.gui.GuiContext;
import com.physmo.minvio.utils.gui.support.GuiMessage;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GridLayoutTest {

    @Test
    void laysOutChildrenInConfiguredGridWithPadding() {
        TestContainer parent = new TestContainer(new Rect(0, 0, 100, 80));
        List<GuiContainer> children = containers(4);

        new GridLayout(2, 2).handleLayout(parent, children);

        assertEquals(new Rect(5, 5, 40, 30), children.get(0).getRect());
        assertEquals(new Rect(55, 5, 40, 30), children.get(1).getRect());
        assertEquals(new Rect(5, 45, 40, 30), children.get(2).getRect());
        assertEquals(new Rect(55, 45, 40, 30), children.get(3).getRect());
    }

    @Test
    void expandsRowsAndColumnsToFitChildren() {
        TestContainer parent = new TestContainer(new Rect(0, 0, 120, 90));
        List<GuiContainer> children = containers(7);

        new GridLayout(2, 2).handleLayout(parent, children);

        assertEquals(new Rect(5, 5, 50, 12), children.get(0).getRect());
        assertEquals(new Rect(5, 71, 50, 12), children.get(6).getRect());
    }

    @Test
    void invokesNestedChildLayoutAndHandlesNoChildren() {
        TestContainer parent = new TestContainer(new Rect(0, 0, 100, 100));
        TestContainer child = new TestContainer(new Rect(0, 0, 10, 10));
        AtomicInteger nestedCalls = new AtomicInteger();
        child.setLayout((ignoredParent, ignoredChildren) -> nestedCalls.incrementAndGet());

        GridLayout layout = new GridLayout();
        layout.handleLayout(parent, List.of());
        layout.handleLayout(parent, List.of(child));

        assertEquals(1, nestedCalls.get());
    }

    @Test
    void verySmallParentsStillProduceValidChildDimensions() {
        TestContainer parent = new TestContainer(new Rect(0, 0, 2, 2));
        TestContainer child = new TestContainer(new Rect(0, 0, 10, 10));

        new GridLayout(1, 1).handleLayout(parent, List.of(child));

        assertEquals(new Rect(5, 5, 1, 1), child.getRect());
    }

    @Test
    void rejectsInvalidGridDimensions() {
        assertThrows(IllegalArgumentException.class, () -> new GridLayout(0, 1));
        assertThrows(IllegalArgumentException.class, () -> new GridLayout(1, 0));
        assertThrows(IllegalArgumentException.class, () -> new GridLayout(-1, 2));
    }

    private static List<GuiContainer> containers(int count) {
        List<GuiContainer> containers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            containers.add(new TestContainer(new Rect(0, 0, 10, 10)));
        }
        return containers;
    }

    private static final class TestContainer extends GuiContainer {
        private TestContainer(Rect rect) {
            super(rect);
        }

        @Override
        public void draw(GuiContext guiContext) {
        }

        @Override
        public void onMessage(GuiMessage guiMessage, Object object) {
        }
    }
}
