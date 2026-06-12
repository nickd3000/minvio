package com.physmo.minvio.utils.gui;

import com.physmo.minvio.DrawingContext;
import com.physmo.minvio.types.PointInt;
import com.physmo.minvio.types.Rect;
import com.physmo.minvio.utils.gui.layout.Layout;
import com.physmo.minvio.utils.gui.support.GuiMessage;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GuiContainerTest {

    @Test
    void constructionAndSetRectDefensivelyCopyValues() {
        Rect source = new Rect(1, 2, 30, 40);
        RecordingContainer container = new RecordingContainer(source);
        source.set(9, 9, 9, 9);

        assertEquals(new Rect(1, 2, 30, 40), container.getRect());
        DrawingContext originalContext = container.getDc();

        container.setRect(new Rect(1, 2, 30, 40));
        assertSame(originalContext, container.getDc());

        container.setRect(new Rect(2, 3, 30, 40));
        assertNotSame(originalContext, container.getDc());
        assertEquals(30, container.getDc().getWidth());
        assertEquals(40, container.getDc().getHeight());
    }

    @Test
    void rejectsInvalidRectangles() {
        assertThrows(NullPointerException.class, () -> new RecordingContainer(null));
        assertThrows(IllegalArgumentException.class,
                () -> new RecordingContainer(new Rect(0, 0, 0, 10)));
        assertThrows(IllegalArgumentException.class,
                () -> new RecordingContainer(new Rect(0, 0, 10, -1)));
    }

    @Test
    void addAssignsParentWithoutDuplicatesAndRejectsReparenting() {
        RecordingContainer firstParent = containerAt(1, 1);
        RecordingContainer secondParent = containerAt(2, 2);
        RecordingContainer child = containerAt(3, 3);

        firstParent.add(child);
        firstParent.add(child);

        assertSame(firstParent, child.parent);
        assertEquals(1, firstParent.children.size());
        assertThrows(IllegalArgumentException.class, () -> secondParent.add(child));
        assertThrows(NullPointerException.class, () -> firstParent.add(null));
    }

    @Test
    void inheritedPositionTraversesEveryParent() {
        RecordingContainer root = containerAt(10, 20);
        RecordingContainer child = containerAt(3, 4);
        RecordingContainer grandchild = containerAt(5, 6);
        RecordingContainer greatGrandchild = containerAt(7, 8);
        root.add(child);
        child.add(grandchild);
        grandchild.add(greatGrandchild);

        PointInt position = greatGrandchild.getInheritedPosition();

        assertEquals(25, position.x);
        assertEquals(38, position.y);
    }

    @Test
    void recursiveLocateUsesPreorderAndDirtyStatePropagates() {
        RecordingContainer root = containerAt(0, 0);
        RecordingContainer child = containerAt(0, 0);
        RecordingContainer grandchild = containerAt(0, 0);
        root.add(child);
        child.add(grandchild);
        List<GuiContainer> located = new ArrayList<>();

        root.recursiveLocate(located);
        root.setDirtyRecursive(false);

        assertEquals(List.of(root, child, grandchild), located);
        assertFalse(root.getDirty());
        assertFalse(child.getDirty());
        assertFalse(grandchild.getDirty());
    }

    @Test
    void drawIfDirtyRunsOnceUntilMarkedDirtyAgain() {
        RecordingContainer container = containerAt(0, 0);

        container.drawIfDirty(null);
        container.drawIfDirty(null);
        container.setDirty(true);
        container.drawIfDirty(null);

        assertEquals(2, container.drawCount);
        assertFalse(container.getDirty());
    }

    @Test
    void calculateLayoutDelegatesWhenConfigured() {
        RecordingContainer container = containerAt(0, 0);
        AtomicInteger calls = new AtomicInteger();
        Layout layout = (parent, children) -> {
            assertSame(container, parent);
            assertSame(container.children, children);
            calls.incrementAndGet();
        };

        container.calculateLayout();
        container.setLayout(layout);
        container.calculateLayout();

        assertSame(layout, container.getLayout());
        assertEquals(1, calls.get());
    }

    private static RecordingContainer containerAt(int x, int y) {
        return new RecordingContainer(new Rect(x, y, 20, 20));
    }

    static class RecordingContainer extends GuiContainer {
        int drawCount;
        final List<GuiMessage> messages = new ArrayList<>();
        final List<Object> messageData = new ArrayList<>();

        RecordingContainer(Rect rect) {
            super(rect);
        }

        @Override
        public void draw(GuiContext guiContext) {
            drawCount++;
        }

        @Override
        public void onMessage(GuiMessage guiMessage, Object object) {
            messages.add(guiMessage);
            messageData.add(object);
        }
    }
}
