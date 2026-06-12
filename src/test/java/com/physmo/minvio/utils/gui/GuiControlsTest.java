package com.physmo.minvio.utils.gui;

import com.physmo.minvio.types.Rect;
import com.physmo.minvio.utils.gui.support.MouseMessageData;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.physmo.minvio.utils.gui.support.GuiMessage.MOUSE_BUTTON_DOWN;
import static com.physmo.minvio.utils.gui.support.GuiMessage.MOUSE_BUTTON_UP;
import static com.physmo.minvio.utils.gui.support.GuiMessage.MOUSE_MOVE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GuiControlsTest {

    @Test
    void buttonTracksPressMovementAndOnlyActivatesWhenReleasedInside() {
        GuiButton button = new GuiButton(new Rect(0, 0, 100, 30), "Run");
        AtomicInteger actions = new AtomicInteger();
        button.addActionListener(actions::incrementAndGet);

        assertTrue(button.isPointInside(0, 0));
        assertTrue(button.isPointInside(100, 30));
        assertFalse(button.isPointInside(-1, 0));
        assertFalse(button.isPointInside(101, 30));

        button.onMessage(MOUSE_BUTTON_DOWN, mouse(10, 10));
        assertTrue(button.buttonActivated);
        assertTrue(button.visiblyPressed);

        button.onMessage(MOUSE_MOVE, mouse(110, 10));
        assertFalse(button.visiblyPressed);
        button.onMessage(MOUSE_BUTTON_UP, mouse(110, 10));
        assertEquals(0, actions.get());
        assertFalse(button.buttonActivated);

        button.onMessage(MOUSE_BUTTON_DOWN, mouse(10, 10));
        button.onMessage(MOUSE_MOVE, mouse(20, 10));
        button.onMessage(MOUSE_BUTTON_UP, mouse(20, 10));
        assertEquals(1, actions.get());
        assertFalse(button.visiblyPressed);
        assertTrue(button.getDirty());
    }

    @Test
    void buttonListenersRunInRegistrationOrder() {
        GuiButton button = new GuiButton(new Rect(0, 0, 100, 30));
        List<String> calls = new ArrayList<>();
        button.addActionListener(() -> calls.add("first"));
        button.addActionListener(() -> calls.add("second"));

        button.onMessage(MOUSE_BUTTON_DOWN, mouse(1, 1));
        button.onMessage(MOUSE_BUTTON_UP, mouse(1, 1));

        assertEquals(List.of("first", "second"), calls);
    }

    @Test
    void sliderClampsValuesAndDistinguishesSilentAndNotifyingSetters() {
        GuiSlider slider = new GuiSlider(new Rect(0, 0, 100, 20));
        List<Double> changes = new ArrayList<>();
        slider.addChangeListener(changes::add);

        slider.setValue(2.0);
        assertEquals(1.0, slider.getValue());
        assertEquals(List.of(), changes);

        slider.setValueAndNotify(-1.0);
        slider.setValueAndNotify(0.5);
        slider.setValueAndNotify(0.5);

        assertEquals(0.5, slider.getValue());
        assertEquals(List.of(0.0, 0.5), changes);
        assertTrue(slider.getDirty());
    }

    @Test
    void sliderHitTestingAndDraggingRespectGrabOffsetAndBounds() {
        GuiSlider slider = new GuiSlider(new Rect(0, 0, 100, 20));
        slider.setValue(0.5);
        int handleX = 50;

        assertTrue(slider.isMouseOverHandle(handleX, 10));
        assertFalse(slider.isMouseOverHandle(handleX, 18));
        assertFalse(slider.isMouseOverHandle(handleX + 10, 10));

        slider.onMessage(MOUSE_MOVE, mouse(handleX + 2, 10));
        slider.onMessage(MOUSE_BUTTON_DOWN, mouse(handleX + 2, 10));
        assertTrue(slider.grabbed);
        slider.onMessage(MOUSE_MOVE, mouse(500, 10));
        assertEquals(1.0, slider.getValue());
        slider.onMessage(MOUSE_MOVE, mouse(-500, 10));
        assertEquals(0.0, slider.getValue());
        slider.onMessage(MOUSE_BUTTON_UP, mouse(-500, 10));
        assertFalse(slider.grabbed);
    }

    @Test
    void zeroLengthSliderTrackRemainsAtZero() {
        GuiSlider slider = new GuiSlider(new Rect(0, 0, 20, 20));
        slider.setHandleSize(20);
        slider.setValue(0.5);

        slider.onMessage(MOUSE_MOVE, mouse(20, 10));
        slider.onMessage(MOUSE_BUTTON_DOWN, mouse(20, 10));
        slider.onMessage(MOUSE_MOVE, mouse(100, 10));

        assertEquals(0.0, slider.getValue());
    }

    @Test
    void labelStateChangesMarkItDirty() {
        GuiLabel label = new GuiLabel(new Rect(0, 0, 100, 20), "Old");
        label.setDirty(false);

        label.setShowBorder(true);
        assertTrue(label.isShowBorder());
        assertTrue(label.getDirty());

        label.setDirty(false);
        label.setText("New");
        assertTrue(label.getDirty());
    }

    private static MouseMessageData mouse(int x, int y) {
        return new MouseMessageData(x, y, 1);
    }
}
