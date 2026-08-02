package com.physmo.minvio.utils.gui;

import com.physmo.minvio.TestBasicDisplay;
import com.physmo.minvio.types.Rect;
import com.physmo.minvio.utils.gui.support.GuiMessage;
import com.physmo.minvio.utils.gui.support.GuiStyle;
import com.physmo.minvio.utils.gui.support.MouseMessageData;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GuiContextTest {

    @Test
    void locatesNestedContainersAndHitTestsInclusiveEdges() {
        TestBasicDisplay display = new TestBasicDisplay(200, 200);
        GuiContext context = new GuiContext(display);
        GuiContainerTest.RecordingContainer root = container(new Rect(10, 20, 100, 100));
        GuiContainerTest.RecordingContainer child = container(new Rect(5, 6, 20, 30));
        root.add(child);
        context.add(root);

        context.locateAll();

        assertEquals(List.of(root, child), List.copyOf(context.allChildren));
        assertEquals(List.of(root, child), context.getListOfContainersAtPoint(15, 26));
        assertEquals(List.of(root, child), context.getListOfContainersAtPoint(35, 56));
        assertEquals(List.of(), context.getListOfContainersAtPoint(111, 121));
    }

    @Test
    void mouseEventsUseLocalCoordinatesAndReleaseOriginalTargets() {
        TestBasicDisplay display = new TestBasicDisplay(200, 200);
        GuiContext context = new GuiContext(display);
        GuiContainerTest.RecordingContainer root = container(new Rect(10, 20, 100, 100));
        GuiContainerTest.RecordingContainer child = container(new Rect(5, 6, 20, 30));
        root.add(child);
        context.add(root);
        context.locateAll();

        display.fireMouseMoved(18, 30);
        display.fireButtonDown(18, 30, 2);
        display.fireButtonUp(190, 190, 2);

        assertEquals(List.of(
                GuiMessage.MOUSE_MOVE,
                GuiMessage.MOUSE_BUTTON_DOWN,
                GuiMessage.MOUSE_BUTTON_UP), root.messages);
        assertMouseData(root.messageData.get(0), 8, 10, 0);
        assertMouseData(root.messageData.get(1), 8, 10, 2);
        assertMouseData(root.messageData.get(2), 180, 170, 2);

        assertEquals(root.messages, child.messages);
        assertMouseData(child.messageData.get(0), 3, 4, 0);
        assertMouseData(child.messageData.get(1), 3, 4, 2);
        assertMouseData(child.messageData.get(2), 175, 164, 2);
    }

    @Test
    void tickProcessesDirtyContainersAndStyleCanBeReplaced() {
        TestBasicDisplay display = new TestBasicDisplay(100, 100);
        GuiContext context = new GuiContext(display);
        GuiContainerTest.RecordingContainer container = container(new Rect(0, 0, 20, 20));
        context.add(container);
        GuiStyle style = new FixedStyle();

        context.setGuiStyle(style);
        context.tick();
        context.tick();

        assertSame(style, context.getGuiStyle());
        assertEquals(1, container.drawCount);
    }

    @Test
    void addRefreshesHitTestListImmediately() {
        TestBasicDisplay display = new TestBasicDisplay(100, 100);
        GuiContext context = new GuiContext(display);
        GuiContainerTest.RecordingContainer container = container(new Rect(10, 10, 20, 20));

        context.add(container);

        assertEquals(List.of(container), context.getListOfContainersAtPoint(15, 15));
    }

    @Test
    void rejectsNullCoreObjects() {
        TestBasicDisplay display = new TestBasicDisplay(100, 100);
        GuiContext context = new GuiContext(display);

        assertThrows(NullPointerException.class, () -> new GuiContext(null));
        assertThrows(NullPointerException.class, () -> context.setGuiStyle(null));
        assertThrows(NullPointerException.class, () -> context.add(null));
    }

    private static GuiContainerTest.RecordingContainer container(Rect rect) {
        return new GuiContainerTest.RecordingContainer(rect);
    }

    private static void assertMouseData(Object object, int x, int y, int button) {
        MouseMessageData data = (MouseMessageData) object;
        assertEquals(x, data.x);
        assertEquals(y, data.y);
        assertEquals(button, data.button);
    }

    private static final class FixedStyle implements GuiStyle {
        @Override
        public Color getBackgroundColor() {
            return Color.BLACK;
        }

        @Override
        public Color getButtonColor() {
            return Color.BLACK;
        }

        @Override
        public Color getBevelLight() {
            return Color.WHITE;
        }

        @Override
        public Color getBevelDark() {
            return Color.DARK_GRAY;
        }

        @Override
        public Color getAccent() {
            return Color.BLUE;
        }

        @Override
        public Color getTextColor() {
            return Color.WHITE;
        }
    }
}
